package com.example.pta_counseling_assistant.controller;

import com.example.pta_counseling_assistant.model.Patient;
import com.example.pta_counseling_assistant.model.Prescription;
import com.example.pta_counseling_assistant.repository.PatientRepository;
import com.example.pta_counseling_assistant.repository.PrescriptionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientRepository patientRepository;
    private final PrescriptionRepository prescriptionRepository;

    public PatientController(PatientRepository patientRepository, PrescriptionRepository prescriptionRepository) {
        this.patientRepository = patientRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    // GET /api/patients -> alle Patienten
    @GetMapping
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // GET /api/patients/{id}/presciptions -> Verordnungen eines Patienten
    @GetMapping("/{id}/prescriptions")
    public ResponseEntity<List<Prescription>> getPrescriptionsForPatient(@PathVariable Long id) {
        if (!patientRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        List<Prescription> prescriptions =
                prescriptionRepository.findByPatientIdOrderByPrescribedAtDesc(id);
        return ResponseEntity.ok(prescriptions);
    }
}
