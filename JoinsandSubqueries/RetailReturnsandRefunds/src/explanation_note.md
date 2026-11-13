
# Retail Returns and Refunds
## Advanced SQL – Explanation Note

### 1. Products with Return Rate Above 10%
- Used INNER JOIN to connect products with order_items.
- Used LEFT JOIN to include products even when they have no returns.
- Used SUM() to calculate sold and returned quantities.
- Used HAVING to filter return rates above 10%.

### 2. Customers with Returns but No Refund
- Used INNER JOIN to connect customers, orders and returns.
- Used LEFT JOIN with refunds to identify missing refund records.
- Used IS NULL to find returns without refunds.

### 3. Stores with Highest Refund Value
- Used INNER JOIN to connect stores, orders, returns and refunds.
- Used SUM() to calculate total refund value.
- Used GROUP BY to group refunds by store.
- Used ORDER BY DESC to display the highest refund first.

### 4. Employees and Their Managers
- Used SELF JOIN on the employees table.
- The first alias represents employees.
- The second alias represents managers.
- Used LEFT JOIN to include employees without managers.

### 5. Stores Without Returns
- Used NOT EXISTS to identify stores that have no return records.
- This avoids unnecessary NULL handling.

### 6. NOT IN NULL Pitfall and NOT EXISTS
- Demonstrated how NOT IN behaves when its subquery contains NULL.
- A NULL in the subquery can make the comparison UNKNOWN.
- Used NOT EXISTS as the safer alternative.
- It correctly identifies customers without orders.

### 7. ANY Operator
- Used ANY to compare a store's refund value with refund values of other stores in the same region.
- The condition is TRUE if the refund value is greater than at least one other store's refund value.

### 8. ALL Operator
- Used ALL to compare a store's refund value with every other store in the same region.
- The condition is TRUE only if the refund value is greater than all other stores' refund values.

### 9. Nested Subquery – Product Price
- Used an inner query to calculate the average product price.
- Used an outer query to find products priced above that average.

### 10. Nested Subquery – Store Refund
- Used an inner query to calculate each store's total refund.
- Used another nested query to calculate the average total refund across stores.
- The outer query identifies stores whose refunds exceed that average.

---

## Performance Considerations for 100x Data Growth

| Query | Performance improvement |
|---|---|
| 1 | Add indexes on product_id and order_id. Pre-aggregate sales and returns separately. |
| 2 | Index customer_id, order_id and return_id. |
| 3 | Index store_id and refund relationships. |
| 4 | Index manager_id in employees. |
| 5 | Index store_id and order_id for faster EXISTS checks. |
| 6 | Index customer_id in orders and prefer NOT EXISTS. |
| 7 | Pre-calculate store refund totals and index region. |
| 8 | Pre-calculate regional refund totals and use indexed summary data. |
| 9 | Index price if useful; maintain statistics and evaluate execution plans. |
| 10 | Use a summary table or materialized reporting strategy to avoid repeated aggregation. |

### General Optimization Techniques

1. Create indexes on frequently searched columns.
2. Avoid unnecessary SELECT * statements.
3. Use EXPLAIN to analyse query execution plans.
4. Avoid repeated correlated subqueries on large datasets.
5. Use pagination for large reports.
6. Partition large transaction tables when appropriate.
7. Maintain database statistics.
8. Use pre-aggregated summary tables for frequently accessed reports.
