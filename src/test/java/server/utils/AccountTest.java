package server.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    void signUpTest() {
        String email="test@pku.edu.com";
        String pw="123456";
        assert(Account.signUp(email, pw)==0);
        assert(Account.signUp(email,pw)==-1);
    }


    @Test
    void loginTest() {
        String email="test@pku.edu.com";
        String pw="123456";
        int id=Account.login(email, pw);
        assert(id==1);
    }
}