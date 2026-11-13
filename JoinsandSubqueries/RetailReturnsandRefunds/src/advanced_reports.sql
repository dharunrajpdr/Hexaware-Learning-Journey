
-- =====================================================
-- RETAIL RETURNS AND REFUNDS
-- =====================================================

CREATE DATABASE IF NOT EXISTS retail_returns_db;
USE retail_returns_db;

-- =====================================================
-- 1. DROP EXISTING TABLES
-- =====================================================

DROP TABLE IF EXISTS refunds;
DROP TABLE IF EXISTS returns;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS stores;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS customers;

-- =====================================================
-- 2. CREATE TABLES
-- =====================================================

CREATE TABLE customers (
    customer_id INT PRIMARY KEY,
    customer_name VARCHAR(100),
    city VARCHAR(50)
);

CREATE TABLE products (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(100),
    category VARCHAR(50),
    price DECIMAL(10,2)
);

CREATE TABLE stores (
    store_id INT PRIMARY KEY,
    store_name VARCHAR(100),
    region VARCHAR(50)
);

CREATE TABLE employees (
    employee_id INT PRIMARY KEY,
    employee_name VARCHAR(100),
    manager_id INT,
    store_id INT,
    FOREIGN KEY (manager_id)
        REFERENCES employees(employee_id),
    FOREIGN KEY (store_id)
        REFERENCES stores(store_id)
);

CREATE TABLE orders (
    order_id INT PRIMARY KEY,
    customer_id INT,
    store_id INT,
    order_date DATE,
    FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id),
    FOREIGN KEY (store_id)
        REFERENCES stores(store_id)
);

CREATE TABLE order_items (
    item_id INT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    FOREIGN KEY (order_id)
        REFERENCES orders(order_id),
    FOREIGN KEY (product_id)
        REFERENCES products(product_id)
);

CREATE TABLE returns (
    return_id INT PRIMARY KEY,
    order_id INT,
    product_id INT,
    return_date DATE,
    return_quantity INT,
    return_reason VARCHAR(100),
    FOREIGN KEY (order_id)
        REFERENCES orders(order_id),
    FOREIGN KEY (product_id)
        REFERENCES products(product_id)
);

CREATE TABLE refunds (
    refund_id INT PRIMARY KEY,
    return_id INT,
    refund_date DATE,
    refund_amount DECIMAL(10,2),
    FOREIGN KEY (return_id)
        REFERENCES returns(return_id)
);

-- =====================================================
-- 3. INSERT SAMPLE DATA
-- =====================================================

INSERT INTO customers VALUES
(1, 'Arun', 'Chennai'),
(2, 'Bala', 'Coimbatore'),
(3, 'Charan', 'Salem'),
(4, 'Divya', 'Chennai'),
(5, 'Ezhil', 'Madurai'),
(6, 'Gokul', 'Trichy');

INSERT INTO products VALUES
(101, 'Laptop', 'Electronics', 50000),
(102, 'Mobile', 'Electronics', 20000),
(103, 'Headphones', 'Accessories', 2000),
(104, 'Keyboard', 'Accessories', 1500),
(105, 'Chair', 'Furniture', 5000),
(106, 'Table', 'Furniture', 8000),
(107, 'Monitor', 'Electronics', 12000);

INSERT INTO stores VALUES
(1, 'Chennai Central', 'South'),
(2, 'Coimbatore Store', 'South'),
(3, 'Bangalore Store', 'South'),
(4, 'Mumbai Store', 'West'),
(5, 'Pune Store', 'West');

INSERT INTO employees VALUES
(101, 'Ramesh', NULL, 1),
(102, 'Suresh', 101, 1),
(103, 'Kumar', 101, 1),
(104, 'Vijay', NULL, 2),
(105, 'Ajay', 104, 2),
(106, 'Manoj', NULL, 3),
(107, 'Raj', 106, 3),
(108, 'Priya', NULL, 4),
(109, 'Anu', 108, 4),
(110, 'Kiran', NULL, 5);

INSERT INTO orders VALUES
(1001, 1, 1, '2026-01-10'),
(1002, 2, 2, '2026-01-12'),
(1003, 3, 1, '2026-01-15'),
(1004, 4, 3, '2026-02-01'),
(1005, 5, 4, '2026-02-10'),
(1006, 1, 2, '2026-02-15'),
(1007, 6, 5, '2026-03-01'),
(1008, 2, 1, '2026-03-05'),
(1009, 3, 3, '2026-03-10'),
(1010, 4, 4, '2026-03-15');

INSERT INTO order_items VALUES
(1, 1001, 101, 10),
(2, 1001, 102, 10),
(3, 1002, 103, 20),
(4, 1002, 104, 10),
(5, 1003, 101, 10),
(6, 1003, 105, 10),
(7, 1004, 102, 10),
(8, 1004, 103, 20),
(9, 1005, 104, 20),
(10, 1005, 105, 10),
(11, 1006, 101, 10),
(12, 1006, 106, 10),
(13, 1007, 107, 10),
(14, 1008, 102, 20),
(15, 1008, 103, 10),
(16, 1009, 101, 10),
(17, 1009, 104, 20),
(18, 1010, 105, 10);

INSERT INTO returns VALUES
(1, 1001, 101, '2026-02-01', 2, 'Damaged'),
(2, 1001, 102, '2026-02-02', 1, 'Defective'),
(3, 1002, 103, '2026-02-05', 3, 'Not satisfied'),
(4, 1003, 101, '2026-02-10', 2, 'Damaged'),
(5, 1004, 102, '2026-02-15', 2, 'Wrong product'),
(6, 1005, 104, '2026-02-20', 5, 'Defective'),
(7, 1006, 101, '2026-03-01', 3, 'Damaged'),
(8, 1007, 107, '2026-03-05', 1, 'Not satisfied'),
(9, 1008, 102, '2026-03-10', 2, 'Defective'),
(10, 1009, 104, '2026-03-15', 4, 'Damaged');

INSERT INTO refunds VALUES
(1, 1, '2026-02-05', 10000),
(2, 2, '2026-02-06', 2000),
(3, 4, '2026-02-15', 10000),
(4, 6, '2026-02-25', 7500),
(5, 7, '2026-03-05', 15000),
(6, 8, '2026-03-10', 12000),
(7, 9, '2026-03-15', 40000);

-- =====================================================
-- 4. TEN ADVANCED SQL QUERIES
-- =====================================================

-- QUERY 1:
-- Find products with return rate above 10%.
-- Return rate = returned quantity / sold quantity * 100.

SELECT
    p.product_id,
    p.product_name,
    SUM(oi.quantity) AS total_sold,
    COALESCE(SUM(r.return_quantity), 0) AS total_returned,
    ROUND(
        COALESCE(SUM(r.return_quantity), 0)
        * 100.0 / SUM(oi.quantity), 2
    ) AS return_rate
FROM products p
INNER JOIN order_items oi
    ON p.product_id = oi.product_id
LEFT JOIN returns r
    ON r.product_id = oi.product_id
   AND r.order_id = oi.order_id
GROUP BY p.product_id, p.product_name
HAVING return_rate > 10;


-- QUERY 2:
-- Find customers with returns but no refund.

SELECT DISTINCT
    c.customer_id,
    c.customer_name
FROM customers c
INNER JOIN orders o
    ON c.customer_id = o.customer_id
INNER JOIN returns r
    ON o.order_id = r.order_id
LEFT JOIN refunds f
    ON r.return_id = f.return_id
WHERE f.refund_id IS NULL;


-- QUERY 3:
-- Find stores with the highest refund value.

SELECT
    s.store_id,
    s.store_name,
    SUM(f.refund_amount) AS total_refund
FROM stores s
INNER JOIN orders o
    ON s.store_id = o.store_id
INNER JOIN returns r
    ON o.order_id = r.order_id
INNER JOIN refunds f
    ON r.return_id = f.return_id
GROUP BY s.store_id, s.store_name
ORDER BY total_refund DESC;


-- QUERY 4:
-- Display employees and their managers.
-- SELF JOIN on employees table.

SELECT
    e.employee_id,
    e.employee_name,
    m.employee_name AS manager_name
FROM employees e
LEFT JOIN employees m
    ON e.manager_id = m.employee_id;


-- QUERY 5:
-- Find stores with no returns.

SELECT
    s.store_id,
    s.store_name
FROM stores s
WHERE NOT EXISTS
(
    SELECT 1
    FROM orders o
    INNER JOIN returns r
        ON o.order_id = r.order_id
    WHERE o.store_id = s.store_id
);


-- QUERY 6:
-- Demonstrate the NULL problem with NOT IN.

-- This query returns no rows because the subquery
-- contains NULL values in the customer_id column.

SELECT
    customer_id,
    customer_name
FROM customers
WHERE customer_id NOT IN
(
    SELECT NULL
    UNION
    SELECT customer_id
    FROM orders
);


-- Correct solution using NOT EXISTS.

SELECT
    c.customer_id,
    c.customer_name
FROM customers c
WHERE NOT EXISTS
(
    SELECT 1
    FROM orders o
    WHERE o.customer_id = c.customer_id
);


-- QUERY 7:
-- Find stores whose refund value is greater than
-- ANY other store's refund value in the same region.

SELECT
    s.store_id,
    s.store_name,
    s.region,
    COALESCE(
        (
            SELECT SUM(f.refund_amount)
            FROM orders o
            INNER JOIN returns r
                ON o.order_id = r.order_id
            INNER JOIN refunds f
                ON r.return_id = f.return_id
            WHERE o.store_id = s.store_id
        ), 0
    ) AS total_refund
FROM stores s
WHERE COALESCE(
    (
        SELECT SUM(f.refund_amount)
        FROM orders o
        INNER JOIN returns r
            ON o.order_id = r.order_id
        INNER JOIN refunds f
            ON r.return_id = f.return_id
        WHERE o.store_id = s.store_id
    ), 0
) > ANY
(
    SELECT COALESCE(
        (
            SELECT SUM(f2.refund_amount)
            FROM orders o2
            INNER JOIN returns r2
                ON o2.order_id = r2.order_id
            INNER JOIN refunds f2
                ON r2.return_id = f2.return_id
            WHERE o2.store_id = other_store.store_id
        ), 0
    )
    FROM stores other_store
    WHERE other_store.region = s.region
      AND other_store.store_id <> s.store_id
);


-- QUERY 8:
-- Find stores whose refund value is greater than
-- ALL other stores' refund values in the same region.

SELECT
    s.store_id,
    s.store_name,
    s.region,
    COALESCE(
        (
            SELECT SUM(f.refund_amount)
            FROM orders o
            INNER JOIN returns r
                ON o.order_id = r.order_id
            INNER JOIN refunds f
                ON r.return_id = f.return_id
            WHERE o.store_id = s.store_id
        ), 0
    ) AS total_refund
FROM stores s
WHERE COALESCE(
    (
        SELECT SUM(f.refund_amount)
        FROM orders o
        INNER JOIN returns r
            ON o.order_id = r.order_id
        INNER JOIN refunds f
            ON r.return_id = f.return_id
        WHERE o.store_id = s.store_id
    ), 0
) > ALL
(
    SELECT COALESCE(
        (
            SELECT SUM(f2.refund_amount)
            FROM orders o2
            INNER JOIN returns r2
                ON o2.order_id = r2.order_id
            INNER JOIN refunds f2
                ON r2.return_id = f2.return_id
            WHERE o2.store_id = other_store.store_id
        ), 0
    )
    FROM stores other_store
    WHERE other_store.region = s.region
      AND other_store.store_id <> s.store_id
);


-- QUERY 9:
-- NESTED SUBQUERY 1:
-- Find products whose price is greater than
-- the average product price.

SELECT
    product_id,
    product_name,
    price
FROM products
WHERE price >
(
    SELECT AVG(price)
    FROM products
);


-- QUERY 10:
-- NESTED SUBQUERY 2:
-- Find stores whose total refund is greater than
-- the average total refund of all stores.

SELECT
    s.store_id,
    s.store_name,
    (
        SELECT COALESCE(SUM(f.refund_amount), 0)
        FROM orders o
        INNER JOIN returns r
            ON o.order_id = r.order_id
        INNER JOIN refunds f
            ON r.return_id = f.return_id
        WHERE o.store_id = s.store_id
    ) AS total_refund
FROM stores s
WHERE
(
    SELECT COALESCE(SUM(f.refund_amount), 0)
    FROM orders o
    INNER JOIN returns r
        ON o.order_id = r.order_id
    INNER JOIN refunds f
        ON r.return_id = f.return_id
    WHERE o.store_id = s.store_id
) >
(
    SELECT AVG(store_refund)
    FROM
    (
        SELECT
            s2.store_id,
            COALESCE(SUM(f2.refund_amount), 0) AS store_refund
        FROM stores s2
        LEFT JOIN orders o2
            ON s2.store_id = o2.store_id
        LEFT JOIN returns r2
            ON o2.order_id = r2.order_id
        LEFT JOIN refunds f2
            ON r2.return_id = f2.return_id
        GROUP BY s2.store_id
    ) AS store_totals
);
