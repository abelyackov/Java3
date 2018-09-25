package ru.geekbrains.chat.server;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class AuthService {
    private static Connection connection;
    private static Statement stmt;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //метод добавления пользователя в базу данных и автоматическое создание таблицы блек-листа для пользователя
    public static void addUser(String login, String pass, String nick) {
        try {
            int pwd = pass.hashCode();
            String query = "INSERT INTO main (login,password,nickname)\n" +
                    "VALUES ('" + login + "', " + pwd + ", '" + nick + "');";
            String bl = "CREATE TABLE bl_" + login + "(\n" +
                    "    id       INTEGER PRIMARY KEY,\n" +
                    "    nickname STRING\n" +
                    ");"; //создание таблицы с блек-листом для пользователя с именем таблицы bl_"login пользователя"
            PreparedStatement ps = connection.prepareStatement(query);
            PreparedStatement ps1 = connection.prepareStatement(bl);
            ps1.executeUpdate();
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //метод добавления пользователя в базу bl_"login пользователя"
    public static void addNickBl(String login, String nickname) {
        try {
            String query = "INSERT INTO bl_" + login + " (nickname)\n" +
                    "VALUES ('" + nickname + "');";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //метод удаления пользователя из базы bl_"login пользователя"
    public static void removeNickBl(String login, String nickname) {
        try {
            String query = "DELETE FROM bl_" + login + "\n" +
                    "WHERE nickname = '" + nickname + "';";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean getNickBl(String login, String nickname) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT nickname FROM bl_" + login + " WHERE nickname = '" + nickname + "'");
            if (rs.next()) {
                String dbNickname = rs.getString(2);
                if (nickname == dbNickname) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT nickname, password FROM main WHERE login = '" + login + "'");
            int myHash = pass.hashCode();
            if (rs.next()) {
                String nick = rs.getString(1);
                int dbHash = rs.getInt(2);
                if (myHash == dbHash) {
                    return nick;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
