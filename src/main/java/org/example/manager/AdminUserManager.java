package org.example.manager;

import java.sql.*;

public class AdminUserManager {

    private static final String CHECK_ADMIN_QUERY = "SELECT * FROM users WHERE role = ?";
    private static final String INSERT_ADMIN_QUERY = "INSERT INTO users (first_name, last_name, role, username, password, phone_number) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String ADMIN_PASSWORD = "admin_password";

    public static void createAdminIfNotExists(Connection connection) throws SQLException {
        try (PreparedStatement checkStatement = connection.prepareStatement(CHECK_ADMIN_QUERY)) {
            checkStatement.setString(1, "ADMIN");
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Admin user already exists.");
                return;
            }
        }

        try (PreparedStatement insertStatement = connection.prepareStatement(INSERT_ADMIN_QUERY)) {
            insertStatement.setString(1, "admin aka");
            insertStatement.setString(2, "admin akayev");
            insertStatement.setString(3, "ADMIN");
            insertStatement.setString(4, "admin");
            insertStatement.setString(5, ADMIN_PASSWORD);
            insertStatement.setString(6, "+998991234567");

            int rowsInserted = insertStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Admin user created successfully.");
            } else {
                System.out.println("Failed to create admin user.");
            }
        }
    }
}
