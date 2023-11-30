package classloader;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlLoaderTest {
    public static void main(String[] args) {
        try {
//            Class<?> a = Class.forName("com.mysql.jdbc.Driver");
//            System.out.println(a.getClassLoader());
//            System.out.println("2: " + Thread.currentThread().getContextClassLoader());

            String url = "jdbc:mysql://localhost:3306/mysql?serverTimezone=GMT%2B8";
            String user = "root";
            String password = "123456";
            Connection connections = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}