
USE airline_db;

START TRANSACTION;

SELECT *
FROM seats
WHERE seat_id = 2
FOR UPDATE;

INSERT INTO bookings
(passenger_id, flight_id, seat_id, fare)
VALUES (1, 101, 2, 5000);

UPDATE seats
SET seat_status = 'BOOKED'
WHERE seat_id = 2;

-- Execute after Session 2 requests the same seat
COMMIT;
