package org.example.dao;

import org.example.dto.CategoryDTO;
import org.example.entity.Category;
import org.example.DBService.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public void addCategory(CategoryDTO category) throws SQLException {
        String query = "INSERT INTO categories (name, description) VALUES (?, ?)";

        try (Connection connection = DatabaseUtil.getInstance();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.executeUpdate();
        }
    }

    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories";

        try (Connection connection = DatabaseUtil.getInstance();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
                categories.add(category);
            }
        }
        return categories;
    }

    public Category getCategoryById(int id) throws SQLException {
        String query = "SELECT * FROM categories WHERE id = ?";
        Category category = null;

        try (Connection connection = DatabaseUtil.getInstance();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
            }
        }
        return category;
    }

    public void updateCategory(int id, CategoryDTO category) throws SQLException {
        String query = "UPDATE categories SET name = ?, description = ? WHERE id = ?";

        try (Connection connection = DatabaseUtil.getInstance();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, id);
            statement.executeUpdate();
        }
    }

    public void removeCategory(int id) throws SQLException {
        String query = "DELETE FROM categories WHERE id = ?";

        try (Connection connection = DatabaseUtil.getInstance();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
