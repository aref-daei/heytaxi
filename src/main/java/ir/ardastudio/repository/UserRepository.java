package ir.ardastudio.repository;

import ir.ardastudio.model.User;
import ir.ardastudio.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO user VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(sql)
        ) {
            prepStmt.setString(1, user.getId());
            prepStmt.setString(2, user.getCreatedAt().toString());
            prepStmt.setString(3, user.getUpdatedAt().toString());
            prepStmt.setString(4, user.getName());
            prepStmt.setString(5, user.getPhone());
            prepStmt.setInt(6, user.getX());
            prepStmt.setInt(7, user.getY());
            prepStmt.setDouble(8, user.getScore());
            prepStmt.executeUpdate();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT " +
                "id, " +
                "createdAt, " +
                "updatedAt, " +
                "name, " +
                "phone, " +
                "x, " +
                "y, " +
                "score, " +
                "FROM user";

        try (
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
        ) {
            while (rs.next()) {
                User user = new User(
                        rs.getString("id"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getInt("x"),
                        rs.getInt("y"),
                        rs.getDouble("score"));

                users.add(user);
            }
        }
        return users;
    }

    public User getUserById(String id) throws SQLException {
        String sql = "SELECT " +
                "id, " +
                "createdAt, " +
                "updatedAt, " +
                "name, " +
                "phone, " +
                "x, " +
                "y, " +
                "score, " +
                "FROM user " +
                "WHERE id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preStatement = connection.prepareStatement(sql)
        ) {
            preStatement.setString(1, id);
            try (ResultSet rs = preStatement.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("id"),
                            rs.getString("createdAt"),
                            rs.getString("updatedAt"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getInt("x"),
                            rs.getInt("y"),
                            rs.getDouble("score"));
                }
            }
        }
        return null;
    }

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE user " +
                "SET " +
                "updatedAt, " +
                "name, " +
                "phone, " +
                "x, " +
                "y, " +
                "score, " +
                "WHERE id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(sql)
        ) {
            prepStmt.setString(1, user.getUpdatedAt().toString());
            prepStmt.setString(2, user.getName());
            prepStmt.setString(3, user.getPhone());
            prepStmt.setInt(4, user.getX());
            prepStmt.setInt(5, user.getY());
            prepStmt.setDouble(6, user.getScore());
            prepStmt.setString(7, user.getId());
            prepStmt.executeUpdate();
        }
    }
}
