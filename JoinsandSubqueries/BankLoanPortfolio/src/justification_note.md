
# Bank Loan Portfolio – SQL Justification Note

## 1. INNER JOIN
Used to combine customers, loans and branches.
It returns only matching records from related tables.

## 2. LEFT JOIN
Used to identify branches without loans.
Branches with NULL loan IDs are branches having no loans.

## 3. SELF JOIN
Used to identify guarantors who are also borrowers.
The loans table is joined with itself using different aliases.

## 4. GROUP BY
Used to group loan records based on customers and branches.

## 5. HAVING
Used to filter grouped results, such as customers having loans in multiple branches.

## 6. Correlated Subquery
Used in Query 6 and Query 10.
It refers to the outer query and compares customer exposure or loan amounts with branch-level averages.

## 7. Uncorrelated Subquery
Used in Query 7.
It calculates the average total loan exposure independently of the outer query.

## 8. EXISTS
Used in Query 8 to check whether a customer has at least one loan.

## 9. NOT EXISTS
Used in Query 8 to identify customers who have no repayment during the last 60 days.

## 10. Nested Subquery
Used in Query 9 to calculate the maximum loan amount of each branch and compare it with the average of all branch maximums.
They demonstrate the practical use of joins,
self joins, aggregate functions and advanced subqueries.
