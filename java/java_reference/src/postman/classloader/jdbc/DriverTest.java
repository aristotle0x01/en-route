package classloader.jdbc;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class DriverTest {
    public static void main(String[] args) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()){
            System.out.println(drivers.nextElement().toString());
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println(System.getProperty("jdbc.drivers"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}