package nl.Bank;

import nl.Bank.AppFlow.Banking;
import nl.Bank.AppFlow.Login;
import nl.Bank.AppFlow.Registration;
import nl.Bank.Database.DataManager;
import nl.Bank.Utilities.InputControl;

public class Main {

    /* Bank App 4.8
     * Lets user login or register (required)
     * Lets user use bank menu to control funds */

    public static void main(String[] args) {

        DataManager dataManager = new DataManager();
        dataManager.createDatabase();

        //Run program
        goToBankMenu(getValidUser());

    }
    public static void goToBankMenu(String validUser) {

        Banking fundsControl = new Banking();
        fundsControl.banking(validUser);
    }
    public static String getValidUser() {

        InputControl inC = new InputControl();
        Registration registration = new Registration();
        Login login = new Login();

        String validUser = null;
        boolean userFound;

        //User info
        System.out.print("\nWelcome to the Bank App\nUse the corresponding numbers to select option\nDo you have an account?\n1) Yes\n2) No\nOption: ");

        do {
            switch (inC.integerOnly()) {
                case 1 -> {
                    validUser = login.login();
                    userFound = true;
                }
                case 2 -> {
                    validUser = registration.registration();
                    userFound = true;
                }
                default -> {
                    System.out.println("Error: invalid option!");
                    userFound = false;
                }
            }
        } while (!userFound);
        return validUser;
    }

}