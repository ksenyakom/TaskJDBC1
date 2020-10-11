package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
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
