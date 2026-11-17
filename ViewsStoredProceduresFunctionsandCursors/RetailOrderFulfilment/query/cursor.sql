
USE retail_fulfilment;

-- TASK 4: CURSOR-BASED PROCEDURE

DELIMITER $$

CREATE PROCEDURE sp_pending_order_followups()
BEGIN
    DECLARE v_order_id INT;
    DECLARE v_done INT DEFAULT 0;

    -- Declare cursor
    DECLARE order_cursor CURSOR FOR
        SELECT order_id
        FROM orders
        WHERE status = 'PENDING'
        AND order_date < NOW() - INTERVAL 3 DAY;

    -- NOT FOUND handler
    DECLARE CONTINUE HANDLER FOR NOT FOUND
        SET v_done = 1;

    -- Open cursor
    OPEN order_cursor;

    read_loop: LOOP

        FETCH order_cursor INTO v_order_id;

        IF v_done = 1 THEN
            LEAVE read_loop;
        END IF;

        INSERT IGNORE INTO order_followups(
            order_id,
            remarks
        )
        VALUES(
            v_order_id,
            'Order pending for more than 3 days'
        );

    END LOOP;

    -- Close cursor
    CLOSE order_cursor;

END $$

DELIMITER ;


-- Execute cursor procedure
CALL sp_pending_order_followups();


-- SET-BASED APPROACH (Alternative to cursor)

INSERT IGNORE INTO order_followups(
    order_id,
    remarks
)
SELECT
    order_id,
    'Order pending for more than 3 days'
FROM orders
WHERE status = 'PENDING'
AND order_date < NOW() - INTERVAL 3 DAY;


-- Display follow-up records
SELECT * FROM order_followups;
