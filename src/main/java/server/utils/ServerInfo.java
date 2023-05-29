package server.utils;


import java.sql.*;

public class ServerInfo {
    static final String ip="192.168.56.1";
    static final String port="3306";
    static final String database="javaproj";

    /*展示各个表的信息*/
    public static void main(String[] args) {
        String url="jdbc:mysql://"+ip+":"+port+"/"+database;
        String username="admin";
        String password="1q3w5e7r9t";
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
}
