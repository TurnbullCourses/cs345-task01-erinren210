package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
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
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        // email can not be empty or not contain @
        if (email == null || email.isEmpty()) {
            return false;
        }

        String lowEmail = email.toLowerCase();

        for (int i = 0; i < lowEmail.length(); i++){
            char c = lowEmail.charAt(i);
            // check email contains a-z and/or 0-9
            if ((c >= 'a' && c <= 'z') || (c >= 0 && c <= 9)){
                continue;
            } 

            if ((Character.valueOf(c).equals(".")) || (Character.valueOf(c).equals("-")) || (Character.valueOf(c).equals("_"))){

                // cannot be a special character at the beginning of end of email
                if (i == 0 || i == (lowEmail.length() - 1)){
                    return false;
                }

                // cannot have two special characters in a row
                if (i > 0 && (lowEmail.charAt(i - 1) == '.' || lowEmail.charAt(i - 1) == '-' || lowEmail.charAt(i - 1) == '_')){
                    return false;
                }

            } else if (c == '@') {
                // can only contain one "@", cannot be at beginning or end
                if (i == 0 || i == lowEmail.length() - 1 || lowEmail.indexOf('@') != lowEmail.lastIndexOf('@')) {
                    return false;
                }

            } else {
                // everything else is false
                return false;
            }
        } 
        // everything else is valid
        return true;
    }
}