package utils;
import java.sql.*;

public class Account {
    static String url="jdbc:mysql://"+ServerInfo.ip+":"+ServerInfo.port+"/"+ServerInfo.database;
    static String username = "public_user";
    static String password = "123456";

    static void signUp(String email, String pw) {
        try(Connection connection=DriverManager.getConnection(url,username,password)) {
            // 执行sql语句
            String sql = "INSERT INTO account(email, password) VALUES (?, ?)";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,email);
            statement.setString(2,pw);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 返回account_id， 若失败返回-1*/
    static int login(String email, String pw) {
        try(Connection connection=DriverManager.getConnection(url,username,password)) {
            // 执行sql语句
            String sql = "SELECT password,id FROM account WHERE email=?";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,email);
            ResultSet res=statement.executeQuery();
            res.next();
            String password=res.getString("password");
            if(password.equals(pw)) {
                return res.getInt("id");
            }else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
