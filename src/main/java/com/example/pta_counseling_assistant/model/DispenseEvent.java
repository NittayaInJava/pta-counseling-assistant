package com.example.pta_counseling_assistant.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "dispense_events")
public class DispenseEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @Column(name = "dispensed_at", nullable = false)
    private LocalDateTime dispensedAt;

    @Column(name = "pharmacy_name", length = 255)
    private String pharmacyName;

    @Column(name = "pta_name", length = 255)
    private String ptaName;

    // Getter/Setter

    public Long getId() {
        return id;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public LocalDateTime getDispensedAt() {
        return dispensedAt;
    }

    public void setDispensedAt(LocalDateTime dispensedAt) {
        this.dispensedAt = dispensedAt;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPtaName() {
        return ptaName;
    }

    public void setPtaName(String ptaName) {
        this.ptaName = ptaName;
    }
}
