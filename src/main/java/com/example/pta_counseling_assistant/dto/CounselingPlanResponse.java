package com.example.pta_counseling_assistant.dto;

import java.util.List;

public class CounselingPlanResponse {

    private PatientSummary patient;
    private List<MedicationSummary> currentMedications;
    private HistorySummary historySummary;
    private  List<AlertItem> alerts;
    private List<String> checklist;

    public PatientSummary getPatient() {
        return patient;
    }

    public void setPatient(PatientSummary patient) {
        this.patient = patient;
    }

    public List<MedicationSummary> getCurrentMedications() {
        return currentMedications;
    }

    public void setCurrentMedications(List<MedicationSummary> currentMedications) {
        this.currentMedications = currentMedications;
    }

    public HistorySummary getHistorySummary() {
        return historySummary;
    }

    public void setHistorySummary(HistorySummary historySummary) {
        this.historySummary = historySummary;
    }

    public List<AlertItem> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<AlertItem> alerts) {
        this.alerts = alerts;
    }

    public List<String> getChecklist() {
        return checklist;
    }

    public void setChecklist(List<String> checklist) {
        this.checklist = checklist;
    }

    // Innere DTOs

    public static class PatientSummary {
        private String fullName;
        private int age;
        private String healthInsurance;

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getHealthInsurance() {
            return healthInsurance;
        }

        public void setHealthInsurance(String healthInsurance) {
            this.healthInsurance = healthInsurance;
        }
    }

        public static class MedicationSummary {
            private String pzn;
            private String name;
            private String activeIngredient;
            private String dosageText;
            private String standardAdvice;

            public String getPzn() { return pzn; }
            public void setPzn(String pzn) { this.pzn = pzn; }

            public String getName() { return name; }
            public void setName(String name) { this.name = name; }

            public String getActiveIngredient() { return activeIngredient; }
            public void setActiveIngredient(String activeIngredient) { this.activeIngredient = activeIngredient; }

            public String getDosageText() { return dosageText; }
            public void setDosageText(String dosageText) { this.dosageText = dosageText; }

            public String getStandardAdvice() { return standardAdvice; }
            public void setStandardAdvice(String standardAdvice) { this.standardAdvice = standardAdvice; }
        }

        public static class HistorySummary {
            private List<String> previousMedications; // Namen oder Wirkstoffe
            private String lastDispenseAt;            // ISO-String reicht erstmal

            public List<String> getPreviousMedications() { return previousMedications; }
            public void setPreviousMedications(List<String> previousMedications) { this.previousMedications = previousMedications; }

            public String getLastDispenseAt() { return lastDispenseAt; }
            public void setLastDispenseAt(String lastDispenseAt) { this.lastDispenseAt = lastDispenseAt; }
        }

        public static class AlertItem {
            private String type;      // AGE / INTERACTION / GENERAL
            private String severity;  // LOW / MEDIUM / HIGH
            private String message;

            public String getType() { return type; }
            public void setType(String type) { this.type = type; }

            public String getSeverity() { return severity; }
            public void setSeverity(String severity) { this.severity = severity; }

            public String getMessage() { return message; }
            public void setMessage(String message) { this.message = message; }
        }

    }

