package server.utils;

import java.io.RandomAccessFile;
import java.sql.*;

public class NewFriend {
    static String url=ServerInfo.url;
    static String username = ServerInfo.username;
    static String password = ServerInfo.password;

    Timestamp date_t;
    public int sender_id;
    public int receiver_id;

    static final int MAXNEWFRIEND=20;

    public NewFriend(int sender_id, int receiver_id) {
        this.date_t=null;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
    }

    public NewFriend(Timestamp date_t, int sender_id, int receiver_id) {
        this.date_t = date_t;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
    }

    /*插入新的条目
    * 如果已经有了，返回-1
    * 成功插入返回0*/
    static public int insertEntry(NewFriend f) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String check="SELECT * FROM new_friend WHERE sender_id=? AND receiver_id=?";
            PreparedStatement checkstmt=connection.prepareStatement(check);
            checkstmt.setInt(1,f.sender_id);
            checkstmt.setInt(2,f.receiver_id);
            ResultSet res=checkstmt.executeQuery();
            if(res.next()) {
                res.close();
                checkstmt.close();
                return -1;
            }
            res.close();
            checkstmt.close();

            String insert="INSERT INTO new_friend(date_t, sender_id, receiver_id) VALUES (?,?,?)";
            PreparedStatement insertstmt=connection.prepareStatement(insert);
            insertstmt.setTimestamp(1,f.date_t);
            insertstmt.setInt(2,f.sender_id);
            insertstmt.setInt(3,f.receiver_id);
            insertstmt.executeUpdate();
            insertstmt.close();
            return 0;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* 查询 A 申请加 B 的好友这条申请是否存在*/
    static public boolean CheckFriendRequest(int A, int B) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String check="SELECT * FROM new_friend WHERE sender_id=? AND receiver_id=?";
            PreparedStatement checkstmt=connection.prepareStatement(check);
            checkstmt.setInt(1,A);
            checkstmt.setInt(2,B);
            ResultSet res=checkstmt.executeQuery();
            boolean ret = res.next();
            res.close();
            checkstmt.close();
            return ret;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*成功删除返回0
    * 条目不存在返回-1*/
    static int deleteEntry(NewFriend f) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT * FROM new_friend WHERE sender_id=? AND receiver_id=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1,f.sender_id);
            stmt.setInt(2, f.receiver_id);
            ResultSet res=stmt.executeQuery();
            if(!res.next()) {
                res.close();
                stmt.close();
                return -1;
            }
            res.close();
            stmt.close();

            String del="DELETE FROM new_friend WHERE sender_id=? AND receiver_id=?";
            PreparedStatement delstmt=connection.prepareStatement(del);
            delstmt.setInt(1,f.sender_id);
            delstmt.setInt(2, f.receiver_id);
            delstmt.executeUpdate();
            delstmt.close();
            return 0;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*查询用户接收到的所有好友申请*/
    static public NewFriend[] receivedNewFriend(int id) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT * FROM new_friend WHERE receiver_id=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1,id);
            ResultSet res=stmt.executeQuery();
            NewFriend[] ans=new NewFriend[MAXNEWFRIEND];
            int cnt=0;
            while(res.next()) {
                Timestamp dt=res.getTimestamp("date_t");
                int sid=res.getInt("sender_id");
                int rid=res.getInt("receiver_id");
                NewFriend cur=new NewFriend(dt,sid,rid);
                ans[cnt++]=cur;
                if(cnt>=MAXNEWFRIEND) break;
            }
            return ans;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*查询用户发送的到的所有好友申请*/
    static NewFriend[] sentNewFriend(int id) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT * FROM new_friend WHERE sender_id=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1,id);
            ResultSet res=stmt.executeQuery();
            NewFriend[] ans=new NewFriend[MAXNEWFRIEND];
            int cnt=0;
            while(res.next()) {
                Timestamp dt=res.getTimestamp("date_t");
                int sid=res.getInt("sender_id");
                int rid=res.getInt("receiver_id");
                NewFriend cur=new NewFriend(dt,sid,rid);
                ans[cnt++]=cur;
                if(cnt>=MAXNEWFRIEND) break;
            }
            return ans;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
