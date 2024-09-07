package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Gosha", "Kichenko",(byte) 20);
        userService.saveUser("Ilya", "Kriulyak",(byte) 22);
        userService.saveUser("Artyr", "Pirozhkov",(byte) 40);
        userService.saveUser("Ron", "Ginger",(byte) 45);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
