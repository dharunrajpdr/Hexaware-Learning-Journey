
USE retail_fulfilment;

-- View 1: Order Summary
-- Cost price and supplier columns are hidden

CREATE OR REPLACE VIEW vw_order_summary AS
SELECT
    o.order_id,
    o.customer_id,
    o.order_date,
    o.status,
    oi.product_id,
    p.product_name,
    oi.quantity,
    oi.unit_price,
    (oi.quantity * oi.unit_price) AS total_amount
FROM orders o
JOIN order_items oi ON o.order_id = oi.order_id
JOIN products p ON oi.product_id = p.product_id;


-- View 2: Low Stock Products

CREATE OR REPLACE VIEW vw_low_stock AS
SELECT
    product_id,
    product_name,
    stock_qty,
    reorder_level
FROM products
WHERE stock_qty <= reorder_level;


-- Updatable View with WITH CHECK OPTION

CREATE OR REPLACE VIEW vw_stock_update AS
SELECT
    product_id,
    product_name,
    stock_qty,
    reorder_level
FROM products
WHERE stock_qty <= reorder_level
WITH CHECK OPTION;


-- Display views
SELECT * FROM vw_order_summary;

SELECT * FROM vw_low_stock;

-- Valid update (remains within view condition)
UPDATE vw_stock_update
SET stock_qty = 2
WHERE product_id = 2;

-- Invalid update (violates CHECK OPTION)
-- Expected to generate an error
UPDATE vw_stock_update
SET stock_qty = 100
WHERE product_id = 2;
