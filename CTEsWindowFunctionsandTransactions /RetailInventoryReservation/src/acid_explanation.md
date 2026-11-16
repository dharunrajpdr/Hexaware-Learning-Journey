
# Retail Inventory Reservation – ACID Properties

## 1. Atomicity

Atomicity means a transaction is completed fully or not executed at all.

Example:
When a customer places an order:
- Stock is decreased.
- An order is inserted.
- Order items are inserted.

If stock is insufficient, ROLLBACK cancels all changes.

## 2. Consistency

Consistency ensures that the database remains valid before and after a transaction.

Example:
If a product has 10 units in stock and a customer purchases 3 units, the remaining stock becomes 7.

The stock should never become negative.

## 3. Isolation

Isolation ensures that multiple transactions do not interfere with each other.

Example:
Two customers try to purchase the last available product.

Using SELECT ... FOR UPDATE, one transaction locks the product row. The second transaction waits until the first transaction completes.

This prevents both customers from reserving the same stock.

## 4. Durability

Durability ensures that committed data is permanently saved.

Example:
After an order is successfully placed and COMMIT is executed, the order and stock changes remain saved even if the application restarts.

# Non-Transactional Table Example

Consider a MyISAM table.

MyISAM does not support transactions.

Example:

1. Stock is decreased from 10 to 5.
2. An order is inserted.
3. Order item insertion fails.
4. ROLLBACK is executed.

Since MyISAM does not support transactions, the stock reduction and order insertion cannot be rolled back.

This creates inconsistent inventory records.

# Conclusion

ACID properties ensure reliable inventory reservation.

Using InnoDB, START TRANSACTION, COMMIT, ROLLBACK and SELECT ... FOR UPDATE helps prevent incorrect stock updates and incomplete orders.
