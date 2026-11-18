
# Hospital Bed Capacity Control – Justification Note

## 1. Trigger Implementation

The hospital database uses triggers to control bed capacity and
maintain admission history.

- BEFORE INSERT checks whether the ward has available beds.
- AFTER INSERT increases occupied beds and records the admission.
- AFTER UPDATE records changes and updates bed counts when a patient
  moves between wards.
- AFTER DELETE decreases occupied beds and records the deletion.

## 2. Multi-row INSERT Behaviour in InnoDB

- MySQL triggers execute once for every affected row.
- If a multi-row INSERT exceeds ward capacity, the BEFORE INSERT
  trigger raises SQLSTATE '45000'.
- Since the admission table uses InnoDB, the failed INSERT statement
  is rolled back completely.
- Earlier rows inserted by the same statement are also rolled back.
- Audit records and occupied-bed updates made by those triggers
  are rolled back as part of the same transaction.

Note: This assumes transactional InnoDB tables and no external
non-transactional side effects.

## 3. CHECK Constraint vs Trigger vs Stored Procedure

### CHECK Constraint
- Used to validate simple conditions on individual rows.
- Example: occupied_beds must not exceed bed_capacity.
- Easy to maintain for basic data validation.

### Trigger
- Used when an automatic action must happen during INSERT,
  UPDATE or DELETE.
- Example: Automatically maintaining admission audit history.

### Stored Procedure
- Used for complex business operations involving multiple SQL
  statements.
- Example: Checking ward capacity, admitting a patient and
  updating bed availability in a controlled transaction.

## 4. JDBC Application-Layer Testing

The trigger can be tested through Java JDBC.

Testing steps:

1. Establish a database connection using DriverManager.
2. Create a PreparedStatement for the INSERT query.
3. Execute the admission INSERT using executeUpdate().
4. If the ward is full, catch SQLException.
5. Retrieve the SQLState using getSQLState().
6. Verify that the SQLState is 45000.
7. Query the ward and admission_audit tables to verify that
   no invalid admission was recorded.

Example:

```java
try {
    String sql = "INSERT INTO admission " +
                 "(admission_id, patient_name, ward_id) " +
                 "VALUES (?, ?, ?)";

    PreparedStatement ps = connection.prepareStatement(sql);

    ps.setInt(1, 106);
    ps.setString(2, "Vijay");
    ps.setInt(3, 1);

    ps.executeUpdate();

    System.out.println("Admission successful");

} catch (SQLException e) {
    System.out.println("Admission failed: " + e.getMessage());
    System.out.println("SQL State: " + e.getSQLState());
}
```

## 5. Conclusion

Triggers help prevent admissions beyond ward capacity and
automatically maintain admission audit records. CHECK constraints
are suitable for simple validations, while stored procedures are
useful for complex database operations.
