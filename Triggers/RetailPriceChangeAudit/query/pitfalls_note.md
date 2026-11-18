
# Retail Price Change Audit – Trigger Pitfalls

## 1. Risks of Triggers

### Risk 1: Hidden Logic
- Triggers execute automatically in the background.
- Developers may not easily identify why a particular action occurred.
- This makes debugging and maintenance difficult.

### Risk 2: Cascading Effects
- One trigger may modify another table.
- This modification may activate another trigger.
- Multiple automatic operations can make the system complex.

## 2. When Application Code Is Better

Application code is better when implementing complex business workflows involving:
- Multiple validations.
- External API calls.
- Notifications and emails.
- Complex transaction management.

These operations are easier to maintain, test and debug in application code.

## 3. MySQL Trigger Execution

- MySQL triggers are row-level triggers.
- They execute once for each affected row.
- A single-row UPDATE executes the trigger once.
- A multi-row UPDATE affecting three rows executes the trigger three times.

## 4. Conclusion

Triggers help maintain automatic auditing and enforce database-level
business rules. However, excessive trigger usage can make applications
difficult to debug and maintain.
