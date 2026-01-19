package com.example.pta_counseling_assistant.repository;

import com.example.pta_counseling_assistant.model.DispenseEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DispenseEventRepository extends JpaRepository<DispenseEvent, Long> {

    List<DispenseEvent> findByPrescriptionPatientIdOrderByDispensedAtDesc(Long patientId);
}
