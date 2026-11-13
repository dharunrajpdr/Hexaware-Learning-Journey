
-- ============================================
-- BANK LOAN PORTFOLIO
-- Advanced SQL Assignment
-- Database: MySQL
-- ============================================

CREATE DATABASE IF NOT EXISTS bank_loan_db;
USE bank_loan_db;

-- ============================================
-- 1. DROP EXISTING TABLES
-- ============================================

DROP TABLE IF EXISTS guarantors;
DROP TABLE IF EXISTS repayments;
DROP TABLE IF EXISTS loans;
DROP TABLE IF EXISTS branches;
DROP TABLE IF EXISTS customers;

-- ============================================
-- 2. CREATE TABLES
-- ============================================

CREATE TABLE customers (
    customer_id INT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    city VARCHAR(50),
    phone VARCHAR(15)
);

CREATE TABLE branches (
    branch_id INT PRIMARY KEY,
    branch_name VARCHAR(100) NOT NULL,
    branch_city VARCHAR(50)
);

CREATE TABLE loans (
    loan_id INT PRIMARY KEY,
    customer_id INT,
    branch_id INT,
    loan_amount DECIMAL(12,2),
    loan_date DATE,
    total_instalments INT,
    paid_instalments INT DEFAULT 0,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (branch_id) REFERENCES branches(branch_id)
);

CREATE TABLE repayments (
    repayment_id INT PRIMARY KEY,
    loan_id INT,
    repayment_date DATE,
    amount DECIMAL(12,2),
    FOREIGN KEY (loan_id) REFERENCES loans(loan_id)
);

CREATE TABLE guarantors (
    guarantor_id INT PRIMARY KEY,
    loan_id INT,
    customer_id INT,
    FOREIGN KEY (loan_id) REFERENCES loans(loan_id),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- ============================================
-- 3. INSERT SAMPLE DATA
-- ============================================

INSERT INTO customers VALUES
(1, 'Arun', 'Chennai', '9876543210'),
(2, 'Bala', 'Coimbatore', '9876543211'),
(3, 'Charan', 'Chennai', '9876543212'),
(4, 'Divya', 'Salem', '9876543213'),
(5, 'Ezhil', 'Madurai', '9876543214'),
(6, 'Gokul', 'Chennai', '9876543215'),
(7, 'Hari', 'Trichy', '9876543216'),
(8, 'Kavin', 'Erode', '9876543217');

INSERT INTO branches VALUES
(101, 'Chennai Main Branch', 'Chennai'),
(102, 'Coimbatore Branch', 'Coimbatore'),
(103, 'Salem Branch', 'Salem'),
(104, 'Madurai Branch', 'Madurai'),
(105, 'Trichy Branch', 'Trichy');

INSERT INTO loans VALUES
(1001, 1, 101, 500000, '2025-01-10', 24, 10),
(1002, 2, 102, 300000, '2025-02-15', 24, 20),
(1003, 3, 101, 700000, '2025-03-20', 36, 15),
(1004, 1, 102, 200000, '2025-04-10', 12, 5),
(1005, 4, 103, 400000, '2025-05-15', 24, 18),
(1006, 5, 104, 600000, '2025-06-10', 36, 8),
(1007, 6, 101, 250000, '2025-07-01', 12, 12),
(1008, 7, 105, 350000, '2025-08-10', 24, 10);

INSERT INTO repayments VALUES
(1, 1001, '2026-09-01', 20000),
(2, 1001, '2026-10-01', 20000),
(3, 1002, '2026-10-15', 15000),
(4, 1003, '2026-08-01', 25000),
(5, 1004, '2026-09-10', 10000),
(6, 1005, '2026-11-01', 18000),
(7, 1006, '2026-07-01', 22000),
(8, 1007, '2026-10-20', 21000);

INSERT INTO guarantors VALUES
(1, 1001, 2),
(2, 1002, 1),
(3, 1003, 4),
(4, 1004, 3),
(5, 1005, 1),
(6, 1006, 6),
(7, 1007, 5),
(8, 1008, 7);

-- ============================================
-- 4. ADVANCED SQL REPORT QUERIES
-- ============================================

-- QUERY 1:
-- Display all loans with customer and branch details.
-- JOIN is used to combine related tables.

SELECT
    l.loan_id,
    c.customer_name,
    b.branch_name,
    l.loan_amount,
    l.loan_date
FROM loans l
INNER JOIN customers c
    ON l.customer_id = c.customer_id
INNER JOIN branches b
    ON l.branch_id = b.branch_id;


-- QUERY 2:
-- Find loans overdue by more than three instalments.
-- Difference between total and paid instalments
-- identifies outstanding instalments.

SELECT
    l.loan_id,
    c.customer_name,
    l.total_instalments,
    l.paid_instalments,
    (l.total_instalments - l.paid_instalments)
        AS pending_instalments
FROM loans l
INNER JOIN customers c
    ON l.customer_id = c.customer_id
WHERE (l.total_instalments - l.paid_instalments) > 3;


-- QUERY 3:
-- Find customers having loans in more than one branch.
-- GROUP BY and HAVING count distinct branches.

SELECT
    c.customer_id,
    c.customer_name,
    COUNT(DISTINCT l.branch_id) AS branch_count
FROM customers c
INNER JOIN loans l
    ON c.customer_id = l.customer_id
GROUP BY c.customer_id, c.customer_name
HAVING COUNT(DISTINCT l.branch_id) > 1;


-- QUERY 4:
-- Find guarantors who are also borrowers.
-- SELF JOIN is used on customers and loans.

SELECT DISTINCT
    g.customer_id AS guarantor_customer_id,
    c.customer_name AS guarantor_name,
    l.loan_id AS own_loan_id
FROM guarantors g
INNER JOIN customers c
    ON g.customer_id = c.customer_id
INNER JOIN loans l
    ON g.customer_id = l.customer_id;


-- QUERY 5:
-- Find branches that have no loans.
-- LEFT JOIN returns all branches.
-- NULL identifies branches without loans.

SELECT
    b.branch_id,
    b.branch_name
FROM branches b
LEFT JOIN loans l
    ON b.branch_id = l.branch_id
WHERE l.loan_id IS NULL;


-- QUERY 6:
-- Find customers whose total loan exposure
-- exceeds their branch's average customer exposure.
-- CORRELATED SUBQUERY compares each customer's exposure
-- with the average exposure per customer in that branch.

SELECT
    c.customer_id,
    c.customer_name,
    l.branch_id,
    SUM(l.loan_amount) AS total_exposure
FROM customers c
INNER JOIN loans l
    ON c.customer_id = l.customer_id
GROUP BY c.customer_id, c.customer_name, l.branch_id
HAVING SUM(l.loan_amount) >
(
    SELECT AVG(customer_exposure)
    FROM
    (
        SELECT
            customer_id,
            SUM(loan_amount) AS customer_exposure
        FROM loans
        WHERE branch_id = l.branch_id
        GROUP BY customer_id
    ) AS branch_customers
);


-- QUERY 7:
-- Find customers whose total loan exposure
-- exceeds the overall average customer exposure.
-- UNCORRELATED SUBQUERY executes independently.

SELECT
    c.customer_id,
    c.customer_name,
    SUM(l.loan_amount) AS total_exposure
FROM customers c
INNER JOIN loans l
    ON c.customer_id = l.customer_id
GROUP BY c.customer_id, c.customer_name
HAVING SUM(l.loan_amount) >
(
    SELECT AVG(customer_total)
    FROM
    (
        SELECT
            customer_id,
            SUM(loan_amount) AS customer_total
        FROM loans
        GROUP BY customer_id
    ) AS customer_totals
);


-- QUERY 8:
-- Find customers who have a loan but no repayment
-- in the last 60 days.
-- EXISTS checks whether a loan exists.
-- NOT EXISTS checks the absence of recent repayments.

SELECT
    c.customer_id,
    c.customer_name
FROM customers c
WHERE EXISTS
(
    SELECT 1
    FROM loans l
    WHERE l.customer_id = c.customer_id
)
AND NOT EXISTS
(
    SELECT 1
    FROM loans l
    INNER JOIN repayments r
        ON l.loan_id = r.loan_id
    WHERE l.customer_id = c.customer_id
      AND r.repayment_date >=
          DATE_SUB(CURDATE(), INTERVAL 60 DAY)
      AND r.repayment_date <= CURDATE()
);


-- QUERY 9:
-- Find branches whose highest loan amount
-- is greater than the average of all branch maximum loans.
-- Nested subquery calculates branch maximums first.

SELECT
    b.branch_id,
    b.branch_name,
    MAX(l.loan_amount) AS highest_loan
FROM branches b
INNER JOIN loans l
    ON b.branch_id = l.branch_id
GROUP BY b.branch_id, b.branch_name
HAVING MAX(l.loan_amount) >
(
    SELECT AVG(branch_maximum)
    FROM
    (
        SELECT
            branch_id,
            MAX(loan_amount) AS branch_maximum
        FROM loans
        GROUP BY branch_id
    ) AS branch_maximums
);


-- QUERY 10:
-- Find customers who have loans in a branch
-- where their loan amount is greater than
-- the average loan amount of that branch.
-- Correlated subquery compares loans branch-wise.

SELECT
    c.customer_id,
    c.customer_name,
    l.loan_id,
    l.branch_id,
    l.loan_amount
FROM customers c
INNER JOIN loans l
    ON c.customer_id = l.customer_id
WHERE l.loan_amount >
(
    SELECT AVG(l2.loan_amount)
    FROM loans l2
    WHERE l2.branch_id = l.branch_id
);

