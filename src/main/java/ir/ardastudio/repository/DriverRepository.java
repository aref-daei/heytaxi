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
                "id, " +
                "createdAt, " +
                "updatedAt, " +
                "name, " +
                "phone, " +
                "x, " +
                "y, " +
                "score, " +
                "car.id AS car_id, " +
                "car.createdAt AS car_createdAt, " +
                "car.updatedAt AS car_updatedAt, " +
                "car.name AS car_name, " +
                "car.color AS car_color, " +
                "car.licensePlate AS car_licensePlate " +
                "FROM driver " +
                "JOIN car ON driver.car_id = car.id";

        try (
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
        ) {
            while (rs.next()) {
                Car car = new Car(
                        rs.getString("car_id"),
                        rs.getString("car_createdAt"),
                        rs.getString("car_updatedAt"),
                        rs.getString("car_name"),
                        rs.getString("car_color"),
                        rs.getString("car_licensePlate"));

                Driver driver = new Driver(
                        car,
                        rs.getString("id"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getInt("x"),
                        rs.getInt("y"),
                        rs.getDouble("score"));

                drivers.add(driver);
            }
        }
        return drivers;
    }

    public Driver getDriverById(String id) throws SQLException {
        String sql = "SELECT " +
                "id, " +
                "createdAt, " +
                "updatedAt, " +
                "name, " +
                "phone, " +
                "x, " +
                "y, " +
                "score, " +
                "car.id AS car_id, " +
                "car.createdAt AS car_createdAt, " +
                "car.updatedAt AS car_updatedAt, " +
                "car.name AS car_name, " +
                "car.color AS car_color, " +
                "car.licensePlate AS car_licensePlate " +
                "FROM driver " +
                "JOIN car ON driver.car_id = car.id " +
                "WHERE id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preStatement = connection.prepareStatement(sql)
        ) {
            preStatement.setString(1, id);
            try (ResultSet rs = preStatement.executeQuery()) {
                if (rs.next()) {
                    Car car = new Car(
                            rs.getString("car_id"),
                            rs.getString("car_createdAt"),
                            rs.getString("car_updatedAt"),
                            rs.getString("car_name"),
                            rs.getString("car_color"),
                            rs.getString("car_licensePlate"));

                    return new Driver(
                            car,
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

    public void updateDriver(Driver driver) throws SQLException {
        String sql = "UPDATE driver " +
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
            prepStmt.setString(1, driver.getUpdatedAt().toString());
            prepStmt.setString(2, driver.getName());
            prepStmt.setString(3, driver.getPhone());
            prepStmt.setInt(4, driver.getX());
            prepStmt.setInt(5, driver.getY());
            prepStmt.setDouble(6, driver.getScore());
            prepStmt.setString(7, driver.getId());
            prepStmt.executeUpdate();
        }
    }
}
