package com.example.pta_counseling_assistant.repository;

import com.example.pta_counseling_assistant.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    Optional<Medication> findByPzn(String pzn);
}
