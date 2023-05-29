package server.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    void signUpTest() {
        String email="test@pku.edu.com";
        String pw="123456";
        Account.signUp(email, pw);
    }

    @Test
    void accountExistsTest() {
        String email="test@pku.edu.com";
        String pw="123456";
        Account.signUp(email, pw);
    }

    @Test
    void loginTest() {
        String email="test@pku.edu.com";
        String pw="123456";
        int id=Account.login(email, pw);
        System.out.println(id);
    }
}