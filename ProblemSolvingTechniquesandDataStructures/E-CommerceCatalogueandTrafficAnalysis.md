# 🛒 E-Commerce Catalogue and Traffic Analysis

## 📌 1. Business Scenario

An e-commerce platform handles thousands of products, customer orders, and service requests every day. The platform needs efficient data structures and algorithms to improve category browsing, perform instant SKU look-ups, manage microservice deployment dependencies, and analyze peak traffic.

This project demonstrates how **Java Data Structures and Algorithms (DSA)** can solve real-world e-commerce problems efficiently.

## 🎯 2. Project Objectives

- Organize product categories for efficient searching and traversal.
- Perform fast SKU look-ups using hashing techniques.
- Manage microservice dependencies and identify circular dependencies.
- Identify the busiest continuous three-hour traffic window.
- Find pairs of order values that match a given gift-card amount.

## ⚙️ 3. Tasks and Implementations

### 1. Binary Search Tree – Product Categories

**Problem:** Organize and search 15 sample product categories efficiently.

**Implementation:**
- Create a Binary Search Tree (BST).
- Insert 15 product categories.
- Search for a specific category.
- Display three tree traversals:
  - Inorder Traversal
  - Preorder Traversal
  - Postorder Traversal

**Expected Outcome:** Efficient category searching and organized product navigation.

### 2. Hashing and HashMap – SKU Look-up

**Problem:** Retrieve product information quickly using unique Stock Keeping Unit (SKU) identifiers.

**Implementation:**
- Implement a custom hash table using separate chaining.
- Demonstrate a hash collision.
- Implement the same functionality using Java's built-in `HashMap`.
- Generate 100,000 sample SKUs.
- Compare the look-up performance of both implementations.

**Expected Outcome:** Understand collision handling and compare custom hashing with Java's built-in HashMap.

### 3. Graph – Microservice Deployment Dependencies

**Problem:** Manage deployment dependencies between e-commerce microservices.

**Implementation:**
- Represent microservices as graph vertices.
- Represent dependencies as directed edges.
- Model dependencies such as:
  - Cart depends on Pricing.
  - Pricing depends on Inventory.
  - Order depends on Cart.
- Generate a valid deployment order using BFS (Kahn's Topological Sort) and DFS.
- Introduce a circular dependency and detect the cycle.

**Expected Outcome:** Determine a valid deployment sequence and identify circular dependencies.

### 4. Sliding Window – Peak Traffic Analysis

**Problem:** Identify the busiest continuous three-hour period over 24 hours.

**Implementation:**
- Generate or define requests-per-minute data for 24 hours (1,440 minutes).
- Use the sliding window technique.
- Calculate the total requests in every consecutive 180-minute window.
- Identify the maximum traffic period.

**Expected Outcome:** Find the peak traffic window to help estimate infrastructure requirements.

### 5. Two Pointers – Gift Card Order Matching

**Problem:** Find all pairs of order values whose sum equals a given gift-card amount.

**Implementation:**
- Use a sorted array of order values.
- Initialize two pointers at opposite ends.
- Compare the sum with the target gift-card amount.
- Move pointers according to the calculated sum.
- Identify all valid pairs.

**Expected Outcome:** Efficiently identify matching order combinations without checking every possible pair.

## 🗂️ 4. Project Structure

```text
E-Commerce-Catalogue-Traffic-Analysis/
│
├── src/
│   ├── Main.java
│   ├── ProductCategoryBST.java
│   ├── SKUHashTable.java
│   ├── SKUHashMapComparison.java
│   ├── MicroserviceGraph.java
│   ├── TrafficAnalysis.java
│   └── GiftCardPairFinder.java
│
├── output/
│   └── execution-output.txt
│
├── README.md
└── .gitignore
```

## 📊 5. Decision Table

| Problem | Data Structure / Pattern | Reason |
|---|---|---|
| Product Category Search | Binary Search Tree | Supports organized insertion, searching and traversal |
| SKU Look-up | Hash Table with Chaining | Handles collisions while providing efficient average look-ups |
| Built-in SKU Look-up | HashMap | Provides ready-made key-value storage and efficient retrieval |
| Microservice Dependencies | Directed Graph | Represents dependencies between services |
| Deployment Ordering | BFS / DFS | Produces a valid topological order |
| Circular Dependency | Cycle Detection | Identifies dependency loops |
| Peak Traffic Analysis | Sliding Window | Calculates consecutive three-hour totals efficiently |
| Gift Card Matching | Two Pointers | Finds matching pairs in a sorted array efficiently |

## ⏱️ 6. Algorithm Complexity

| Algorithm | Time Complexity |
|---|---|
| BST Insertion | Average: O(log n), Worst: O(n) |
| BST Search | Average: O(log n), Worst: O(n) |
| BST Traversals | O(n) |
| Hash Table Look-up | Average: O(1), Worst: O(n) |
| HashMap Look-up | Average: O(1) |
| Graph BFS / DFS | O(V + E) |
| Cycle Detection | O(V + E) |
| Sliding Window | O(n) |
| Two Pointers | O(n) |

*Note: Complexity values represent standard expected or worst-case behavior under the stated conditions.*

## 🛠️ 7. Technologies Used

- **Programming Language:** Java
- **IDE:** IntelliJ IDEA / Eclipse / VS Code
- **Core Concepts:** Data Structures and Algorithms
- **Java Collections:** HashMap, ArrayList, Queue
- **Algorithms:** Tree Traversal, Graph Traversal, Sliding Window, Two Pointers

## ▶️ 8. How to Run

**Step 1:** Clone the repository.

```bash
git clone <repository-url>
```

**Step 2:** Navigate to the project directory.

```bash
cd E-Commerce-Catalogue-Traffic-Analysis
```

**Step 3:** Compile the Java files.

```bash
javac -d out src/*.java
```

**Step 4:** Execute the main class.

```bash
java -cp out Main
```
