
-- CURSOR VS SET-BASED COMPARISON

SELECT
    'Cursor' AS method,
    'Processes policies one by one' AS working,
    'Uses OPEN, FETCH and CLOSE' AS implementation,
    'Suitable for individual row processing' AS usage

UNION ALL

SELECT
    'Set-Based',
    'Processes multiple policies together',
    'Uses INSERT SELECT and UPDATE',
    'Suitable for bulk database operations';
