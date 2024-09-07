package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection con = Util.getConnection(); Statement statement = con.createStatement()) {
            if (statement.executeUpdate("SHOW TABLE STATUS LIKE 'users'") == 0) {
                statement.executeUpdate("CREATE TABLE `users` (\n" +
                        "  `Id` bigint NOT NULL AUTO_INCREMENT,\n" +
                        "  `Name` varchar(45) NOT NULL,\n" +
                        "  `LastName` varchar(45) NOT NULL,\n" +
                        "  `age` tinyint NOT NULL,\n" +
                        "  PRIMARY KEY (`Id`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb3");
                System.out.println("Таблица создана!");
            } else {
                System.out.println("Таблица с таким именем уже создана!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection con = Util.getConnection(); Statement statement = con.createStatement()) {
            if (statement.executeUpdate("SHOW TABLE STATUS LIKE 'users'") == -1) {
                statement.execute("drop table users");
                System.out.println("Таблица стерта с лица Земли!");
            } else {
                System.out.println("Таблицы с таким именем и так не существовало");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection con = Util.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet rs = statement.executeQuery("SELECT * FROM users")) {
            rs.moveToInsertRow();
            rs.updateString(2, name);
            rs.updateString(3, lastName);
            rs.updateByte(4, age);
            rs.insertRow();
            rs.moveToCurrentRow();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("User с именем - %s добавлен в базу\n", name);
    }

    public void removeUserById(long id) {
        try (Connection con = Util.getConnection(); Statement statement = con.createStatement()) {
            String str = String.format("delete from users where id = %d", id);
            statement.executeUpdate(str);
            System.out.printf("User c id: %d - удалён\n", id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection con = Util.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM users");
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                userList.add(new User(rs.getLong("Id"),rs.getString("Name"),
                        rs.getString("LastName"), rs.getByte("age")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection con = Util.getConnection(); Statement statement = con.createStatement()) {
            statement.executeUpdate("delete from users where id > 0");
            System.out.println("Таблица очищена!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
