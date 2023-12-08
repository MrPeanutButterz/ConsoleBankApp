package nl.Bank.Utilities;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class InputControl {

    //Objects
    Scanner input = new Scanner(System.in);

    public int integerOnly() {

        do {
            try {
                int tryNumber = input.nextInt();
                if (tryNumber > 0) {
                    return tryNumber;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Option menus can only contain numbers!");
                input.next();
            }
        }
        while (true);
    }
    public String confirmValidNames() {

        String name;
        boolean correctName = true;

        //Not allowed
        String specialCharacters = " !#$%&'()*+,-./:;<=>?@[]^_`{|}~0123456789";

        do {
            name = input.nextLine();

            //Arjan from cyber-security!
            //String[] stuff = specialCharacters.split("");
            //correctName = Arrays.stream(stuff).noneMatch(name::contains);

            //My solution
            String[] inputControl = name.split("");
            for (String s : inputControl) {
                if (specialCharacters.contains(s)) {
                    System.out.println("Error: Names can only contain characters!");
                    correctName = false;
                    break;
                } else {
                    correctName = true;
                }
            }

        } while (!correctName);
        return name;
    }
    public String confirmValidMailAddress() {
        boolean validMailAddress;
        String email;

        //restrict use
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

        do {
            email = input.next();
            Pattern pat = Pattern.compile(emailRegex);

            assert email != null;
            validMailAddress = pat.matcher(email).matches();

            if (validMailAddress) {
                return email;
            } else {
                System.out.println("Error: Invalid mail address!");
            }
        } while (true);
    }
    public int validPinCode() {
        int pinCode1;
        int pinCode2;

        do {
            System.out.print("Enter 4 digit pin code: ");
            pinCode1 = integerOnly();
            String forDigitPin = Integer.toString(pinCode1);

            if (forDigitPin.length() == 4) {
                System.out.print("Repeat the  same  code: ");
                pinCode2 = input.nextInt();

                if (pinCode1 == pinCode2) {
                    return pinCode1;
                } else {
                    System.out.println("Error: numbers don't match!");
                }
            } else {
                System.out.println("Error: pin must contain 4 digits!");
            }
        } while (true);
    }
    public String strAccount(String mail, String fName, String lName, int pin, int checkin, int saving) {
        return mail + "%" + fName + "%" + lName + "%" + pin + "%" + checkin + "%" + saving + "_";
    }

}