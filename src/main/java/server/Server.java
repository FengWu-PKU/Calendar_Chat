package server;

import server.utils.Account;
import common.UserLogin;
import common.UserRegister;
import common.Message;
import common.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public Server() {
        try {
            ServerSocket socket = new ServerSocket(9999);
            while (true){
                Socket s = socket.accept();
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                Object u = ois.readObject();
                if (u instanceof UserRegister) { // 注册用户
                    UserRegister ur = (UserRegister) u;
                    int ret = Account.signUp(ur.getUsername(), ur.getEncryptedPassword());
                    if (ret == 0) {
                        // 注册失败
                        //oos.writeObject(new Message(MessageType.REGISTER_FAILED));
                        //s.close();
                    } else {
                        //注册成功
                        //oos.writeObject(new Message(MessageType.REGISTER_SUCCEED));
                        //ServerConClientThread scct = new ServerConClientThread(s);
                        //int account_id = Account.login(ur.getUsername(), ur.getEncryptedPassword())
                        //ManageClientThread.addClientThread(account_id, scct);
                        //scct.start();
                    }
                } else if (u instanceof UserLogin) { // 登录用户
                    UserLogin ul = (UserLogin) u;
                    int account_id = Account.login(ul.getUsername(), ul.getEncryptedPassword());
                    if (account_id != -1 && ManageClientThread.getClientThread(account_id) != null) {
                        // 已经登录
                        oos.writeObject(new Message(MessageType.ALREADY_LOGIN));
                        s.close();
                    } else if (account_id != -1 && ManageClientThread.getClientThread(account_id) == null) {
                        // 登录成功
                        oos.writeObject(new Message(MessageType.LOGIN_SUCCEED));
                        ServerConClientThread scct = new ServerConClientThread(s);
                        ManageClientThread.addClientThread(account_id, scct);
                        scct.start();
                    } else {
                        // 登录失败
                        oos.writeObject(new Message(MessageType.LOGIN_FAILED));
                        s.close();
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Server();
    }
}