package com.example.pta_counseling_assistant.model;

import jakarta.persistence.*;

@Entity
@Table(name = "medication")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 32)
    private String pzn;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "active_ingredient", nullable = false, length = 255)
    private String activeIngredient;

    @Column(length = 50)
    private String strength;

    @Column(length = 50)
    private String form;

    @Column(name = "atc_code", length = 50)
    private String atcCode;

    @Column(name = "standard_advice", columnDefinition = "text")
    private String standardAdvice;

    //Getter/Setter

    public Long getId() {
        return id;
    }

    public String getPzn() {
        return pzn;
    }

    public void setPzn(String pzn) {
        this.pzn = pzn;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActiveIngredient() {
        return activeIngredient;
    }

    public void setActiveIngredient(String activeIngredient) {
        this.activeIngredient = activeIngredient;
    }

    public String getStrength() {
        return strength;

    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getForm () {
        return form;

    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getAtcCode() {
        return atcCode;
    }

    public void setAtcCode(String atcCode) {
        this.atcCode = atcCode;
    }

    public String getStandardAdvice() {
        return standardAdvice;
    }

    public void setStandardAdvice(String standardAdvice) {
        this.standardAdvice = standardAdvice;
    }
}
