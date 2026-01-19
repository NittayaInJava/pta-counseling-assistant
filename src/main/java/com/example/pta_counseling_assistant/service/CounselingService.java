package com.example.pta_counseling_assistant.service;

import com.example.pta_counseling_assistant.dto.CounselingPlanResponse;
import com.example.pta_counseling_assistant.dto.ERezeptMedicationItem;
import com.example.pta_counseling_assistant.dto.ERezeptRequest;
import com.example.pta_counseling_assistant.model.DispenseEvent;
import com.example.pta_counseling_assistant.model.Medication;
import com.example.pta_counseling_assistant.model.Patient;
import com.example.pta_counseling_assistant.repository.DispenseEventRepository;
import com.example.pta_counseling_assistant.repository.MedicationRepository;
import com.example.pta_counseling_assistant.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CounselingService {

    private final PatientRepository patientRepository;
    private final MedicationRepository medicationRepository;
    private final DispenseEventRepository dispenseEventRepository;

    public CounselingService(PatientRepository patientRepository,
                             MedicationRepository medicationRepository,
                             DispenseEventRepository dispenseEventRepository) {
        this.patientRepository = patientRepository;
        this.medicationRepository = medicationRepository;
        this.dispenseEventRepository = dispenseEventRepository;
    }

    public CounselingPlanResponse generatePlan(ERezeptRequest request) {
        Patient patient = patientRepository.findByInsuranceNumber(request.getPatientInsuranceNumber())
                .orElseThrow(() -> new NoSuchElementException(("Patient not found or insurance number: "+ request.getPatientInsuranceNumber())));

        int age = calculateAge(patient.getDateOfBirth());

        // Historie (Abgaben) laden
        List<DispenseEvent> historyEvents =
                dispenseEventRepository.findByPrescriptionPatientIdOrderByDispensedAtDesc(patient.getId());


        // Vorherige Medikation als Liste (Namen) + Wirkstoffe als Set
        List<String> previousMedicationNames = historyEvents.stream()
                .map(ev -> ev.getPrescription().getMedication().getName())
                .distinct()
                .limit(10)
                .toList();

        Set<String> previousActiveIngredients = historyEvents.stream()
                .map(ev -> ev.getPrescription().getMedication().getActiveIngredient())
                .filter(Objects::nonNull)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        String lastDispenseAt = historyEvents.stream()
                .findFirst()
                .map(ev -> ev.getDispensedAt().toString())
                .orElse(null);

        // Aktuelle E-Rezept-Positionen → MedicationSummary
        List<CounselingPlanResponse.MedicationSummary> currentMeds = new ArrayList<>();
        for (ERezeptMedicationItem item : request.getMedications()) {
            Medication med = medicationRepository.findByPzn(item.getPzn())
                    .orElseThrow(() -> new NoSuchElementException("Medication not found for PZN: " + item.getPzn()));

            CounselingPlanResponse.MedicationSummary ms = new CounselingPlanResponse.MedicationSummary();
            ms.setPzn(med.getPzn());
            ms.setName(med.getName());
            ms.setActiveIngredient(med.getActiveIngredient());
            ms.setDosageText(item.getDosageText());
            ms.setStandardAdvice(med.getStandardAdvice());
            currentMeds.add(ms);
        }

        // Alerts erzeugen (erstmal wenige, aber nachvollziehbare Regeln)
        List<CounselingPlanResponse.AlertItem> alerts = new ArrayList<>();

        if (age >= 65) {
            alerts.add(alert("AGE", "MEDIUM", "Senior (" + age + "): auf altersabhängige Risiken/Dosierung achten."));
        }

        // Beispiel-Interaktionsregel: NSAR (z.B. Ibuprofen) + ASS in Historie
        // (Demo-Regel, nicht vollständige klinische Beratung)
        boolean currentHasIbuprofen = currentMeds.stream()
                .map(CounselingPlanResponse.MedicationSummary::getActiveIngredient)
                .filter(Objects::nonNull)
                .map(String::toLowerCase)
                .anyMatch(ai -> ai.contains("ibuprofen"));

        boolean historyHasAss = previousActiveIngredients.stream()
                .anyMatch(ai -> ai.contains("acetylsalicyl") || ai.equals("ass"));

        if (currentHasIbuprofen && historyHasAss) {
            alerts.add(alert("INTERACTION", "HIGH",
                    "Hinweis: NSAR (z.B. Ibuprofen) + ASS in der Vorgeschichte kann das Blutungsrisiko erhöhen. Beratung/Arztkontakt je nach Situation erwägen."));
        }

        // Checkliste (standardisiert)
        List<String> checklist = List.of(
                "Einnahmezeitpunkte erklärt?",
                "Selbstmedikation (weitere Schmerzmittel / OTC) abgefragt?",
                "Warnhinweise (Magen, Niere, Blutungen je nach Medikament) angesprochen?",
                "Lagerung und Anwendung erklärt?"
        );

        // Response zusammenbauen
        CounselingPlanResponse response = new CounselingPlanResponse();

        CounselingPlanResponse.PatientSummary ps = new CounselingPlanResponse.PatientSummary();
        ps.setFullName(patient.getFirstName() + " " + patient.getLastName());
        ps.setAge(age);
        ps.setHealthInsurance(patient.getHealthInsurance());

        CounselingPlanResponse.HistorySummary hs = new CounselingPlanResponse.HistorySummary();
        hs.setPreviousMedications(previousMedicationNames);
        hs.setLastDispenseAt(lastDispenseAt);

        response.setPatient(ps);
        response.setCurrentMedications(currentMeds);
        response.setHistorySummary(hs);
        response.setAlerts(alerts);
        response.setChecklist(checklist);

        return response;
    }

    private static int calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) return 0;
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    private static CounselingPlanResponse.AlertItem alert(String type, String severity, String message) {
        CounselingPlanResponse.AlertItem a = new CounselingPlanResponse.AlertItem();
        a.setType(type);
        a.setSeverity(severity);
        a.setMessage(message);
        return a;
    }
    }

