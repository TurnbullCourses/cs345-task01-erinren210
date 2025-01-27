package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address ; valid
        assertFalse(BankAccount.isEmailValid(""));         // empty string         ; invalid

        assertFalse(BankAccount.isEmailValid("@gmail.com")); //missing prefix      ; invalid
        assertFalse(BankAccount.isEmailValid("erinren210@")); //missing domain     ; invalid
        assertFalse(BankAccount.isEmailValid("erinren210gmail.com")); //missing @  ; invalid

        assertFalse(BankAccount.isEmailValid("erinren..210@gmail.com")); //double dots       ; invalid, checks for partition of double special characters
        assertFalse(BankAccount.isEmailValid("erinren$210@gmail.com")); //invalid character  ; invalid, checks for partition of non-allowed special character
        assertFalse(BankAccount.isEmailValid(".erinren210@gmail.com")); //starts with dot    ; invalid, checks for partition of starting with special character
        assertFalse(BankAccount.isEmailValid("erinren210.@gmail.com")); //dot before @       ; invalid, checks for partition of special character right before the '@'

        //assertFalse(BankAccount.isEmailValid("erinren210@gmail.c")); //last part of domain too short  ; invalid, border case, checks for partition of domain being at least two characters       
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}