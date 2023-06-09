package server.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.sql.*;

import common.OnedayInfo;
import common.TodoInfoRequest;
import common.TodoItem;

public class ToDoList {
    static String url=ServerInfo.url;
    static String username = ServerInfo.username;
    static String password = ServerInfo.password;

    static final int MAXTODOLISTNUM=10;

    int account_id;
    String entry_name;
    String content;
    Date ddl;
    boolean pub;

    public ToDoList(int account_id, String entry_name, String content, Date ddl, boolean pub) {
        this.account_id = account_id;
        this.entry_name = entry_name;
        this.content = content;
        this.ddl = ddl;
        this.pub = pub;
    }

    /*插入新的条目*/
    static void insertEntry(ToDoList toDoList) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="INSERT INTO todolist(entry_name, content, ddl, pub, account_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setString(1, toDoList.entry_name);
            stmt.setString(2, toDoList.content);
            java.sql.Date date=new java.sql.Date(toDoList.ddl.getTime());
            stmt.setDate(3, date);
            stmt.setBoolean(4, toDoList.pub);
            stmt.setInt(5, toDoList.account_id);
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



    /*根据内容查找*/
    static public ArrayList<TodoItem> findDateEntry(TodoInfoRequest q) {
        int account_id=q.show_uid;
        Date dates=q.date;
        ArrayList<TodoItem> ans=new ArrayList<TodoItem>();
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT * FROM todolist WHERE account_id=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, account_id);
            ResultSet res= stmt.executeQuery();
            while(res.next()) {
                String entry_name=res.getString("entry_name");
                Date ddl=res.getDate("Date");
                Calendar c1=Calendar.getInstance(),c2=Calendar.getInstance();
                c1.setTime(dates);
                c2.setTime(ddl);
                if(c1.get(Calendar.YEAR)!=c2.get(Calendar.YEAR)||c1.get(Calendar.DAY_OF_YEAR)!=c2.get(Calendar.DAY_OF_YEAR)) continue; // 只有在Dates当天才返回
                boolean pub=res.getBoolean("pub");
                if(q.show_uid!=q.my_uid&&pub==false)continue;
                String content=res.getString("Content");
                TodoItem cur=new TodoItem(entry_name, content, ddl, pub);
                ans.add(cur);
                if(ans.size()>=MAXTODOLISTNUM) break;
            }
            return ans;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static public ArrayList<TodoItem>[][] find28DaysEntry(TodoInfoRequest q){
        ArrayList<TodoItem>[][] todoLists=new ArrayList[4][7];
        for(int i=0;i<4;i++){
            for(int j=0;j<7;j++){
                todoLists[i][j]=findDateEntry(q);
                long time=q.date.getTime();
                q.date.setTime(time+24*60*60*1000);
            }
        }
        return todoLists;
    }
    static public void UpdateDateEntry(OnedayInfo q){
        ArrayList<TodoItem> tmp=findDateEntry(q);
        for(TodoItem item:tmp){
            deleteEntry(q.my_uid,item.getTitle());
        }
        for(TodoItem item:q.todoList){
            insertEntry(new ToDoList(q.my_uid,item.getTitle(),item.getContent(),item.getDeadline(),item.getPub()));
        }
    }
}

