
USE retail_inventory;

-- ==========================================
-- INSERT SALES DATA
-- ==========================================

INSERT INTO orders (customer_name, order_date)
VALUES
('Arun', '2026-01-10'),
('Kumar', '2026-01-15'),
('Rahul', '2026-02-10'),
('Vijay', '2026-02-15'),
('Dharun', '2026-03-10'),
('Arun', '2026-03-15');

SET @o1 = 2;
SET @o2 = 3;
SET @o3 = 4;
SET @o4 = 5;
SET @o5 = 6;
SET @o6 = 7;

-- Insert order items
INSERT INTO order_items
(order_id, product_id, quantity, price)
VALUES
(@o1, 101, 2, 50000),
(@o1, 102, 3, 20000),
(@o2, 101, 1, 50000),
(@o2, 103, 5, 2000),
(@o3, 102, 4, 20000),
(@o3, 103, 3, 2000),
(@o4, 104, 5, 1000),
(@o4, 105, 2, 1500),
(@o5, 101, 3, 50000),
(@o5, 104, 4, 1000),
(@o6, 102, 2, 20000),
(@o6, 105, 3, 1500);


-- ==========================================
-- TASK 3: CTE WITH ROW_NUMBER()
-- TOP 3 PRODUCTS PER CATEGORY
-- ==========================================

WITH product_sales AS (

    SELECT
        p.product_id,
        p.product_name,
        c.category_name,
        SUM(oi.quantity * oi.price) AS total_sales

    FROM products p

    JOIN categories c
        ON p.category_id = c.category_id

    JOIN order_items oi
        ON p.product_id = oi.product_id

    GROUP BY
        p.product_id,
        p.product_name,
        c.category_name
),

ranked_products AS (

    SELECT
        product_id,
        product_name,
        category_name,
        total_sales,

        ROW_NUMBER() OVER (
            PARTITION BY category_name
            ORDER BY total_sales DESC
        ) AS sales_rank

    FROM product_sales
)

SELECT *
FROM ranked_products
WHERE sales_rank <= 3
ORDER BY category_name, sales_rank;


-- ==========================================
-- TASK 4: LEAD() AND LAG()
-- MONTH-ON-MONTH SALES CHANGE
-- ==========================================

WITH monthly_sales AS (

    SELECT
        oi.product_id,
        p.product_name,

        DATE_FORMAT(o.order_date, '%Y-%m') AS sales_month,

        SUM(oi.quantity * oi.price) AS total_sales

    FROM order_items oi

    JOIN orders o
        ON oi.order_id = o.order_id

    JOIN products p
        ON oi.product_id = p.product_id

    GROUP BY
        oi.product_id,
        p.product_name,
        DATE_FORMAT(o.order_date, '%Y-%m')
),

sales_comparison AS (

    SELECT
        product_id,
        product_name,
        sales_month,
        total_sales,

        LAG(total_sales) OVER (
            PARTITION BY product_id
            ORDER BY sales_month
        ) AS previous_month_sales,

        LEAD(total_sales) OVER (
            PARTITION BY product_id
            ORDER BY sales_month
        ) AS next_month_sales

    FROM monthly_sales
)

SELECT
    product_id,
    product_name,
    sales_month,
    total_sales,
    previous_month_sales,
    next_month_sales,

    total_sales - previous_month_sales
        AS month_on_month_change

FROM sales_comparison
ORDER BY product_id, sales_month;
