package com.example.pta_counseling_assistant.repository;

import com.example.pta_counseling_assistant.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByInsuranceNumber(String insuranceNumber);
}
