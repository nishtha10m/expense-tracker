import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExpenseTracker {
    private List<Expense> expenses;
    private static final String FILE_NAME = "expenses.dat";

    public ExpenseTracker() {
        expenses = loadExpenses();
    }

    public void addExpense(String description, double amount) {
        Expense expense = new Expense(description, amount);
        expenses.add(expense);
        System.out.println("Added expense: " + expense);
    }

    public void displayExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            for (Expense expense : expenses) {
                System.out.println(expense);
            }
        }
    }

    public void saveExpenses() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(expenses);
            System.out.println("Expenses saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Expense> loadExpenses() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Expense>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        ExpenseTracker tracker = new ExpenseTracker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter 'add' to add an expense, 'show' to display expenses, 'save' to save, 'quit' to quit:");
            String action = scanner.nextLine();

            switch (action) {
                case "add":
                    System.out.print("Enter expense description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter expense amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // consume the newline character
                    tracker.addExpense(description, amount);
                    break;
                case "show":
                    tracker.displayExpenses();
                    break;
                case "save":
                    tracker.saveExpenses();
                    break;
                case "quit":
                    tracker.saveExpenses();
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid action. Please try again.");
                    break;
            }
        }
    }
}
