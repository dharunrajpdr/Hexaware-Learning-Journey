-- BANK BRANCH ACCOUNTS
-- Normalized relational design (3NF / BCNF)

CREATE DATABASE IF NOT EXISTS bank_branch_accounts;
USE bank_branch_accounts;

-- 1. CUSTOMERS
CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    address_line VARCHAR(150) NOT NULL,
    city VARCHAR(60) NOT NULL,
    state VARCHAR(60) NOT NULL,
    postal_code CHAR(6) NOT NULL
) ENGINE = InnoDB;

-- 2. BRANCHES
CREATE TABLE branches (
    branch_id INT AUTO_INCREMENT PRIMARY KEY,
    branch_name VARCHAR(100) NOT NULL,
    branch_code VARCHAR(10) NOT NULL,
    address_line VARCHAR(150) NOT NULL,
    city VARCHAR(60) NOT NULL,
    CONSTRAINT uq_branch_code UNIQUE (branch_code)
) ENGINE = InnoDB;

-- 3. RELATIONSHIP MANAGERS
CREATE TABLE relationship_managers (
    manager_id INT AUTO_INCREMENT PRIMARY KEY,
    manager_name VARCHAR(100) NOT NULL,
    branch_id INT NOT NULL,

    -- Composite alternate key supports the branch-aware FK below.
    CONSTRAINT uq_manager_branch UNIQUE (manager_id, branch_id),

    CONSTRAINT fk_manager_branch
        FOREIGN KEY (branch_id)
        REFERENCES branches(branch_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE = InnoDB;

-- 4. ACCOUNTS
CREATE TABLE accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL,
    customer_id INT NOT NULL,
    account_type VARCHAR(20) NOT NULL,
    branch_id INT NOT NULL,
    manager_id INT NOT NULL,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    opened_date DATE NOT NULL,

    -- account_number is an alternate candidate key.
    CONSTRAINT uq_account_number UNIQUE (account_number),

    CONSTRAINT chk_account_balance
        CHECK (balance >= 0.00),

    CONSTRAINT chk_account_type
        CHECK (account_type IN ('SAVINGS', 'CURRENT', 'FIXED_DEPOSIT')),

    CONSTRAINT fk_account_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT fk_account_branch
        FOREIGN KEY (branch_id)
        REFERENCES branches(branch_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    -- Ensures the assigned relationship manager belongs to this branch.
    CONSTRAINT fk_account_manager_branch
        FOREIGN KEY (manager_id, branch_id)
        REFERENCES relationship_managers(manager_id, branch_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE = InnoDB;

-- 5. ALTER TABLE demonstrations
-- Add KYC status to the accounts table.
ALTER TABLE accounts
    ADD COLUMN kyc_status VARCHAR(15) NOT NULL DEFAULT 'PENDING';

-- Rename a table.
ALTER TABLE relationship_managers
    RENAME TO branch_managers;

-- Drop a constraint (demonstration only).
-- This removes the account-type validation. Run only if you need to
-- demonstrate DROP CONSTRAINT; recreate it afterwards if required.
-- ALTER TABLE accounts DROP CHECK chk_account_type;
