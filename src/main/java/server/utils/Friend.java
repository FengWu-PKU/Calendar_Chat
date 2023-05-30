package server.utils;

import java.sql.*;

public class Friend {
    static String url=ServerInfo.url;
    static String username = ServerInfo.username;
    static String password = ServerInfo.password;

    static final int MAXFRIENDNUM=100;

    int account_id;
    public int friend_id;
    public String friend_nickname;

    public Friend(int account_id, int friend_id, String friend_nickname) {
        this.account_id = account_id;
        this.friend_id = friend_id;
        this.friend_nickname = friend_nickname;
    }

    /*添加好友,已经存在好友关系返回-1，成功插入返回0*/
    static int insertFriend(int account_id, int friend_id, String nickname) {
        try (Connection connection=DriverManager.getConnection(url, username,password)){
            String sql="SELECT * FROM friend WHERE account_id=? AND friend_id=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, account_id);
            stmt.setInt(2, friend_id);
            ResultSet res=stmt.executeQuery();
            if(res.next()) {  // 已经存在好友关系
                return -1;
            }
            res.close();
            stmt.close();
            String insert="INSERT INTO friend(account_id, friend_id, friend_nickname) VALUES (?, ?, ?)";
            PreparedStatement insertStmt=connection.prepareStatement(insert);
            insertStmt.setInt(1, account_id);
            insertStmt.setInt(2, friend_id);
            insertStmt.setString(3,nickname);
            insertStmt.executeUpdate();
            insertStmt.close();
            return 0;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*删除好友关系，不存在返回-1，成功删除返回0*/
    static int deleteFriend(int account_id, int friend_id) {
        try (Connection connection=DriverManager.getConnection(url, username,password)){
            String sql="SELECT * FROM friend WHERE account_id=? AND friend_id=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, account_id);
            stmt.setInt(2, friend_id);
            ResultSet res=stmt.executeQuery();
            if(!res.next()) {  // 不存在好友关系
                return -1;
            }
            res.close();
            stmt.close();
            String del="DELETE FROM friend WHERE account_id=? AND friend_id=?";
            PreparedStatement delStmt=connection.prepareStatement(del);
            delStmt.setInt(1, account_id);
            delStmt.setInt(2, friend_id);
            delStmt.executeUpdate();
            delStmt.close();
            return 0;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*返回所有好友*/
    static public Friend[] findAllFriends(int account_id) {
        Friend[] ans=new Friend[MAXFRIENDNUM];
        try (Connection connection=DriverManager.getConnection(url, username,password)){
            String sql="SELECT * FROM friend WHERE account_id=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, account_id);
            ResultSet res=stmt.executeQuery();
            int cnt=0;
            while(res.next()) {
                int acid=res.getInt("account_id");
                int fid=res.getInt("friend_id");
                String nn=res.getString("friend_nickname");
                Friend cur=new Friend(acid, fid, nn);
                ans[cnt++]=cur;
            }
            return ans;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**更改昵称
     * 好友关系不存在返回-1
     * 成功更改返回0*/
    static int changeNickname(int account_id, int friend_id, String name) {
        try (Connection connection=DriverManager.getConnection(url, username, password)){
            String sql="SELECT * FROM friend WHERE account_id=? AND friend_id=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, account_id);
            stmt.setInt(2, friend_id);
            ResultSet res=stmt.executeQuery();
            if(!res.next()) {  // 不存在好友关系
                return -1;
            }
            res.close();
            stmt.close();

            String change="UPDATE friend SET friend_nickname=? WHERE account_id=? AND friend_id=?";
            PreparedStatement changeStmt=connection.prepareStatement(change);
            changeStmt.setString(1, name);
            changeStmt.setInt(2,account_id);
            changeStmt.setInt(3,friend_id);
            changeStmt.executeUpdate();
            changeStmt.close();
            return 0;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
