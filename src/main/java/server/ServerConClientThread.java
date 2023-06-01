package server;

import common.Message;
import common.MessageType;
import common.ChatWindowInfo;
import common.UserMessage;
import server.utils.Account;
import server.utils.Friend;
import server.utils.QQUser;

import java.net.*;
import java.util.*;
import java.io.*;
import java.time.*;
import java.sql.Timestamp;

public class ServerConClientThread extends Thread {
    Socket s;
    int account_id;

    public ServerConClientThread(Socket s, int id) {
        this.s = s;
        this.account_id = id;
    }

    void GetFriendList() throws IOException{
        Friend[] fri = Friend.findAllFriends(account_id);
        ArrayList<common.FriendItem> list = new ArrayList<>();
        for (Friend i : fri) if (i != null){
            QQUser tmp = QQUser.getUserByAccountID(i.friend_id);
            list.add(new common.FriendItem(i.friend_id, Account.getUsernameByID(i.friend_id)/*i.friend_id的username*/, i.friend_nickname, "", null, 0));
        }
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(new Message(MessageType.RET_FRIENDS, list));
    }

    void GetChatWindowInfo(int A,int B) throws IOException {
        System.out.println(A+" 请求打开和 "+B+" 的聊天窗口需要的信息。");
        QQUser tmp = QQUser.getUserByAccountID(B);
        LocalDate localbir = null;
        if (tmp.birthday != null) {
            Date bir = new Date(tmp.birthday.getTime());
            localbir = bir.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            System.out.println(bir);
        }
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
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(new Message(MessageType.CHAT_WINDOW_INFO, cwi));
    }

    void SendMessage(UserMessage m) throws IOException {
        int A = m.getSenderUid(), B = m.getReceiverUid();
        System.out.println(A+" 发了一条消息给 "+B);
        ServerConClientThread sB = ManageClientThread.getClientThread(B);
        int ret = server.utils.Message.sendMsg(new server.utils.Message(A, B, Timestamp.valueOf(m.getSendTime()), m.getText()));
        System.out.println("消息存储情况为 "+ret);
        if (sB != null) { // B 在线
            ObjectOutputStream oos = new ObjectOutputStream(sB.s.getOutputStream());
            oos.writeObject(new Message(MessageType.SERVER_SEND_MESSAGE, m));
            System.out.println("消息发送成功");
        }
    }

    public void run() {
        try {
            GetFriendList();
        } catch (Exception e){
            e.printStackTrace();
            ManageClientThread.removeClientThread(account_id);
            return;
        }
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Message m = (Message) ois.readObject();
                if (m.getMessageType() == MessageType.OPEN_CHAT_WINDOW) {
                    int A = account_id, B = (Integer)m.getContent();
                    GetChatWindowInfo(A, B);
                } else if (m.getMessageType() == MessageType.CLIENT_SEND_MESSAGE) {
                    UserMessage m2 = (UserMessage)m.getContent();
                    SendMessage(m2);
                }
            } catch (Exception e){
                e.printStackTrace();
                ManageClientThread.removeClientThread(account_id);
                return;
            }
        }
    }
}