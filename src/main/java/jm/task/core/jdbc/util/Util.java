package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static Connection connection = createConnection();
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/userdb?useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "Middlejav1984";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";


    public static Connection getConnection() {
        return connection;
    }

    public static Connection createConnection() {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Exception while registering jdbc driver");
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.err.println("Exception while connection to database");
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Exception while closing connection to database");
            e.printStackTrace();
        }
    }
}
