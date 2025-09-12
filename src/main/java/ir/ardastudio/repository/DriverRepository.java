package ir.ardastudio.repository;

import ir.ardastudio.model.Car;
import ir.ardastudio.model.Driver;
import ir.ardastudio.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverRepository {
    public void addDriver(Driver driver) throws SQLException {
        String sql = "INSERT INTO driver VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(sql)
        ) {
            prepStmt.setString(1, driver.getId());
            prepStmt.setString(2, driver.getCreatedAt().toString());
            prepStmt.setString(3, driver.getUpdatedAt().toString());
            prepStmt.setString(4, driver.getName());
            prepStmt.setString(5, driver.getPhone());
            prepStmt.setInt(6, driver.getX());
            prepStmt.setInt(7, driver.getY());
            prepStmt.setDouble(8, driver.getScore());
            prepStmt.setString(9, driver.getCar().getId());
            prepStmt.executeUpdate();
        }
    }

    public List<Driver> getAllDrivers() throws SQLException {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT " +
                "d.id AS d_id, " +
                "d.createdAt AS d_createdAt, " +
                "d.updatedAt AS d_updatedAt, " +
                "d.name AS d_name, " +
                "d.phone AS d_phone, " +
                "d.x AS d_x, " +
                "d.y AS d_y, " +
                "d.score AS d_score, " +
                "c.id AS c_id, " +
                "c.createdAt AS c_createdAt, " +
                "c.updatedAt AS c_updatedAt, " +
                "c.name AS c_name, " +
                "c.color AS c_color, " +
                "c.licensePlate AS c_licensePlate " +
                "FROM driver d " +
                "JOIN car c ON d.car_id = c.id";

        try (
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
        ) {
            while (rs.next()) {
                Car car = new Car(
                        rs.getString("c_id"),
                        rs.getString("c_createdAt"),
                        rs.getString("c_updatedAt"),
                        rs.getString("c_name"),
                        rs.getString("c_color"),
                        rs.getString("c_licensePlate"));

                Driver driver = new Driver(
                        car,
                        rs.getString("d_id"),
                        rs.getString("d_createdAt"),
                        rs.getString("d_updatedAt"),
                        rs.getString("d_name"),
                        rs.getString("d_phone"),
                        rs.getInt("d_x"),
                        rs.getInt("d_y"),
                        rs.getDouble("d_score"));

                drivers.add(driver);
            }
        }
        return drivers;
    }

    public Driver getDriverById(String id) throws SQLException {
        String sql = "SELECT " +
                "d.id AS d_id, " +
                "d.createdAt AS d_createdAt, " +
                "d.updatedAt AS d_updatedAt, " +
                "d.name AS d_name, " +
                "d.phone AS d_phone, " +
                "d.x AS d_x, " +
                "d.y AS d_y, " +
                "d.score AS d_score, " +
                "c.id AS c_id, " +
                "c.createdAt AS c_createdAt, " +
                "c.updatedAt AS c_updatedAt, " +
                "c.name AS c_name, " +
                "c.color AS c_color, " +
                "c.licensePlate AS c_licensePlate " +
                "FROM driver d " +
                "JOIN car c ON driver.car_id = c.id " +
                "WHERE id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preStatement = connection.prepareStatement(sql)
        ) {
            preStatement.setString(1, id);
            try (ResultSet rs = preStatement.executeQuery()) {
                if (rs.next()) {
                    Car car = new Car(
                            rs.getString("c_id"),
                            rs.getString("c_createdAt"),
                            rs.getString("c_updatedAt"),
                            rs.getString("c_name"),
                            rs.getString("c_color"),
                            rs.getString("c_licensePlate"));

                    return new Driver(
                            car,
                            rs.getString("d_id AS "),
                            rs.getString("d_createdAt AS "),
                            rs.getString("d_updatedAt AS "),
                            rs.getString("d_name AS "),
                            rs.getString("d_phone AS "),
                            rs.getInt("d_x AS "),
                            rs.getInt("d_y AS "),
                            rs.getDouble("d_score AS "));
                }
            }
        }
        return null;
    }

    public void updateDriver(Driver driver) throws SQLException {
        String sql = "UPDATE driver " +
                "SET " +
                "updatedAt = ?, " +
                "name = ?, " +
                "phone = ?, " +
                "x = ?, " +
                "y = ?, " +
                "score = ?, " +
                "car_id = ? " +
                "WHERE id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(sql)
        ) {
            prepStmt.setString(1, driver.getUpdatedAt().toString());
            prepStmt.setString(2, driver.getName());
            prepStmt.setString(3, driver.getPhone());
            prepStmt.setInt(4, driver.getX());
            prepStmt.setInt(5, driver.getY());
            prepStmt.setDouble(6, driver.getScore());
            prepStmt.setString(7, driver.getCar().getId());
            prepStmt.setString(8, driver.getId());
            prepStmt.executeUpdate();
        }
    }
}
