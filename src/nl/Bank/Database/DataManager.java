package nl.Bank.Database;

import nl.Bank.Utilities.InputControl;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.io.*;

public class DataManager {

    /* Create database, Import, Export */

    InputControl inC = new InputControl();
    Crypto crypto = new Crypto();
    final String secretKey = "Hide";

    private final String fileName = "Database.txt";
    private final String filePath = "C:\\Users\\ceysb\\OneDrive\\Documenten\\School\\Novi\\2. Basis Programmeren\\BankAppCollection\\BankApp4.8\\src\\nl\\Bank\\Database\\" + fileName;
    private final ArrayList<String> allAccounts = new ArrayList<>();



    //Global
    public void createDatabase() {

        //if database don't exist make new
        File data = new File(filePath);

        try {
            if (data.createNewFile()) {
                System.out.println("Database created: " + fileName);
            } else {
                System.out.println("Connected to database");
            }

        } catch (IOException e) {
            System.out.println("Error: unable to read database");
            e.printStackTrace();
        }
    }
    public void importAllAccounts() {

        allAccounts.clear();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            //import accounts in lines en decryptString
            while (scanner.hasNextLine()) {
                String account = scanner.nextLine();
                String decryptedString = crypto.decryptString(account, secretKey);
                allAccounts.add(decryptedString);
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: unable to read database");
            e.printStackTrace();
        }
    }
    public void exportAllAccounts() {

        try {
            File file = new File(filePath);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            //Loop allAccounts -> encryptString en write lines
            for (String oneAccount : allAccounts) {
                String encryptedString = crypto.encryptString(oneAccount, secretKey);
                bw.write(encryptedString);
                bw.newLine();
            }
            bw.close();

        } catch (IOException e) {
            System.out.println("Error: saving account");
            e.printStackTrace();
        }
    }

    //Functional
    public int getLoginCode(String mail) {

        importAllAccounts();

        String[] part;
        int pin;
        for (String allAccount : allAccounts) {

            //Finds email
            part = allAccount.split("%");
            if (Objects.equals(part[0], mail)) {

                //returns pin
                pin = Integer.parseInt(part[3]);
                return pin;
            }
        }
        return 0;
    }
    public String[] getAccountDetails(String mail) {

        importAllAccounts();

        String[] accountDetails = new String[6];
        for (String account : allAccounts) {

            //Finds email
            String[] accountPart = account.split("_");
            accountDetails = accountPart[0].split("%");

            if (Objects.equals(accountDetails[0], mail)) {
                break;
            }
        }
        return accountDetails;
    }
    public void makeTransaction(String strAccount, String transID) {

        if (strAccount == null) { return; }
        String[] strAccountDetails = strAccount.split("%");
        String accountMail = strAccountDetails[0];

        importAllAccounts();

        //add or replace account to database
        boolean knownUser = false;
        for (int i = 0; i < allAccounts.size(); i++) {

            //finds email
            String[] parts = allAccounts.get(i).split("_");
            String[] part = parts[0].split("%");
            String history = parts[1] + transID;

            if (Objects.equals(part[0], accountMail)) {

                //replace index
                allAccounts.set(i, strAccount + history);
                knownUser = true;
            }
        }

        //if not found add new account
        if (!knownUser) { allAccounts.add(strAccount + transID); }

        exportAllAccounts();
    }
    public String getHistory(String mail) {

        importAllAccounts();
        String history = null;

        //add or replace account to database
        for (String allAccount : allAccounts) {

            //finds email
            String[] parts = allAccount.split("_");
            history = parts[1];

            String[] part = parts[0].split("%");


            if (Objects.equals(part[0], mail)) {
                return history;
            }
        }
        return history;
    }
    public void resetMail(String oldMail, String newMail) {


        importAllAccounts();

        for (int i = 0; i < allAccounts.size(); i++) {

            String[] accountPart = allAccounts.get(i).split("_");
            String[] accountDetails = accountPart[0].split("%");

            //Finds email
            if (Objects.equals(accountDetails[0], oldMail)) {

                String account = inC.strAccount(newMail, accountDetails[1], accountDetails[2],
                        Integer.parseInt(accountDetails[3]), Integer.parseInt(accountDetails[4]),
                        Integer.parseInt(accountDetails[5]));
                String fullAccount = account + accountPart[1];

                allAccounts.set(i, fullAccount);
                break;
            }
        }

        exportAllAccounts();
    }
    public void resetPin(int oldPin, int newPin) {

        importAllAccounts();

        for (int i = 0; i < allAccounts.size(); i++) {

            String[] accountPart = allAccounts.get(i).split("_");
            String[] accountDetails = accountPart[0].split("%");

            //Finds email
            if (Objects.equals(accountDetails[3], Integer.toString(oldPin))) {

                String account = inC.strAccount(accountDetails[0], accountDetails[1], accountDetails[2],
                        newPin, Integer.parseInt(accountDetails[4]),
                        Integer.parseInt(accountDetails[5]));
                String fullAccount = account + accountPart[1];

                allAccounts.set(i, fullAccount);
                break;
            }
        }

        exportAllAccounts();
    }

}