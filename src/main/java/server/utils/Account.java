package server.utils;
import java.sql.*;

public class Account {
    static String url="jdbc:mysql://"+ServerInfo.ip+":"+ServerInfo.port+"/"+ServerInfo.database;
    static String username = "public_user";
    static String password = "123456";

    static public int signUp(String email, String pw) {
        try(Connection connection=DriverManager.getConnection(url,username,password)) {
            // 先查询账号是否已经存在
            String check="SELECT 1 FROM account WHERE email=?";
            PreparedStatement checkstmt=connection.prepareStatement(check);
            checkstmt.setString(1, email);
            ResultSet exists=checkstmt.executeQuery();
            if(exists.next()) {  // 账号已经存在
//                System.out.println("账号已经存在，请登录");
                return -1;
            }
            exists.close();
            checkstmt.close();

            String sql = "INSERT INTO account(email, password) VALUES (?, ?)";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,email);
            statement.setString(2,pw);
            statement.executeUpdate();
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 返回account_id， 若失败返回-1*/
    static public int login(String email, String pw) {
        try(Connection connection=DriverManager.getConnection(url,username,password)) {
            // 执行sql语句
            String sql = "SELECT password,id FROM account WHERE email=?";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,email);
            ResultSet res=statement.executeQuery();
            res.next();
            String password=res.getString("password");
            res.close();
            statement.close();

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
