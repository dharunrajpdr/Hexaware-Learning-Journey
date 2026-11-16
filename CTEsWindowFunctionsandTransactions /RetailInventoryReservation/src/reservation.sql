
-- ==========================================
-- RETAIL INVENTORY RESERVATION
-- MySQL 8.0
-- ==========================================

CREATE DATABASE retail_inventory;
USE retail_inventory;

-- 1. Create category table
CREATE TABLE categories (
    category_id INT PRIMARY KEY,
    category_name VARCHAR(50)
) ENGINE=InnoDB;

-- 2. Create products table
CREATE TABLE products (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(50),
    category_id INT,
    stock INT,
    price DECIMAL(10,2),
    FOREIGN KEY (category_id)
        REFERENCES categories(category_id)
) ENGINE=InnoDB;

-- 3. Create orders table
CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(50),
    order_date DATE
) ENGINE=InnoDB;

-- 4. Create order_items table
CREATE TABLE order_items (
    item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    quantity INT,
    price DECIMAL(10,2),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
) ENGINE=InnoDB;

-- 5. Insert categories
INSERT INTO categories VALUES
(1, 'Electronics'),
(2, 'Clothing'),
(3, 'Furniture');

-- 6. Insert products
INSERT INTO products VALUES
(101, 'Laptop', 1, 20, 50000),
(102, 'Mobile', 1, 15, 20000),
(103, 'Headphones', 1, 5, 2000),
(104, 'Shirt', 2, 30, 1000),
(105, 'Jeans', 2, 10, 1500),
(106, 'Jacket', 2, 3, 2500),
(107, 'Table', 3, 12, 5000),
(108, 'Chair', 3, 8, 2000);

-- ==========================================
-- TASK 1: STOCK RESERVATION TRANSACTION
-- ==========================================

START TRANSACTION;

-- Check and lock product stock
SELECT product_id, product_name, stock
FROM products
WHERE product_id = 101
FOR UPDATE;

-- Decrement stock only if sufficient
UPDATE products
SET stock = stock - 2
WHERE product_id = 101
AND stock >= 2;

-- Check affected rows in application.
-- Continue only if stock update succeeded.

-- Insert order
INSERT INTO orders (customer_name, order_date)
VALUES ('Dharun', CURRENT_DATE);

SET @order_id = LAST_INSERT_ID();

-- Insert order item
INSERT INTO order_items
(order_id, product_id, quantity, price)
VALUES
(@order_id, 101, 2, 50000);

-- Save transaction
COMMIT;


-- ==========================================
-- ROLLBACK WHEN STOCK IS INSUFFICIENT
-- ==========================================

START TRANSACTION;

SELECT product_id, stock
FROM products
WHERE product_id = 103
FOR UPDATE;

-- Attempt to reserve more stock than available
UPDATE products
SET stock = stock - 100
WHERE product_id = 103
AND stock >= 100;

-- Check ROW_COUNT() immediately.
-- If zero, stock is insufficient.

SELECT ROW_COUNT() AS affected_rows;

-- Demonstration: cancel the transaction
ROLLBACK;

-- Verify stock remains unchanged
SELECT * FROM products;


-- ==========================================
-- TASK 2: CORRELATED SUBQUERY
-- PRODUCTS BELOW CATEGORY AVERAGE STOCK
-- ==========================================

SELECT
    p.product_id,
    p.product_name,
    c.category_name,
    p.stock

FROM products p
JOIN categories c
    ON p.category_id = c.category_id

WHERE p.stock < (
    SELECT AVG(p2.stock)
    FROM products p2
    WHERE p2.category_id = p.category_id
);


-- ==========================================
-- VERIFY ORDERS
-- ==========================================

SELECT * FROM orders;

SELECT * FROM order_items;

SELECT * FROM products;
