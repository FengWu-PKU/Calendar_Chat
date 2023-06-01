package server.utils;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class QQUserTest {

    @Test
    void insertUser() {
        QQUser u=new QQUser(1, "用户名", "4008208820", "alice@pku.edu.com", "2366441630", Date.valueOf("2001-11-03"));
        assert(QQUser.insertUser(u)==0);
    }

    @Test
    void deleteUser() {
        assert(QQUser.deleteUser(2)==-1);
        assert(QQUser.deleteUser(1)==0);
        assert(QQUser.deleteUser(1)==-1);
    }

    @Test
    void getUserByAccountID() {
        QQUser u=QQUser.getUserByAccountID(14);
        assert u!=null;
        //System.out.println(u.usr_name);
    }
}