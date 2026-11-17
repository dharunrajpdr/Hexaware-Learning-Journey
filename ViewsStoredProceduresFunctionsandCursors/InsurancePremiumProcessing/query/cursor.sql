
USE insurance_db;

-- TASK 4 AND 5: CURSOR WITH INOUT PARAMETER

DELIMITER $$

CREATE PROCEDURE sp_lapse_policies(
    INOUT p_processed_count INT
)
BEGIN
    DECLARE v_policy_id INT;
    DECLARE v_done INT DEFAULT 0;
    DECLARE v_old_status VARCHAR(20);

    -- Declare cursor
    DECLARE policy_cursor CURSOR FOR
        SELECT policy_id
        FROM policies
        WHERE next_due_date < CURDATE()
        AND status = 'ACTIVE';

    -- NOT FOUND handler
    DECLARE CONTINUE HANDLER FOR NOT FOUND
        SET v_done = 1;

    OPEN policy_cursor;

    read_loop: LOOP

        FETCH policy_cursor INTO v_policy_id;

        IF v_done = 1 THEN
            LEAVE read_loop;
        END IF;

        -- Get old status
        SELECT status
        INTO v_old_status
        FROM policies
        WHERE policy_id = v_policy_id;

        -- Update policy status
        UPDATE policies
        SET status = 'LAPSED'
        WHERE policy_id = v_policy_id;

        -- Insert status log
        INSERT INTO policy_status_log(
            policy_id,
            old_status,
            new_status,
            remarks
        )
        VALUES(
            v_policy_id,
            v_old_status,
            'LAPSED',
            'Premium payment overdue'
        );

        -- Increase processed count
        SET p_processed_count = p_processed_count + 1;

    END LOOP;

    CLOSE policy_cursor;

END $$

DELIMITER ;


-- Execute cursor procedure

SET @processed_count = 0;

CALL sp_lapse_policies(@processed_count);

SELECT @processed_count AS total_processed_policies;


-- Display lapsed policies
SELECT * FROM policies
WHERE status = 'LAPSED';

-- Display status logs
SELECT * FROM policy_status_log;


-- SET-BASED APPROACH

START TRANSACTION;

-- Log policies before updating
INSERT INTO policy_status_log(
    policy_id,
    old_status,
    new_status,
    remarks
)
SELECT
    policy_id,
    status,
    'LAPSED',
    'Premium payment overdue'
FROM policies
WHERE next_due_date < CURDATE()
AND status = 'ACTIVE';

-- Update all eligible policies
UPDATE policies
SET status = 'LAPSED'
WHERE next_due_date < CURDATE()
AND status = 'ACTIVE';

COMMIT;
