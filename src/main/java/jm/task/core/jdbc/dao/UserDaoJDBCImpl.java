package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS kata_users.users" +
                "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL, " +
                "lastName VARCHAR(45) NOT NULL, " +
                "age TINYINT) ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Таблица успешно создана!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        // удалить таблицу (DELETE)
        String sql = "DROP TABLE IF EXISTS kata_users.users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Таблица удалена!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
//            System.out.println("You don't delete the Table");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        // создать чела
        String sql = "INSERT INTO kata_users.users (name, lastName, age) values (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь успешно создан!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
//            System.out.println("You don't create new User");
        }
    }

    public void removeUserById(long id) {
        // удалить чела по индексу
        String sql = "DELETE FROM kata_users.users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь успешно удалён!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
//            System.out.println("You don't remove the User");
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String sql = "SELECT id, name, lastName, age from kata_users.users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
                System.out.println(user);
            }
            System.out.println("Все пользовател выведены!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
//            System.out.println("You don't get this information");
        }
        return userList;

    }

    public void cleanUsersTable() {
        // очистить таблицу (TRUNCATE)
        String sql = "TRUNCATE TABLE kata_users.users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            System.out.println("Таблица успешно очищена!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
//            System.out.println("You don't clean this Table");
        }
    }
}
