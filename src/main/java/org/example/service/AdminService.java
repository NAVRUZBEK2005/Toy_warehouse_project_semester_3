package org.example.service;

import org.example.dao.UserDAO;

import java.util.Scanner;

import static org.example.Main.categoryManager;
import static org.example.Main.productManager;

public class AdminService {

    private static final Scanner scanner = new Scanner(System.in);
    private final UserDAO userDAO;

    public AdminService() {
        this.userDAO = new UserDAO();
    }

    public void showAdminMenu() {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View Users");
            System.out.println("2. Delete User");
            System.out.println("3. Update User Role");
            System.out.println("4. Category Management");
            System.out.println("5. Product Management");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewUsers();
                    break;
                case 2:
                    deleteUser();
                    break;
                case 3:
                    updateUserRole();
                    break;
                case 4:
                    categoryManager.categoryMenu();
                    break;
                case 5:
                    productManager.productMenu();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void viewUsers() {
        userDAO.viewUsers();
    }

    public void deleteUser() {
        System.out.print("Enter the user ID to delete: ");
        int userId = scanner.nextInt();
        userDAO.deleteUser(userId);
    }

    public void updateUserRole() {
        System.out.print("Enter the user ID to update role: ");
        int userId = scanner.nextInt();
        scanner.nextLine();
        userDAO.updateUserRole(userId);
    }
}
