package server;

import common.Message;
import common.MessageType;
import common.ChatWindowInfo;
import common.UserMessage;
import server.utils.Friend;
import server.utils.QQUser;

import java.net.*;
import java.util.*;
import java.io.*;
import java.time.*;

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
            list.add(new common.FriendItem(i.friend_id, tmp.usr_name, i.friend_nickname, "", null, 0));
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(new Message(MessageType.RET_FRIENDS, list));
        } catch (Exception e){
            e.printStackTrace();
        }
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Message m = (Message) ois.readObject();
                if (m.getMessageType() == MessageType.OPEN_CHAT_WINDOW) {
                    int A = account_id, B = (Integer)ois.readObject();
                    System.out.println(A+" 请求打开和 "+B+" 的聊天窗口需要的信息。");
                    ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                    QQUser tmp = QQUser.getUserByAccountID(B);
                    Date bir = tmp.birthday;
                    LocalDate localbir = bir.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    server.utils.Message[] BtoA = server.utils.Message.receiveMsg(A, B);
                    server.utils.Message[] AtoB = server.utils.Message.receiveMsg(B, A);
                    ArrayList<UserMessage> msglist = new ArrayList<>();
                    if (BtoA != null) {
                        for (server.utils.Message i : BtoA) if (i != null) {
                            msglist.add(new UserMessage(i.sender_id, i.receiver_id, i.date_t.toLocalDateTime(), i.content));
                        }
                    }
                    if (AtoB != null) {
                        for (server.utils.Message i : AtoB) if (i != null) {
                            msglist.add(new UserMessage(i.sender_id, i.receiver_id, i.date_t.toLocalDateTime(), i.content));
                        }
                    }
                    msglist.sort( (o1,o2) -> {
                        return o1.getSendTime().compareTo(o2.getSendTime());
                    });
                    ChatWindowInfo cwi = new ChatWindowInfo(B, tmp.usr_name, tmp.phonenum, tmp.email, localbir, tmp.descriptor, msglist);
                    server.utils.Message.receiveMsg(A, B);
                    oos.writeObject(new Message(MessageType.CHAT_WINDOW_INFO, cwi));
                }
            } catch (Exception e){
                e.printStackTrace();
                ManageClientThread.removeClientThread(account_id);
                return;
            }
        }
    }
}