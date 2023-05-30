package server.utils;

import java.sql.*;

public class ToDoList {
    static String url=ServerInfo.url;
    static String username = ServerInfo.username;
    static String password = ServerInfo.password;

    static final int MAXTODOLISTNUM=10;

    int account_id;
    String usr_name;
    String entry_name;
    String content;
    Timestamp ddl;
    boolean alarm;
    boolean complete;

    public ToDoList(int account_id, String user_name, String entry_name, String content, Timestamp ddl, boolean alarm) {
        this.account_id = account_id;
        this.usr_name = user_name;
        this.entry_name = entry_name;
        this.content = content;
        this.ddl = ddl;
        this.alarm = alarm;
        this.complete=false;
    }

    public ToDoList(int account_id, String user_name, String entry_name, String content, Timestamp ddl, boolean alarm, boolean complete) {
        this.account_id = account_id;
        this.usr_name = user_name;
        this.entry_name = entry_name;
        this.content = content;
        this.ddl = ddl;
        this.alarm = alarm;
        this.complete=complete;
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

    /*删除条目*/
    static void deleteEntry(int account_id, String entry_name) {
        try (Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="DELETE FROM todolist WHERE account_id=? AND entry_name=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, account_id);
            stmt.setString(2, entry_name);
            stmt.executeUpdate();
            stmt.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**更改条目, 前两个参数是旧的条目信息，第三个参数是新的条目*/
    static void modifyEntry(int account_id, String entry_name, ToDoList toDoList) {
        assert(toDoList.account_id==account_id);
        try (Connection connection=DriverManager.getConnection(url, username, password)) {
            //先删除旧的，在插入新的
            String sql="DELETE FROM todolist WHERE account_id=? AND entry_name=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, account_id);
            stmt.setString(2, entry_name);
            stmt.executeUpdate();
            stmt.close();
            ToDoList.insertEntry(toDoList);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*将某个条目设置为完成*/
    static void completeEntry(int account_id, String entry_name) {
        try (Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="UPDATE todolist SET complete=TRUE WHERE account_id=? AND entry_name=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, account_id);
            stmt.setString(2, entry_name);
            stmt.executeUpdate();
            stmt.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*查询所有已经完成的条目*/
    static ToDoList[] findCompletedEntry(int account_id) {
        return findCompletedORNotEntry(account_id, true);
    }

    /*查询所有未完成的条目*/
    static ToDoList[] findNotCompletedEntry(int account_id) {
        return findCompletedORNotEntry(account_id, false);
    }

    static ToDoList[] findCompletedORNotEntry(int account_id, boolean comp) {
        ToDoList[] ans=new ToDoList[MAXTODOLISTNUM];
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT * FROM todolist WHERE account_id=? AND complete=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, account_id);
            stmt.setBoolean(2, comp);
            ResultSet res= stmt.executeQuery();
            int cnt=0;
            while(res.next()) {
                String usr_name=res.getString("usr_name");
                String entry_name=res.getString("entry_name");
                String content=res.getString("content");
                Timestamp ddl=res.getTimestamp("ddl");
                boolean alarm=res.getBoolean("alarm");
                ToDoList cur=new ToDoList(account_id, usr_name, entry_name, content, ddl, alarm, comp);
                ans[cnt++]=cur;
                if(cnt>=MAXTODOLISTNUM) break;
            }
            return ans;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*根据条目名称查询，条目名不重复*/
    static ToDoList findNameEntry(int account_id, String name) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT * FROM todolist WHERE account_id=? AND entry_name=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, account_id);
            stmt.setString(2, name);
            ResultSet res= stmt.executeQuery();
            if(!res.next()) return null;  // 没有符合条件的条目

            String usr_name=res.getString("usr_name");
            String entry_name=res.getString("entry_name");
            String content=res.getString("content");
            Timestamp ddl=res.getTimestamp("ddl");
            boolean alarm=res.getBoolean("alarm");
            boolean complete=res.getBoolean("complete");
            return new ToDoList(account_id, usr_name, entry_name, content, ddl, alarm, complete);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*根据内容查找*/
    static ToDoList[] findContentEntry(int account_id, String cont) {
        ToDoList[] ans=new ToDoList[MAXTODOLISTNUM];
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT * FROM todolist WHERE account_id=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, account_id);
            ResultSet res= stmt.executeQuery();
            int cnt=0;
            while(res.next()) {
                String usr_name=res.getString("usr_name");
                String entry_name=res.getString("entry_name");
                String content=res.getString("content");
                if(!content.contains(cont)) continue; // 只有包含查询串的条目被返回
                Timestamp ddl=res.getTimestamp("ddl");
                boolean alarm=res.getBoolean("alarm");
                boolean complete=res.getBoolean("complete");
                ToDoList cur=new ToDoList(account_id, usr_name, entry_name, content, ddl, alarm, complete);
                ans[cnt++]=cur;
                if(cnt>=MAXTODOLISTNUM) break;
            }
            return ans;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
