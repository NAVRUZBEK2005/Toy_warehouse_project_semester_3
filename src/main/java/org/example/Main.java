package org.example;

import org.example.enums.RoleEnum;
import org.example.service.UserService;
import org.example.service.AdminService;
import org.example.manager.CategoryManager;
import org.example.manager.ProductManager;
import org.example.dto.UserDTO;  // Import your DTO class to handle the user role

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final AdminService adminService = new AdminService();
    public static final CategoryManager categoryManager = new CategoryManager();
    public static final ProductManager productManager = new ProductManager();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Welcome to the System ===");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    UserDTO user = userService.authenticateUser();
                    if (user != null) {
                        if (user.getRole() != null && user.getRole() == RoleEnum.ADMIN) {
                            adminService.showAdminMenu();
                        }
                        else if (user.getRole() != null && user.getRole() == RoleEnum.USER) {
                            userMenu();
                        } else {
                            System.out.println("Invalid role entered.");
                        }
                    }
                    break;
                case 2:
                    userService.signUpUser();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. Category Management");
            System.out.println("2. Product Management");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
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
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
