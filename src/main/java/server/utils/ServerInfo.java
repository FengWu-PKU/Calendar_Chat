package server.utils;


import java.sql.*;

public class ServerInfo {
    static final String ip="162.105.146.37";
    static final String port="43306";
    static final String database="stu2000013727";
    static final String url="jdbc:mysql://"+ip+":"+port+"/"+database;
    static final String username="stu2000013727";
    static final String password="stu2000013727";


    /*展示各个表的信息*/
    static void showCreateTables(String[] args) {
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SHOW TABLES")) {

            while (rs.next()) {
                String tableName = rs.getString(1);
                String showCreateTableSql = "SHOW CREATE TABLE " + tableName;

                Statement stmt2 = conn.createStatement();
                ResultSet createTableRs = stmt2.executeQuery(showCreateTableSql);
                if (createTableRs.next()) {
                    String createStatement = createTableRs.getString(2);
                    System.out.println("Table: " + tableName);
                    System.out.println("Create statement:");
                    System.out.println(createStatement);
                    System.out.println();
                }
                createTableRs.close();
                stmt2.close();
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*清空所有表*/
    static void clearAll() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            Statement statement = conn.createStatement();
            // 设置foreign_key_checks为0
            statement.executeUpdate("SET foreign_key_checks = 0");

            // 清空各个表格
            statement.executeUpdate("TRUNCATE TABLE account");
            statement.executeUpdate("TRUNCATE TABLE bill");
            statement.executeUpdate("TRUNCATE TABLE diary");
            statement.executeUpdate("TRUNCATE TABLE friend");
            statement.executeUpdate("TRUNCATE TABLE message");
            statement.executeUpdate("TRUNCATE TABLE new_friend");
            statement.executeUpdate("TRUNCATE TABLE qq_user");
            statement.executeUpdate("TRUNCATE TABLE todolist");

            // 设置foreign_key_checks为1
            statement.executeUpdate("SET foreign_key_checks = 1");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
