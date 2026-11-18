
USE hospital_db;

-- TEST 1: Single-row INSERT
INSERT INTO admission (admission_id, patient_name, ward_id)
VALUES (101, 'Arun', 1);

SELECT * FROM ward;
SELECT * FROM admission;
SELECT * FROM admission_audit;


-- TEST 2: Multi-row INSERT within capacity
INSERT INTO admission (admission_id, patient_name, ward_id)
VALUES
(102, 'Kumar', 1),
(103, 'Ravi', 1);

SELECT * FROM ward;
SELECT * FROM admission;
SELECT * FROM admission_audit;


-- TEST 3: Multi-row INSERT exceeding capacity
-- Expected: Error SQLSTATE 45000
-- The third admission exceeds the available capacity.
-- InnoDB rolls back the entire statement.

INSERT INTO admission (admission_id, patient_name, ward_id)
VALUES
(104, 'Manoj', 1),
(105, 'Suresh', 1);

-- Verify that the failed statement inserted no rows
SELECT * FROM admission WHERE ward_id = 1;

-- Verify occupied beds
SELECT * FROM ward WHERE ward_id = 1;

-- Verify audit history
SELECT * FROM admission_audit;


-- TEST 4: UPDATE Admission
UPDATE admission
SET patient_name = 'Arun Kumar'
WHERE admission_id = 101;

SELECT * FROM admission;
SELECT * FROM admission_audit;


-- TEST 5: Move Patient to Another Ward
UPDATE admission
SET ward_id = 2
WHERE admission_id = 101;

SELECT * FROM ward;
SELECT * FROM admission;
SELECT * FROM admission_audit;


-- TEST 6: DELETE Admission
DELETE FROM admission
WHERE admission_id = 102;

SELECT * FROM ward;
SELECT * FROM admission;
SELECT * FROM admission_audit;


-- TEST 7: List All Triggers
SELECT
    TRIGGER_NAME,
    EVENT_MANIPULATION,
    EVENT_OBJECT_TABLE,
    ACTION_TIMING,
    ACTION_STATEMENT
FROM information_schema.TRIGGERS
WHERE TRIGGER_SCHEMA = DATABASE();
