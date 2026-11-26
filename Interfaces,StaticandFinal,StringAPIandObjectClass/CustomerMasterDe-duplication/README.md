
# 👥 Customer Master De-duplication

## 📌 Project Overview

The **Customer Master De-duplication** project is a Java-based application designed to identify and remove duplicate customer records received from multiple CRM channels.

The application uses normalized email addresses as unique customer identifiers. It implements custom equals() and hashCode() methods to ensure reliable duplicate detection using HashSet.

It also demonstrates Java Records, String API operations, String immutability, the String Constant Pool, interfaces, static and default methods, marker interfaces, and functional interfaces.

## 🎯 Objectives

- Create a Customer class with equals(), hashCode(), and toString().
- Remove duplicate customers using HashSet.
- Implement the same email-based value identity using Java Records.
- Compare manually implemented and generated methods.
- Normalize email addresses.
- Clean and split customer names.
- Mask customer phone numbers.
- Demonstrate String immutability.
- Understand the String Constant Pool.
- Implement interfaces with default and static methods.
- Demonstrate marker and functional interfaces.
- Apply static and final keywords.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java 21+ | Core programming |
| HashSet | Duplicate removal |
| Java Records | Value object representation |
| String API | String manipulation |
| equals() | Object comparison |
| hashCode() | Hash-based identification |
| Interfaces | Common notification contract |
| Default Methods | Reusable interface implementation |
| Static Methods | Interface helper functionality |
| Marker Interface | Customer audit classification |
| Functional Interface | Lambda expressions |
| static and final | Constants and utility methods |

## 📂 Project Structure

```text
CustomerMasterDeduplication/
│
├── src/
│   └── com/
│       └── crm/
│           │
│           ├── Main.java
│           │
│           ├── model/
│           │   ├── Customer.java
│           │   ├── CustomerRecord.java
│           │   └── ImportedCustomer.java
│           │
│           ├── interfaces/
│           │   ├── Notifier.java
│           │   ├── EmailNotifier.java
│           │   ├── SmsNotifier.java
│           │   ├── Auditable.java
│           │   └── CustomerProcessor.java
│           │
│           └── utility/
│               └── StringUtil.java
│
├── out/
├── run.bat
└── README.md
```

## 📋 Business Rules

### 1. Customer Identification

The normalized email address is considered the unique customer identifier.

Normalization includes:
- Removing leading and trailing spaces.
- Converting email addresses to lowercase.

Example:

```text
ARUN@GMAIL.COM
 arun@gmail.com
Arun@gmail.com
```

All three values represent the same customer identity.

### 2. Duplicate Removal

The application receives customer records from three CRM channels.

| Channel | Records |
|---|---:|
| Channel 1 | 3 |
| Channel 2 | 3 |
| Channel 3 | 3 |
| Total | 9 |

After duplicate removal:

```text
Total Records = 9
Unique Customers = 7
Duplicates Removed = 2
```

### 3. Object Comparison

The Customer class overrides:

- equals()
- hashCode()
- toString()

The CustomerRecord record automatically generates these methods based on its email component.

### 4. String Cleaning

| Operation | Method |
|---|---|
| Remove spaces | trim() |
| Lowercase conversion | toLowerCase() |
| Split full name | split() |
| Remove internal whitespace | replaceAll() |
| Mask phone number | substring(), repeat() |

### 5. Notification Services

The application supports:

- Email notifications.
- SMS notifications.
- Common notification status using a default method.
- Recipient validation using a static interface method.

## ⚙️ Java Concepts Implemented

| Java Concept | Application |
|---|---|
| Classes and Objects | Customer |
| Encapsulation | Private customer fields |
| Object Class | equals(), hashCode(), toString() |
| HashSet | Duplicate removal |
| Records | CustomerRecord |
| String API | Cleaning and normalization |
| String Immutability | Original String remains unchanged |
| String Constant Pool | == and equals() |
| static | Utility methods |
| final | Constants and immutable fields |
| Interface | Notifier |
| Default Method | notificationStatus() |
| Static Interface Method | isValidRecipient() |
| Marker Interface | Auditable |
| Functional Interface | CustomerProcessor |
| Lambda Expression | Customer processing |

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
cd CustomerMasterDeduplication
```

### Step 3: Compile the Source Files

```bash
javac -d out src/com/crm/interfaces/*.java src/com/crm/model/*.java src/com/crm/utility/*.java src/com/crm/Main.java
```

### Step 4: Run the Application

```bash
java -cp out com.crm.Main
```

## 📊 Sample Output

```text
===== CUSTOMER MASTER DE-DUPLICATION =====
Total records received: 9
Unique customers: 7

===== UNIQUE CUSTOMER RECORDS =====
Customer{name='Arun', email='arun@gmail.com', phone='9876543210'}
Customer{name='Bala', email='bala@gmail.com', phone='9876543211'}
Customer{name='Charan', email='charan@gmail.com', phone='9876543212'}
Customer{name='Deepak', email='deepak@gmail.com', phone='9876543214'}
Customer{name='Ezhil', email='ezhil@gmail.com', phone='9876543215'}
Customer{name='Fahad', email='fahad@gmail.com', phone='9876543217'}
Customer{name='Gokul', email='gokul@gmail.com', phone='9876543218'}

===== RECORD COMPARISON =====
Records equal: true
Same hash code: true

===== STRING CONSTANT POOL =====
first == second       : true
first == third        : false
first.equals(third)   : true
```

## 🧪 Test Cases

| Test Case | Input | Expected Result |
|---|---|---|
| Same email | arun@gmail.com | Duplicate |
| Uppercase email | ARUN@GMAIL.COM | Duplicate |
| Email with spaces | " arun@gmail.com " | Duplicate |
| Different email | bala@gmail.com | Unique |
| Empty email | "" | Exception |
| Null email | null | Exception |
| Same record email | Two normalized emails | Equal |
| String comparison | == | Reference comparison |
| String comparison | equals() | Content comparison |

## 📖 equals() and hashCode() Contract

- If two objects are equal according to equals(), they must have the same hashCode().
- If two objects have the same hashCode(), they are not necessarily equal.
- equals() and hashCode() must use the same identity fields.
- HashSet uses hashCode() to locate a bucket and equals() to identify duplicates.

In this project, both methods use the normalized email address.

## 📌 Expected Outcome

The application demonstrates how Java can be used to maintain clean and consistent customer master data.

It provides reliable duplicate detection, string normalization, reusable notification interfaces, and functional programming capabilities.

---


