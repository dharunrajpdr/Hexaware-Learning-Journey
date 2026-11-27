
# Support Ticket Queue System

## Business Scenario

A service desk receives support tickets from different users.
Each ticket contains a ticket number, user, summary, category,
priority, creation time, and resolution status.

The system removes duplicate tickets, sorts tickets based on
priority, processes tickets using a PriorityQueue, generates
reports, and removes resolved tickets.

## Technologies Used

- Java 17+
- Collections Framework
- Generics
- ArrayList
- LinkedHashSet
- HashSet concepts
- Comparator
- PriorityQueue
- HashMap
- TreeMap
- Iterator
- LocalDateTime

## Project Structure

```text
SupportTicketQueue/
│
├── src/
│   └── com/
│       └── servicedesk/
│           ├── Main.java
│           └── model/
│               └── Ticket.java
│
├── out/
├── run.bat
└── README.md
```

## Tasks Implemented

### 1. Duplicate Removal

- Uses LinkedHashSet.
- Identifies duplicates using user and summary.
- Sorts tickets by creation time before insertion.
- Retains the earliest ticket for each duplicate group.

### 2. Ticket Sorting

Uses Comparator.comparing() and thenComparing().

Sorting order:
1. Priority - Descending
2. Creation time - Ascending
3. Ticket number - Ascending

### 3. Priority Queue

- Uses PriorityQueue.
- Highest-priority ticket is served first.
- Creation time and ticket number resolve ties.

### 4. Ticket Reports

- HashMap generates category reports.
- TreeMap generates user reports.
- HashMap does not guarantee iteration order.
- TreeMap maintains keys in sorted order.

### 5. Iterator

- Uses Iterator to remove resolved tickets.
- Safely modifies the collection during iteration.
- Avoids ConcurrentModificationException.

## Expected Results

| Description | Expected count |
|---|---:|
| Original tickets | 30 |
| Duplicate tickets removed | 4 |
| Unique tickets | 26 |
| Resolved tickets removed | 8 |
| Remaining open tickets | 18 |

### Category Report

| Category | Tickets |
|---|---:|
| Account | 7 |
| Payment | 6 |
| Technical | 7 |
| Network | 6 |

### User Report

| User | Tickets |
|---|---:|
| Arun | 4 |
| Bala | 4 |
| Charan | 4 |
| Deepak | 3 |
| Ezhil | 3 |
| Fahad | 4 |
| Gokul | 4 |

## Important Concepts

### LinkedHashSet
Maintains insertion order and does not allow duplicate elements.

### Comparator
Defines custom sorting logic for objects.

### PriorityQueue
Processes elements according to their priority rather than insertion order.

### HashMap
Stores key-value pairs without guaranteeing iteration order.

### TreeMap
Stores key-value pairs with keys sorted in ascending order by default.

### Iterator
Allows safe removal of elements while traversing a collection.

### ConcurrentModificationException
Occurs when a collection is structurally modified during iteration
using an unsupported method, such as removing an element directly
inside a for-each loop.

## equals() and hashCode() Contract

- If two objects are equal according to equals(), they must have
  the same hash code.
- If two objects have the same hash code, they are not necessarily equal.
- Both methods must use the same identity fields.

In this project, user and summary determine ticket equality.

## How to Run

### Using run.bat

Double-click run.bat.

### Using VS Code Terminal

Compile:

```bash
javac -d out src/com/servicedesk/model/Ticket.java src/com/servicedesk/Main.java
```

Execute:

```bash
java -cp out com.servicedesk.Main
```

## Conclusion

The Support Ticket Queue System demonstrates how Java Collections
and Generics can be used to manage support tickets efficiently.
