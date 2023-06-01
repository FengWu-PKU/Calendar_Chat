package server;

import common.*;
import server.utils.Account;
import server.utils.Friend;
import server.utils.NewFriend;
import server.utils.QQUser;

import java.net.*;
import java.util.*;
import java.io.*;
import java.time.*;
import java.sql.Timestamp;

public class ServerConClientThread {
    Socket s;
    int account_id;

    public ServerConClientThread(Socket s, int id) {
        this.s = s;
        this.account_id = id;
    }

    ArrayList<UserMessage> GetMessageBetweenAAndB(int A, int B) { // 按照时间排序 只保留时间最近的50条
        server.utils.Message[] BtoA = server.utils.Message.receiveMsg(A, B);
        server.utils.Message[] AtoB = server.utils.Message.receiveMsg(B, A);
        ArrayList<UserMessage> msglist = new ArrayList<>();
        if (BtoA != null) {
            for (server.utils.Message i : BtoA) if (i != null) {
                msglist.add(new UserMessage(i.sender_id, i.receiver_id, i.date_t.toLocalDateTime(), i.content));
            }
        }
        if ((AtoB != null) && (A != B)) {
            for (server.utils.Message i : AtoB) if (i != null) {
                msglist.add(new UserMessage(i.sender_id, i.receiver_id, i.date_t.toLocalDateTime(), i.content));
            }
        }
        msglist.sort( (o1,o2) -> {
            return -o1.getSendTime().compareTo(o2.getSendTime());
        });
        while (msglist.size() > 50) {
            msglist.remove(msglist.size() - 1);
        }
        Collections.reverse(msglist);
        return msglist;
    }

    void GetMainWindowInfo() throws IOException {
        Friend[] fri = Friend.findAllFriends(account_id);
        ArrayList<common.FriendItem> list = new ArrayList<>();
        for (Friend i : fri) if (i != null) {
            QQUser tmp = QQUser.getUserByAccountID(i.friend_id);
            ArrayList<UserMessage> msglist = GetMessageBetweenAAndB(account_id, i.friend_id);
            if (msglist.isEmpty()) {
                list.add(new common.FriendItem(i.friend_id, Account.getUsernameByID(i.friend_id), i.friend_nickname,
                        "", null, 0));
            } else {
                UserMessage las = msglist.get(msglist.size() - 1);
                list.add(new common.FriendItem(i.friend_id, Account.getUsernameByID(i.friend_id), i.friend_nickname,
                        las.getText(), las.getSendTime(), server.utils.Message.unreadMsgNum(account_id, i.friend_id)));
            }
        }
        NewFriend[] nf = NewFriend.receivedNewFriend(account_id);
        int cnt = 0;
        for (NewFriend i : nf) if (i != null) ++cnt;
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(new Message(MessageType.MAIN_WINDOW_INFO, new MainWindowInfo(list, cnt)));
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
        ArrayList<UserMessage> msglist = GetMessageBetweenAAndB(A, B);
        ChatWindowInfo cwi = new ChatWindowInfo(B, tmp.usr_name, tmp.phonenum, tmp.email, localbir, tmp.descriptor, msglist);
        server.utils.Message.readMsg(A, B);
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

    void AlreadyRead(int id) { // id 已读了 account_id 发的所有消息
        server.utils.Message.readMsg(id, account_id);
    }

    void AddFriend(int A,String Busrname) throws IOException { // A 申请加 B 的好友
        System.out.println("用户 "+A+" 申请添加 "+Busrname+" 的好友");
        int B = Account.getIDByUsername(Busrname);
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        if (B == -1) {
            System.out.println("该用户名不存在");
            oos.writeObject(new Message(MessageType.ADD_FRIEND_RESULT, -1));
            return;
        }
        if (Friend.CheckAlreadyFriend(A, B)) {
            System.out.println("已经是好友");
            oos.writeObject(new Message(MessageType.ADD_FRIEND_RESULT, 2));
            return;
        }
        if (NewFriend.CheckFriendRequest(A, B)) {
            System.out.println("好友申请已经发过了");
            oos.writeObject(new Message(MessageType.ADD_FRIEND_RESULT, 0));
            return;
        }
        System.out.println("好友申请可以发！");
        oos.writeObject(new Message(MessageType.ADD_FRIEND_RESULT, 1));
        NewFriend.insertEntry(new NewFriend(A, B));
    }

    public void run() {
        try {
            GetMainWindowInfo();
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
                } else if (m.getMessageType() == MessageType.ALREADY_READ) {
                    AlreadyRead((Integer)m.getContent());
                } else if (m.getMessageType() == MessageType.ADD_FRIEND_REQUEST) {
                    AddFriend(account_id, (String)m.getContent());
                }
            } catch (Exception e){
                e.printStackTrace();
                ManageClientThread.removeClientThread(account_id);
                return;
            }
        }
    }
}