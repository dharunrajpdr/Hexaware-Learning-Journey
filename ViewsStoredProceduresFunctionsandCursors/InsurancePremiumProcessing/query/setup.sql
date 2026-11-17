
CREATE DATABASE IF NOT EXISTS insurance_db;
USE insurance_db;

CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(15)
) ENGINE=InnoDB;

CREATE TABLE policies (
    policy_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    policy_type VARCHAR(50),
    premium_amount DECIMAL(10,2),
    policy_start DATE,
    policy_end DATE,
    next_due_date DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    sensitive_id VARCHAR(50),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
) ENGINE=InnoDB;

CREATE TABLE claims (
    claim_id INT PRIMARY KEY AUTO_INCREMENT,
    policy_id INT,
    claim_amount DECIMAL(10,2),
    claim_status VARCHAR(20),
    FOREIGN KEY (policy_id) REFERENCES policies(policy_id)
) ENGINE=InnoDB;

CREATE TABLE premium_audit (
    audit_id INT PRIMARY KEY AUTO_INCREMENT,
    policy_id INT,
    old_premium DECIMAL(10,2),
    new_premium DECIMAL(10,2),
    renewal_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    remarks VARCHAR(255),
    FOREIGN KEY (policy_id) REFERENCES policies(policy_id)
) ENGINE=InnoDB;

CREATE TABLE policy_status_log (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    policy_id INT,
    old_status VARCHAR(20),
    new_status VARCHAR(20),
    changed_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    remarks VARCHAR(255),
    FOREIGN KEY (policy_id) REFERENCES policies(policy_id)
) ENGINE=InnoDB;


-- Sample customers
INSERT INTO customers(customer_name, email, phone)
VALUES
('Arun', 'arun@gmail.com', '9876543210'),
('Ram', 'ram@gmail.com', '9876543211'),
('Kumar', 'kumar@gmail.com', '9876543212');


-- Sample policies
INSERT INTO policies
(customer_id, policy_type, premium_amount,
 policy_start, policy_end, next_due_date, status, sensitive_id)
VALUES
(1, 'Health', 10000, '2025-01-01', '2027-12-31',
 DATE_ADD(CURDATE(), INTERVAL 15 DAY), 'ACTIVE', 'ID101'),

(2, 'Life', 15000, '2025-01-01', '2027-12-31',
 DATE_ADD(CURDATE(), INTERVAL 30 DAY), 'ACTIVE', 'ID102'),

(3, 'Vehicle', 8000, '2024-01-01', '2025-12-31',
 DATE_SUB(CURDATE(), INTERVAL 10 DAY), 'ACTIVE', 'ID103');


-- Sample claims
INSERT INTO claims(policy_id, claim_amount, claim_status)
VALUES
(1, 2000, 'APPROVED'),
(2, 5000, 'PENDING'),
(3, 1000, 'REJECTED');
