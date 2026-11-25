
# 🏦 Insurance Policy Hierarchy

## 📌 Project Overview

The **Insurance Policy Hierarchy** is a Java-based application that demonstrates Object-Oriented Programming concepts through an insurance management system.

The application supports multiple insurance policies, including Motor, Health, Travel, and Home insurance.

Each policy has its own premium calculation formula while sharing common attributes and reporting functionality through an abstract parent class and interface.

The system also demonstrates arrays, enums, encapsulation, inheritance, runtime polymorphism, abstraction, composition, and the Open/Closed Principle.

## 🎯 Objectives

- Create an enum to represent policy statuses.
- Implement an abstract Policy class.
- Apply encapsulation with policy number validation.
- Implement Motor, Health, and Travel policy subclasses.
- Demonstrate runtime polymorphism using a Policy array.
- Use interfaces for common reporting functionality.
- Demonstrate composition using PolicyHolder.
- Calculate regional premium collections using 2D arrays.
- Use jagged arrays for regions with different branch counts.
- Extend the application with a new Home policy.
- Demonstrate the Open/Closed Principle.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java 21+ | Core programming |
| Enum | Policy status management |
| Abstract Class | Common policy structure |
| Interface | Common reporting contract |
| Inheritance | Policy specialization |
| Polymorphism | Dynamic method dispatch |
| Arrays | Store multiple policies |
| 2D Arrays | Regional collections |
| Jagged Arrays | Different branch counts |
| printf() | Formatted reports |

## 📂 Project Structure

```text
InsurancePolicyHierarchy/
│
├── src/
│   └── com/
│       └── insurance/
│           │
│           ├── Main.java
│           │
│           ├── model/
│           │   ├── Policy.java
│           │   ├── PolicyHolder.java
│           │   ├── PolicyStatus.java
│           │   ├── MotorPolicy.java
│           │   ├── HealthPolicy.java
│           │   ├── TravelPolicy.java
│           │   └── HomePolicy.java
│           │
│           ├── interfaces/
│           │   └── Reportable.java
│           │
│           └── service/
│               └── CollectionReport.java
│
├── out/
├── run.bat
└── README.md
```

## 📋 Business Rules

### 1. Policy Status

The PolicyStatus enum contains four statuses:

| Status | Description |
|---|---|
| DRAFT | Policy created but not activated |
| ACTIVE | Policy currently active |
| LAPSED | Policy validity expired |
| CANCELLED | Policy cancelled |

### 2. Policy Number Validation

Every policy must have a unique-format policy number.

Expected format:

```text
POL-1234
```

Invalid policy numbers are rejected through IllegalArgumentException.

### 3. Premium Calculation

**Motor Insurance**

```text
Premium = Vehicle Value × Risk Percentage / 100
```

**Health Insurance**

| Age | Premium Rate |
|---|---:|
| Up to 40 | 2% |
| 41–60 | 3% |
| Above 60 | 5% |

```text
Premium = Coverage Amount × Age-based Rate / 100
```

**Travel Insurance**

```text
Premium = Travel Days × Daily Premium
```

**Home Insurance**

```text
Premium = Property Value × Coverage Rate / 100
```

These formulas are illustrative business rules for this project.

## ⚙️ Java Concepts Implemented

| Java Concept | Application |
|---|---|
| Class and Object | Policy and PolicyHolder |
| Encapsulation | Private fields and validation |
| Enum | PolicyStatus |
| Abstract Class | Policy |
| Abstract Method | calculatePremium() |
| Inheritance | Policy subclasses |
| Method Overriding | Individual premium formulas |
| Runtime Polymorphism | Policy array |
| Interface | Reportable |
| Composition | Policy contains PolicyHolder |
| Arrays | Store eight policies |
| 2D Arrays | Equal branches per region |
| Jagged Arrays | Different branches per region |
| Open/Closed Principle | Adding HomePolicy |

## 🚀 Installation and Execution

### Prerequisites

- JDK 21 or later
- VS Code / IntelliJ IDEA / Eclipse
- Command Prompt or Terminal

### Step 1: Verify Java Installation

```bash
java -version
```

Check the compiler:

```bash
javac -version
```

### Step 2: Navigate to the Project

```bash
cd InsurancePolicyHierarchy
```

### Step 3: Compile the Source Files

Run:

```bash
javac -d out src/com/insurance/interfaces/Reportable.java src/com/insurance/model/*.java src/com/insurance/service/*.java src/com/insurance/Main.java
```

### Step 4: Execute the Application

```bash
java -cp out com.insurance.Main
```

## 📊 Sample Output

```text
========== INSURANCE POLICY REPORT ==========

Policy No    Holder          Status       Premium (₹)
--------------------------------------------------------
POL-1001     Arun            ACTIVE       10000.00
POL-1002     Bala            ACTIVE       12000.00
POL-1003     Charan          ACTIVE       10000.00
POL-1004     Deepak          DRAFT        24000.00
POL-1005     Ezhil           ACTIVE       1000.00
POL-1006     Fahad           LAPSED       2250.00
POL-1007     Gokul           ACTIVE       5000.00
POL-1008     Hari            ACTIVE       12000.00

===== REGIONAL COLLECTIONS (2D ARRAY) =====

Region 1 Total: ₹37000.00
Region 2 Total: ₹60000.00
Region 3 Total: ₹43000.00
Grand Total: ₹140000.00

===== REGIONAL COLLECTIONS (JAGGED ARRAY) =====

Region 1 (2 branches): ₹25000.00
Region 2 (3 branches): ₹60000.00
Region 3 (4 branches): ₹54000.00
Grand Total: ₹139000.00
```

## 🧪 Test Cases

| Test Case | Input | Expected Result |
|---|---|---|
| Valid policy number | POL-1001 | Accepted |
| Invalid policy number | ABC-1001 | Exception |
| Null policy holder | null | Exception |
| Valid status | ACTIVE | Accepted |
| Invalid status | null | Exception |
| Motor policy | Vehicle value 500000 | Premium ₹10000 |
| Health policy | Coverage 500000, age 35 | Premium ₹10000 |
| Travel policy | 10 days × ₹100 | Premium ₹1000 |
| Home policy | Property 5000000, rate 0.10% | Premium ₹5000 |

## 🧩 Class Diagram

The class hierarchy demonstrates:

- Policy as the abstract parent class.
- MotorPolicy, HealthPolicy, TravelPolicy, and HomePolicy as subclasses.
- Reportable as the common reporting interface.
- PolicyHolder as a composed object.
- PolicyStatus as an enum.

## 📌 Open/Closed Principle

The project follows the Open/Closed Principle by allowing new policy types to be introduced through inheritance.

For example, HomePolicy extends the existing Policy class and provides its own calculatePremium() implementation.

Existing policy implementations remain unchanged.

## 📌 Expected Outcome

The application demonstrates how object-oriented programming can be used to build an extensible insurance management system.

It supports multiple policy types, individual premium calculations, common reporting, and regional collection analysis.

---

**Developed using Java 21+**
