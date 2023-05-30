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
    public static void main(String[] args) {
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
