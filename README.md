# Expense Tracker CLI

A command-line expense tracker built in Java. Add, update, delete, and summarize your expenses — all stored locally in a file.

## Features

- Add expenses with a description and amount
- Update existing expenses by ID
- Delete expenses by ID
- List all expenses in a formatted table
- View a total summary of all expenses
- View a summary of expenses for a specific month

## Requirements

- Java 11 or higher

## Getting Started

Clone the repository and compile the source files:

```bash
javac main.java Expense.java ExpenseService.java
```

## Usage

### Add an expense
```bash
java main add --description "Team Lunch" --amount 25
# Expense added successfully (ID:1)
```

### List all expenses
```bash
java main list
# ID    Date         Description     Amount
# 1     2024-08-06   Team Lunch      $25.0
```

### Update an expense
```bash
java main update --id 1 --description "Team Dinner" --amount 30
# Expense updated.
```

### Delete an expense
```bash
java main delete --id 1
# ID deleted.
```

### View total summary
```bash
java main summary
# Total expenses: $55.0
```

### View summary for a specific month
```bash
java main summary --month 8
# Total expenses for August: $55.0
```

## Data Storage

Expenses are stored in a local `expenses.txt` file in the project directory, using `|` as a delimiter:

```
1|2024-08-06|Team Lunch|25.0
2|2024-08-06|Coffee|5.0
```

## Project Structure

```
├── main.java           # Entry point, routes commands
├── Expense.java        # Expense data model
├── ExpenseService.java # Command logic (add, list, delete, update, summary)
└── expenses.txt        # Auto-generated data file
```

## Limitations

- Descriptions with `|` characters will break the file format
- IDs are not reassigned after deletion
- No support for multiple users or remote storage

## Built With

Java — no external libraries or frameworks used.
