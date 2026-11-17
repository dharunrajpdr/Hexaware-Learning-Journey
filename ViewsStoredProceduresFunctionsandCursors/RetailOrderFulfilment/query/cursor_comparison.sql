
-- CURSOR VS SET-BASED COMPARISON

SELECT
    'Cursor' AS method,
    'Processes records one by one' AS working,
    'Slower for large datasets' AS performance,
    'Useful for row-by-row operations' AS usage

UNION ALL

SELECT
    'Set-Based',
    'Processes multiple records together',
    'Generally faster for large datasets',
    'Useful for bulk database operations';
