import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ExpenseService {
    static void addExpense(String[] args, Expense expense) {
        for (int i = 1; i < args.length; i++) {
            if (args[i].equals("--description")) {
                StringBuilder desc = new StringBuilder();
                for (int x = i+1; x < args.length && !args[x].startsWith("--"); x++) {
                    desc.append(args[x]);
                    desc.append(" ");
                }
                expense.description = desc.toString().trim();
            } else if (args[i].equals("--amount")) {
                expense.amount = Double.parseDouble(args[i+1]);
            }
        }
        if (expense.amount < 0) {
            System.out.println("Amount cannot be negative.");
            return;
        }
        try (BufferedReader fileReader = new BufferedReader(new FileReader("expenses.txt"))) {
            String line;
            String lastLine = "";
            while ((line = fileReader.readLine()) != null) {
                lastLine = line;
            }

            if (lastLine.isEmpty()) {
                expense.ID = 1;
            } else {
                String[] lines = lastLine.split("\\|");
                int lastId = Integer.parseInt(lines[0]);
                expense.ID = lastId+1;
            }

        } catch (IOException e) {
            System.out.println("An error occured.");
        }
        try (FileWriter fileWriter = new FileWriter("expenses.txt", true)) {
            fileWriter.write(expense.ID + "|" + expense.date + "|" + expense.description + "|" + expense.amount + "\n");
            System.out.println("Expense added successfully (ID:" + expense.ID + ")");
        } catch (IOException e) {
            System.out.println("An error occured.");
        }
    }

    static void listExpense(String[] args, Expense expense) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("expenses.txt"))) {
            String line;
            System.out.printf("%-5s %-12s %-15s %-10s%n", "ID", "Date", "Description", "Amount");
            while ((line = fileReader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                String[] split = line.split("\\|");
                System.out.printf("%-5s %-12s %-15s $%-10s%n", split[0], split[1], split[2], split[3]);
            }
        } catch (IOException e) {
            System.out.println("An error occured.");
        }
    }

    static void totalSummary(String[] args, Expense expense) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("expenses.txt"))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                String[] split = line.split("\\|");
                expense.summary += Double.parseDouble(split[3]);
            }
            System.out.println("Total expenses: $" + expense.summary);
        } catch (IOException e) {
            System.out.println("An error occured.");
        }
    }

    static void deleteExpense(String[] args, Expense expense) {
        if (args.length < 3 || !args[1].equals("--id")) {
            System.out.println("Usage: delete --id <id>");
            return;
        }
        try {
            List<String> lines = Files.readAllLines(Path.of("expenses.txt"));
            boolean found = false;
            for (int i = 0; i < lines.size(); i++) {
                String[] split = lines.get(i).split("\\|");
                if (split[0].equals(args[2])) {
                    lines.remove(i);
                    found = true;
                    System.out.println("ID deleted.");
                    break;
                }
            }
            if (!found) {
                System.out.println("ID not found.");
            }
            Files.write(Path.of("expenses.txt"), lines);
        } catch (IOException e) {
            System.out.println("An error occured.");
        }
    }

    static void monthSummary(String[] args, Expense expense) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("expenses.txt"))) {
            String line;
            boolean found = false;
            int targetMonth = Integer.parseInt(args[2]);
            while ((line = fileReader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                String[] split = line.split("\\|");
                String[] month = split[1].split("-");
                int monthNumber = Integer.parseInt(month[1]);
                if (monthNumber == targetMonth) {
                    expense.summary += Double.parseDouble(split[3]);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Month not found");
                return;
            }
            System.out.println("Total expenses: $" + expense.summary);
        } catch (IOException e) {
            System.out.println("An error occured.");
        }
    }

    static void updateExpense (String[] args, Expense expense) {
        try {
            List<String> lines = Files.readAllLines(Path.of("expenses.txt"));
            boolean found = false;

            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("--description")) {
                    StringBuilder desc = new StringBuilder();
                    for (int x = i+1; x < args.length && !args[x].startsWith("--"); x++) {
                        desc.append(args[x]);
                        desc.append(" ");
                    }
                    expense.description = desc.toString().trim();
                } else if (args[i].equals("--amount")) {
                    expense.amount = Double.parseDouble(args[i+1]);
                }
            }
            for (int i = 0; i < lines.size(); i++) {
                String[] split = lines.get(i).split("\\|");
                if (split[0].equals(args[2])) {
                    lines.set(i,split[0] + "|" + expense.date + "|" + expense.description + "|" + expense.amount);
                    found = true;
                    System.out.println("Expense updated.");
                    break;
                }
            }
            if (!found) {
                System.out.println("ID not found.");
            }
            Files.write(Path.of("expenses.txt"), lines);
        } catch (IOException e) {
            System.out.println("An error occured.");
        }
    }
 }
