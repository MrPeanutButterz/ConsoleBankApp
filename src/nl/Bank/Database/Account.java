package nl.Bank.Database;

import nl.Bank.Utilities.InputControl;

import java.util.Objects;

public class Account {

    DataManager dataManager = new DataManager();
    InputControl inC = new InputControl();


    //Account details
    private String firstname;
    private String lastname;
    private String email;
    private int pin;

    //Funds
    private int savings;
    private int checking;


    //Constructor
    public Account() {

    }

    //Get
    public String getFirstName() {
        return firstname;
    }
    public String getLastName() {
        return lastname;
    }
    public String getEmail() {
        return email;
    }
    public int getPinCode() {
        return pin;
    }
    public int getBalance() {
        return getChecking() + getSavings();
    }
    public int getChecking() {
        return checking;
    }
    public int getSavings() {
        return savings;
    }

    //Set
    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }
    public void setLastName(String lastName) {
        this.lastname = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPinCode(int pinCode) {
        this.pin = pinCode;
    }

    //Transaction
    public void setChecking(String addSubSetTran, int value) {

        if (Objects.equals(addSubSetTran, "add")) {
            this.checking = getChecking() + value;

            String account = inC.strAccount(getEmail(), getFirstName(),
                    getLastName(), getPinCode(), getChecking(), getSavings());
            dataManager.makeTransaction(account, "%CD$" + value);


        } else if (Objects.equals(addSubSetTran, "sub")) {
            if ((getChecking() - value) < 0) {
                System.out.println("Insufficient funds\nCurrent balance in checking: " + getChecking());

            } else {
                this.checking = getChecking() - value;
                String account = inC.strAccount(getEmail(), getFirstName(), getLastName(), getPinCode(), getChecking(), getSavings());
                dataManager.makeTransaction(account, "%CW$" + value);

            }
        } else if (Objects.equals(addSubSetTran, "tran")) {
            if ((getChecking() - value) < 0) {
                System.out.println("Insufficient funds\nCurrent balance in checking: " + getChecking());

            } else {
                this.checking = getChecking() - value;
                this.savings = getSavings() + value;
                String account = inC.strAccount(getEmail(), getFirstName(), getLastName(), getPinCode(), getChecking(), getSavings());
                dataManager.makeTransaction(account, "%CT$" + value);

            }
        } else if (Objects.equals(addSubSetTran, "set")) {
            this.checking = value;

        } else if (Objects.equals(addSubSetTran, "egg")) {
            this.checking = getChecking() + value;

            String account = inC.strAccount(getEmail(), getFirstName(),
                    getLastName(), getPinCode(), getChecking(), getSavings());
            dataManager.makeTransaction(account, "%CF$" + value);
        }
    }
    public void setSavings(String addSubSetTran, int value) {

        if (Objects.equals(addSubSetTran, "add")) {
            this.savings = getSavings() + value;

            String account = inC.strAccount(getEmail(), getFirstName(),
                    getLastName(), getPinCode(), getChecking(), getSavings());
            dataManager.makeTransaction(account, "%SD$" + value);

        } else if (Objects.equals(addSubSetTran, "sub")) {
            if ((getSavings() - value) < 0) {
                System.out.println("Insufficient funds\nCurrent balance in savings: " + getSavings());

            } else {
                this.savings = getSavings() - value;

                String account = inC.strAccount(getEmail(), getFirstName(),
                        getLastName(), getPinCode(), getChecking(), getSavings());
                dataManager.makeTransaction(account, "%SW$" + value);

            }
        } else if (Objects.equals(addSubSetTran, "tran")) {
            if ((getSavings() - value) < 0) {
                System.out.println("Insufficient funds\nCurrent balance in checking: " + getSavings());

            } else {
                this.savings = getSavings() - value;
                this.checking = getChecking() + value;

                String account = inC.strAccount(getEmail(), getFirstName(),
                        getLastName(), getPinCode(), getChecking(), getSavings());
                dataManager.makeTransaction(account, "%ST$" + value);

            }
        } else if (Objects.equals(addSubSetTran, "set")) {
            this.savings = value;

        } else if (Objects.equals(addSubSetTran, "egg")) {
            this.savings = getSavings() + value;

            String account = inC.strAccount(getEmail(), getFirstName(),
                    getLastName(), getPinCode(), getChecking(), getSavings());
            dataManager.makeTransaction(account, "%SF$" + value);
        }
    }
}