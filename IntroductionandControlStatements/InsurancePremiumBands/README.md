
# 🛡️ Insurance Premium Bands

## 📌 Project Overview

The **Insurance Premium Bands** project is a Java-based application that calculates insurance premiums based on an applicant's age, BMI, smoking status, and policy term.

The system calculates Body Mass Index (BMI), classifies applicants into different health categories, assigns additional premium loading percentages, and determines the final insurance premium.

It processes eight applicants using Java control flow statements and demonstrates pattern matching, labelled break, enhanced for loops, and JVM memory management.

## 🎯 Objectives

- Calculate BMI using weight and height.
- Classify applicants based on BMI categories.
- Assign loading percentages using nested if statements.
- Determine base premiums using switch expressions.
- Implement pattern matching with guards (`when`).
- Calculate final premiums using arithmetic and logical operators.
- Process eight applicants using enhanced for loops.
- Implement labelled break for batch validation.
- Understand stack memory, heap memory, and garbage collection.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java 21+ | Core programming |
| Java Records | Applicant data representation |
| Sealed Interface | Age band classification |
| Pattern Matching | Switch expressions |
| Collections | Store applicant records |
| Enhanced For Loop | Process applicants |
| printf() | Formatted output |

## 📂 Project Structure

```text
InsurancePremiumBands/
│
├── src/
│   └── com/
│       └── insurance/
│           │
│           ├── Main.java
│           │
│           ├── model/
│           │   └── Applicant.java
│           │
│           └── service/
│               └── PremiumService.java
│
├── out/
├── run.bat
└── README.md
```

### File Description

**1. Main.java**
- Acts as the driver class.
- Creates eight applicant records.
- Performs batch validation.
- Uses enhanced for loops.
- Uses labelled break to stop validation when an ineligible applicant is found.
- Displays the premium report.

**2. Applicant.java**
- Uses a Java record to represent applicant information.
- Stores applicant ID, name, age, weight, height, smoking status, and policy term.

**3. PremiumService.java**
- Calculates BMI.
- Classifies BMI categories.
- Determines loading percentages.
- Classifies age bands.
- Determines base premiums using enhanced switch expressions.
- Calculates final insurance premiums.
- Validates applicant information.

## 📋 Business Rules

### 1. BMI Calculation

BMI is calculated using the following formula:

```text
BMI = Weight (kg) / Height (m)²
```

Example:

```text
Weight = 70 kg
Height = 1.75 m

BMI = 70 / (1.75 × 1.75)
BMI = 22.86
```

### 2. BMI Classification and Loading

| BMI Range | Category | Loading |
|---|---|---:|
| Below 16 | Severe Underweight | 20% |
| 16–18.49 | Underweight | 5% |
| 18.5–24.99 | Normal | 0% |
| 25–27.49 | Overweight | 5% |
| 27.5–29.99 | Overweight | 10% |
| 30–34.99 | Obese | 15% |
| 35 and above | Severe Obese | 25% |

The loading percentage is calculated using nested if statements.

### 3. Age Band Classification

| Age Range | Category |
|---|---|
| 18–30 | Young |
| 31–45 | Adult |
| 46–60 | Senior |
| Above 60 | Not Eligible |

### 4. Base Premium by Age and Policy Term

| Age Band | Term up to 10 Years | Term up to 20 Years | Term above 20 Years |
|---|---:|---:|---:|
| Young | ₹8,000 | ₹10,000 | ₹12,000 |
| Adult | ₹10,000 | ₹13,000 | ₹15,000 |
| Senior | ₹15,000 | ₹18,000 | ₹22,000 |

The base premium is determined using a switch expression with pattern matching and guards.

### 5. Smoking Loading

- Non-smoker: 0% additional loading.
- Smoker: 20% additional loading.

### 6. Final Premium Calculation

```text
Total Loading = BMI Loading + Smoking Loading

Final Premium =
Base Premium + (Base Premium × Total Loading / 100)
```

Example:

```text
Base Premium = ₹13,000
BMI Loading = 5%
Smoking Loading = 20%

Total Loading = 25%

Final Premium = 13000 + (13000 × 25 / 100)

Final Premium = ₹16,250
```

## ⚙️ Java Concepts Implemented

| Java Concept | Application |
|---|---|
| Packages | Organize source files |
| Records | Store applicant details |
| var | Local variable type inference |
| Nested if | BMI classification |
| Sealed Interface | Age band representation |
| Switch Expression | Base premium calculation |
| Pattern Matching | Match age band types |
| Guards (when) | Check policy term conditions |
| Arithmetic Operators | BMI and premium calculation |
| Relational Operators | Applicant eligibility |
| Logical Operators | Combine validation conditions |
| Enhanced For Loop | Process applicants |
| Labelled Break | Stop batch validation |
| Stack and Heap | JVM memory management |
| Garbage Collection | Automatic memory cleanup |

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
cd InsurancePremiumBands
```

### Step 3: Compile the Source Files

```bash
javac -d out src/com/insurance/model/Applicant.java src/com/insurance/service/PremiumService.java src/com/insurance/Main.java
```

### Step 4: Execute the Application

```bash
java -cp out com.insurance.Main
```

## 📊 Sample Output

```text
===== INSURANCE BATCH VALIDATION =====

Applicant Arun - Valid
Applicant Bala - Valid
Applicant Charan - Valid
Applicant Deepak - Valid
Applicant Ezhil is INELIGIBLE.
Batch validation stopped!

========== PREMIUM REPORT ==========

ID    Name       Age    BMI            BMI Category   Loading      Premium
--------------------------------------------------------------------------
101   Arun       25     22.86          Normal         0.00         8000.00
102   Bala       35     26.23          Overweight     25.00        16250.00
103   Charan     50     31.14          Obese          15.00        20700.00
104   Deepak     28     18.37          Underweight    5.00         12600.00
105   Ezhil      66     N/A            Ineligible     N/A          N/A
106   Fahad      40     32.65          Obese          35.00        13500.00
107   Gokul      45     23.53          Normal         0.00         13000.00
108   Hari       60     25.95          Overweight     25.00        18750.00

===================================
```

## 🧪 Test Cases

| Applicant | Condition | Expected Result |
|---|---|---|
| Arun | Normal BMI, non-smoker | ₹8,000 |
| Bala | Overweight BMI, smoker | ₹16,250 |
| Charan | Obese BMI, non-smoker | ₹20,700 |
| Deepak | Underweight BMI | ₹12,600 |
| Ezhil | Age above 60 | Ineligible |
| Fahad | Obese BMI, smoker | ₹13,500 |
| Gokul | Normal BMI, non-smoker | ₹13,000 |
| Hari | Overweight BMI, smoker | ₹18,750 |

## 🧠 JVM Memory Management

### Stack Memory

- Stores local primitive variables.
- Stores local object references.
- Maintains method execution frames.
- Each thread has its own JVM stack.

### Heap Memory

- Stores objects created using `new`.
- Stores applicant objects.
- Stores service objects and collection objects.
- Shared across application threads.

### Garbage Collection

When an object is no longer reachable through any live reference, it becomes eligible for garbage collection.

The JVM automatically manages object memory and reclaims unused heap space.

## 📌 Expected Outcome

The project demonstrates how Java programming concepts can be applied to a real-world insurance premium classification scenario.

It provides BMI-based risk classification, age-based premium calculation, smoking-related loading, applicant validation, and automated premium reporting.

---

**Developed using Java 21+**
