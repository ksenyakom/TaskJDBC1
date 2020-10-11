package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class Main {
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/userdb?useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "Middlejav1984";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();
        service.dropUsersTable();
        service.createUsersTable();

        service.saveUser("Nick", "Cave", (byte) 18);
        service.saveUser("Mike", "Wazovski", (byte) 25);
        service.saveUser("John", "Doe", (byte) 30);
        service.saveUser("Anna", "Maria", (byte) 35);

        System.out.println(service.getAllUsers());

        service.cleanUsersTable();
        System.out.println(service.getAllUsers());

        service.dropUsersTable();
    }
}
