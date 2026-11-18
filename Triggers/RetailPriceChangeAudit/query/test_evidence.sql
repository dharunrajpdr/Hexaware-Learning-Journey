
USE retail_db;

-- TEST 1: Single-row UPDATE
UPDATE product_price
SET selling_price = 47000.00
WHERE product_id = 101;

SELECT * FROM product_price;
SELECT * FROM price_audit;

-- TEST 2: Multi-row UPDATE
UPDATE product_price
SET selling_price = selling_price + 500
WHERE product_id IN (102, 103);

SELECT * FROM product_price;
SELECT * FROM price_audit;

-- TEST 3: Reject price below cost
-- Expected: Error SQLSTATE 45000
UPDATE product_price
SET selling_price = 30000.00
WHERE product_id = 101;

-- Verify rejected update
SELECT * FROM product_price;

-- Verify audit records
SELECT * FROM price_audit;

-- TEST 4: Verify row-level trigger execution
-- Two rows updated = Two audit records
UPDATE product_price
SET selling_price = selling_price + 100
WHERE product_id IN (101, 102);

SELECT * FROM price_audit
ORDER BY audit_id;

-- TEST 5: List all triggers in current schema
SELECT
    TRIGGER_NAME,
    EVENT_MANIPULATION,
    EVENT_OBJECT_TABLE,
    ACTION_TIMING,
    ACTION_STATEMENT
FROM information_schema.TRIGGERS
WHERE TRIGGER_SCHEMA = DATABASE();
