
# Retail Order Fulfilment – SQL Assignment

## 1. Introduction

The Retail Order Fulfilment system implements business rules at the database level using MySQL.

The main objectives are:

1. Create Views to display order and stock information.
2. Create Stored Procedures to place orders and manage stock.
3. Create a Stored Function to calculate order totals with tax.
4. Use Cursors to process pending orders.
5. Handle business errors using SIGNAL SQLSTATE '45000'.

---

## 2. Database Structure

The project uses the following tables:

| Table Name | Description |
|---|---|
| customers | Stores customer details |
| products | Stores product and stock details |
| orders | Stores order information |
| order_items | Stores products belonging to each order |
| order_followups | Stores follow-up details for pending orders |

### Table Relationships

- One customer can place multiple orders.
- One order can contain multiple order items.
- One product can appear in multiple order items.
- One order can have a follow-up record.

All tables should use the InnoDB storage engine to support transactions and foreign keys.

---

## 3. Task 1 – Views

### 3.1 vw_order_summary

This view displays order details by joining the orders, order_items, and products tables.

**Purpose:**
- Displays order ID and customer ID.
- Displays product name and quantity.
- Calculates the total amount.
- Hides cost_price and supplier_id columns.

### 3.2 vw_low_stock

This view displays products whose available stock is less than or equal to the reorder level.

**Condition:**

```sql
stock_qty <= reorder_level
```

### 3.3 Updatable View with WITH CHECK OPTION

An updatable view allows users to modify records through the view.

The WITH CHECK OPTION ensures that updated records continue to satisfy the view condition.

**Example:**

```sql
UPDATE vw_stock_update
SET stock_qty = 2
WHERE product_id = 2;
```

This update is allowed if the updated stock satisfies the view condition.

An update that changes stock to 100 is rejected if it violates the condition.

**Advantages of Views:**
- Provides data security.
- Hides sensitive columns.
- Simplifies complex queries.
- Provides reusable SQL statements.

---

## 4. Task 2 – Stored Procedure

### Procedure Name: sp_place_order

A stored procedure is a group of SQL statements stored in the database and executed whenever required.

The sp_place_order procedure handles the order placement process.

### Parameters

| Parameter | Type | Purpose |
|---|---|---|
| p_customer_id | IN | Receives customer ID |
| p_product_id | IN | Receives product ID |
| p_quantity | IN | Receives product quantity |
| p_order_no | OUT | Returns generated order number |

### Execution Steps

1. Starts a transaction.
2. Validates the customer ID.
3. Validates the requested quantity.
4. Checks available product stock.
5. Inserts a new order into the orders table.
6. Inserts order details into the order_items table.
7. Updates the available product stock.
8. Returns the generated order number.
9. Commits the transaction.

### Transaction Handling

The procedure uses:

- START TRANSACTION – Begins a transaction.
- COMMIT – Saves all successful changes.
- ROLLBACK – Cancels changes if an error occurs.
- DECLARE EXIT HANDLER – Handles SQL exceptions.
- RESIGNAL – Sends the original error back to the caller.
- FOR UPDATE – Locks the selected product row during the transaction.

### Procedure Execution

```sql
SET @order_no = 0;

CALL sp_place_order(1, 1, 2, @order_no);

SELECT @order_no;
```

**Advantages:**
- Centralizes business logic.
- Maintains data consistency.
- Reduces repeated SQL code.
- Supports transaction management.

---

## 5. Task 3 – Stored Function

### Function Name: fn_order_total_with_tax

A stored function is a database object that performs a calculation and returns a value.

The function calculates the total order amount, including 18% tax.

### Calculation

```text
Order Total = Quantity × Unit Price

Tax Amount = Order Total × 18%

Final Total = Order Total + Tax Amount
```

### Function Execution

```sql
SELECT
    order_id,
    fn_order_total_with_tax(order_id) AS total_with_tax
FROM orders;
```

### Functions vs Stored Procedures

| Function | Stored Procedure |
|---|---|
| Must return a value | Does not need to return a value |
| Uses RETURN | Can use OUT parameters |
| Can be called inside SELECT | Called using CALL |
| Mainly used for calculations | Used for business operations |
| Cannot manage transactions explicitly | Can manage transactions |

---

## 6. Task 4 – Cursor

### Procedure Name: sp_pending_order_followups

A cursor is used to process query results one row at a time.

The procedure identifies orders that are pending for more than three days and inserts follow-up records.

### Cursor Components

| Component | Purpose |
|---|---|
| DECLARE CURSOR | Defines the query |
| DECLARE HANDLER | Handles the NOT FOUND condition |
| OPEN | Opens the cursor |
| FETCH | Retrieves one row |
| CLOSE | Closes the cursor |

### Processing Steps

1. Selects pending orders older than three days.
2. Opens the cursor.
3. Fetches one order ID at a time.
4. Checks whether more records are available.
5. Inserts a follow-up record for each order.
6. Closes the cursor.

### NOT FOUND Handler

The NOT FOUND handler identifies when the cursor has no more records.

```sql
DECLARE CONTINUE HANDLER FOR NOT FOUND
SET v_done = 1;
```

When all records are processed, the loop terminates.

### Cursor Execution

```sql
CALL sp_pending_order_followups();

SELECT * FROM order_followups;
```

---

## 7. Cursor vs Set-Based Approach

A set-based query processes multiple records in a single SQL statement instead of processing them individually.

### Set-Based SQL Statement

```sql
INSERT IGNORE INTO order_followups
(order_id, remarks)
SELECT
    order_id,
    'Order pending for more than 3 days'
FROM orders
WHERE status = 'PENDING'
AND order_date < NOW() - INTERVAL 3 DAY;
```

### Comparison

| Cursor | Set-Based |
|---|---|
| Processes records one by one | Processes multiple records together |
| Uses OPEN, FETCH and CLOSE | Uses INSERT SELECT |
| Requires NOT FOUND handler | Does not require cursor handler |
| More procedural code | Less code |
| Generally slower for bulk operations | Generally faster for bulk operations |
| Useful for complex row-level processing | Useful for bulk database operations |

### Conclusion

Set-based operations are generally preferred because they are faster, simpler, and more efficient for bulk data processing.

Cursors are useful when individual records require different processing logic.

---

## 8. Task 5 – Business Error Handling

### SIGNAL SQLSTATE '45000'

SIGNAL is used to generate custom business errors in MySQL.

The SQLSTATE value 45000 represents a user-defined exception.

### Example

```sql
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = 'Insufficient stock';
```

### Business Validation

The order procedure checks whether sufficient stock is available.

If the requested quantity exceeds the available stock, it raises a custom error.

### Example Caller

```sql
SET @order_no = 0;

CALL sp_place_order(1, 1, 1000, @order_no);
```

### Expected Error

```text
Error Code: 1644

Insufficient stock
```

The EXIT HANDLER rolls back the transaction, and RESIGNAL passes the error to the caller.

**Advantages:**
- Prevents invalid business operations.
- Provides meaningful error messages.
- Maintains database consistency.
- Helps applications handle database errors.

---

## 9. Verification Queries

The following queries can be used to verify the database objects and results.

### Display Tables

```sql
SHOW TABLES;
```

### Display Views

```sql
SHOW FULL TABLES
WHERE Table_type = 'VIEW';
```

### Display Order Summary

```sql
SELECT * FROM vw_order_summary;
```

### Display Low Stock Products

```sql
SELECT * FROM vw_low_stock;
```

### Display Orders

```sql
SELECT * FROM orders;
```

### Display Order Items

```sql
SELECT * FROM order_items;
```

### Display Follow-up Records

```sql
SELECT * FROM order_followups;
```

### Check Stored Procedures

```sql
SHOW PROCEDURE STATUS
WHERE Db = 'retail_fulfilment';
```

### Check Stored Functions

```sql
SHOW FUNCTION STATUS
WHERE Db = 'retail_fulfilment';
```

---

## 10. Execution Order

Execute the SQL files in the following order:

| Order | File Name | Description |
|---|---|---|
| 1 | views.sql | Creates the required views |
| 2 | procedures.sql | Creates stored procedure and function |
| 3 | cursor.sql | Creates cursor and set-based query |
| 4 | cursor_comparison.sql | Displays cursor comparison |

**Prerequisite:** Create the database and required tables before executing these files.

---

## 11. Important Notes

1. The implementation uses MySQL 8.0 syntax.
2. All participating tables should use InnoDB.
3. The customer and product IDs used in procedure calls must exist.
4. Sufficient stock must be available for a successful order.
5. The invalid view update is intentional and should raise an error.
6. The tax rate of 18% is an assumption for this assignment.
7. The cursor and set-based approaches are alternative methods for inserting follow-up records.
8. A unique constraint on order_id in order_followups prevents duplicate follow-up records.
9. The SIGNAL statement demonstrates custom business error handling.

---

## 12. Conclusion

The Retail Order Fulfilment system demonstrates how advanced MySQL concepts can be used to implement business rules at the database level.

Views provide secure and reusable data access. Stored procedures manage order placement and transactions. Stored functions perform reusable calculations. Cursors process records individually, while set-based statements provide an efficient alternative for bulk operations.

The SIGNAL statement provides meaningful business error messages and helps maintain data integrity.

By implementing these concepts, the database becomes more secure, reusable, consistent, and efficient.
