package org.example;

import org.example.dao.ProductDAO;
import org.example.dto.CategoryDTO;
import org.example.dao.CategoryDAO;
import org.example.dto.ProductDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CategoryDAO categoryDAO = new CategoryDAO();

    public static void main(String[] args) {
        while (true) {
            System.out.println("=== Main Menu ===");
            System.out.println("1. Category Management");
            System.out.println("2. Product Management");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    categoryMenu();
                    break;
                case 2:
                    productMenu();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void categoryMenu() {
        while (true) {
            System.out.println("=== Category Management ===");
            System.out.println("1. Add Category");
            System.out.println("2. View Categories");
            System.out.println("3. Update Category");
            System.out.println("4. Remove Category");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCategory();
                    break;
                case 2:
                    viewCategories();
                    break;
                case 3:
                    updateCategory();
                    break;
                case 4:
                    removeCategory();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void productMenu() {
        while (true) {
            System.out.println("=== Product Management ===");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Remove Product");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    viewProducts();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    removeProduct();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addCategory() {
        System.out.print("Enter category name: ");
        String name = scanner.nextLine();
        System.out.print("Enter category description: ");
        String description = scanner.nextLine();

        CategoryDTO categoryDTO = new CategoryDTO(null, name, description);

        try {
            categoryDAO.addCategory(categoryDTO);
            System.out.println("Category added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding category: " + e.getMessage());
        }
    }

    private static void viewCategories() {
        try {
            List<Category> categories = categoryDAO.getAllCategories();
            if (categories.isEmpty()) {
                System.out.println("No categories found.");
            } else {
                categories.forEach(category ->
                        System.out.println("ID: " + category.getId() +
                                ", Name: " + category.getName() +
                                ", Description: " + category.getDescription()));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching categories: " + e.getMessage());
        }
    }

    private static void updateCategory() {
        System.out.print("Enter category ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new description: ");
        String description = scanner.nextLine();

        CategoryDTO categoryDTO = new CategoryDTO(id, name, description);

        try {
            categoryDAO.updateCategory(id, categoryDTO);
            System.out.println("Category updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating category: " + e.getMessage());
        }
    }

    private static void removeCategory() {
        System.out.print("Enter category ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            categoryDAO.removeCategory(id);
            System.out.println("Category removed successfully!");
        } catch (SQLException e) {
            System.out.println("Error removing category: " + e.getMessage());
        }
    }

    private static final ProductDAO productDAO = new ProductDAO();

    private static void addProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product description: ");
        String description = scanner.nextLine();
        System.out.print("Enter product price: ");
        long price = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Enter category ID for the product: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();

        ProductDTO productDTO = new ProductDTO(null, name, description, price, categoryId);

        try {
            productDAO.addProduct(productDTO);
            System.out.println("Product added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    private static void viewProducts() {
        try {
            List<ProductDTO> products = productDAO.getAllProducts();
            if (products.isEmpty()) {
                System.out.println("No products found.");
            } else {
                System.out.println("=== Product List ===");
                for (ProductDTO product : products) {
                    System.out.printf("ID: %d, Name: %s, Description: %s, Price: %d, Category ID: %d%n",
                            product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCategoryId());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
        }
    }

    private static void updateProduct() {
        System.out.print("Enter product ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new product description: ");
        String description = scanner.nextLine();
        System.out.print("Enter new product price: ");
        long price = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Enter new category ID for the product: ");
        int categoryId = scanner.nextInt();
        scanner.nextLine();

        ProductDTO productDTO = new ProductDTO(id, name, description, price, categoryId);

        try {
            productDAO.updateProduct(id, productDTO);
            System.out.println("Product updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }

    private static void removeProduct() {
        System.out.print("Enter product ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            productDAO.removeProduct(id);
            System.out.println("Product removed successfully!");
        } catch (SQLException e) {
            System.out.println("Error removing product: " + e.getMessage());
        }
    }

}
