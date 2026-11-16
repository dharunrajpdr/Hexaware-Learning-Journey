
USE airline_db;

START TRANSACTION;

SELECT *
FROM seats
WHERE seat_id = 2
FOR UPDATE;

-- Wait until Session 1 commits.
-- Check seat_status = 'BOOKED'.

ROLLBACK;
