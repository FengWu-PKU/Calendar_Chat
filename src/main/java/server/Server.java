package server;

import server.utils.Account;
import server.utils.QQUser;
import common.UserLogin;
import common.UserRegister;
import common.Message;
import common.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    class work extends Thread {
        Socket s;
        public work(Socket _s) {
            s = _s;
        }
        public void run() {
            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                while (true) {
                    Object u = ois.readObject();
                    System.out.println("读入成功");
                    if (u instanceof UserRegister) { // 注册用户
                        UserRegister ur = (UserRegister) u;
                        int ret = Account.signUp(ur.getUsername(), ur.getEncryptedPassword());
                        if (ret == -1) {
                            // 注册失败
                            System.out.println("注册失败");
                            oos.writeObject(new Message(MessageType.REGISTER_FAILED));
                            //s.close();
                        } else {
                            //注册成功
                            System.out.println("注册成功");
                            oos.writeObject(new Message(MessageType.REGISTER_SUCCEED));
                            ServerConClientThread scct = new ServerConClientThread(s);
                            int account_id = Account.login(ur.getUsername(), ur.getEncryptedPassword());
                            QQUser.insertUser(new QQUser(account_id,
                                    ur.getUsername(),
                                    ur.getPhone(),
                                    ur.getEmail(),
                                    "",
                                    java.sql.Date.valueOf(ur.getBirth()))
                            );
                            ManageClientThread.addClientThread(account_id, scct);
                            scct.start();
                            break;
                        }
                    } else if (u instanceof UserLogin) { // 登录用户
                        UserLogin ul = (UserLogin) u;
                        int account_id = Account.login(ul.getUsername(), ul.getEncryptedPassword());
                        //int account_id = -1;
                        if (account_id != -1 && ManageClientThread.getClientThread(account_id) != null) {
                            // 已经登录
                            System.out.println("登录失败");
                            oos.writeObject(new Message(MessageType.ALREADY_LOGIN));
                            //s.close();
                        } else if (account_id != -1 && ManageClientThread.getClientThread(account_id) == null) {
                            // 登录成功
                            System.out.println("登录成功");
                            oos.writeObject(new Message(MessageType.LOGIN_SUCCEED));
                            ServerConClientThread scct = new ServerConClientThread(s);
                            ManageClientThread.addClientThread(account_id, scct);
                            scct.start();
                            break;
                        } else {
                            // 登录失败
                            System.out.println("登录失败");
                            oos.writeObject(new Message(MessageType.LOGIN_FAILED));
                            //s.close();
                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public Server() {
        try {
            ServerSocket socket = new ServerSocket(9999);
            while (true){
                Socket s = socket.accept();
                System.out.println("一个客户端正在链接...:"+s.getLocalAddress());
                work ald = new work(s);
                ald.start();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Server();
    }
}