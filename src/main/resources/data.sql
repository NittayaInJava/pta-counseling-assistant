
-- =========================
-- Patienten
-- =========================
INSERT INTO patients (insurance_number, first_name, last_name, date_of_birth, gender, health_insurance)
VALUES
('A123456789', 'Max', 'Mustermann', DATE '1957-03-12', 'männlich', 'MusterKasse'),
('B987654321', 'Anna', 'Beispiel',   DATE '1990-08-25', 'weiblich', 'BeispielKasse');

-- =========================
-- Medikamente
-- =========================
INSERT INTO medications (pzn, name, active_ingredient, strength, form, atc_code, standard_advice)
VALUES
('11111111', 'Ibuprofen 600 mg Tabletten', 'Ibuprofen', '600 mg', 'Tabletten', 'M01AE01',
 'Mit reichlich Flüssigkeit nach dem Essen einnehmen. Nicht auf nüchternen Magen.'),
('22222222', 'ASS 100 mg Tabletten', 'Acetylsalicylsäure', '100 mg', 'Tabletten', 'B01AC06',
 'Täglich zur gleichen Zeit einnehmen. Nicht mit weiteren NSAR kombinieren ohne ärztlichen Rat.'),
('33333333', 'Pantoprazol 20 mg Tabletten', 'Pantoprazol', '20 mg', 'Tabletten', 'A02BC02',
 'Morgens nüchtern, mindestens 30 Minuten vor dem Frühstück mit Wasser einnehmen.');

-- =========================
-- Verordnungen (ohne harte IDs)
-- =========================
INSERT INTO prescriptions (patient_id, medication_id, prescribed_at, dosage_text, source)
SELECT p.id, m.id, TIMESTAMP '2025-11-01 10:00:00', '1-0-0', 'EREZEPT'
FROM patients p, medications m
WHERE p.insurance_number = 'A123456789' AND m.pzn = '22222222';

INSERT INTO prescriptions (patient_id, medication_id, prescribed_at, dosage_text, source)
SELECT p.id, m.id, TIMESTAMP '2025-12-20 15:30:00', '1-0-1', 'EREZEPT'
FROM patients p, medications m
WHERE p.insurance_number = 'A123456789' AND m.pzn = '11111111';

INSERT INTO prescriptions (patient_id, medication_id, prescribed_at, dosage_text, source)
SELECT p.id, m.id, TIMESTAMP '2025-11-15 09:00:00', '1-0-0', 'PAPIER'
FROM patients p, medications m
WHERE p.insurance_number = 'B987654321' AND m.pzn = '33333333';

-- =========================
-- Abgaben (ohne harte prescription_id)
-- =========================
INSERT INTO dispense_events (prescription_id, dispensed_at, pharmacy_name, pta_name)
SELECT pr.id, TIMESTAMP '2025-11-01 10:05:00', 'Apotheke am Markt', 'Lisa PTA'
FROM prescriptions pr
JOIN patients p ON p.id = pr.patient_id
JOIN medications m ON m.id = pr.medication_id
WHERE p.insurance_number = 'A123456789' AND m.pzn = '22222222';

INSERT INTO dispense_events (prescription_id, dispensed_at, pharmacy_name, pta_name)
SELECT pr.id, TIMESTAMP '2025-11-15 09:10:00', 'Apotheke am Markt', 'Lisa PTA'
FROM prescriptions pr
JOIN patients p ON p.id = pr.patient_id
JOIN medications m ON m.id = pr.medication_id
WHERE p.insurance_number = 'B987654321' AND m.pzn = '33333333';
