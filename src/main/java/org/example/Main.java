package org.example;

import org.example.DBService.DatabaseUtil;
import org.example.manager.CategoryManager;
import org.example.manager.ProductManager;

import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final Scanner scanner = new Scanner(System.in);
    private static final CategoryManager categoryManager = new CategoryManager();
    private static final ProductManager productManager = new ProductManager();

    public static void main(String[] args) {
        DatabaseUtil databaseUtil = DatabaseUtil.getInstance();

        if (!authenticateUser()) {
            System.out.println("Access denied. Exiting...");
            return;
        }

        while (true) {
            showMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    categoryManager.categoryMenu();
                    break;
                case 2:
                    productManager.productMenu();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static boolean authenticateUser() {
        System.out.println("=== User Login ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            if (DatabaseUtil.authenticateUser(username, password)) {
                logger.info("Login successful for user: " + username);
                return true;
            }
        } catch (Exception e) {
            logger.severe("Error during authentication: " + e.getMessage());
        }

        System.out.println("Invalid username or password.");
        return false;
    }

    private static void showMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Category Management");
        System.out.println("2. Product Management");
        System.out.println("3. Exit");
        System.out.print("Select an option: ");
    }
}
