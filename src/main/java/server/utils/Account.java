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

    /** 返回account_id， 若密码不匹配返回-1，账号不存在返回-2*/
    static public int login(String email, String pw) {
        try(Connection connection=DriverManager.getConnection(url,username,password)) {
            // 执行sql语句
            String sql = "SELECT password,id FROM account WHERE email=?";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1,email);
            ResultSet res=statement.executeQuery();
            if(!res.next()) {
                return -2;  // 账号不存在
            }

            String password=res.getString("password");
            if(password.equals(pw)) {
                return res.getInt("id");
            }else {
                return -1;  // 密码不匹配
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


    static public String getUsernameByID(int id) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT email FROM account WHERE id=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1,id);
            ResultSet res=stmt.executeQuery();
            if(!res.next()) {
                return null;
            }
            String name=res.getString("email");
            res.close();
            stmt.close();
            return name;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static public int getIDByUsername(String email) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT id FROM account WHERE email=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setString(1,email);
            ResultSet res=stmt.executeQuery();
            if(!res.next()) {
                return -1;
            }
            int id=res.getInt("id");
            res.close();
            stmt.close();
            return id;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
