
CREATE DATABASE airline_db;
USE airline_db;

-- Create flights table
CREATE TABLE flights (
    flight_id INT PRIMARY KEY,
    flight_name VARCHAR(50)
) ENGINE=InnoDB;

-- Create seats table
CREATE TABLE seats (
    seat_id INT PRIMARY KEY,
    flight_id INT,
    seat_number VARCHAR(10),
    seat_status VARCHAR(20) DEFAULT 'AVAILABLE',
    FOREIGN KEY (flight_id) REFERENCES flights(flight_id),
    UNIQUE (flight_id, seat_number)
) ENGINE=InnoDB;

-- Create passengers table
CREATE TABLE passengers (
    passenger_id INT PRIMARY KEY,
    passenger_name VARCHAR(50)
) ENGINE=InnoDB;

-- Create bookings table
CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    passenger_id INT,
    flight_id INT,
    seat_id INT UNIQUE,
    fare DECIMAL(10,2),
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (passenger_id) REFERENCES passengers(passenger_id),
    FOREIGN KEY (flight_id) REFERENCES flights(flight_id),
    FOREIGN KEY (seat_id) REFERENCES seats(seat_id)
) ENGINE=InnoDB;

-- Insert flights
INSERT INTO flights VALUES
(101, 'Air India AI101'),
(102, 'IndiGo 6E202');

-- Insert seats
INSERT INTO seats VALUES
(1, 101, 'A1', 'AVAILABLE'),
(2, 101, 'A2', 'AVAILABLE'),
(3, 101, 'A3', 'AVAILABLE'),
(4, 102, 'B1', 'AVAILABLE'),
(5, 102, 'B2', 'AVAILABLE');

-- Insert passengers
INSERT INTO passengers VALUES
(1, 'Dharun'),
(2, 'Arun'),
(3, 'Kumar'),
(4, 'Rahul'),
(5, 'Vijay');

-- Booking transaction
START TRANSACTION;

SELECT seat_id, seat_status
FROM seats
WHERE seat_id = 1
FOR UPDATE;

-- Execute only if seat_status = 'AVAILABLE'
INSERT INTO bookings
(passenger_id, flight_id, seat_id, fare)
VALUES (1, 101, 1, 5000);

UPDATE seats
SET seat_status = 'BOOKED'
WHERE seat_id = 1
AND seat_status = 'AVAILABLE';

COMMIT;

-- Verify booking
SELECT * FROM seats;
SELECT * FROM bookings;

-- Rollback example
START TRANSACTION;

UPDATE seats
SET seat_status = 'AVAILABLE'
WHERE seat_id = 2;

ROLLBACK;
