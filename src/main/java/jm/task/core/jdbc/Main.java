package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        UserServiceImpl service = new UserServiceImpl();
        service.createUsersTable();

        Connection connection = Util.getConnection();
        try {
            connection.setAutoCommit(false);

            service.saveUser("Nick", "Cave", (byte) 18);
            service.saveUser("Mike", "Wazovski", (byte) 25);
            service.saveUser("John", "Doe", (byte) 30);
            service.saveUser("Anna", "Maria", (byte) 35);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        System.out.println(service.getAllUsers());

        service.cleanUsersTable();
        System.out.println(service.getAllUsers());

        service.dropUsersTable();
    }
}
