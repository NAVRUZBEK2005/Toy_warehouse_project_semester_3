package org.example.dao;

import org.example.DBService.DatabaseUtil;
import org.example.dto.UserDTO;
import org.example.entity.User;
import org.example.RoleEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Connection getConnection() throws SQLException {
        return DatabaseUtil.getConnection();
    }

    public UserDTO getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Assuming the result set has columns: id, first_name, last_name, password, phone_number, role
                return new UserDTO(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        RoleEnum.valueOf(resultSet.getString("role")),  // Assuming role is stored as a string
                        resultSet.getString("password"),
                        resultSet.getString("phone_number")
                );
            }
        }
        return null;  // No user found
    }

    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO users (first_name, last_name, role, password, phone_number) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getRole().name()); // Enum role
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
}
