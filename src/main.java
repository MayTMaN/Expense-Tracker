import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class main {
    public static void main(String[] args) throws IOException {

        File expenseTracker = new File("expenses.txt");

        if (!expenseTracker.exists()) {
            expenseTracker.createNewFile();
        }

        Expense expense = new Expense();

        switch (args[0]) {
            case "add" -> {
                ExpenseService.addExpense(args,expense);
            }

            case "list" -> {
                ExpenseService.listExpense(args,expense);
            }

            case "summary" -> {
                if (args.length > 1 && args[1].equals("--month")) {
                    ExpenseService.monthSummary(args,expense);
                } else {
                    ExpenseService.totalSummary(args,expense);
                }

            }

            case "delete" -> {
                ExpenseService.deleteExpense(args,expense);
            }

            case "update" -> {
                ExpenseService.updateExpense(args, expense);
            }

            default -> {
                System.out.println("Unknown command.");
            }

        }




    }

}
