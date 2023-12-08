package nl.Bank.AppFlow;

import nl.Bank.Database.DataManager;
import nl.Bank.Utilities.InputControl;

public class Login {

    /* Login required */

    InputControl inC = new InputControl();
    DataManager dataManager = new DataManager();

    public String login() {

        while (true) {
            try {
                //Enter mail address
                System.out.print("\nLogin form\nEnter email: ");
                String validMailAddress = inC.confirmValidMailAddress();

                //Enter pin
                if (dataManager.getLoginCode(validMailAddress) != 0) {
                    for (int i = 2; i >= 0;) {
                        System.out.print("Enter pin: ");
                        int pin = inC.integerOnly();

                        if (pin == dataManager.getLoginCode(validMailAddress)) {
                            return validMailAddress;

                        } else if (i == 0) {
                            System.out.println("Account is blocked deu to suspicious activities\nPlease contact your bank");
                            System.exit(0);

                        } else {
                            System.out.println("Pin is incorrect: " + i + " more tries left");
                            i--;
                        }
                    }

                } else { System.out.println("Error: Unknown user"); }
            } catch (NumberFormatException e) { System.out.println("Unknown mail address"); }
        }
    }

}
