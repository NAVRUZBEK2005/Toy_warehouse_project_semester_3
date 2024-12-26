package org.example.service;

import org.example.DBService.DatabaseUtil;
import org.example.dto.UserDTO;
import org.example.enums.RoleEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    private static final Scanner scanner = new Scanner(System.in);

    public UserDTO authenticateUser() {
        System.out.println("=== User Login ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try (Connection connection = DatabaseUtil.getInstance()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                UserDTO user = new UserDTO();
                user.setId(resultSet.getInt("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setPhoneNumber(resultSet.getString("phone_number"));

                String role = resultSet.getString("role");
                if (role != null) {
                    user.setRole(RoleEnum.valueOf(role));
                }

                logger.info("Login successful for user: " + username);
                return user;
            }
        } catch (SQLException e) {
            logger.severe("Error during authentication: " + e.getMessage());
        }

        System.out.println("Invalid username or password.");
        return null;
    }

    public void signUpUser() {
        System.out.println("=== User Sign Up ===");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        boolean isRegistered = DatabaseUtil.registerUser(firstName, lastName, username, password, RoleEnum.valueOf(String.valueOf(RoleEnum.USER)), phoneNumber);

        if (isRegistered) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }
}
