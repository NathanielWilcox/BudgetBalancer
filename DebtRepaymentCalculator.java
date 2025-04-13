//This program will calculate how long it would take to pay off a user’s debt using various //budgeting methods (snowball, interest-only, consolidation).

import java.util.Scanner;

public class DebtRepaymentCalculator {

    Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //Main menu
        System.out.println("Welcome to the Debt Repayment Calculator!");
        System.out.println("What are your debt repayment goals?");
        System.out.println("1. Snowball Method");
        System.out.println("2. Interest-Only Method");
        System.out.println("3. Debt Consolidation Method");
        System.out.println("4. Exit");
        System.out.print("Please enter your choice (1-4): ");

        int choice = input.nextInt();
        switch (choice) {
            case 1:
                snowballMethod(input);
                break;
            case 2:
                interestOnlyMethod(input);
                break;
            case 3:
                debtConsolidationMethod(input);
                break;
            case 4:
                System.out.println("Thank you for using the Debt Repayment Calculator. Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please restart the program.");
        }
    }


    
}
