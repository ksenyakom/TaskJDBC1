package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE = "CREATE TABLE userdb.users (id serial NOT NULL primary key, " +
            "name varchar(45) NOT NULL, last_name varchar(45) NOT NULL, age TINYINT NOT NULL)";
    private static final String SAVE = "INSERT INTO userdb.users (name, last_name, age) VALUES (?,?,?)";
    private static final String SELECT_ALL = "SELECT * FROM userdb.users";
    private static final String DROP = "DROP TABLE userdb.users";
    private static final String CLEAN = "TRUNCATE TABLE userdb.users";
    private static final String REMOVE_BY_ID = "DELETE FROM userdb.users WHERE id = ?";

    private static Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    public void createUsersTable() {
        try (PreparedStatement statement = connection.prepareStatement(CREATE)) {
            statement.executeUpdate();
            System.out.println("Table created");
        } catch (SQLException e) {
            System.err.println("Table already exist");
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement statement = connection.prepareStatement(DROP)) {
            statement.executeUpdate();
            System.out.println("Table deleted");
        } catch (SQLException e) {
            System.err.println("Exception while removing table");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connection.prepareStatement(SAVE)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setInt(3, age);
            statement.executeUpdate();
            System.out.println("Юзер " + name + " добавлен в базу");
        } catch (SQLException e) {
            System.err.println("Exception while saving user");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = connection.prepareStatement(REMOVE_BY_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Exception while removing by id");
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Exception while getAllUsers");
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try (PreparedStatement statement = connection.prepareStatement(CLEAN)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Exception while cleaning");
        }
    }
}
