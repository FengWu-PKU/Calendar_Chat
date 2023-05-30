package server.utils;

import java.sql.*;

public class QQUser {
    static String url="jdbc:mysql://"+ServerInfo.ip+":"+ServerInfo.port+"/"+ServerInfo.database;
    static String username = "public_user";
    static String password = "123456";

    int account_id;
    String usr_name;
    String phonenum;
    String email;
    String account_number;
    Date birthday;

    public QQUser(int account_id, String usr_name, String phonenum, String email, String account_number, Date birthday) {
        this.account_id = account_id;
        this.usr_name = usr_name;
        this.phonenum = phonenum;
        this.email = email;
        this.account_number = account_number;
        this.birthday = birthday;
    }

    /**新建账号，
     * account_id存在返回-1，
     * account_numer存在返回-2，account_number不允许重复
     * 成功插入返回0*/
    static int insertUser(QQUser user) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT * FROM qq_user WHERE account_id=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, user.account_id);
            ResultSet res=stmt.executeQuery();
            if(res.next()) {  // account_id已经存在
                return -1;
            }
            res.close();
            stmt.close();

            String sql2="SELECT * FROM qq_user WHERE account_number=?";
            PreparedStatement stmt2=connection.prepareStatement(sql2);
            stmt2.setString(1, user.account_number);
            ResultSet res2=stmt2.executeQuery();
            if(res2.next()) {  // account_number已经存在
                return -2;
            }
            res2.close();
            stmt2.close();

            String insert="INSERT INTO qq_user(usr_name, phone_num, email, account_number, birthday, account_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt=connection.prepareStatement(insert);
            insertStmt.setString(1, user.usr_name);
            insertStmt.setString(2, user.phonenum);
            insertStmt.setString(3, user.email);
            insertStmt.setString(4, user.account_number);
            insertStmt.setDate(5, user.birthday);
            insertStmt.setInt(6,user.account_id);
            insertStmt.executeUpdate();
            insertStmt.close();
            return 0;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*删除账号，账号不存在返回-1，成功删除返回0*/
    static int deleteUser(int account_id) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT * FROM qq_user WHERE account_id=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, account_id);
            ResultSet res=stmt.executeQuery();
            if(!res.next()) {  // 账号不存在
                return -1;
            }
            res.close();
            stmt.close();
            String del="DELETE FROM qq_user WHERE account_id=?";
            PreparedStatement delstmt=connection.prepareStatement(del);
            delstmt.setInt(1, account_id);
            delstmt.executeUpdate();
            delstmt.close();
            return 0;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**根据account_number返回account_id，用于好友关系
     * 不存在返回-1*/
    static int getAccountIDByAccountNumber(String account_number) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT account_id FROM qq_user WHERE account_number=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setString(1, account_number);
            ResultSet res=stmt.executeQuery();
            if(!res.next()) {
                res.close();
                stmt.close();
                return -1;
            }
            int id= res.getInt("account_id");
            res.close();
            stmt.close();
            return id;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**根据usr_name返回account_id数组，用于好友关系*/
    static int[] getAccountIDByUsrname(String name) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT account_id FROM qq_user WHERE usr_name=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet res=stmt.executeQuery();
            final int maxn=20;  // 最多返回20个
            int[] ids=new int[maxn];
            int cnt=0;
            while(res.next()) {
                int cur=res.getInt("account_id");
                ids[cnt++]=cur;
                if(cnt>=maxn) break;
            }
            res.close();
            stmt.close();
            return ids;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
