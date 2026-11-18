
CREATE DATABASE IF NOT EXISTS hospital_db;
USE hospital_db;

-- Ward Table
CREATE TABLE IF NOT EXISTS ward (
    ward_id INT PRIMARY KEY,
    ward_name VARCHAR(100),
    bed_capacity INT NOT NULL,
    occupied_beds INT DEFAULT 0,
    CHECK (bed_capacity >= 0),
    CHECK (occupied_beds >= 0),
    CHECK (occupied_beds <= bed_capacity)
) ENGINE = InnoDB;

-- Admission Table
CREATE TABLE IF NOT EXISTS admission (
    admission_id INT PRIMARY KEY,
    patient_name VARCHAR(100) NOT NULL,
    ward_id INT NOT NULL,
    admission_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ward_id) REFERENCES ward(ward_id)
) ENGINE = InnoDB;

-- Admission Audit Table
CREATE TABLE IF NOT EXISTS admission_audit (
    audit_id INT AUTO_INCREMENT PRIMARY KEY,
    admission_id INT,
    patient_name VARCHAR(100),
    ward_id INT,
    action_type VARCHAR(20),
    changed_by VARCHAR(100),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB;

-- Sample Ward Data
INSERT INTO ward
(ward_id, ward_name, bed_capacity, occupied_beds)
VALUES
(1, 'General Ward', 3, 0),
(2, 'ICU', 2, 0),
(3, 'Emergency Ward', 2, 0);

-- Remove Existing Triggers
DROP TRIGGER IF EXISTS trg_before_admission_insert;
DROP TRIGGER IF EXISTS trg_after_admission_insert;
DROP TRIGGER IF EXISTS trg_after_admission_update;
DROP TRIGGER IF EXISTS trg_after_admission_delete;

DELIMITER //

-- 1. BEFORE INSERT: Check Ward Capacity
CREATE TRIGGER trg_before_admission_insert
BEFORE INSERT ON admission
FOR EACH ROW
BEGIN
    DECLARE current_occupied INT;
    DECLARE max_capacity INT;

    SELECT occupied_beds, bed_capacity
    INTO current_occupied, max_capacity
    FROM ward
    WHERE ward_id = NEW.ward_id
    FOR UPDATE;

    IF current_occupied >= max_capacity THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Admission rejected: Ward is at full capacity';
    END IF;
END //

-- 2. AFTER INSERT: Record New Admission
CREATE TRIGGER trg_after_admission_insert
AFTER INSERT ON admission
FOR EACH ROW
BEGIN
    UPDATE ward
    SET occupied_beds = occupied_beds + 1
    WHERE ward_id = NEW.ward_id;

    INSERT INTO admission_audit
    (admission_id, patient_name, ward_id, action_type, changed_by)
    VALUES
    (NEW.admission_id, NEW.patient_name, NEW.ward_id,
     'INSERT', CURRENT_USER());
END //

-- 3. AFTER UPDATE: Record Admission Update
CREATE TRIGGER trg_after_admission_update
AFTER UPDATE ON admission
FOR EACH ROW
BEGIN
    IF OLD.ward_id <> NEW.ward_id THEN

        UPDATE ward
        SET occupied_beds = occupied_beds - 1
        WHERE ward_id = OLD.ward_id;

        UPDATE ward
        SET occupied_beds = occupied_beds + 1
        WHERE ward_id = NEW.ward_id;

    END IF;

    INSERT INTO admission_audit
    (admission_id, patient_name, ward_id, action_type, changed_by)
    VALUES
    (NEW.admission_id, NEW.patient_name, NEW.ward_id,
     'UPDATE', CURRENT_USER());
END //

-- 4. AFTER DELETE: Record Admission Deletion
CREATE TRIGGER trg_after_admission_delete
AFTER DELETE ON admission
FOR EACH ROW
BEGIN
    UPDATE ward
    SET occupied_beds = occupied_beds - 1
    WHERE ward_id = OLD.ward_id;

    INSERT INTO admission_audit
    (admission_id, patient_name, ward_id, action_type, changed_by)
    VALUES
    (OLD.admission_id, OLD.patient_name, OLD.ward_id,
     'DELETE', CURRENT_USER());
END //

DELIMITER ;

-- Display Tables
SELECT * FROM ward;
SELECT * FROM admission;
SELECT * FROM admission_audit;
