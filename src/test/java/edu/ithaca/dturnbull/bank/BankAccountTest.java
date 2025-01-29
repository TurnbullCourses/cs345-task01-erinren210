package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance(), 0.001);
        
        BankAccount bankAccount2 = new BankAccount("c@d.com", 100);
        assertEquals(100, bankAccount2.getBalance(), 0.001);

        BankAccount bankAccount3 = new BankAccount("e@f.com", 0);
        assertEquals(0, bankAccount3.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);

        //insufficient funds exceptions
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
        bankAccount.withdraw(100);
        assertEquals(0, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(1));

        //invalid amount exception
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-1));
        BankAccount bankAccount2 = new BankAccount("c@d.com", 200);
        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(-1));
        assertThrows(IllegalArgumentException.class, () -> bankAccount2.withdraw(100.001));
    }

    @Test
    void isEmailValidTest(){
        //assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address ; valid
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
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 100.01));
    }

    @Test
    void isAmountValidTest(){
        assertTrue(BankAccount.isAmountValid(100)); //valid amount
        assertTrue(BankAccount.isAmountValid(100.01)); //valid amount with two decimal places

        assertFalse(BankAccount.isAmountValid(-100)); //negative amount

        assertFalse(BankAccount.isAmountValid(0)); //zero

        assertFalse(BankAccount.isAmountValid(100.001)); //more than two decimal places
    }
}