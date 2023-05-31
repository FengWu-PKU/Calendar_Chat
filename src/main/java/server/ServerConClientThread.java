package server;

import common.Message;
import common.MessageType;
import server.utils.Friend;
import server.utils.QQUser;

import java.net.*;
import java.util.*;
import java.io.*;

public class ServerConClientThread extends Thread {
    Socket s;
    int account_id;

    public ServerConClientThread(Socket s, int id) {
        this.s = s;
        this.account_id = id;
    }

    public void run() {
        Friend[] fri = Friend.findAllFriends(account_id);
        ArrayList<common.FriendItem> list = new ArrayList<>();
        for (Friend i : fri) if (i != null){
            QQUser tmp = QQUser.getUserByAccountID(i.friend_id);
            System.out.println(account_id+" "+i.friend_id);
            list.add(new common.FriendItem(i.friend_id, tmp.usr_name, i.friend_nickname, "", null, 0));
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(new Message(MessageType.RET_FRIENDS, list));
            System.out.println("输出成功");
        } catch (Exception e){
            e.printStackTrace();
        }
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Message m = (Message) ois.readObject();

            } catch (Exception e){
                e.printStackTrace();
                ManageClientThread.removeClientThread(account_id);
                return;
            }
        }
    }
}