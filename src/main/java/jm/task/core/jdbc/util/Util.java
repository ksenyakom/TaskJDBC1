package jm.task.core.jdbc.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static Connection connection;
    private static SessionFactory sessionFactory;
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/userdb?useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "Middlejav1984";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {
        if (connection == null) {
            return createConnection();
        } else {
            return connection;
        }
    }

    private static Connection createConnection() {
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

    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null) {
            return createSessionFactory();
        } else {
            return sessionFactory;
        }
    }

    private static SessionFactory createSessionFactory(){

        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.url", DB_URL );
        properties.setProperty("dialect", "org.hibernate.dialect.MySQL");
        properties.setProperty("hibernate.connection.username", USER);
        properties.setProperty("hibernate.connection.password", PASS);
        properties.setProperty("hibernate.connection.driver_class", DB_DRIVER);

        SessionFactory sessionFactory = new Configuration()
                .addAnnotatedClass(jm.task.core.jdbc.model.User.class)
                .addProperties(properties)
                .buildSessionFactory();
        return sessionFactory;

    }



}
