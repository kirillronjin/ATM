import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class ATM {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner sc = new Scanner(System.in);

        Bank theBank = new Bank("My Bank");
        User aUser = theBank.addUser("John", "Doe", "1234");

        User curUser;
        while (true) {
            curUser = ATM.mainMenuPrompt(theBank, sc);
            ATM.printUserMenu(curUser, sc);
        }
    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc) {
        String userID;
        String pin;
        User authUser;

        do {
            System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter userID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();

            authUser = theBank.userLoging(userID, pin);
            if (authUser == null) {
                System.out.println("ERROR! Try again.");
            }
        } while(authUser == null);
        return authUser;
    }

    private static void printUserMenu(User theUser, Scanner sc) {
        theUser.printAccountsSummary();

        int choice;

        do {
            System.out.printf("Welcome %s, what would you like to do?\n",
                    theUser.getFirstName());
            System.out.println("  1) Show account transaction history");
            System.out.println("  2) Withdraw");
            System.out.println("  3) Deposit");
            System.out.println("  4) Transfer");
            System.out.println("  5) Quit");
            System.out.println();
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            if (choice < 0 || choice > 5) {
                System.out.println("Try again");
            }
        } while(choice < 0 || choice > 5);

        switch (choice) {
            case 1 -> ATM.showTransHistory(theUser, sc);
            case 2 -> ATM.withdrawFunds(theUser, sc);
            case 3 -> ATM.depositFunds(theUser, sc);
            case 4 -> ATM.transferFunds(theUser, sc);
            case 5 -> sc.nextLine();
        }
        if (choice != 5) {
            ATM.printUserMenu(theUser, sc);
        }
    }

    public static void showTransHistory(User theUser, Scanner sc) {
        int theAcct;

        do {
            System.out.printf("Enter the number (1-%d) whose accounts you want to see: ", theUser.getAccounts().size());
            theAcct = sc.nextInt() - 1;
            if (theAcct < 0 || theAcct >= theUser.getAccounts().size()) {
                System.out.println("Try again");
            }
        } while (theAcct < 0 || theAcct >= theUser.getAccounts().size());
        theUser.printAcctTransHistory(theAcct);
    }

    private static void transferFunds(User theUser, Scanner sc) {
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;
        do {
            System.out.printf("Enter the number (1-%d) of the accounts to transfer from: ", theUser.getAccounts().size());
            fromAcct = sc.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.getAccounts().size()) {
                System.out.println("Try again");
            }
        } while(fromAcct < 0 || fromAcct >= theUser.getAccounts().size());
        acctBal = theUser.getAcctBalance(fromAcct);

        do {
            System.out.printf("Enter the number (1-%d) of the accounts to transfer to: ", theUser.getAccounts().size());
            toAcct = sc.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.getAccounts().size()) {
                System.out.println("Try again");
            }
        } while(toAcct < 0 || toAcct >= theUser.getAccounts().size());

        do {
            System.out.printf("Enter the amount to transfer (max %.02f): ", acctBal);
            amount = sc.nextDouble();
            if (amount < 0 || amount > acctBal) {
                System.out.println("Invalid amount");
            }
        } while (amount < 0 || amount > acctBal);

        theUser.addAcctTransaction(fromAcct, -1*amount,
                String.format("Transfer to account %s",
                theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount,
                String.format("Transfer from account %s",
                        theUser.getAcctUUID(fromAcct)));
    }

    private static void withdrawFunds(User theUser, Scanner sc) {
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of the accounts to withdraw from: ", theUser.getAccounts().size());
            fromAcct = sc.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.getAccounts().size()) {
                System.out.println("Try again");
            }
        } while(fromAcct < 0 || fromAcct >= theUser.getAccounts().size());
        acctBal = theUser.getAcctBalance(fromAcct);

        do {
            System.out.printf("Enter the amount to withdraw (max %.02f): ", acctBal);
            amount = sc.nextDouble();
            if (amount < 0 || amount > acctBal) {
                System.out.println("Invalid amount");
            }
        } while (amount < 0 || amount > acctBal);

        sc.nextLine();
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        theUser.addAcctTransaction(fromAcct, -1*amount, memo);
    }

    private static void depositFunds(User theUser, Scanner sc) {
        int toAcct;
        double amount;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of the accounts to deposit to: ", theUser.getAccounts().size());
            toAcct = sc.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.getAccounts().size()) {
                System.out.println("Try again");
            }
        } while(toAcct < 0 || toAcct >= theUser.getAccounts().size());

        do {
            System.out.print("Enter the amount to deposit: ");
            amount = sc.nextDouble();
            if (amount < 0) {
                System.out.println("Invalid amount");
            }
        } while (amount < 0);

        sc.nextLine();
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        theUser.addAcctTransaction(toAcct, amount, memo);
    }
}


