
# Airline Seat Booking – Locking Observation

## Objective
To understand how transactions and row-level locking prevent two customers from booking the same airline seat simultaneously.

## Locking Simulation

### Session 1
1. Started a transaction using START TRANSACTION.
2. Executed SELECT ... FOR UPDATE to lock the selected seat.
3. Inserted the passenger booking.
4. Updated the seat status to BOOKED.
5. Executed COMMIT.

### Session 2
1. Started another transaction.
2. Tried to access the same seat using SELECT ... FOR UPDATE.
3. Waited because Session 1 already held the row lock.
4. After Session 1 executed COMMIT, Session 2 obtained the lock.
5. Checked the seat status and found it as BOOKED.
6. Executed ROLLBACK without inserting another booking.

## Observed Locking Behaviour

- SELECT ... FOR UPDATE locks the selected row.
- Another transaction requesting the same row must wait.
- COMMIT saves changes and releases the lock.
- ROLLBACK cancels uncommitted changes.
- Only one customer successfully books the selected seat.

## Result
The database successfully prevented duplicate seat bookings through row-level locking and transaction management.

## Conclusion
Transactions ensure that booking insertion and seat status updates are handled atomically.

SELECT ... FOR UPDATE prevents concurrent transactions from booking the same seat simultaneously.

The UNIQUE constraint on seat_id provides additional protection against duplicate bookings.
