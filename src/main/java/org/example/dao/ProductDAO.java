package org.example.dao;

import org.example.DatabaseUtil;
import org.example.dto.ProductDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void addProduct(ProductDTO productDTO) throws SQLException {
        String query = "INSERT INTO products (name, description, price, category_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, productDTO.getName());
            statement.setString(2, productDTO.getDescription());
            statement.setLong(3, productDTO.getPrice());
            statement.setInt(4, productDTO.getCategoryId());
            statement.executeUpdate();
        }
    }

    public List<ProductDTO> getAllProducts() throws SQLException {
        List<ProductDTO> products = new ArrayList<>();
        String query = """
            SELECT p.id AS product_id, p.name AS product_name, p.description AS product_description, 
                   p.price AS product_price, p.category_id AS category_id
            FROM products p
        """;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(resultSet.getInt("product_id"));
                productDTO.setName(resultSet.getString("product_name"));
                productDTO.setDescription(resultSet.getString("product_description"));
                productDTO.setPrice(resultSet.getLong("product_price"));
                productDTO.setCategoryId(resultSet.getInt("category_id"));
                products.add(productDTO);
            }
        }

        return products;
    }

    public void updateProduct(int id, ProductDTO productDTO) throws SQLException {
        String query = "UPDATE products SET name = ?, description = ?, price = ?, category_id = ? WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, productDTO.getName());
            statement.setString(2, productDTO.getDescription());
            statement.setLong(3, productDTO.getPrice());
            statement.setInt(4, productDTO.getCategoryId());
            statement.setInt(5, id);
            statement.executeUpdate();
        }
    }

    public void removeProduct(int id) throws SQLException {
        String query = "DELETE FROM products WHERE id = ?";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
