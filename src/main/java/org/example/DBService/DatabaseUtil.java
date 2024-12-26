package org.example.DBService;

import org.example.enums.RoleEnum;

import java.sql.*;

public class DatabaseUtil {

    private static final String URL = "jdbc:postgresql://localhost:5432/toy_warehouse";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Big2005boy";

    private static Connection connection;

    public static Connection getInstance() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public static boolean authenticateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getInstance();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            throw e;
        }
    }

    public static boolean registerUser(String firstName, String lastName, String username, String password, RoleEnum role, String phoneNumber) {
        String query = "INSERT INTO users (first_name, last_name, username, password, role, phone_number) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getInstance()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.setString(5, String.valueOf(role));
            statement.setString(6, phoneNumber);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
