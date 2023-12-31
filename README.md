# BANKING APPLICATION

## Table of Contents
1. Used Technologies 
2. Approach
3. Programming Choices 
   - Examples of Code from the Internet 
   - Explanation per Evaluation Criterion and Implementation 
   - Reflection on Your Own Work

### Used Technologies
The code is written in Java, JDK Oracle openjdk - 18.0.2.1, using the IntelliJ IDE.

IMPORTANT: Before running the program, update the PATH in the DATABASE CLASS!

### Libraries:
* java.util.*
* javax.crypto.*
* java.nio.*
* java.security.*
* java.io.*

# Approach
For my assignment, I chose a banking application with the scope of managing user financial resources.

I adopted an iterative approach during development, gradually adding functionalities.

### Folder Structure:

1. nl.Bank.AppFlow: Input of data in choice menus.
2. nl.Bank.Database: Saving and retrieving data.
3. nl.Bank.Utilities: Ensuring correct input.

### Class Structure:
* Registration: Creating new users.
* Login: Logging in existing users.
* Banking: Financial management.
* Account: User data collection.
* DataManager: Structured storage of user data. 
* Crypto: Making stored data unreadable.
* InputControls: Ensuring correct data input.
* Examples of Code from the Internet
* The Crypto class, found on javaguides.net, was copied and adapted. The class has 3 functions, with 2 used for data encryption (line 27) or decryption (line 38).

# Programming Choices

### Main
Before using the banking application, a valid email address is required. The application functionality depends on it. Therefore, you must either log in or register to ensure proper operation. Once the registration or login process is completed, the email address is returned to the main menu of the application from the respective registration or login class.

### Registration
Users are prompted to enter a first name, last name, email, and a PIN code. All entered data is validated using the functions of the InputControls class. Users remain in this process until the data is correctly entered. The entered data is immediately saved via the DataManager class, and users receive a message indicating a registration bonus has been deposited into their account. Subsequently, the Banking class is initiated, and the user's email address is passed to that class.

### Login
After completing registration, users can log in directly via the login menu. This class prompts users to enter their email and a PIN code. The database is then searched for the user's email and the corresponding PIN code. If an incorrect PIN code is entered, users have three attempts to enter the correct one before the program closes to prevent abuse. If the email and PIN match, the Banking class is started, and the user's email address is passed to that class.

### Banking
At the start of this class, the user's data is retrieved from the database via the email address. Subsequently, all data is loaded into the Account class, and the user can use the banking menu where they can manage financial resources through two accounts, a checking and savings account. Management is done through a menu where each user action leads to a function with the following options:

* Balance: View finances on the checking and savings accounts and the total
* Deposit: When depositing money, the amount is placed in one of the two accounts, with a checking function explained further in the account class
* Withdraw: When withdrawing money, the amount is deducted from one of the two accounts, with a checking function explained further in the account class
* Transfer: Transfer between the checking and savings accounts or to a friend via email (in the form of a payment request)
* History: Transaction history retrieved from the database and printed on the screen via a for loop. Transactions are separated based on their transaction ID and displayed on the screen with (account +/- amount)
* Settings: Change email or PIN and report a problem to the bank
* Logout: End the program

### Account
In the Account class, all user data is stored. Line 67 setChecking(); Line 109 setSaving(); functions are more detailed. If you want to use these functions, you pass two values: the type of transaction consisting of adding, subtracting, transferring, and the amount you want to process. When withdrawing, it is first checked that you do not spend more than you can afford. Then the transaction is carried out, and a transaction ID is created with the amount, which is then stored in your account.

The storage structure works as follows: The transaction ID consists of 2 letters, the first representing the account (C = checking, S = savings) and the 2nd letter is the action (D = deposit, W = withdraw), followed by a $ symbol and the amount.

- Example:

SD$150: account: savings, transaction: deposit of one hundred fifty
CW$200: account: checking, transaction: withdraw of two hundred
All transactions that occur are stored sequentially in the database class.

### DataManager
This is an extensive class that I will describe per function.

Line 24 CreateDatabase();: This function is called at the beginning of the program and ensures that a txt file is created to store all data. If the file already exists, the program will display a message at the beginning, saying "Connected to database."

Line 41 importAllAccounts();: When importing all accounts, the txt file is read line by line. Each line represents an account that is added to an ArrayList.

Line 62 exportAllAccounts();: When exporting all accounts, each account is written to a txt file, one account per line. I use an ArrayList so that I can add users endlessly and perform operations on the ArrayLists, saving and loading with these functions.

The structure in which accounts are stored looks like this:

Mail%voornaam%achternaam%pincode%checking%saving_CD$150%DW$200%CS$20
In the following functions, I break this string apart with the .split("_") function, which breaks the line into 2 parts.

1st part: user data
2nd part: transaction history
Both parts can then be split again with the same .split("%") function.

Line 84 getLoginCode(mail);: This function searches the ArrayList for the given email, finds the associated PIN code, and returns that number.

Line 103 getAccountDetails(mail);: Here, we search for the provided email address, and if it matches, the full account is returned.

Line 119 makeTransaction(full account + transaction ID);: Here, we again search for the user's email address. If it matches the email address in the ArrayList, the new transaction is appended to the transaction history and saved.

Line 149 getHistory(mail);: This function searches for a matching email in the ArrayList. If found, the function returns the history of that account. The history is further divided in the Banking class and displayed on the screen with the corresponding account and whether it was withdrawn or deposited.

Line 170 & 195 reset functions for mail and pin: Both functions work the same way; they search for the user's email address and update the email/pin in the ArrayList.

### InputControls
I created a separate class for input control because it is applied to multiple classes. Even if the written program becomes larger, you can further extend this class and easily find everything in one place.

Line 12 integerOnly();: This function is especially created for all choice menus to ensure that only numbers can be entered. If you enter a char or String in a menu, the program would stop working due to an error message.

Line 27 comfirmValidNames();: This function checks the input of names and only allows char or String; symbols and numbers are not allowed.

Line 52 comfirmValidMailAddress();: Further explained in examples from the internet.

Line 73 validPinCode();: This code ensures that you can only enter a 4-digit PIN code. However, it is not entirely to my liking because it is not possible to enter a PIN code with 0000.

Line 96 srtAccount();: The function that concatenates all account data for exporting to the database. All data is assembled according to the following structure.

- Example:

Mail%voornaam%achternaam%pincode%checking%saving_
The % symbols are between the parts so that the program can later split it via a .split() method. At the end of the sentence, you will find an _; this is because the transactions are appended here, making a distinction between accounts and transactions.

Examples of Code from the Internet
I found the Crypto class on javaguides.net and copied and adapted it to my liking. The class consists of 3 functions, of which 2 are used to encrypt the data (Line 27) or decrypt it (Line 38). The function at the top at Line 15 supports only with a key.

The comfirmValidMailAddress(); function in the InputControls class was found on GeeksForGeeks.org. Originally, I created a function that checked for an @ and a . in an email address, but I was not satisfied with it because it was too easy to pass without using a real email address.

Reflection on My Own Work
I am (for now) satisfied with what I have created. However, there are a few things I would improve, such as the transaction IDs because they might not be clear enough if another programmer has to take over my work.

I would also make some adjustments in the DataManager class. Almost all functions work based on searching for an email in the ArrayList, and I might have been better off creating a function that only does that and using it in other functions.