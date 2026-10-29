# 🏥 Hospital Appointment Desk

## 📌 Project Overview

The Hospital Appointment Desk manages patient records and doctor appointments in a multi-speciality hospital. The system helps receptionists search patient details, organize appointments by urgency, detect duplicate bookings, manage walk-in tokens, and schedule appointments efficiently.

This project demonstrates essential **Data Structures and Algorithms (DSA)** concepts using Java.

## 🎯 Objectives

- Search patient records efficiently using Linear Search and Binary Search.
- Sort appointments by urgency and arrival time.
- Detect duplicate appointment bookings.
- Implement a custom Queue and Stack.
- Compare Dynamic Programming and Greedy approaches for appointment scheduling.
- Analyze time and space complexity.

## 📝 Tasks

### 1. Patient Search

Implement Linear Search and Binary Search to find patients using their patient IDs.

**Requirements:**
- Generate or store 20,000 patient records.
- Perform 1,000 patient lookups.
- Measure and compare execution times using `System.nanoTime()`.
- Display the search results and performance comparison.

**Expected Complexity:**

| Algorithm | Average Time | Worst Time |
|---|---|---|
| Linear Search | O(n) | O(n) |
| Binary Search | O(log n) | O(log n) |

*Note: Binary Search requires the patient records to be sorted by patient ID.*

### 2. Appointment Sorting

Sort appointments according to:
1. Urgency level.
2. Arrival time when urgency levels are equal.

Implement and compare Selection Sort and Quick Sort.

**Requirements:**
- Display the sorted appointments.
- Count swaps and measure execution time.
- Compare the performance of both algorithms.

| Algorithm | Average Time | Worst Time |
|---|---|---|
| Selection Sort | O(n²) | O(n²) |
| Quick Sort | O(n log n) | O(n²) |

### 3. Duplicate Booking Detection

Prevent a patient from booking the same appointment slot more than once.

A booking is considered a duplicate when the **patient ID, appointment date, and time slot** are identical.

**Requirements:**
- Use a `HashSet` to detect duplicate bookings.
- Display duplicate booking details.
- Test bookings with the same patient in different time slots.

**Expected Complexity:**
- Average time: O(n) for processing all bookings.
- Space: O(n).

### 4. Custom Queue and Stack

Implement the following data structures from scratch without using Java's built-in Queue or Stack implementations for the core storage.

**Queue — Walk-in Patient Tokens**
- `enqueue()` — Add a patient token.
- `dequeue()` — Remove the oldest token.
- `isEmpty()` — Check whether the queue is empty.

**Stack — Recently Viewed Patients**
- `push()` — Add a patient record.
- `pop()` — Remove the most recently viewed patient.
- `peek()` — View the top patient without removing it.

Use an array-based circular Queue and an array-based Stack.

**Expected Complexity:** O(1) for standard enqueue, dequeue, push, and pop operations, excluding occasional resizing.

### 5. Appointment Scheduling

Determine the maximum number of non-overlapping appointments a doctor can attend during a shift.

**Requirements:**
- Represent each appointment with start and end times.
- Implement a Dynamic Programming approach.
- Implement a Greedy approach based on the earliest finishing appointment.
- Compare the results and execution times.

| Algorithm | Time Complexity | Extra Space |
|---|---|---|
| Greedy | O(n log n) including sorting | O(1) excluding sorting |
| Dynamic Programming | O(n log n) with sorting and binary search | O(n) |

Both approaches should return the maximum number of non-overlapping appointments for this objective.

## 📂 Project Structure

```text
Hospital-Appointment-Desk/
├── data/
│   ├── patients.csv
│   └── appointments.csv
├── src/
│   ├── Patient.java
│   ├── Appointment.java
│   ├── PatientSearch.java
│   ├── AppointmentSorting.java
│   ├── DuplicateBookingDetector.java
│   ├── CustomQueue.java
│   ├── CustomStack.java
│   └── AppointmentScheduler.java
├── output/
│   ├── search-results.txt
│   ├── sorting-results.txt
│   ├── duplicate-booking-results.txt
│   └── scheduling-results.txt
└── docs/
    ├── complexity-analysis.md
    └── sorting-trace.md
```

## ▶️ Commands to Run the Program

**Prerequisite:** Install the Java Development Kit (JDK) and ensure `java` and `javac` are available in your terminal.

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd Hospital-Appointment-Desk
```

### 2. Compile the Java Files

If all Java files are inside the `src` folder and do not declare a package:

```bash
javac -d out src/*.java
```

### 3. Run the Programs

Run each class that contains a `main()` method:

```bash
java -cp out PatientSearch
java -cp out AppointmentSorting
java -cp out DuplicateBookingDetector
java -cp out CustomQueue
java -cp out CustomStack
java -cp out AppointmentScheduler
```

*Note: Each command assumes the corresponding class has a `main()` method. If you use Java packages or a single main class, adjust the commands accordingly.*

## 🧪 Testing

Test the implementation using these scenarios:

- Search for existing and missing patient IDs.
- Compare Linear Search and Binary Search performance.
- Sort appointments with different urgency levels and arrival times.
- Detect duplicate bookings and allow bookings in different slots.
- Test Queue and Stack operations, including empty structures.
- Compare Greedy and Dynamic Programming scheduling results.
- Record execution times and verify the algorithmic complexity.
