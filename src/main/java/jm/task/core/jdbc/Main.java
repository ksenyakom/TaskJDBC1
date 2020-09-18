package jm.task.core.jdbc;

import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static String CREATE = "CREATE TABLE userdb.users " +
            "(id serial NOT NULL primary key, " +
            "name varchar(45) NOT NULL, " +
            "last_name varchar(45) NOT NULL, " +
            "age TINYINT NOT NULL)";

    public static void main(String[] args) {
        Connection connection = Util.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(CREATE)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // создание таблицы

    }
}
