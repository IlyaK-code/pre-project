package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    private static String url = "jdbc:mysql://localhost:3306/pp_jdbc";
    private static String name = "root";
    private static String password = "qwerty";
    private static Connection con;
    private static Statement statement;

    public static Connection getConnection() {
        try {
            con = DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            System.err.println("Неудалось подключиться к БД!");
        }
        return con;
    }

    public static void close()  {
        try {
            if (!con.isClosed()) {
                con.close();
                System.out.println("Соединение закрыто!");
            } else {
            System.out.println("Связь с БД уже закрыта!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
