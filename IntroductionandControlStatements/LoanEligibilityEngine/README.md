
# 🏦 Loan Eligibility Engine

## 📌 Project Overview

The **Loan Eligibility Engine** is a Java-based application designed to evaluate loan applications based on predefined banking eligibility criteria.

The system analyzes an applicant's age, monthly income, existing EMI, credit score, employment type, and existing loans to determine whether the applicant qualifies for a loan.

It processes multiple applicants, validates input records, calculates loan offers, and generates a formatted decision report.

## 🎯 Objectives

- Implement loan eligibility rules using Java.
- Apply conditional statements and nested if-else logic.
- Use enhanced switch expressions with pattern matching.
- Process multiple applicants using loops.
- Handle invalid records using `continue`.
- Control daily loan approvals using `break`.
- Generate formatted reports using `printf()`.
- Handle edge cases and invalid inputs.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java 21+ | Core programming |
| Java Records | Applicant data representation |
| Sealed Interface | Credit score classification |
| Pattern Matching | Enhanced switch expression |
| Collections | Store applicant records |
| printf() | Formatted decision table |

## 📂 Project Structure

```text
LoanEligibilityEngine/
│
├── src/
│   └── com/
│       └── bank/
│           │
│           ├── Main.java
│           │
│           ├── model/
│           │   └── Applicant.java
│           │
│           └── service/
│               └── LoanEligibilityService.java
│
├── out/                  # Compiled class files
│
└── README.md
```

### File Description

**1. Main.java**
- Acts as the driver class.
- Creates ten applicant records.
- Processes applicants using a for-each loop.
- Skips invalid records using `continue`.
- Stops processing after reaching the daily approval limit using `break`.
- Displays the final decision table.

**2. Applicant.java**
- Uses a Java record to represent applicant information.
- Stores applicant ID, name, age, income, EMI, credit score, employment type, and existing loans.

**3. LoanEligibilityService.java**
- Implements all six eligibility rules.
- Validates applicant information.
- Classifies credit scores.
- Determines loan products and interest rates.
- Generates the final eligibility decision.

## 📋 Business Rules

The system follows six major business rules.

### 1. Age Eligibility

- Minimum age: 21 years.
- Maximum age: 60 years.
- Applicants outside this range are rejected.

### 2. Income-to-EMI Ratio

The applicant's monthly income must be at least three times the existing monthly EMI.

**Formula:**

```text
Monthly Income >= Monthly EMI × 3
```

Example:

```text
Monthly Income = ₹60,000
Monthly EMI    = ₹15,000

Required Income = ₹15,000 × 3
                = ₹45,000

Result: Eligible
```

### 3. Credit Score Classification

| Credit Score | Classification |
|---|---|
| 750–900 | Excellent |
| 700–749 | Good |
| 650–699 | Fair |
| 300–649 | Poor |

### 4. Employment Type

- Salaried employees are eligible for this rule.
- Self-employed applicants must have a minimum monthly income of ₹25,000.
- Other employment types are rejected.

### 5. Existing Loan Limit

- Maximum permitted existing loans: 2.
- Applicants having more than 2 existing loans are rejected.

### 6. Final Loan Decision

An applicant is approved only when all eligibility conditions are satisfied.

A poor credit score results in rejection.

## 💰 Interest Rate and Loan Product

| Credit Score | Interest Rate | Loan Product |
|---|---:|---|
| Excellent | 8.5% | Premium Loan |
| Good | 10% | Standard Loan |
| Fair | 12.5% | Basic Loan |
| Poor | Not Available | Not Available |

The interest rate and loan product are mapped using an enhanced switch expression with pattern matching.

## ⚙️ Java Concepts Implemented

| Java Concept | Application |
|---|---|
| Packages | Organize project files |
| Records | Store applicant details |
| `var` | Local variable type inference |
| If-else ladder | Eligibility evaluation |
| Nested conditions | Employment and final decision |
| Sealed interface | Credit score categories |
| Enhanced switch | Loan offer mapping |
| Pattern matching | Match credit score types |
| For-each loop | Process applicants |
| `continue` | Skip invalid applicants |
| `break` | Stop at approval limit |
| `printf()` | Display formatted output |

## 🚀 Installation and Execution

### Prerequisites

Make sure the following are installed:

- JDK 21 or later
- VS Code / IntelliJ IDEA / Eclipse
- Command Prompt or Terminal

### Step 1: Verify Java Installation

Open the terminal and execute:

```bash
java -version
```

Check the Java compiler:

```bash
javac -version
```

Ensure the installed version is Java 21 or later.

### Step 2: Open the Project Directory

Navigate to the project root directory:

```bash
cd LoanEligibilityEngine
```

### Step 3: Compile the Java Files

Execute the following command:

```bash
javac -d out src/com/bank/model/Applicant.java src/com/bank/service/LoanEligibilityService.java src/com/bank/Main.java
```

**Explanation:**

- `javac` – Java compiler.
- `-d out` – Stores compiled `.class` files inside the `out` directory.
- Remaining arguments – Java source files to compile.

### Step 4: Run the Application

Execute:

```bash
java -cp out com.bank.Main
```

**Explanation:**

- `java` – Runs the Java application.
- `-cp out` – Sets the classpath to the compiled output directory.
- `com.bank.Main` – Specifies the fully qualified main class name.

## 📊 Sample Console Output

```text
========== LOAN ELIGIBILITY ENGINE ==========

ID    Name       Age          Credit       Decision        Loan Product
---------------------------------------------------------------------
101   Arun       25           780          Approved        Premium Loan
102   Bala       -5           750          Invalid         N/A
103   Charan     30           950          Invalid         N/A
104   Deepak     35           800          Approved        Premium Loan
105   Ezhil      19           720          Rejected - Age   N/A
106   Fahad      40           720          Approved        Standard Loan

Daily approval limit reached!

Total Approved: 3
==============================================
```

## 🧪 Test Cases

| Applicant | Input Condition | Expected Result |
|---|---|---|
| Arun | All rules satisfied | Approved |
| Bala | Negative age | Invalid |
| Charan | Credit score above 900 | Invalid |
| Deepak | All rules satisfied | Approved |
| Ezhil | Age below 21 | Rejected |
| Fahad | All rules satisfied | Approved |
| Gokul | Self-employed income below ₹25,000 | Rejected |
| Hari | Fair credit score | Approved |
| Imran | More than 2 existing loans | Rejected |
| John | Poor credit score | Rejected |

**Note:** Processing stops when three approvals are reached. Therefore, applicants after Fahad are not processed during the sample execution.

## ⚠️ Edge Cases Handled

- Zero monthly income.
- Negative age.
- Negative income or EMI.
- Credit score below 300.
- Credit score above 900.
- Negative existing loan count.
- Invalid employment type.
- Daily approval limit reached.

## 📌 Expected Outcome

The application successfully demonstrates how Java programming concepts can be applied to a real-world banking pre-screening scenario.

It provides structured applicant validation, rule-based eligibility evaluation, loan product classification, and formatted decision reporting.

---

**Developed using Java 21+**
