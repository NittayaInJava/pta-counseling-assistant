DROP TABLE IF EXISTS dispense_events;
DROP TABLE IF EXISTS prescriptions;
DROP TABLE IF EXISTS medications;
DROP TABLE IF EXISTS patients;

CREATE TABLE patients (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    insurance_number VARCHAR(32) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(10),
    health_insurance VARCHAR(255)
);

CREATE TABLE medications (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pzn VARCHAR(32) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    active_ingredient VARCHAR(255) NOT NULL,
    strength VARCHAR(50),
    form VARCHAR(50),
    atc_code VARCHAR(50),
    standard_advice TEXT
);

CREATE TABLE prescriptions (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    medication_id BIGINT NOT NULL,
    prescribed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    dosage_text VARCHAR(255),
    source VARCHAR(50),

    CONSTRAINT fk_prescriptions_patient
        FOREIGN KEY (patient_id)
        REFERENCES patients (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_prescriptions_medication
        FOREIGN KEY (medication_id)
        REFERENCES medications (id)
        ON DELETE RESTRICT
);

CREATE TABLE dispense_events (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    prescription_id BIGINT NOT NULL,
    dispensed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    pharmacy_name VARCHAR(255),
    pta_name VARCHAR(255),

    CONSTRAINT fk_dispense_prescription
        FOREIGN KEY (prescription_id)
        REFERENCES prescriptions (id)
        ON DELETE CASCADE
);
