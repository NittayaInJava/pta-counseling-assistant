package com.example.pta_counseling_assistant.dto;

import jakarta.validation.constraints.NotBlank;

public class ERezeptMedicationItem {

    @NotBlank
    private String pzn;
    private String dosageText;

    public String getPzn() {
        return pzn;
    }

    public void setPzn(String pzn) {
        this.pzn = pzn;
    }

    public String getDosageText() {
        return dosageText;
    }

    public void setDosageText(String dosageText) {
        this.dosageText = dosageText;
    }


}
