package server;

import server.utils.Account;
import server.utils.Friend;
import server.utils.QQUser;
import common.UserLogin;
import common.UserRegister;
import common.Message;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    boolean havedb = true;
    database db = new database();
    class work extends Thread {
        Socket s;
        public work(Socket _s) {
            s = _s;
        }
        int Login(UserLogin ul, ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
            int account_id = 0;
            if (havedb == true) account_id = Account.login(ul.getUsername(), ul.getEncryptedPassword());
            else account_id = db.find(ul.getUsername(), ul.getEncryptedPassword());
            System.out.println("登录的 account_id 为 "+account_id);
            if (account_id == -1 || account_id == -2) {
                // 密码错了或者用户不存在
                System.out.println("登录失败");
                oos.writeObject(new Message(MessageType.LOGIN_FAILED));
                return 0;
            } else if (ManageClientThread.getClientThread(account_id) != null) {
                // 已经登录
                System.out.println("登录失败");
                oos.writeObject(new Message(MessageType.ALREADY_LOGIN));
                return 0;
            } else {
                // 登录成功
                System.out.println("登录成功");
                oos.writeObject(new Message(MessageType.LOGIN_SUCCEED, account_id));
                ServerConClientThread scct = new ServerConClientThread(s, account_id);
                ManageClientThread.addClientThread(account_id, scct);
                scct.run();
                return 1;
            }
        }
        int Register(UserRegister ur, ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
            int ret = 0;
            if (havedb == true) ret = Account.signUp(ur.getUsername(), ur.getEncryptedPassword());
                else ret = db.signUp(ur.getUsername(), ur.getEncryptedPassword());
            System.out.println("注册返回信息为 "+ret);
            if (ret == -1) {
                // 注册失败
                System.out.println("注册失败");
                oos.writeObject(new Message(MessageType.REGISTER_FAILED));
                return 0;
            } else {
                //注册成功
                System.out.println("注册成功");
                int account_id = havedb ? Account.login(ur.getUsername(), ur.getEncryptedPassword()) : ret;
                Friend.insertFriend(new Friend(account_id, null, account_id, ""));
                oos.writeObject(new Message(MessageType.REGISTER_SUCCEED, account_id));
                ServerConClientThread scct = new ServerConClientThread(s, account_id);
                java.sql.Date bir = null;
                if (ur.getBirth() != null) bir = java.sql.Date.valueOf(ur.getBirth());
                if (havedb == true) {
                    int tmp = QQUser.insertUser(new QQUser(account_id, ur.getName(), ur.getIntro(), ur.getPhone(),
                            ur.getEmail(), ur.getUsername(), bir));
                    System.out.println("注册结果为 "+tmp);
                } else {
                    db.insert(account_id, ur.getUsername(), ur.getPhone(), ur.getEmail(), bir);
                }
                ManageClientThread.addClientThread(account_id, scct);
                scct.run();
                return 1;
            }
        }
        public void run() {
            try {
                while (true) {
                    ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                    Object u = ois.readObject();
                    System.out.println("读入成功");
                    if (u instanceof UserRegister) { // 注册用户
                        if (Register((UserRegister) u, ois, oos) == 1) break;
                    } else if (u instanceof UserLogin) { // 登录用户
                        if (Login((UserLogin) u, ois, oos) == 1) break;
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

    public static void main(String[] args) {
        new Server();
    }
}