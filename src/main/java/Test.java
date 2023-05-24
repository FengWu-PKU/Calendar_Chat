import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
    static final String ip="192.168.56.1";
    static final String port="3306";
    static final String database="javaproj";
    public static void main(String[] args) {
        // MySQL数据库连接信息
//        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String url="jdbc:mysql://"+ip+":"+port+"/"+database;
        String username = "public_user";
        String password = "123456";

        // 连接数据库
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // 创建Statement对象
            Statement statement = connection.createStatement();

            // 执行查询语句
            String sql = "SHOW TABLES";
            ResultSet resultSet = statement.executeQuery(sql);

            // 处理结果
//            while (resultSet.next()) {
//                // 从结果集中获取数据
//                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                // 其他列...
//
//                // 输出数据
//                System.out.println("ID: " + id + ", Name: " + name);
//            }

            // 关闭结果集和Statement对象
            resultSet.close();
            statement.close();
            System.out.println("成功连接");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
