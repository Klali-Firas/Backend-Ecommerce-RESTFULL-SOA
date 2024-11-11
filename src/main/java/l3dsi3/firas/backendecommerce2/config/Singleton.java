package l3dsi3.firas.backendecommerce2.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Singleton {
    private static Connection con;
    static{
        try {
            //com.mysql.cj.jdbc.Driver
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5433/ecommerce", "postgres", "CondorA8");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getConnection() {
        return con;
    }

    public static void main(String[] args) {
        System.out.println(Singleton.getConnection());
    }
}
