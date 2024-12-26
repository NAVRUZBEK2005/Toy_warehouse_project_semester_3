package org.example.dao;

import org.example.DBService.DatabaseUtil;
import org.example.dto.UserDTO;
import org.example.entity.User;
import org.example.enums.RoleEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDAO {

    private Connection getConnection() throws SQLException {
        return DatabaseUtil.getInstance();
    }

    public UserDTO getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DatabaseUtil.getInstance();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new UserDTO(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        RoleEnum.valueOf(resultSet.getString("role")),
                        resultSet.getString("password"),
                        resultSet.getString("phone_number")
                );
            }
        }
        return null;
    }

    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO users (first_name, last_name, role, password, phone_number) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getRole().name());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getPhoneNumber());
            statement.executeUpdate();
        }
    }

    public void updateUser(int id, User user) throws SQLException {
        String query = "UPDATE users SET first_name = ?, last_name = ?, role = ?, password = ?, phone_number = ? WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getRole().name());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getPhoneNumber());
            statement.setInt(6, id);
            statement.executeUpdate();
        }
    }

    public void removeUser(int id) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        RoleEnum.valueOf(resultSet.getString("role")),
                        resultSet.getString("password"),
                        resultSet.getString("phone_number")
                );
                users.add(user);
            }
        }
        return users;
    }

    public void viewUsers() {
        String query = "SELECT id, first_name, last_name, username, role FROM users";
        try (Connection conn = DatabaseUtil.getInstance();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                        ", Name: " + resultSet.getString("first_name") + " " + resultSet.getString("last_name") +
                        ", Username: " + resultSet.getString("username") +
                        ", Role: " + resultSet.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) {
        try (Connection conn = DatabaseUtil.getInstance()) {
            String query = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setInt(1, userId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("User deleted successfully.");
                } else {
                    System.out.println("No such user found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserRole(int userId) {
        System.out.print("Enter the new role for the user (ADMIN/USER): ");
        String roleInput = new Scanner(System.in).nextLine().toUpperCase();
        RoleEnum newRole = RoleEnum.valueOf(roleInput);

        try (Connection conn = DatabaseUtil.getInstance()) {
            String query = "UPDATE users SET role = ? WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, newRole.name());
                statement.setInt(2, userId);
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("User role updated successfully.");
                } else {
                    System.out.println("No such user found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
