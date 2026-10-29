# 🏦 Retail Banking Transaction Review

## 📌 Business Scenario

A retail bank's operations team reviews the day's card transactions. Auditors need quick lookups by transaction ID, statements sorted by posting date, and an early warning system to identify duplicate transaction postings.

This project demonstrates core **Data Structures and Algorithms (DSA)** using Java to improve transaction review efficiency.

## 🎯 Tasks

### 🔍 Task 1: Transaction ID Search

Search a sorted list of 100,000 transaction IDs using Binary Search and compare its performance with Linear Search.

**Requirements:**
- Implement Binary Search and Linear Search.
- Count comparisons for best, average, and worst cases.
- Compare the number of comparisons for both algorithms.

**Expected complexity:**

| Algorithm | Best Case | Average Case | Worst Case | Space |
|---|---|---|---|---|
| Linear Search | O(1) | O(n) | O(n) | O(1) |
| Binary Search | O(1) | O(log n) | O(log n) | O(1) iterative |

For 100,000 sorted transaction IDs, Binary Search needs at most approximately 17 comparisons to locate an existing transaction, while Linear Search can require up to 100,000 comparisons.

### 📅 Task 2: Sort Customer Statements

Sort a customer's transaction statement by posting date using Insertion Sort and Merge Sort.

**Requirements:**
- Implement Insertion Sort.
- Implement Merge Sort.
- Compare their execution times.
- Explain sorting stability using transactions with identical timestamps.

**Why stability matters:**

A stable sorting algorithm preserves the original order of transactions that have equal timestamps. This is useful when multiple transactions share the same posting time and their original sequence must remain unchanged.

| Algorithm | Best Case | Average Case | Worst Case | Space |
|---|---|---|---|---|
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) |

Both algorithms can be implemented as stable sorts when equal timestamps are handled correctly.

### 🚨 Task 3: Duplicate Transaction Detection

Identify possible duplicate postings when transactions have the same account, amount, and merchant within 60 seconds.

**Requirements:**
- Compare account number, transaction amount, and merchant.
- Check the time difference between transactions.
- Identify possible duplicate postings.
- Justify the data structure used.

**Data structure:** HashMap or HashSet.

A hash-based data structure provides expected O(1) lookup and insertion, making it suitable for detecting potential duplicates efficiently.

**Expected complexity:** O(n) average time and O(n) additional space for n transactions, assuming efficient hashing and a suitable comparison strategy.

### 📥 Task 4: Queue and Stack Implementation

Implement a Queue and a Stack without using library collections for their core storage.

**Queue — Manual Review Backlog**
- Follows FIFO (First In, First Out).
- The first transaction added is the first transaction reviewed.
- Implement enqueue, dequeue, peek, and isEmpty.

**Stack — Reverse Last Adjustment**
- Follows LIFO (Last In, First Out).
- The most recent adjustment is reversed first.
- Implement push, pop, peek, and isEmpty.

| Operation | Expected Time | Space |
|---|---|---|
| Queue enqueue | O(1) | O(n) total |
| Queue dequeue | O(1) | O(n) total |
| Stack push | O(1) | O(n) total |
| Stack pop | O(1) | O(n) total |

Use an array-based implementation with manual index management or linked nodes, rather than Java's built-in collection classes, for the initial implementations.

### 📊 Task 5: Highest and Lowest Transaction Amount

Find the maximum and minimum transaction amounts in a day's transaction file using Divide and Conquer, then compare the result with a brute-force scan.

**Requirements:**
- Implement a brute-force minimum and maximum scan.
- Implement a recursive Divide-and-Conquer approach.
- Compare results and execution time.

| Approach | Best Case | Average Case | Worst Case | Space |
|---|---|---|---|---|
| Brute Force | O(n) | O(n) | O(n) | O(1) |
| Divide and Conquer | O(n) | O(n) | O(n) | O(log n) recursion |

Divide and Conquer divides the input into smaller sections, finds each section's minimum and maximum, and combines the results.

## 📦 Deliverables

- Java source code for all five tasks.
- Sample transaction input data.
- Sample console output.
- Comparison counts and benchmark results.
- Complexity table covering best, average, and worst cases and space.
- Short justification for each algorithm and data structure.

## 🗂️ Suggested Project Structure

```text
Retail-Banking-Transaction-Review/
├── transactions.csv
├── src/
│   ├── Transaction.java
│   ├── TransactionSearch.java
│   ├── StatementSorting.java
│   ├── DuplicateDetector.java
│   ├── CustomQueue.java
│   ├── CustomStack.java
│   └── TransactionMinMax.java
├── output/
│   ├── search-results.txt
│   ├── sorting-results.txt
│   ├── duplicate-results.txt
│   ├── queue-stack-results.txt
│   └── min-max-results.txt
```

## 🧪 Testing and Benchmarking

1. Test searching with the first, middle, and last transaction IDs, plus an ID that does not exist.
2. Test sorting with ascending, descending, and identical timestamps.
3. Test duplicate detection with matching transactions inside and outside the 60-second window.
4. Test stack and queue operations, including empty structures.
5. Test minimum and maximum calculations with positive, negative, and repeated amounts.
6. Record actual execution times using `System.nanoTime()` and compare the results.

---

**Developed as part of Hexaware Training — Data Structures and Algorithms Assignment.**
