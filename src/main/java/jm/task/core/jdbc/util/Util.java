package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final static String DB_URL =
            "jdbc:mysql://localhost:3306/userdb?useUnicode=true&serverTimezone=UTC&useSSL=false";
    private final static String USER = "root";
    private final static String PASS = "Middlejav1984";

    private static Connection connection;

    private final static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            System.out.println("Regestering jdbc driver...");
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Exception while regestering jdbc driver");
            e.printStackTrace();
        }

        try {
            System.out.println("Connection to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.err.println("Exception while connection to database");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
