package server.utils;

import java.sql.*;

public class ToDoList {
    static String url="jdbc:mysql://"+ServerInfo.ip+":"+ServerInfo.port+"/"+ServerInfo.database;
    static String username = "public_user";
    static String password = "123456";

    int account_id;
    String usr_name;
    String entry_name;
    String content;
    Timestamp ddl;
    boolean alarm;

    public ToDoList(int account_id, String user_name, String entry_name, String content, Timestamp ddl, boolean alarm) {
        this.account_id = account_id;
        this.usr_name = user_name;
        this.entry_name = entry_name;
        this.content = content;
        this.ddl = ddl;
        this.alarm = alarm;
    }

    /*插入新的条目*/
    static void insertEntry(ToDoList toDoList) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="INSERT INTO todolist(usr_name, entry_name, content, ddl, alarm, account_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setString(1, toDoList.usr_name);
            stmt.setString(2, toDoList.entry_name);
            stmt.setString(3, toDoList.content);
            stmt.setTimestamp(4, toDoList.ddl);
            stmt.setBoolean(5, toDoList.alarm);
            stmt.setInt(6, toDoList.account_id);
            stmt.executeUpdate();
            stmt.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
