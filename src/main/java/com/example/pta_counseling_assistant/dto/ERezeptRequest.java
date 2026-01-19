package com.example.pta_counseling_assistant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class ERezeptRequest {

    @NotBlank
    private String patientInsuranceNumber;

    @NotEmpty
    private List<ERezeptMedicationItem> medications;

    public String getPatientInsuranceNumber() {
        return patientInsuranceNumber;
    }

    public void setPatientInsuranceNumber(String patientInsuranceNumber) {
        this.patientInsuranceNumber = patientInsuranceNumber;

    }

    public List<ERezeptMedicationItem> getMedications() {
        return medications;
    }

    public void setMedications(List<ERezeptMedicationItem> medications) {
        this.medications = medications;
    }
}
