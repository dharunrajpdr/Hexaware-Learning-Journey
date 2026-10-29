# 🛒 Retail Order Desk

## 📌 1. Business Scenario

An online retailer processes approximately **50,000 orders per day**. Different teams need to manage these orders efficiently.

- 🔍 **Customer Support:** Find an order quickly using its ID.
- 📦 **Warehouse:** Sort and rank orders by amount for dispatch.
- 💳 **Finance:** Identify duplicate orders before processing payments.

This assignment demonstrates how Java data structures and algorithms can solve these real-world problems efficiently.

---

## 🧩 2. Tasks

### 🔎 Task 1: Search for an Order

**Input File:** `orders.csv`

| Column | Description |
|---|---|
| `orderId` | Unique ID of an order |
| `customerId` | ID of the customer |
| `amount` | Total order amount |
| `createdAt` | Date and time the order was created |

**Requirements:**

1. Load orders from `orders.csv`.
2. Implement Linear Search to find an order by ID.
3. Implement Binary Search to find an order by ID. Sort the orders by `orderId` before searching.
4. Benchmark both algorithms using 50,000 records.
5. Record execution times and explain the observed differences.

**⏱️ Time Complexity:**
- Linear Search: `O(n)`
- Binary Search: `O(log n)` on sorted data.

### 📊 Task 2: Sort Orders by Amount

Sort orders by amount in **descending order**, from highest to lowest.

**Requirements:**

1. Implement one `O(n²)` sorting algorithm, such as Bubble Sort.
2. Implement one `O(n log n)` sorting algorithm, such as Merge Sort.
3. Trace the first three passes or iterations of each algorithm using a small example.
4. Compare both algorithms with Java's built-in sorting approach using 10,000 records.
5. Record actual execution times.

**⏱️ Time Complexity:**
- Bubble Sort: `O(n²)` average and worst case.
- Merge Sort: `O(n log n)`.
- Java built-in object sorting: Typically `O(n log n)`.

### 🛡️ Task 3: Detect Duplicate Orders

Identify possible duplicate orders using these conditions:

- Same `customerId`.
- Same `amount`.
- Same creation-time minute.

**Requirements:**

1. Use a hash-based structure such as `HashSet` or `HashMap`.
2. Create a key using the customer ID, amount, and creation-time minute.
3. If the key already exists, flag the order as a possible duplicate.
4. Explain the time and space complexity.

**⏱️ Expected Complexity:**
- Time: `O(n)` on average.
- Space: `O(n)`.

💡 **Note:** Timestamps such as `10:35:12` and `10:35:48` belong to the same minute.

### 📚 Task 4: Implement a Stack and a Queue

Implement your own Stack and Queue in Java.

**↩️ Stack — Undo the Last Order Edit**
- Push each order edit onto the stack.
- Pop the latest edit when the user selects Undo.
- Demonstrate Last In, First Out (LIFO).

**📦 Queue — Manage the Dispatch Line**
- Add orders to the back of the queue.
- Remove orders from the front for dispatch.
- Demonstrate First In, First Out (FIFO).

Replace your implementations with Java's built-in `ArrayDeque` and compare their behavior.

**⏱️ Expected Complexity:**
- Stack push/pop: `O(1)` per operation.
- Queue enqueue/dequeue: `O(1)` per operation.
- Storage: `O(n)`.

### 💰 Task 5: Minimum Notes or Coins for a Refund

Calculate the minimum number of notes or coins required to return a refund amount.

**Requirements:**

1. Implement a Greedy Algorithm that repeatedly selects the largest denomination possible.
2. Demonstrate a denomination set where the greedy algorithm fails.
3. Solve the same problem using Dynamic Programming.
4. Compare the results.

**🧮 Example: Greedy Algorithm Failure**

Denominations: `{1, 3, 4}`

Refund amount: `6`

- Greedy result: `4 + 1 + 1` = **3 coins**
- Optimal result: `3 + 3` = **2 coins**

Dynamic Programming finds the minimum number of coins by using solutions to smaller amounts.

---

## 📦 3. Deliverables

- 💻 Java source code for all five tasks.
- 📂 `orders.csv` input file or instructions for obtaining it.
- 🖥️ Console output for each task.
- 🔍 Search benchmark results for 50,000 records.
- 📊 Sorting benchmark results for 10,000 records.
- 📝 A one-page note explaining algorithm complexity and data-structure choices.
- 📖 A README containing compilation and execution instructions.

---

## 🗂️ 4. Suggested Project Structure

```text
Retail-Order-Desk/
├── src/
│   ├── Order.java
│   ├── OrderSearch.java
│   ├── OrderSorting.java
│   ├── DuplicateOrderDetector.java
│   ├── CustomStack.java
│   ├── CustomQueue.java
│   └── RefundCalculator.java
├── output/
│   ├── task1-search-results.txt
│   ├── task2-sorting-results.txt
│   ├── task3-duplicate-results.txt
│   ├── task4-stack-queue-results.txt
│   └── task5-refund-results.txt
```

---

## ⏱️ 5. Benchmarking Guidelines

1. Use the same input data for all algorithms being compared.
2. Keep file loading outside the timed section unless measuring file-loading performance.
3. Measure binary-search sorting time separately from search time.
4. Run benchmarks multiple times and calculate a representative result.
5. Verify correctness before comparing execution times.
6. Record actual measurements instead of inventing timings.

Actual performance depends on the computer, Java version, and implementation.

---

## 📈 6. Complexity Summary

| Task | Algorithm/Data Structure | Time Complexity | Extra Space |
|---|---|---|---|
| 1 | Linear Search | `O(n)` | `O(1)` |
| 1 | Binary Search | `O(log n)` | `O(1)` iterative |
| 2 | Bubble Sort | `O(n²)` average/worst | `O(1)` |
| 2 | Merge Sort | `O(n log n)` | `O(n)` |
| 2 | Built-in object sorting | Typically `O(n log n)` | Implementation-dependent |
| 3 | Hash-based duplicate detection | `O(n)` expected | `O(n)` |
| 4 | Stack operations | `O(1)` per operation | `O(n)` storage |
| 4 | Queue operations | `O(1)` per operation | `O(n)` storage |
| 5 | Greedy refund calculation | Depends on denominations | Usually `O(1)` |
| 5 | Dynamic Programming | `O(A × D)` | `O(A)` |

Here, `n` is the number of orders, `A` is the refund amount, and `D` is the number of denominations.

---

## ✅ 7. Final Checklist

- [ ] All Java files compile and execute correctly.
- [ ] Search algorithms return the correct order.
- [ ] Sorting algorithms rank orders from highest to lowest amount.
- [ ] Duplicate detection follows all three specified conditions.
- [ ] Stack undo and queue dispatch are demonstrated.
- [ ] Greedy and Dynamic Programming results are compared.
- [ ] Benchmark results and console outputs are included.
- [ ] The complexity note is complete.
- [ ] The repository README explains how to run the programs.
