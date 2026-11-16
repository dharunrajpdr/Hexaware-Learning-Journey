
USE airline_db;

-- Insert sample bookings
INSERT INTO bookings
(passenger_id, flight_id, seat_id, fare)
VALUES
(2, 101, 2, 6000),
(3, 101, 3, 6000),
(4, 102, 4, 4500),
(5, 102, 5, 7000);

-- TASK 1: RANK AND DENSE_RANK

SELECT
    f.flight_name,
    p.passenger_name,
    b.fare,

    RANK() OVER (
        PARTITION BY b.flight_id
        ORDER BY b.fare DESC
    ) AS fare_rank,

    DENSE_RANK() OVER (
        PARTITION BY b.flight_id
        ORDER BY b.fare DESC
    ) AS dense_rank

FROM bookings b
JOIN passengers p
    ON b.passenger_id = p.passenger_id
JOIN flights f
    ON b.flight_id = f.flight_id;


-- TASK 2: CTE WITH RUNNING TOTAL

WITH passenger_ranking AS (
    SELECT
        f.flight_name,
        b.flight_id,
        p.passenger_name,
        b.fare,

        RANK() OVER (
            PARTITION BY b.flight_id
            ORDER BY b.fare DESC
        ) AS fare_rank,

        DENSE_RANK() OVER (
            PARTITION BY b.flight_id
            ORDER BY b.fare DESC
        ) AS dense_rank,

        SUM(b.fare) OVER (
            PARTITION BY b.flight_id
            ORDER BY b.booking_id
            ROWS BETWEEN UNBOUNDED PRECEDING
            AND CURRENT ROW
        ) AS running_total

    FROM bookings b
    JOIN passengers p
        ON b.passenger_id = p.passenger_id
    JOIN flights f
        ON b.flight_id = f.flight_id
)

SELECT *
FROM passenger_ranking
ORDER BY flight_id, booking_id;


-- TASK 3: CORRELATED SUBQUERY

SELECT
    p.passenger_name,
    f.flight_name,
    b.fare

FROM bookings b
JOIN passengers p
    ON b.passenger_id = p.passenger_id
JOIN flights f
    ON b.flight_id = f.flight_id

WHERE b.fare > (
    SELECT AVG(b2.fare)
    FROM bookings b2
    WHERE b2.flight_id = b.flight_id
);
