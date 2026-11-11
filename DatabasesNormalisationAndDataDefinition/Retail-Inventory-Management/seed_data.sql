-- Retail Inventory Management sample data
USE retail_inventory;

-- 1. Suppliers (10 rows)
INSERT INTO suppliers (supplier_name, supplier_phone) VALUES
('ABC Traders', '9876543210'),
('XYZ Supplies', '9876501234'),
('Global Electronics', '9876512345'),
('Tech World', '9876523456'),
('Smart Distributors', '9876534567'),
('Prime Suppliers', '9876545678'),
('Digital Hub', '9876556789'),
('Metro Traders', '9876567890'),
('Future Tech', '9876578901'),
('Elite Products', '9876589012');

-- 2. Categories (10 rows)
INSERT INTO categories (category_name) VALUES
('Electronics'),
('Accessories'),
('Furniture'),
('Stationery'),
('Home Appliances'),
('Clothing'),
('Footwear'),
('Sports'),
('Books'),
('Kitchenware');

-- 3. Warehouses (10 rows)
INSERT INTO warehouses (warehouse_name, location) VALUES
('Chennai Warehouse', 'Chennai'),
('Madurai Warehouse', 'Madurai'),
('Coimbatore Warehouse', 'Coimbatore'),
('Salem Warehouse', 'Salem'),
('Trichy Warehouse', 'Tiruchirappalli'),
('Erode Warehouse', 'Erode'),
('Karur Warehouse', 'Karur'),
('Bangalore Warehouse', 'Bengaluru'),
('Hyderabad Warehouse', 'Hyderabad'),
('Mumbai Warehouse', 'Mumbai');

-- 4. Products (10 rows)
INSERT INTO products (sku, product_name, category_id, supplier_id) VALUES
('P101', 'Laptop', 1, 1),
('P102', 'Wireless Mouse', 2, 2),
('P103', 'Keyboard', 2, 3),
('P104', 'Office Chair', 3, 4),
('P105', 'Notebook', 4, 5),
('P106', 'Refrigerator', 5, 6),
('P107', 'Cotton T-Shirt', 6, 7),
('P108', 'Running Shoes', 7, 8),
('P109', 'Football', 8, 9),
('P110', 'Cooking Pan', 10, 10);

-- 5. Inventory (10 rows)
INSERT INTO inventory (sku, warehouse_id, quantity, reorder_level) VALUES
('P101', 1, 50, 10),
('P102', 1, 100, 20),
('P103', 2, 75, 15),
('P104', 3, 30, 5),
('P105', 4, 200, 50),
('P106', 5, 15, 5),
('P107', 6, 120, 25),
('P108', 7, 60, 10),
('P109', 8, 45, 10),
('P110', 9, 80, 20);

-- Verify row counts
SELECT 'suppliers' AS table_name, COUNT(*) AS total_rows FROM suppliers
UNION ALL
SELECT 'categories', COUNT(*) FROM categories
UNION ALL
SELECT 'warehouses', COUNT(*) FROM warehouses
UNION ALL
SELECT 'products', COUNT(*) FROM products
UNION ALL
SELECT 'inventory', COUNT(*) FROM inventory;
