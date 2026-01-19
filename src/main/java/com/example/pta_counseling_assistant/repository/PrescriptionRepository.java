package com.example.pta_counseling_assistant.repository;

import com.example.pta_counseling_assistant.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByPatientIdOrderByPrescribedAtDesc(Long patientId);
}
