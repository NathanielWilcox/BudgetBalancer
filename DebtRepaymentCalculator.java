//This program will calculate how long it would take to pay off a user’s debt using various //budgeting methods (snowball, interest-only, consolidation).
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// This class will handle the debt repayment calculations and user interactions.
public class DebtRepaymentCalculator {

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        double interestRate = 0; // Initialize interest rate
        double income = 0; // Initialize income
        double expenses = 0; // Initialize expenses
        double totalDebt = 0; // Initialize total debt
        int numDebts = 0; // Initialize number of debts

        //Main menu
        System.out.println("Welcome to the Debt Repayment Calculator! Let's get started:/n");
        System.out.println("Please enter your name:");
        String name = input.nextLine();
        System.out.println("Hello, " + name + "! Let's get started with your debt repayment plan.");
        System.out.println(name + ", what is your net monthly income?");
        income = input.nextDouble();
        System.out.println("What are your monthly expenses, not including payments on your current debt?");
        expenses = input.nextDouble();

        // Check if the user has enough income to cover expenses
        if (income <= expenses) {
            System.out.println("Sorry " + name + ", your expenses exceed or equal your income. Please adjust your budget.");
            return;
        } else {
            System.out.println("Your income exceeds your expenses. Let's proceed with the debt calculation.");
        }

        System.out.println("Please enter your best guess as to your Credit Score (0-850):");
        int creditScore = input.nextInt();
        System.out.println("Now let's get some info on your debts!");
        System.out.println("Please enter the total number of debts you have:");
        numDebts = input.nextInt();
        // Check if the number of debts is valid   
        if (numDebts <= 0) {
                System.out.println("Invalid number of debts. Please enter a positive integer.");
                return;
            }
            else {
                System.out.println("You have " + numDebts + " debts to manage.");
            }

        // Create an ArrayList to store the debts
        ArrayList<Debt> debts = new ArrayList<>();

        // Collect debt details from the user
        for (int i = 0; i < numDebts; i++) {
            System.out.println("Enter the amount for debt " + (i + 1) + ":");
            double amount = input.nextDouble();

            System.out.println("Enter the interest rate for debt " + (i + 1) + " (as a percentage, e.g., 5 for 5%):");
            interestRate = input.nextDouble() / 100;

            System.out.println("Enter the type of debt " + (i + 1) + ":");
            System.out.println("1. Student Loan");
            System.out.println("2. Credit Card");
            System.out.println("3. Personal Loan");
            System.out.println("4. Other");
            int typeOption = input.nextInt();
            String type;
            switch (typeOption) {
                case 1:
                    type = "Student Loan";
                    break;
                case 2:
                    type = "Credit Card";
                    break;
                case 3:
                    type = "Personal Loan";
                    break;
                case 4:
                    System.out.println("Please specify the type of debt:");
                    input.nextLine(); // Consume the newline character
                    type = input.nextLine();
                    break;
                default:
                    System.out.println("Invalid option. Defaulting to 'Other'.");
                    type = "Other";
                    break;
            }

            debts.add(new Debt(amount, interestRate, type));
            totalDebt += amount; // Add the debt amount to the total debt
        }

        System.out.println("Your total debt amount is: $" + totalDebt);

        System.out.println("What is your preffered method of debt payment?");
        System.out.println("1. Snowball method (pay off smallest debt first)");
        System.out.println("2. Interest-only method (pay off highest interest debt first)");
        System.out.println("3. Consolidation method (combine all debts into one for only one interest rate and payment)");

        int method = input.nextInt();
        

        switch (method) {
            case 1:
                calculateSnowballMethod(debts, income, expenses);
                break;
            case 2:
                calculateInterestOnlyMethod(debts, income, expenses);
                break;
            case 3:
                calculateConsolidationMethod(debts, income, expenses, creditScore);
                break;
            default:
                System.out.println("Invalid method selected. Please try again.");
                return;
        }
    }

    // Move the Debt class outside the main method and make it a static nested class
    public static class Debt {
        private double amount;
        private double interestRate;
        private String type;

        public Debt(double amount, double interestRate, String type) {
            this.amount = amount;
            this.interestRate = interestRate;
            this.type = type;
        }

        // Getters and setters for encapsulation
        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getInterestRate() {
            return interestRate;
        }

        public void setInterestRate(double interestRate) {
            this.interestRate = interestRate;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }


    // Methods to calculate the time to pay off debts using different methods
    // Snowball method: pay off the smallest debt first
    public static void calculateSnowballMethod(ArrayList<Debt> debts, double income, double expenses) {
        // Implement the snowball method calculation here
        // Sort debts by amount and pay off the smallest first
        Collections.sort(debts, Comparator.comparingDouble(Debt::getAmount));

        // Calculate the remaining income after expenses
        double remainingIncome = income - expenses;
        if (remainingIncome <= 0) {
            System.out.println("Your expenses exceed or equal your income. Please adjust your budget.");
            return;
        }

        // Calculate the time to pay off total debt using the snowball method
        double months = 0;
        for (Debt debt : debts) {
            double monthlyPayment = remainingIncome;
            while (debt.getAmount() > 0) {
            debt.setAmount(debt.getAmount() - monthlyPayment);
            months++;
            }
        }

        // Display the results
        System.out.println("You will pay off your debts in " + months + " months using the snowball method.");
        
    }

    // Interest-only method: pay off the highest interest debt first
    // This method will sort the debts by interest rate and pay off the highest first
    public static void calculateInterestOnlyMethod(ArrayList<Debt> debts, double income, double expenses) {
        // Sort debts by interest rate in descending order (highest interest first)
        debts.sort((d1, d2) -> Double.compare(d2.getInterestRate(), d1.getInterestRate()));

        // Calculate the remaining income after expenses
        double remainingIncome = income - expenses;
        if (remainingIncome <= 0) {
            System.out.println("Your expenses exceed or equal your income. Please adjust your budget.");
            return;
        }

        // Calculate the time to pay off each debt using the interest-only method
        double months = 0;
        for (Debt debt : debts) {
            double monthlyPayment = remainingIncome;
            while (debt.getAmount() > 0) {
            debt.setAmount(debt.getAmount() - monthlyPayment);
            months++;
            }
        }

        // Display the results
        System.out.println("You will pay off your debts in " + months + " months using the interest-only method.");
    
    }

    // Consolidation method: combine all debts into one for only one interest rate and payment
    // This method will calculate the new APR based on the total amount of debt and approximate credit score
    public static void calculateConsolidationMethod(ArrayList<Debt> debts, double income, double expenses, int creditScore) {
        // Combine all debts into one
        double totalDebtAmount = 0;
        for (Debt debt : debts) {
            totalDebtAmount += debt.getAmount();
        }

        // Generate a new APR based on the total debt and credit score
        double newInterestRate;
        if (creditScore >= 750) {
            newInterestRate = 0.05; // 5% for excellent credit
        } else if (creditScore >= 650) {
            newInterestRate = 0.10; // 10% for good credit
        } else if (creditScore >= 550) {
            newInterestRate = 0.15; // 15% for fair credit
        } else {
            newInterestRate = 0.20; // 20% for poor credit
        }

        // Calculate the remaining income after expenses
        double remainingIncome = income - expenses;
        if (remainingIncome <= 0) {
            System.out.println("Your expenses exceed or equal your income. Please adjust your budget.");
            return;
        }

        // Calculate the time to pay off the consolidated debt
        double monthlyPayment = remainingIncome;
        double months = 0;
        while (totalDebtAmount > 0) {
            double interest = totalDebtAmount * newInterestRate / 12; // Monthly interest
            totalDebtAmount += interest; // Add interest to the debt
            totalDebtAmount -= monthlyPayment; // Subtract the monthly payment
            months++;
        }

        // Display the results
        System.out.println("You will pay off your consolidated debt in " + months + " months with an interest rate of " 
                   + (newInterestRate * 100) + "%.");
    }
}


