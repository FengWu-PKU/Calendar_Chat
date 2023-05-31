package server.utils;

import java.sql.*;
import java.time.LocalDate;

public class Bill {
    static String url=ServerInfo.url;
    static String username = ServerInfo.username;
    static String password = ServerInfo.password;

    static final int MAXBILLNUM=100;

    int account_id;
    String usr_name;
    Timestamp date_t;
    float amount;
    public enum Type{
        IN,
        OUT
    };
    Type inorout;
    String goWhere;
    public enum Category {
        STUDY,
        ENTERTAINMENT,
        FOOD,
        HEALTHCARE,
        OTHERS
    };
    Category category;
    String comment;

    public Bill(int account_id, String usr_name, Timestamp date_t, float amount, Type inorout, String goWhere, Category category, String comment) {
        this.account_id = account_id;
        this.usr_name = usr_name;
        this.date_t = date_t;
        this.amount = amount;
        this.inorout = inorout;
        this.goWhere = goWhere;
        this.category = category;
        this.comment = comment;
    }

    /*插入新条目*/
    static void insertEntry(Bill bill) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="INSERT INTO bill(account_id, usr_name,date_t,amount,inOrOut,go_where,category, comment) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1,bill.account_id);
            stmt.setString(2,bill.usr_name);
            stmt.setTimestamp(3,bill.date_t);
            stmt.setFloat(4,bill.amount);
            stmt.setString(5, bill.inorout.toString());
            stmt.setString(6,bill.goWhere);
            stmt.setString(7,bill.category.toString());
            stmt.setString(8,bill.comment);
            stmt.executeUpdate();
            stmt.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static class IEInfo {
        float income;
        float expense;

        public IEInfo(float income, float expense) {
            this.income = income;
            this.expense = expense;
        }

        public IEInfo() {
            this.income=0f;
            this.expense=0f;
        }
    }

    /*统计某一天的收支*/
    static IEInfo getOneDayInfo(Date querydate) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT amount, inOrOut FROM bill WHERE DATE(date_t)=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setDate(1,querydate);
            ResultSet res= stmt.executeQuery();
            IEInfo ans=new IEInfo();
            while(res.next()) {
                String io=res.getString("inOrOut");
                float amt=res.getFloat("amount");
                if(io.equals(Type.IN.toString())) {
                    ans.income+=amt;
                }else if(io.equals(Type.OUT.toString())) {
                    ans.expense+=amt;
                }
            }
            res.close();
            stmt.close();
            return ans;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*统计某一天特定category的收支*/
    static IEInfo certainCateOneDay(Date querydate, Category category) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT amount, inOrOut FROM bill WHERE DATE(date_t)=? AND category=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setDate(1,querydate);
            stmt.setString(2,category.toString());
            ResultSet res= stmt.executeQuery();
            IEInfo ans=new IEInfo();
            while(res.next()) {
                String io=res.getString("inOrOut");
                float amt=res.getFloat("amount");
                if(io.equals(Type.IN.toString())) {
                    ans.income+=amt;
                }else if(io.equals(Type.OUT.toString())) {
                    ans.expense+=amt;
                }
            }
            res.close();
            stmt.close();
            return ans;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*统计某一月的总体收支
    * 用1~12表示*/
    static IEInfo getOneMonthInfo(int querymonth) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT amount, inOrOut FROM bill WHERE MONTH(date_t)=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1,querymonth);
            ResultSet res= stmt.executeQuery();
            IEInfo ans=new IEInfo();
            while(res.next()) {
                String io=res.getString("inOrOut");
                float amt=res.getFloat("amount");
                if(io.equals(Type.IN.toString())) {
                    ans.income+=amt;
                }else if(io.equals(Type.OUT.toString())) {
                    ans.expense+=amt;
                }
            }
            res.close();
            stmt.close();
            return ans;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*统计某一月特定category的收支*/
    static IEInfo certainCateOneMonth(int querymonth, Category category) {
        try(Connection connection=DriverManager.getConnection(url, username, password)) {
            String sql="SELECT amount, inOrOut FROM bill WHERE MONTH(date_t)=? AND category=?";
            PreparedStatement stmt=connection.prepareStatement(sql);
            stmt.setInt(1,querymonth);
            stmt.setString(2,category.toString());
            ResultSet res= stmt.executeQuery();
            IEInfo ans=new IEInfo();
            while(res.next()) {
                String io=res.getString("inOrOut");
                float amt=res.getFloat("amount");
                if(io.equals(Type.IN.toString())) {
                    ans.income+=amt;
                }else if(io.equals(Type.OUT.toString())) {
                    ans.expense+=amt;
                }
            }
            res.close();
            stmt.close();
            return ans;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
