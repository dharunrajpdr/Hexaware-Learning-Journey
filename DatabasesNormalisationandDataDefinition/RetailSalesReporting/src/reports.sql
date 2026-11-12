
-- ============================================================
-- PROJECT: RETAIL SALES REPORTING
-- DATABASE: MySQL 8.0
-- DESCRIPTION: Daily and monthly retail sales reports
-- ============================================================


-- 1. CREATE DATABASE

CREATE DATABASE IF NOT EXISTS retail_db;

USE retail_db;


-- 2. DROP EXISTING TABLES (FOR RE-RUNNING THE SCRIPT)

DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS customers;


-- 3. CREATE TABLES

CREATE TABLE customers (
    customer_id INT PRIMARY KEY,
    customer_name VARCHAR(100),
    city VARCHAR(50),
    email VARCHAR(100),
    registration_date DATE
);

CREATE TABLE products (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(100),
    category VARCHAR(50)
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY,
    customer_id INT,
    order_date DATE,
    FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id)
);

CREATE TABLE order_items (
    order_item_id INT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    unit_price DECIMAL(10,2),
    FOREIGN KEY (order_id)
        REFERENCES orders(order_id),
    FOREIGN KEY (product_id)
        REFERENCES products(product_id)
);

CREATE TABLE payments (
    payment_id INT PRIMARY KEY,
    order_id INT,
    payment_date DATE,
    amount DECIMAL(10,2),
    payment_status VARCHAR(30),
    FOREIGN KEY (order_id)
        REFERENCES orders(order_id)
);


-- 4. INSERT SAMPLE CUSTOMERS

INSERT INTO customers VALUES
(1, 'Arun', 'Chennai', 'arun@gmail.com', '2025-10-01'),
(2, 'Priya', 'Coimbatore', 'priya@gmail.com', '2025-11-15'),
(3, 'Kumar', 'Salem', 'kumar@gmail.com', '2025-12-01'),
(4, 'Meena', 'Erode', 'meena@gmail.com', '2026-01-10'),
(5, 'Raj', 'Chennai', 'raj@gmail.com', '2026-01-15'),
(6, 'Divya', 'Coimbatore', 'divya@gmail.com', '2026-02-01');


-- 5. INSERT SAMPLE PRODUCTS

INSERT INTO products VALUES
(101, 'Laptop', 'Electronics'),
(102, 'Mobile', 'Electronics'),
(103, 'Headphones', 'Accessories'),
(104, 'Keyboard', 'Accessories'),
(105, 'Office Chair', 'Furniture');


-- 6. INSERT SAMPLE ORDERS

INSERT INTO orders VALUES
(1001, 1, '2026-01-05'),
(1002, 1, '2026-01-10'),
(1003, 1, '2026-02-05'),
(1004, 1, '2026-02-14'),
(1005, 2, '2026-01-15'),
(1006, 2, '2026-02-14'),
(1007, 2, '2026-03-01'),
(1008, 2, '2026-03-05'),
(1009, 3, '2026-02-14'),
(1010, 3, '2026-03-10'),
(1011, 4, '2026-03-15'),
(1012, 5, '2026-03-15');


-- 7. INSERT SAMPLE ORDER ITEMS

INSERT INTO order_items VALUES
(1, 1001, 101, 1, 50000),
(2, 1002, 102, 2, 20000),
(3, 1003, 103, 3, 1500),
(4, 1004, 101, 1, 50000),
(5, 1005, 104, 2, 1000),
(6, 1006, 105, 1, 8000),
(7, 1007, 102, 1, 20000),
(8, 1008, 103, 2, 1500),
(9, 1009, 101, 1, 50000),
(10, 1010, 104, 3, 1000),
(11, 1011, 105, 2, 8000),
(12, 1012, 103, 1, 1500);


-- 8. INSERT SAMPLE PAYMENTS

INSERT INTO payments VALUES
(1, 1001, '2026-01-05', 50000, 'Paid'),
(2, 1002, '2026-01-10', 40000, 'Paid'),
(3, 1003, '2026-02-05', 4500, 'Paid'),
(4, 1005, '2026-01-15', 2000, 'Paid'),
(5, 1006, '2026-02-14', 8000, 'Paid'),
(6, 1007, '2026-03-01', 20000, 'Paid'),
(7, 1009, '2026-02-14', 50000, 'Paid'),
(8, 1010, '2026-03-10', 3000, 'Paid'),
(9, 1011, '2026-03-15', 16000, 'Paid');


-- ============================================================
-- TASK 1: EIGHT RETAIL SALES REPORTING QUERIES
-- ============================================================


-- QUERY 1: TOP 5 PRODUCTS BY REVENUE

SELECT
    p.product_id,
    p.product_name,
    ROUND(SUM(oi.quantity * oi.unit_price), 2)
        AS total_revenue
FROM products p
JOIN order_items oi
    ON p.product_id = oi.product_id
GROUP BY
    p.product_id,
    p.product_name
ORDER BY total_revenue DESC
LIMIT 5;


-- QUERY 2: MONTH-WISE SALES

SELECT
    DATE_FORMAT(o.order_date, '%Y-%m')
        AS sales_month,
    ROUND(SUM(oi.quantity * oi.unit_price), 2)
        AS monthly_sales
FROM orders o
JOIN order_items oi
    ON o.order_id = oi.order_id
GROUP BY
    DATE_FORMAT(o.order_date, '%Y-%m')
ORDER BY sales_month;


-- QUERY 3: CUSTOMERS WITH MORE THAN THREE ORDERS

SELECT
    c.customer_id,
    UPPER(
        CONCAT(c.customer_name, ' - ', c.city)
    ) AS customer_details,
    COUNT(o.order_id) AS total_orders
FROM customers c
JOIN orders o
    ON c.customer_id = o.customer_id
GROUP BY
    c.customer_id,
    c.customer_name,
    c.city
HAVING COUNT(o.order_id) > 3
ORDER BY total_orders DESC;


-- QUERY 4: AVERAGE BASKET VALUE PER CITY

SELECT
    c.city,
    ROUND(AVG(order_values.order_total), 2)
        AS average_basket_value
FROM customers c
JOIN (
    SELECT
        o.order_id,
        o.customer_id,
        SUM(oi.quantity * oi.unit_price) AS order_total
    FROM orders o
    JOIN order_items oi
        ON o.order_id = oi.order_id
    GROUP BY
        o.order_id,
        o.customer_id
) AS order_values
    ON c.customer_id = order_values.customer_id
GROUP BY c.city
ORDER BY average_basket_value DESC;


-- QUERY 5: ORDERS WITHOUT PAYMENT (LEFT JOIN)

SELECT
    o.order_id,
    o.customer_id,
    DATE_FORMAT(o.order_date, '%d-%b-%Y')
        AS order_date,
    'Payment not recorded' AS payment_message
FROM orders o
LEFT JOIN payments p
    ON o.order_id = p.order_id
WHERE p.payment_id IS NULL
ORDER BY o.order_id;


-- QUERY 6: CUSTOMERS WITH NO ORDERS

SELECT
    c.customer_id,
    CONCAT(
        UPPER(c.customer_name),
        ' - ',
        c.city
    ) AS customer_details,
    DATEDIFF(
        CURDATE(),
        c.registration_date
    ) AS days_since_registration
FROM customers c
LEFT JOIN orders o
    ON c.customer_id = o.customer_id
WHERE o.order_id IS NULL
ORDER BY c.customer_id;


-- QUERY 7: PRODUCT CATEGORY REVENUE SHARE

SELECT
    category_sales.category,
    ROUND(category_sales.category_revenue, 2)
        AS category_revenue,
    ROUND(
        category_sales.category_revenue * 100
        / NULLIF(total_sales.total_revenue, 0),
        2
    ) AS revenue_percentage
FROM (
    SELECT
        p.category,
        SUM(oi.quantity * oi.unit_price)
            AS category_revenue
    FROM products p
    JOIN order_items oi
        ON p.product_id = oi.product_id
    GROUP BY p.category
) AS category_sales
CROSS JOIN (
    SELECT
        SUM(quantity * unit_price) AS total_revenue
    FROM order_items
) AS total_sales
ORDER BY revenue_percentage DESC;


-- QUERY 8: DAY WITH THE HIGHEST SALES

SELECT
    o.order_date AS highest_sales_day,
    ROUND(SUM(oi.quantity * oi.unit_price), 2)
        AS daily_sales
FROM orders o
JOIN order_items oi
    ON o.order_id = oi.order_id
GROUP BY o.order_date
ORDER BY daily_sales DESC
LIMIT 1;


-- ============================================================
-- TASK 3: DEMONSTRATE NULL BEHAVIOUR
-- COUNT(*), COUNT(column), SUM(), AVG()
-- ============================================================

SELECT
    c.customer_id,
    c.customer_name,
    COUNT(*) AS total_rows,
    COUNT(o.order_id) AS total_orders,
    SUM(o.order_id) AS sum_order_ids,
    AVG(o.order_id) AS average_order_id
FROM customers c
LEFT JOIN orders o
    ON c.customer_id = o.customer_id
GROUP BY
    c.customer_id,
    c.customer_name
ORDER BY c.customer_id;


-- ============================================================
-- TASK 4: WHERE VS HAVING
-- ============================================================

SELECT
    customer_id,
    COUNT(*) AS total_orders
FROM orders
WHERE order_date >= '2026-01-01'
GROUP BY customer_id
HAVING COUNT(*) > 3;


-- ============================================================
-- END OF RETAIL SALES REPORTING PROJECT
-- ============================================================
