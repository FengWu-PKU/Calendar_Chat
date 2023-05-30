package server.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void sendMsg() {
//        Account.signUp("Alice", "123456");
//        Account.signUp("Bob", "123456");
        int a=Account.login("Alice", "123456");
        int b=Account.login("Bob", "123456");
        Message m1=new Message(a,b,new Timestamp(System.currentTimeMillis()),"bob你好");
        assert Message.sendMsg(m1)==0;
    }

    @Test
    void readMsg() {
    }

    @Test
    void receiveMsg() {
        int a=Account.login("Alice", "123456");
        int b=Account.login("Bob", "123456");
        Message[] ans=Message.receiveMsg(b,a);
        for(Message msg:ans) {
            if(msg==null) break;
            System.out.print(msg.receiver_id);
            System.out.print(", ");
            System.out.print(msg.sender_id);
            System.out.print(", ");
            System.out.println(msg.content);
        }
    }

    @Test
    void unreadMsgNum() {
    }
}