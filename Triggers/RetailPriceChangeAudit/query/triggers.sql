
CREATE DATABASE IF NOT EXISTS retail_db;
USE retail_db;

-- Product Price Table
CREATE TABLE IF NOT EXISTS product_price (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(100),
    cost_price DECIMAL(10,2) NOT NULL,
    selling_price DECIMAL(10,2) NOT NULL
);

-- Price Audit Table
CREATE TABLE IF NOT EXISTS price_audit (
    audit_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    old_price DECIMAL(10,2),
    new_price DECIMAL(10,2),
    changed_by VARCHAR(100),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Sample Data
INSERT INTO product_price
(product_id, product_name, cost_price, selling_price)
VALUES
(101, 'Laptop', 40000.00, 45000.00),
(102, 'Mobile', 10000.00, 12000.00),
(103, 'Keyboard', 500.00, 800.00);

-- Remove existing triggers if present
DROP TRIGGER IF EXISTS trg_before_price_update;
DROP TRIGGER IF EXISTS trg_after_price_update;

DELIMITER //

-- BEFORE UPDATE Trigger: Prevent price below cost
CREATE TRIGGER trg_before_price_update
BEFORE UPDATE ON product_price
FOR EACH ROW
BEGIN
    IF NEW.selling_price < NEW.cost_price THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Selling price cannot be below cost price';
    END IF;
END //

-- AFTER UPDATE Trigger: Record price changes
CREATE TRIGGER trg_after_price_update
AFTER UPDATE ON product_price
FOR EACH ROW
BEGIN
    IF OLD.selling_price <> NEW.selling_price THEN
        INSERT INTO price_audit
        (product_id, old_price, new_price, changed_by, changed_at)
        VALUES
        (
            OLD.product_id,
            OLD.selling_price,
            NEW.selling_price,
            CURRENT_USER(),
            CURRENT_TIMESTAMP
        );
    END IF;
END //

DELIMITER ;

-- Display tables
SELECT * FROM product_price;
SELECT * FROM price_audit;
