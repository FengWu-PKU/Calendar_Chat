package server.utils;
import java.sql.*;

public class Account {
    static String url=ServerInfo.url;
    static String username = ServerInfo.username;
    static String password = ServerInfo.password;

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
            if(password.equals(pw)) {
                return res.getInt("id");
            }else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static void deleteAccount(int id) {
        try(Connection connection=DriverManager.getConnection(url,username,password)) {
            // 执行sql语句
            String sql = "DELETE FROM account WHERE id=?";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
