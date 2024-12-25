package org.example.dao;

import org.example.DBService.DatabaseUtil;
import org.example.dto.ProductDTO;
import org.example.entity.Product;  // Importing the Product entity
import org.example.entity.Category; // Importing Category entity for category retrieval

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Method to add a product
    public void addProduct(ProductDTO product) throws SQLException {
        String query = "INSERT INTO products (name, description, price, category_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setLong(3, product.getPrice());
            statement.setInt(4, product.getCategoryId()); // Assuming Category has an `id` field
            statement.executeUpdate();
        }
    }

    // Method to get all products
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = """
            SELECT p.id AS product_id, p.name AS product_name, p.description AS product_description, 
                   p.price AS product_price, p.category_id AS category_id
            FROM products p
        """;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("product_id");
                String name = resultSet.getString("product_name");
                String description = resultSet.getString("product_description");
                long price = resultSet.getLong("product_price");
                int categoryId = resultSet.getInt("category_id");

                // Assume CategoryDAO can fetch a Category by its ID
                Category category = new CategoryDAO().getCategoryById(categoryId);

                // Creating a Product object
                Product product = new Product(id, name, description, price, category);
                products.add(product);
            }
        }

        return products;
    }

    // Method to update a product
    public void updateProduct(int id, ProductDTO product) throws SQLException {
        String query = "UPDATE products SET name = ?, description = ?, price = ?, category_id = ? WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setLong(3, product.getPrice());
            statement.setInt(4, product.getCategoryId()); // Assuming Category has an `id` field
            statement.setInt(5, id);
            statement.executeUpdate();
        }
    }

    // Method to remove a product
    public void removeProduct(int id) throws SQLException {
        String query = "DELETE FROM products WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
