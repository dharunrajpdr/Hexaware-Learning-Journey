
USE insurance_db;

-- View: Policies due within next 30 days

CREATE OR REPLACE VIEW vw_policy_due AS
SELECT
    p.policy_id,
    c.customer_id,
    c.customer_name,
    c.email,
    c.phone,
    p.policy_type,
    p.premium_amount,
    p.policy_start,
    p.policy_end,
    p.next_due_date,
    p.status
FROM policies p
JOIN customers c
    ON p.customer_id = c.customer_id
WHERE p.next_due_date BETWEEN CURDATE()
AND DATE_ADD(CURDATE(), INTERVAL 30 DAY)
AND p.status = 'ACTIVE';


-- Display policies due
SELECT * FROM vw_policy_due;
