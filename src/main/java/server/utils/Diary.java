package server.utils;

import java.sql.*;
import java.time.LocalDateTime;

public class Diary {
    static String url="jdbc:mysql://"+ServerInfo.ip+":"+ServerInfo.port+"/"+ServerInfo.database;
    static String username = "public_user";
    static String password = "123456";

    static final int MAXDIARYNUM=10;

    int account_id;
    String writer_name;
    Timestamp date_t;
    String content;

    public Diary(int account_id, String writer_name, Timestamp date_t, String content) {
        this.account_id = account_id;
        this.writer_name = writer_name;
        this.date_t = date_t;
        this.content = content;
    }

    public Diary(int account_id, String writer_name, String content) {
        this.account_id = account_id;
        this.writer_name = writer_name;
        this.date_t=new Timestamp(System.currentTimeMillis());
        this.content = content;
    }

    /*插入新的日记*/
    static void InsertDiary(Diary diary) {
        try(Connection connection=DriverManager.getConnection(url,username,password)) {
            String sql = "INSERT INTO diary(usr_name, date_t, content, account_id) VALUES (?, ?, ?, ?)";
            PreparedStatement statement=connection.prepareStatement(sql);
            statement.setString(1, diary.writer_name);
            statement.setTimestamp(2,diary.date_t);
            statement.setString(3,diary.content);
            statement.setInt(4,diary.account_id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* 返回指定用户，指定日期的日记 */
    static Diary[] findDiary(int id, Timestamp queryDate) {
        Diary[] diaries=new Diary[MAXDIARYNUM];
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT * FROM diary WHERE account_id=? AND DATE(date_t)=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setDate(2, new java.sql.Date(queryDate.getTime()));
            ResultSet res=stmt.executeQuery();
            int cnt=0;
            while(res.next()) {
                int acid=res.getInt("account_id");
                String wn=res.getString("writer_name");
                Timestamp dt=res.getTimestamp("date_t");
                String ct=res.getString("content");
                Diary cur=new Diary(acid, wn, dt, ct);
                diaries[cnt++]=cur;
                if(cnt>=MAXDIARYNUM) break;  // 最多返回MAXDIARYNUM条
            }
            res.close();
            stmt.close();
            return diaries;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 指定用户，根绝内容的查找*/
    static Diary[] searchDiary(int id, String target) {
        Diary[] diaries=new Diary[MAXDIARYNUM];
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT * FROM diary WHERE account_id=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet res=stmt.executeQuery();
            int cnt=0;
            while(res.next()) {
                String ct=res.getString("content");
                if(!ct.contains(target)) continue; // 不包含查询串
                int acid=res.getInt("account_id");
                String wn=res.getString("writer_name");
                Timestamp dt=res.getTimestamp("date_t");
                Diary cur=new Diary(acid, wn, dt, ct);
                diaries[cnt++]=cur;
                if(cnt>=MAXDIARYNUM) break;  // 最多返回MAXDIARYNUM条
            }
            res.close();
            stmt.close();
            return diaries;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /** 指定用户，查询串出现的次数*/
    static int countDiary(int id, String target) {
        Diary[] diaries=new Diary[MAXDIARYNUM];
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT * FROM diary WHERE account_id=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet res=stmt.executeQuery();
            int cnt=0;
            while(res.next()) {
                String ct=res.getString("content");
                if(!ct.contains(target)) continue; // 不包含查询串
                cnt++;
                if(cnt>=MAXDIARYNUM) break;  // 最多返回MAXDIARYNUM条
            }
            res.close();
            stmt.close();
            return cnt;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
