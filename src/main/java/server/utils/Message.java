package server.utils;

import java.sql.*;

public class Message {
    static String url=ServerInfo.url;
    static String username = ServerInfo.username;
    static String password = ServerInfo.password;

    static final int MAXMESSAGENUM=50;

    public int sender_id;
    public int receiver_id;
    public Timestamp date_t;
    public String content;
    Boolean read_t;
    int account_id;

    //发送新消息时用这个构造函数
    public Message(int sender_id, int receiver_id, Timestamp date_t, String content) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.date_t = date_t;
        this.content = content;
        this.read_t=false;  // 新建的消息默认都是未读的
        this.account_id=0;  // 需要为发送方和接收方都添加一条记录，这样就可以实现本地的删除记录
    }

    public Message(int sender_id, int receiver_id, Timestamp date_t, String content, Boolean read_t, int account_id) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.date_t = date_t;
        this.content = content;
        this.read_t = read_t;
        this.account_id = account_id;
    }

    /*发送消息
    * 发送方不存在返回-1
    * 接收方不存在返回-2
    * 正常发送返回0*/
    static public int sendMsg(Message msg) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            if(QQUser.getUserByAccountID(msg.sender_id)==null) {
                return -1;
            }
            if(QQUser.getUserByAccountID(msg.receiver_id)==null) {
                return -2;
            }
            String sql="INSERT INTO message(sender_id, receiver_id, date_t, content, account_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt1=connection.prepareStatement(sql);
            stmt1.setInt(1, msg.sender_id);
            stmt1.setInt(2, msg.receiver_id);
            stmt1.setTimestamp(3,msg.date_t);
            stmt1.setString(4,msg.content);
            stmt1.setInt(5, msg.sender_id);  // sender方的记录
            stmt1.executeUpdate();
            stmt1.close();

            PreparedStatement stmt2=connection.prepareStatement(sql);
            stmt2.setInt(1, msg.sender_id);
            stmt2.setInt(2, msg.receiver_id);
            stmt2.setTimestamp(3,msg.date_t);
            stmt2.setString(4,msg.content);
            stmt2.setInt(5, msg.receiver_id);  // receiver方的记录
            stmt2.executeUpdate();
            stmt2.close();

            return 0;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*user_id(a)打开和friend_id(b)的聊天窗口，将b发给a的所有消息已读*/
    static public void readMsg(int user_id, int friend_id) {
        try(Connection connection=DriverManager.getConnection(url, username,password)) {
            String sql="UPDATE message SET read_t=TRUE WHERE account_id=? AND sender_id=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, user_id);
            stmt.setInt(2,friend_id);
            stmt.executeUpdate();
            stmt.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*user_id查看friend_id发送给自己的所有消息*/
    static public Message[] receiveMsg(int user_id, int friend_id) {
        try(Connection connection=DriverManager.getConnection(url, username,password)) {
            if(QQUser.getUserByAccountID(user_id)==null||QQUser.getUserByAccountID(friend_id)==null) {
                return null;  // 发送方或接收方不存在
            }
            String sql="SELECT * FROM message WHERE account_id=? AND sender_id=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, user_id);
            stmt.setInt(2,friend_id);
            ResultSet res=stmt.executeQuery();
            Message[] ans=new Message[MAXMESSAGENUM];
            int cnt=0;
            while(res.next()) {
                int si=res.getInt("sender_id");
                int ri=res.getInt("receiver_id");
                Timestamp dt=res.getTimestamp("date_t");
                String content=res.getString("content");
                Boolean read_t=res.getBoolean("read_t");
                int acid=res.getInt("account_id");
                Message cur=new Message(si, ri, dt, content, read_t, acid);
                ans[cnt++]=cur;
                if(cnt>=MAXMESSAGENUM) break;
            }
            return ans;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*获得未读消息数*/
    static int unreadMsgNum(int user_id, int friend_id) {
        Message[] msgs=receiveMsg(user_id, friend_id);
        int cnt=0;
        for(Message msg:msgs) {
            if(msg==null) break;
            if(!msg.read_t) cnt++;
        }
        return cnt;
    }

    /*TODO:本地删除记录功能，函数还没写，感觉参数不好确定，若有必要再写*/

}
