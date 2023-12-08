package nl.Bank.AppFlow;

import nl.Bank.Database.Account;
import nl.Bank.Database.DataManager;
import nl.Bank.Utilities.InputControl;

import java.util.Objects;

public class Banking {

    /* Gives user control over funds */

    Account account = new Account();
    InputControl inC = new InputControl();
    DataManager dataManager = new DataManager();


    int thisValue;

    public void banking(String validUser) {
        constructAccountDetails(validUser);
        mainMenu();
    }
    public void constructAccountDetails(String validUser) {
        try {
            String[] details = dataManager.getAccountDetails(validUser);
            account.setEmail(details[0]);
            account.setFirstName(details[1]);
            account.setLastName(details[2]);
            account.setPinCode(Integer.parseInt(details[3]));
            account.setChecking("set", Integer.parseInt(details[4]));
            account.setSavings("set", Integer.parseInt(details[5]));
        } catch (NumberFormatException e) {
            System.out.println("Account is blocked deu to suspicious activities\nPlease contact your bank");
            System.exit(0);
        }
    }
    private void mainMenu() {

        boolean running = true;
        do {
            System.out.print("\nBank menu:\n1) Balance\n2) Deposit\n3) Withdraw\n4) Transfer\n5) History\n6) Settings\n7) Logout\nOption: ");
            switch (inC.integerOnly()) {
                case 1 -> balance();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> transfer();
                case 5 -> history();
                case 6 -> settings();
                case 7 -> running = logout();
                default -> System.out.println("Invalid option");
            }
        } while (running);
    }


    //Menu options
    public void balance() {
        System.out.println("\nTotal balance: \t$" + account.getBalance());
        System.out.println("Checking: \t\t$" + account.getChecking());
        System.out.println("Saving: \t\t$" + account.getSavings());
    }
    public void deposit() {
        System.out.print("\nMake a deposit in:\n1) Checking\n2) Savings\nOption: ");
        switch (inC.integerOnly()) {
            case 1 -> {
                System.out.print("Amount: $");
                thisValue = inC.integerOnly();
                account.setChecking("add", thisValue);
            }
            case 2 -> {
                System.out.print("Amount: $");
                thisValue = inC.integerOnly();
                account.setSavings("add", thisValue);
            }
            default -> System.out.println("invalid option");
        }
    }
    public void withdraw() {
        System.out.print("\nMake withdraw form:\n1) Checking\n2) Savings\nOption: ");
        switch (inC.integerOnly()) {
            case 1 -> {
                System.out.print("Amount: $");
                thisValue = inC.integerOnly();
                account.setChecking("sub", thisValue);
            }
            case 2 -> {
                System.out.print("Amount: $");
                thisValue = inC.integerOnly();
                account.setSavings("sub", thisValue);
            }
            default -> System.out.println("invalid option");
        }
    }
    public void transfer() {
        System.out.print("\nTransfer money:\n1) Between account\n2) To a friend\nOption: ");

        switch (inC.integerOnly()) {
            case 1 -> {
                System.out.print("1) Checking\n2) Savings\nFrom: ");
                switch (inC.integerOnly()) {
                    case 1 -> {
                        System.out.print("From checking to savings\nAmount: $");
                        thisValue = inC.integerOnly();
                        account.setChecking("tran", thisValue);
                    }
                    case 2 -> {
                        System.out.print("From savings to checking\nAmount: $");
                        thisValue = inC.integerOnly();
                        account.setSavings("tran", thisValue);
                    }
                    default -> System.out.println("invalid option");
                }
            }
            case 2 -> {
                System.out.print("From Checking only\nAmount: $");
                thisValue = inC.integerOnly();
                System.out.print("Friends email: ");
                String friendMail = inC.confirmValidMailAddress();
                System.out.println("$" + thisValue + " has been send to " + friendMail);
                account.setChecking("sub", thisValue);
            }
            default -> System.out.println("invalid option");
        }
    }
    public void history() {

        String history = dataManager.getHistory(account.getEmail());
        String[] transactions = history.split("%");
        System.out.println("\nAccount\t\t\t\tAmount");

        for (String transaction : transactions) {
            String[] part = transaction.split("\\$");

            if (Objects.equals(part[0], "CD")) {
                System.out.println("Checking\t\t\t+ $" + "\033[32;1;2m" + part[1] + "\033[0m");

            } else if (Objects.equals(part[0], "CW")) {
                System.out.println("Checking\t\t\t- $" + "\033[31;1m" + part[1] + "\033[0m");

            } else if (Objects.equals(part[0], "CT")) {
                System.out.println("Checking to Savings   $" + part[1]);

            } else if (Objects.equals(part[0], "CF")) {
                System.out.println("Checking\t\t\t+ $" + "\033[32;1;2m" + part[1] + "\033[0m"
                        + "\t\tEasterEgg!");


            } else if (Objects.equals(part[0], "SD")) {
                System.out.println("Savings\t\t\t\t+ $" + "\033[32;1;2m" + part[1] + "\033[0m");

            } else if (Objects.equals(part[0], "SW")) {
                System.out.println("Savings\t\t\t\t- $" + "\033[31;1m" + part[1] + "\033[0m");

            } else if (Objects.equals(part[0], "ST")) {
                System.out.println("Savings to Checking   $" + part[1]);

            } else if (Objects.equals(part[0], "SF")) {
                System.out.println("Savings\t\t\t\t+ $" + "\033[32;1;2m" + part[1] + "\033[0m"
                        + "\t\tEasterEgg!");
            }
        }
    }
    public void settings() {
        System.out.print("\n1) Edit Profile\n2) Report problem\nSelect option: ");
        switch (inC.integerOnly()) {
            case 1 -> {
                System.out.print("\n1) Edit mail address\n2) Edit pin code\nSelect option: ");

                switch (inC.integerOnly()) {
                    case 1 -> {
                        System.out.print("\nNew mail address: ");
                        String newMail = inC.confirmValidMailAddress();

                        if (!Objects.equals(account.getEmail(), newMail)) {
                            dataManager.resetMail(account.getEmail(), newMail);
                            account.setEmail(newMail);
                            System.out.println("Email saved");

                        } else {
                            System.out.println("Error: This mail address is already in use!");
                        }
                    }
                    case 2 -> {
                        int newPin = inC.validPinCode();
                        if (account.getPinCode() != newPin) {
                            dataManager.resetPin(account.getPinCode(), newPin);
                            account.setPinCode(newPin);
                            System.out.println("Pin code saved");

                        } else {
                            System.out.println("This code is already in use!");
                        }
                    }
                }
            }
            case 2 -> System.out.println("\nPlease send an email to Complain@theBank.com\nor call 0800 don tca llu s");
            case 12 -> {

                //EasterEgg
                System.out.println("\nErr0r: some strange activity");
                account.setChecking("egg", 35635166);
                account.setSavings("egg", 24552134);

            }
            default -> System.out.println("Invalid option");
        }
    }
    public boolean logout() {
        System.out.println("\nThank you for using our bank\nHave a nice day " + account.getFirstName());
        return false;
    }

}