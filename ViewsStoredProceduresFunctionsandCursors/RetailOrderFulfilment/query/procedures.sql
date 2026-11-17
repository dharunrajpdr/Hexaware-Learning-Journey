
USE retail_fulfilment;

-- TASK 2: STORED PROCEDURE

DELIMITER $$

CREATE PROCEDURE sp_place_order(
    IN p_customer_id INT,
    IN p_product_id INT,
    IN p_quantity INT,
    OUT p_order_no INT
)
BEGIN
    DECLARE v_stock INT;
    DECLARE v_price DECIMAL(10,2);
    DECLARE v_order_id INT;

    -- Rollback handler
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    -- Validate customer
    IF NOT EXISTS (
        SELECT 1 FROM customers
        WHERE customer_id = p_customer_id
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Invalid customer ID';
    END IF;

    -- Validate quantity
    IF p_quantity <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Quantity must be greater than zero';
    END IF;

    -- Check stock and lock product row
    SELECT stock_qty, selling_price
    INTO v_stock, v_price
    FROM products
    WHERE product_id = p_product_id
    FOR UPDATE;

    IF v_stock < p_quantity THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Insufficient stock';
    END IF;

    -- Insert order
    INSERT INTO orders(customer_id, status)
    VALUES(p_customer_id, 'PENDING');

    SET v_order_id = LAST_INSERT_ID();

    -- Insert order line
    INSERT INTO order_items(
        order_id,
        product_id,
        quantity,
        unit_price
    )
    VALUES(
        v_order_id,
        p_product_id,
        p_quantity,
        v_price
    );

    -- Update stock
    UPDATE products
    SET stock_qty = stock_qty - p_quantity
    WHERE product_id = p_product_id;

    -- Return order number
    SET p_order_no = v_order_id;

    COMMIT;

END $$

DELIMITER ;


-- Execute stored procedure

SET @order_no = 0;

CALL sp_place_order(1, 1, 2, @order_no);

SELECT @order_no AS generated_order_number;


-- TASK 3: STORED FUNCTION

DELIMITER $$

CREATE FUNCTION fn_order_total_with_tax(
    p_order_id INT
)
RETURNS DECIMAL(12,2)
READS SQL DATA
BEGIN
    DECLARE v_total DECIMAL(12,2);

    SELECT COALESCE(SUM(quantity * unit_price), 0)
    INTO v_total
    FROM order_items
    WHERE order_id = p_order_id;

    RETURN v_total * 1.18;
END $$

DELIMITER ;


-- Use function inside SELECT

SELECT
    order_id,
    fn_order_total_with_tax(order_id) AS total_with_tax
FROM orders;


-- Function vs Procedure

SELECT 'Function returns a value' AS function_feature,
       'Procedure performs operations' AS procedure_feature;
