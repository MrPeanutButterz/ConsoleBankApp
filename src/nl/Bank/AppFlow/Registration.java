package nl.Bank.AppFlow;

import nl.Bank.Database.DataManager;
import nl.Bank.Utilities.InputControl;

public class Registration {

    /* Sign up required */

    public String registration() {

        //Objects
        InputControl inC = new InputControl();
        DataManager dataManager = new DataManager();

        //Sign up form
        System.out.println("\nPlease fill out the sign up form");
        System.out.print("First name: ");
        String fName = inC.confirmValidNames();
        System.out.print("Last name: ");
        String lName = inC.confirmValidNames();
        System.out.print("Email: ");
        String mail = inC.confirmValidMailAddress();
        int pin = inC.validPinCode();

        //Register bonus
        System.out.println("\nThank you for signing up\nAs a gift we have made a deposit in your checking account!");
        String account = inC.strAccount(mail, fName, lName, pin, 25,0);
        dataManager.makeTransaction(account, "%CD$25");

        return mail;
    }

}