
USE insurance_db;

-- TASK 2: STORED PROCEDURE

DELIMITER $$

CREATE PROCEDURE sp_renew_policy(
    IN p_policy_id INT
)
BEGIN
    DECLARE v_status VARCHAR(20);
    DECLARE v_old_premium DECIMAL(10,2);
    DECLARE v_new_premium DECIMAL(10,2);

    -- Rollback handler
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    -- Lock and fetch policy
    SELECT status, premium_amount
    INTO v_status, v_old_premium
    FROM policies
    WHERE policy_id = p_policy_id
    FOR UPDATE;

    -- Validate active policy
    IF v_status <> 'ACTIVE' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Policy is not active';
    END IF;

    -- Validate pending claims
    IF EXISTS (
        SELECT 1
        FROM claims
        WHERE policy_id = p_policy_id
        AND claim_status = 'PENDING'
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Policy has pending claims';
    END IF;

    -- Recalculate premium (10% increase)
    SET v_new_premium = v_old_premium * 1.10;

    -- Update premium
    UPDATE policies
    SET premium_amount = v_new_premium,
        next_due_date = DATE_ADD(next_due_date, INTERVAL 1 YEAR)
    WHERE policy_id = p_policy_id;

    -- Insert audit record
    INSERT INTO premium_audit(
        policy_id,
        old_premium,
        new_premium,
        remarks
    )
    VALUES(
        p_policy_id,
        v_old_premium,
        v_new_premium,
        'Policy renewed successfully'
    );

    COMMIT;

END $$

DELIMITER ;


-- Execute procedure
CALL sp_renew_policy(1);

-- Check updated premium
SELECT * FROM policies;

-- Check audit records
SELECT * FROM premium_audit;


-- TASK 3: STORED FUNCTION

DELIMITER $$

CREATE FUNCTION fn_premium_with_no_claim_discount(
    p_policy_id INT
)
RETURNS DECIMAL(10,2)
READS SQL DATA
BEGIN
    DECLARE v_premium DECIMAL(10,2);
    DECLARE v_claim_count INT DEFAULT 0;

    SELECT premium_amount
    INTO v_premium
    FROM policies
    WHERE policy_id = p_policy_id;

    SELECT COUNT(*)
    INTO v_claim_count
    FROM claims
    WHERE policy_id = p_policy_id
    AND claim_status IN ('PENDING', 'APPROVED');

    -- Apply 10% discount if there are no pending or approved claims
    IF v_claim_count = 0 THEN
        SET v_premium = v_premium * 0.90;
    END IF;

    RETURN v_premium;

END $$

DELIMITER ;


-- Premium report using function

SELECT
    policy_id,
    policy_type,
    premium_amount AS original_premium,
    fn_premium_with_no_claim_discount(policy_id)
        AS discounted_premium
FROM policies;


-- Function vs Procedure

SELECT
    'Function' AS object_type,
    'Returns a value using RETURN' AS purpose

UNION ALL

SELECT
    'Procedure',
    'Performs business operations using CALL';
