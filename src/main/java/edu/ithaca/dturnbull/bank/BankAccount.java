package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            if (isAmountValid(startingBalance) == false){
                throw new IllegalArgumentException("Starting balance is invalid");
            }
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws InsufficientFundsException if the amount is greater than the balance
     * @throws IllegalArgumentException if the amount is negative
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (!isAmountValid(amount)){
            throw new IllegalArgumentException("Amount is invalid");
        }
        
        else if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1){
            return false;
        }
        else if (email.indexOf('.') == -1){
            return false;
        }
        if (email.startsWith(".") || email.contains(".@")) {
            return false;
        }
        else {
            String emailRegex = "^(?!.*[._%+-]{2})[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
            return email.matches(emailRegex);
        } 

    }

    public static boolean isAmountValid(double amount){
        //returns true if amount is positive and has two decimal places or less, false otherwise
        if (amount <= 0){
            return false;
        }
        else {
            String amountString = Double.toString(amount);
            int decimalIndex = amountString.indexOf('.');
            if (decimalIndex == -1){
                return true;
            }
            else {
                return amountString.length() - decimalIndex <= 3;
            }
        }
    }

    public void deposit(double amount){
        if (isAmountValid(amount)){
            balance += amount;
        }
        else {
            throw new IllegalArgumentException("Amount is invalid");
        }
    }
    
    
    public void transfer(BankAccount other, double amount) throws InsufficientFundsException {
        if (isAmountValid(amount)){
            if (amount <= balance){
                balance -= amount;
                other.deposit(amount);
            }
            else {
                throw new InsufficientFundsException("Not enough money");
            }
        }
        else {
            throw new IllegalArgumentException("Amount is invalid");
        }
    }
}