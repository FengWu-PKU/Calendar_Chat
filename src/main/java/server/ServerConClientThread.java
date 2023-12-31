package server;

import client.gui.DiscussionFrame;
import common.*;
import server.utils.Account;
import server.utils.Friend;
import server.utils.NewFriend;
import server.utils.QQUser;
import server.utils.ToDoList;
import server.Discussion;

import javax.naming.ldap.SortKey;
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

    public Socket GetSocket() {
        return s;
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
        msglist.sort( (o1,o2) -> -o1.getSendTime().compareTo(o2.getSendTime()));
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
            ArrayList<UserMessage> msglist = GetMessageBetweenAAndB(account_id, i.friend_id);
            if (msglist.isEmpty()) {
                Timestamp tmp = i.date_t;
                LocalDateTime lastmessagetime = null;
                if (tmp!=null) lastmessagetime = tmp.toLocalDateTime();
                list.add(new common.FriendItem(i.friend_id, Account.getUsernameByID(i.friend_id), i.friend_nickname,
                        "", lastmessagetime, 0));
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
        }
        ArrayList<UserMessage> msglist = GetMessageBetweenAAndB(A, B);
        ChatWindowInfo cwi = new ChatWindowInfo(B, new UserInfo(tmp.usr_name, tmp.phonenum, tmp.email, localbir, tmp.descriptor), msglist);
        System.out.println(B+" 发给 "+A+" 的未读消息标记为已读");
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

    void AlreadyRead(int id) { // account_id 已读了 id 发的所有消息
        System.out.println(account_id+" 已读了 "+id+" 发的所有消息");
        server.utils.Message.readMsg(account_id, id);
    }

    boolean AddFriend(int A, String Busrname) throws IOException { // A 申请加 B 的好友
        System.out.println("用户 "+A+" 申请添加 "+Busrname+" 的好友");
        int B = Account.getIDByUsername(Busrname);
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        if (B == -1) {
            System.out.println("该用户名不存在");
            oos.writeObject(new Message(MessageType.ADD_FRIEND_RESULT, -1));
            return false;
        }
        if (Friend.CheckAlreadyFriend(A, B)) {
            System.out.println("已经是好友");
            oos.writeObject(new Message(MessageType.ADD_FRIEND_RESULT, 2));
            return false;
        }
        if (NewFriend.CheckFriendRequest(A, B)) {
            System.out.println("好友申请已经发过了");
            oos.writeObject(new Message(MessageType.ADD_FRIEND_RESULT, 0));
            return false;
        }
        if (NewFriend.CheckFriendRequest(B, A)) {
            System.out.println(A+" 还没处理 "+" B "+" 的好友申请");
            oos.writeObject(new Message(MessageType.ADD_FRIEND_RESULT, 3));
            return false;
        }
        System.out.println("好友申请可以发！");
        oos.writeObject(new Message(MessageType.ADD_FRIEND_RESULT, 1));
        NewFriend.insertEntry(new NewFriend(A, B));
        return true;
    }

    void SendToB(String Busrname) throws IOException { //account_id 发送好友申请给 B
        int B = Account.getIDByUsername(Busrname);
        System.out.println("发送好友申请给 "+B);
        ServerConClientThread sB = ManageClientThread.getClientThread(B);
        if (sB != null) {
            ObjectOutputStream oos = new ObjectOutputStream(sB.s.getOutputStream());
            FriendRequestItem tmp = new FriendRequestItem(account_id, Account.getUsernameByID(account_id));
            oos.writeObject(new Message(MessageType.NEW_REQUEST, tmp));
            System.out.println("好友申请发送成功");
        } else {
            System.out.println(B+" 不在线");
        }
    }

    void GetRequestsWindowInfo() throws IOException {
        System.out.println(account_id+" 请求好友申请页面信息");
        ArrayList<FriendRequestItem> FRIlist = new ArrayList<>();
        NewFriend[] nf = NewFriend.receivedNewFriend(account_id);
        for (NewFriend i : nf) if (i != null) {
            int sender = i.sender_id;
            FRIlist.add(new FriendRequestItem(sender, Account.getUsernameByID(sender)));
        }
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(new Message(MessageType.REQUEST_LIST, FRIlist));
    }

    void CreateFriend(UserMessage m) throws IOException { // B 接受了 A 的好友申请
        int B = m.getSenderUid(), A = m.getReceiverUid();
        System.out.println(B+" 接受了 "+A+" 的好友申请");
        NewFriend.deleteEntry(new NewFriend(A, B));
        LocalDateTime sendtime = m.getSendTime();
        Timestamp timestamp = null;
        if (sendtime != null) timestamp = Timestamp.valueOf(sendtime);
        Friend.insertFriend(new Friend(B, timestamp, A, ""));
        Friend.insertFriend(new Friend(A, timestamp, B, ""));
        ServerConClientThread sA = ManageClientThread.getClientThread(A);
        if (sA != null) {
            System.out.println(A+" 将收到 "+B+" 通过他好友申请的消息！");
            ObjectOutputStream oos = new ObjectOutputStream(sA.s.getOutputStream());
            UserMessage tmp = new UserMessage(B, A, m.getSendTime(), Account.getUsernameByID(B));
            oos.writeObject(new Message(MessageType.ACCEPT_MESSAGE, tmp));
        } else {
            System.out.println(A+" 不在线");
        }
    }

    void NotCreateFriend(int A, int B){ // B 拒绝了 A 的好友申请
        System.out.println(B+" 拒绝了 "+A+" 的好友申请");
        NewFriend.deleteEntry(new NewFriend(A, B));
    }

    void ChangeNickName(FriendRemark t) {
        System.out.println(account_id+" 修改对好友 "+t.getUid()+" 的昵称为 "+t.getRemark());
        Friend.changeNickname(account_id, t.getUid(), t.getRemark());
    }

    void DeleteFriend(int B) throws IOException {
        System.out.println(account_id+" 删除了 "+B+" 的好友");
        Friend.deleteFriend(account_id, B);
        Friend.deleteFriend(B, account_id);
        server.utils.Message.DeleteMessage(account_id, B);
        ServerConClientThread sB = ManageClientThread.getClientThread(B);
        if (sB != null) {
            System.out.println(B+" 将收到 "+account_id+" 删除他好友的消息！");
            ObjectOutputStream oos = new ObjectOutputStream(sB.s.getOutputStream());
            oos.writeObject(new Message(MessageType.SERVER_DELETE_FRIEND, account_id));
        } else {
            System.out.println(account_id+" 曾经的好友 "+B+" 不在线");
        }
    }

    void GetUserInfo() throws IOException {
        System.out.println("用户 "+account_id+" 想要修改个人资料");
        QQUser tmp = QQUser.getUserByAccountID(account_id);
        LocalDate localbir = null;
        if (tmp.birthday != null) {
            Date bir = new Date(tmp.birthday.getTime());
            localbir = bir.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        UserInfo ret = new UserInfo(tmp.usr_name, tmp.phonenum, tmp.email, localbir, tmp.descriptor);
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(new Message(MessageType.USER_INFO, ret));
    }

    void ModifyUserInfo(UserInfo newinfo) {
        java.sql.Date bir = null;
        if (newinfo.getBirth() != null) bir = java.sql.Date.valueOf(newinfo.getBirth());
        QQUser tmp = new QQUser(account_id, newinfo.getName(), newinfo.getIntro(), newinfo.getPhone(), newinfo.getEmail(),
                Account.getUsernameByID(account_id), bir);
        QQUser.ModifyUserInfo(tmp);
        if (newinfo.getEncryptedPassword() != null) {
            Account.ModifyPassword(account_id, newinfo.getEncryptedPassword());
        }
        System.out.println("用户 "+account_id+" 修改了个人资料");
    }

    Discussion discussion;
    void CreateDiscussion() {
        System.out.println("用户 "+account_id+" 新创建了一个会议");
        discussion = new Discussion(account_id);
    }

    void ExitDiscussion() throws IOException {
        System.out.println("用户 "+account_id+" 退出了会议");
        discussion.EraseUser(account_id);
        discussion = null;
    }

    void JoinDiscussion(int id) throws IOException {
        System.out.println("用户 "+account_id+" 加入了用户 "+id+" 所在的会议");
        ServerConClientThread sid = ManageClientThread.getClientThread(id);
        discussion = sid.discussion;
        if (discussion == null) {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(new Message(MessageType.DISCUSSION_INFO, null));
            return;
        }
        discussion.InsertUser(account_id);
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(new Message(MessageType.DISCUSSION_INFO, discussion.GetDiscussionInfo()));
    }

    void SendDiscussionMessage(UserMessage um) throws IOException {
        System.out.println("用户 "+account_id+" 在会议里发了一条消息");
        discussion.AddMessage(um);
    }

    void DiscussionDraw(Draw draw) throws IOException {
        System.out.println("用户 "+account_id+" 在画图板上加了一笔");
        discussion.AddDraw(draw);
    }

    void DiscussionClearPaint() throws IOException {
        System.out.println("用户 "+account_id+" 清空了会议的画图板");
        discussion.ClearPaint();
    }

    void DiscussionCLearMessage() throws IOException {
        System.out.println("用户 "+account_id+" 清空了会议的消息记录");
        discussion.ClearMessage();
    }

    void DiscussionInviteFriend(int id) throws IOException {
        System.out.println("用户 "+account_id+" 邀请用户 "+id+" 加入会议");
        ServerConClientThread sid = ManageClientThread.getClientThread(id);
        if (sid == null) {
            System.out.println("用户 "+id+" 不在线");
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(new Message(MessageType.INVITE_RESULT, -1));
            return;
        }
        if (sid.discussion != null) {
            System.out.println("用户 "+id+" 已经在会议中");
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(new Message(MessageType.INVITE_RESULT, 0));
            return;
        }
        System.out.println("邀请成功");
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(new Message(MessageType.INVITE_RESULT, 1));
        oos = new ObjectOutputStream((sid.GetSocket()).getOutputStream());
        oos.writeObject(new Message(MessageType.SERVER_INVITE_FRIEND, account_id));
    }

    void GetOneMonthInfo(TodoInfoRequest q) throws IOException {
        System.out.println("用户 "+q.my_uid+" 获取了用户 "+q.show_uid+" "+q.date.getTime()+" 以来28天的日程");
        Date zs=new Date(q.date.getTime());
        OneMonthInfo ob=new OneMonthInfo(q.my_uid,q.show_uid,zs);
        ArrayList<TodoItem>[][] res=ToDoList.find28DaysEntry(q);
        
        ob.setTodoList(res);
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        System.out.println("返回的时间是:"+ob.date.getTime()+" 当天返回了多少日程:"+ob.todoLists[0][0].size());
        oos.writeObject(new Message(MessageType.CLIENT_REQUEST_ONEMONTH, ob));
    }

    void UpdateOneDayInfo(OnedayInfo f){
        System.out.println("用户 "+account_id+" 更新"+f.date.getTime()+ "当天的日程");
        if(f.my_uid!=f.show_uid)System.out.println("id不合法,没有权限更改");
        else {
            ToDoList.UpdateDateEntry(f);
            System.out.println("更改成功");

        }
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
                    UserMessage tmp = (UserMessage)m.getContent();
                    if (tmp.getReceiverUid() == 0) SendDiscussionMessage((UserMessage)m.getContent());
                    else if (tmp.getText() == null) CreateFriend(tmp);
                    else SendMessage(tmp);
                } else if (m.getMessageType() == MessageType.ALREADY_READ) {
                    AlreadyRead((Integer)m.getContent());
                } else if (m.getMessageType() == MessageType.ADD_FRIEND_REQUEST) {
                    if (AddFriend(account_id, (String)m.getContent())) SendToB((String)m.getContent());
                } else if (m.getMessageType() == MessageType.OPEN_REQUESTS_WINDOW) {
                    GetRequestsWindowInfo();
                } else if (m.getMessageType() == MessageType.REJECT_REQUEST) {
                    NotCreateFriend((Integer)m.getContent(), account_id);
                } else if (m.getMessageType() == MessageType.MODIFY_REMARK) {
                    ChangeNickName((FriendRemark)m.getContent());
                } else if (m.getMessageType() == MessageType.CLIENT_DELETE_FRIEND) {
                    DeleteFriend((Integer)m.getContent());
                } else if (m.getMessageType() == MessageType.OPEN_MODIFY_WINDOW) {
                    GetUserInfo();
                } else if (m.getMessageType() == MessageType.MODIFY_INFO) {
                    ModifyUserInfo((UserInfo)m.getContent());
                } else if (m.getMessageType() == MessageType.CREATE_DISCUSSION) {
                    CreateDiscussion();
                } else if (m.getMessageType() == MessageType.CLIENT_EXIT_DISCUSSION) {
                    ExitDiscussion();
                } else if (m.getMessageType() == MessageType.CLIENT_JOIN_DISCUSSION) {
                    JoinDiscussion((Integer)m.getContent());
                } else if (m.getMessageType() == MessageType.CLIENT_DRAW) {
                    DiscussionDraw((Draw)m.getContent());
                } else if (m.getMessageType() == MessageType.CLIENT_CLEAR_PAINT) {
                    DiscussionClearPaint();
                } else if (m.getMessageType() == MessageType.CLIENT_CLEAR_MESSAGE) {
                    DiscussionCLearMessage();
                } else if (m.getMessageType() == MessageType.CLIENT_INVITE_FRIEND) {
                    DiscussionInviteFriend((Integer)m.getContent());
                } else if (m.getMessageType() == MessageType.CLIENT_REQUEST_ONEMONTH) {
                    GetOneMonthInfo((TodoInfoRequest)m.getContent());
                } else if(m.getMessageType() == MessageType.CLIENT_UPDATE_ONEDAY){
                    UpdateOneDayInfo((OnedayInfo)m.getContent());
                } 
            } catch (Exception e){
                e.printStackTrace();
                if (discussion != null) {
                    try {
                        ExitDiscussion();
                    } catch (Exception ee) {

                    }
                }
                ManageClientThread.removeClientThread(account_id);
                return;
            }
        }
    }
}
