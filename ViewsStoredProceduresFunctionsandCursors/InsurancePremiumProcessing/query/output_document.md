
# Insurance Premium Processing – SQL Assignment

## 1. Introduction

The Insurance Premium Processing system automates policy renewal and premium calculations using MySQL.

The main objectives are:

1. Create Views to display policies due for renewal.
2. Create Stored Procedures to renew policies with validation and transaction management.
3. Create Stored Functions to calculate premiums with no-claim discounts.
4. Use Cursors to process policies with overdue premiums.
5. Use INOUT parameters to return the number of processed policies.

---

## 2. Database Structure

The project uses the following tables:

| Table Name | Description |
|---|---|
| customers | Stores customer information |
| policies | Stores insurance policy details |
| claims | Stores customer claim information |
| premium_audit | Stores premium renewal history |
| policy_status_log | Stores policy status changes |

### Table Relationships

- One customer can have multiple insurance policies.
- One policy can have multiple claims.
- One policy can have multiple audit records.
- One policy can have multiple status log records.

All tables use the InnoDB storage engine to support transactions and foreign key relationships.

---

## 3. Task 1 – Views

### View Name: vw_policy_due

A View is a virtual table created using a SQL SELECT statement.

The vw_policy_due view displays policies that are due within the next 30 days along with customer information.

### Information Displayed

- Policy ID
- Customer ID
- Customer name
- Email address
- Phone number
- Policy type
- Premium amount
- Policy start date
- Policy end date
- Next due date
- Policy status

### Sensitive Information Protection

The view does not display the sensitive_id column.

### Condition Used

```sql
next_due_date BETWEEN CURDATE()
AND DATE_ADD(CURDATE(), INTERVAL 30 DAY)
```

Only active policies are included in the view.

### Advantages of Views

1. Protects sensitive information.
2. Simplifies complex queries.
3. Provides reusable database objects.
4. Improves data security.

---

## 4. Task 2 – Stored Procedure

### Procedure Name: sp_renew_policy

A Stored Procedure is a collection of SQL statements stored in the database and executed whenever required.

The sp_renew_policy procedure manages the insurance policy renewal process.

### Parameter

| Parameter | Type | Purpose |
|---|---|---|
| p_policy_id | IN | Receives the policy ID to renew |

### Processing Steps

1. Starts a database transaction.
2. Checks whether the policy exists.
3. Validates whether the policy is active.
4. Checks whether any pending claims exist.
5. Recalculates the premium amount.
6. Updates the premium in the policies table.
7. Updates the next due date.
8. Inserts the old and new premium amounts into the audit table.
9. Commits the transaction.

### Premium Recalculation

For this assignment, a 10% premium increase is assumed.

```text
New Premium = Old Premium + (Old Premium × 10%)
```

### Transaction Management

The procedure uses the following commands:

| Command | Purpose |
|---|---|
| START TRANSACTION | Begins the transaction |
| COMMIT | Saves successful changes |
| ROLLBACK | Cancels changes after an error |
| DECLARE EXIT HANDLER | Handles SQL exceptions |
| SIGNAL | Generates a custom business error |
| RESIGNAL | Returns the original error to the caller |
| FOR UPDATE | Locks the selected policy row |

### Procedure Execution

```sql
CALL sp_renew_policy(1);
```

Policy ID 1 can be renewed successfully if it is active and has no pending claims.

Policy ID 2 raises a business error because it has a pending claim.

### Advantages of Stored Procedures

1. Centralizes business logic.
2. Maintains data consistency.
3. Supports transaction management.
4. Reduces repeated SQL code.
5. Improves database security.

---

## 5. Task 3 – Stored Function

### Function Name: fn_premium_with_no_claim_discount

A Stored Function performs a calculation and returns a value.

The fn_premium_with_no_claim_discount function calculates the premium after applying a discount when the policy has no pending or approved claims.

### Business Rule

For this assignment, a 10% discount is assumed.

```text
If there are no pending or approved claims:

Discounted Premium = Premium × 90%

Otherwise:

Discounted Premium = Original Premium
```

### Function Processing

1. Receives the policy ID.
2. Retrieves the existing premium amount.
3. Checks the claims table.
4. Counts pending and approved claims.
5. Applies a 10% discount if no qualifying claims exist.
6. Returns the calculated premium.

### Function Usage in Report

```sql
SELECT
    policy_id,
    policy_type,
    premium_amount AS original_premium,
    fn_premium_with_no_claim_discount(policy_id)
        AS discounted_premium
FROM policies;
```

### Functions vs Stored Procedures

| Function | Stored Procedure |
|---|---|
| Must return a value | Does not need to return a value |
| Uses RETURN | Can use OUT or INOUT parameters |
| Can be used inside SELECT | Executed using CALL |
| Mainly used for calculations | Used for business operations |
| Cannot manage transactions explicitly | Can manage transactions |

---

## 6. Task 4 – Cursor

### Procedure Name: sp_lapse_policies

A Cursor is used to process query results one record at a time.

The sp_lapse_policies procedure identifies policies whose premium due date has passed and changes their status to LAPSED.

### Business Condition

```sql
next_due_date < CURDATE()
AND status = 'ACTIVE'
```

### Cursor Components

| Component | Purpose |
|---|---|
| DECLARE CURSOR | Defines the SELECT query |
| DECLARE HANDLER | Handles the NOT FOUND condition |
| OPEN | Opens the cursor |
| FETCH | Retrieves one policy at a time |
| CLOSE | Closes the cursor |

### Processing Steps

1. Selects active policies with overdue premiums.
2. Opens the cursor.
3. Fetches one policy ID at a time.
4. Checks whether more records are available.
5. Updates the policy status to LAPSED.
6. Inserts the old and new status into the policy status log.
7. Continues until all eligible policies are processed.
8. Closes the cursor.

### NOT FOUND Handler

The NOT FOUND handler identifies when there are no more records to fetch.

```sql
DECLARE CONTINUE HANDLER FOR NOT FOUND
SET v_done = 1;
```

When all policies are processed, the loop terminates.

---

## 7. Task 5 – INOUT Parameter

An INOUT parameter is used to receive an initial value and return the modified value to the caller.

In this assignment, the INOUT parameter maintains the number of policies processed by the cursor.

### Parameter

| Parameter | Type | Purpose |
|---|---|---|
| p_processed_count | INOUT | Receives and returns the processed policy count |

### Execution

```sql
SET @processed_count = 0;

CALL sp_lapse_policies(@processed_count);

SELECT @processed_count AS total_processed_policies;
```

### Working

1. The caller initializes the count to zero.
2. The procedure receives the count.
3. Each processed policy increases the count by one.
4. The final count is returned to the caller.

### Example Output

```text
total_processed_policies
------------------------
1
```

The actual output depends on the number of eligible policies in the database.

---

## 8. Cursor vs Set-Based Approach

A Set-Based approach processes multiple records using a single SQL statement instead of processing them individually.

### Set-Based Operations

The set-based implementation performs two operations:

1. Inserts log records for all eligible policies.
2. Updates all eligible policies to LAPSED.

### Comparison

| Cursor | Set-Based |
|---|---|
| Processes policies one by one | Processes multiple policies together |
| Uses OPEN, FETCH and CLOSE | Uses INSERT SELECT and UPDATE |
| Requires NOT FOUND handler | Does not require cursor handler |
| More procedural code | Less code |
| Generally slower for bulk processing | Generally faster for bulk processing |
| Useful for individual record processing | Useful for bulk database operations |

### Conclusion

Set-based SQL is generally preferred because it is faster and more efficient for processing large numbers of records.

Cursors are useful when each record requires individual processing.

---

## 9. Transaction and Error Handling

The assignment uses transaction handling to ensure that policy renewal and audit insertion are completed together.

### Transaction Commands

- START TRANSACTION – Starts the transaction.
- COMMIT – Saves all successful changes.
- ROLLBACK – Reverses changes when an error occurs.

### Business Error Handling

The SIGNAL statement generates custom errors.

Example:

```sql
SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = 'Policy has pending claims';
```

If a policy contains pending claims, the procedure stops and returns the business error.

The EXIT HANDLER performs ROLLBACK, and RESIGNAL passes the original error to the caller.

### Expected Error

```text
Error Code: 1644

Policy has pending claims
```

---

## 10. Verification Queries

The following queries verify the database objects and results.

### Display Tables

```sql
SHOW TABLES;
```

### Display Views

```sql
SHOW FULL TABLES
WHERE Table_type = 'VIEW';
```

### Display Policies Due

```sql
SELECT * FROM vw_policy_due;
```

### Display Policies

```sql
SELECT * FROM policies;
```

### Display Premium Audit Records

```sql
SELECT * FROM premium_audit;
```

### Display Policy Status Logs

```sql
SELECT * FROM policy_status_log;
```

### Display Claims

```sql
SELECT * FROM claims;
```

### Check Stored Procedures

```sql
SHOW PROCEDURE STATUS
WHERE Db = 'insurance_db';
```

### Check Stored Functions

```sql
SHOW FUNCTION STATUS
WHERE Db = 'insurance_db';
```

---

## 11. Execution Order

Execute the SQL files in the following order:

| Order | File Name | Description |
|---|---|---|
| 1 | setup.sql | Creates database and tables |
| 2 | views.sql | Creates policy due view |
| 3 | procedures.sql | Creates renewal procedure and premium function |
| 4 | cursor.sql | Creates cursor procedure and set-based alternative |
| 5 | comparison_note.sql | Cursor comparison |

### Important Notes

1. The implementation uses MySQL 8.0.
2. All tables use InnoDB.
3. The customer and policy IDs used in procedure calls must exist.
4. Policy renewal is allowed only for active policies without pending claims.
5. The premium increase of 10% is an assumption for this assignment.
6. The no-claim discount of 10% is an assumption for this assignment.
7. The cursor processes active policies whose due date has passed.
8. The INOUT parameter returns the number of policies processed.
9. The cursor and set-based approaches are alternatives. Running the cursor first changes eligible policies to LAPSED, so the subsequent set-based query will not process those same policies again.
10. Run setup.sql only once unless you recreate the database.

---

## 12. Project Structure

```text
Insurance-Premium-Processing/
│
├── setup.sql
├── views.sql
├── procedures.sql
├── cursor.sql
├── comparison_note.sql
└── output_document.md
```

---

## 13. Conclusion

The Insurance Premium Processing system demonstrates how advanced MySQL concepts can automate insurance policy renewal and premium management.

Views provide secure access to policy information. Stored Procedures implement policy renewal with validation and transaction management. Stored Functions calculate premiums with no-claim discounts. Cursors process policies with overdue premiums, while set-based SQL provides an efficient alternative.

The INOUT parameter returns the number of processed policies, and transaction handling ensures data consistency.

By implementing these concepts, the insurance database becomes more secure, reliable, reusable, and efficient.
