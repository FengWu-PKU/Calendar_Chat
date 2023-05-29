package server.utils;

import java.sql.*;

public class Diary {
    static String url="jdbc:mysql://"+ServerInfo.ip+":"+ServerInfo.port+"/"+ServerInfo.database;
    static String username = "public_user";
    static String password = "123456";
    static void InsertDiary(int account_id, String writer_name, Timestamp date_t, String content) {
        try(Connection connection=DriverManager.getConnection(url,username,password)) {
            String sql = "INSERT INTO diary(usr_name, date_t, content, account_id) VALUES (?, ?, ?, ?)";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1, writer_name);
            statement.setTimestamp(2,date_t);
            statement.setString(3,content);
            statement.setInt(4,account_id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
