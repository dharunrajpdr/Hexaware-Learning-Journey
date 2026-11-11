-- Retail Inventory Management

CREATE DATABASE IF NOT EXISTS retail_inventory;
USE retail_inventory;

-- 1. Suppliers
CREATE TABLE suppliers (
    supplier_id INT AUTO_INCREMENT PRIMARY KEY,
    supplier_name VARCHAR(100) NOT NULL,
    supplier_phone VARCHAR(15) NOT NULL,
    CONSTRAINT uq_supplier_phone UNIQUE (supplier_phone)
) ENGINE = InnoDB;

-- 2. Categories
CREATE TABLE categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    CONSTRAINT uq_category_name UNIQUE (category_name)
) ENGINE = InnoDB;

-- 3. Warehouses
CREATE TABLE warehouses (
    warehouse_id INT AUTO_INCREMENT PRIMARY KEY,
    warehouse_name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL,
    CONSTRAINT uq_warehouse_name UNIQUE (warehouse_name)
) ENGINE = InnoDB;

-- 4. Products
CREATE TABLE products (
    sku VARCHAR(20) PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    category_id INT NOT NULL,
    supplier_id INT NOT NULL,

    CONSTRAINT fk_product_category
        FOREIGN KEY (category_id)
        REFERENCES categories(category_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT fk_product_supplier
        FOREIGN KEY (supplier_id)
        REFERENCES suppliers(supplier_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE = InnoDB;

-- 5. Inventory
CREATE TABLE inventory (
    sku VARCHAR(20) NOT NULL,
    warehouse_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    reorder_level INT NOT NULL DEFAULT 10,

    PRIMARY KEY (sku, warehouse_id),

    CONSTRAINT chk_inventory_quantity
        CHECK (quantity >= 0),

    CONSTRAINT chk_inventory_reorder_level
        CHECK (reorder_level >= 0),

    CONSTRAINT fk_inventory_product
        FOREIGN KEY (sku)
        REFERENCES products(sku)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT fk_inventory_warehouse
        FOREIGN KEY (warehouse_id)
        REFERENCES warehouses(warehouse_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE = InnoDB;
