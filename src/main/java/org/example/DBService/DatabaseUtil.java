package org.example.DBService;

import org.example.manager.AdminUserManager;

import java.sql.*;

public class DatabaseUtil {

    private static final String URL = "jdbc:postgresql://localhost:5432/toy_warehouse";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Big2005boy";

    private static DatabaseUtil instance;

    private DatabaseUtil() {
        ensureAdminUser();
    }

    public static DatabaseUtil getInstance() {
        if (instance == null) {
            instance = new DatabaseUtil();
        }
        return instance;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private void ensureAdminUser() {
        try (Connection connection = getConnection()) {
            AdminUserManager.createAdminIfNotExists(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Error ensuring admin user: " + e.getMessage(), e);
        }
    }

    public static boolean authenticateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String dbPassword = resultSet.getString("password");
                return dbPassword.equals(password);
            }
        }
        return false;
    }
}
