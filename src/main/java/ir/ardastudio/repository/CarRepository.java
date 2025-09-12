package ir.ardastudio.repository;

import ir.ardastudio.model.Car;
import ir.ardastudio.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {
    public void addCar(Car car) throws SQLException {
        String sql = "INSERT INTO car VALUES(?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(sql)
        ) {
            prepStmt.setString(1, car.getId());
            prepStmt.setString(2, car.getCreatedAt().toString());
            prepStmt.setString(3, car.getUpdatedAt().toString());
            prepStmt.setString(4, car.getName());
            prepStmt.setString(5, car.getColor());
            prepStmt.setString(6, car.getLicensePlate());
            prepStmt.executeUpdate();
        }
    }

    public List<Car> getAllCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM car";

        try (
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
        ) {
            while (rs.next()) {
                Car car = new Car(
                        rs.getString("id"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getString("name"),
                        rs.getString("color"),
                        rs.getString("licensePlate"));
                cars.add(car);
            }
        }
        return cars;
    }

    public Car getCarById(String id) throws SQLException {
        String sql = "SELECT * FROM car WHERE id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(sql)
        ) {
            prepStmt.setString(1, id);
            try (ResultSet rs = prepStmt.executeQuery()) {
                if (rs.next()) {
                    return new Car(
                            rs.getString("id"),
                            rs.getString("createdAt"),
                            rs.getString("updatedAt"),
                            rs.getString("name"),
                            rs.getString("color"),
                            rs.getString("licensePlate"));
                }
            }
        }
        return null;
    }

    public void updateCar(Car car) throws SQLException {
        String sql = "UPDATE car " +
                "SET " +
                "updatedAt = ?, " +
                "name = ?, " +
                "color = ?, " +
                "licensePlate = ? " +
                "WHERE id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(sql)
        ) {
            prepStmt.setString(1, car.getUpdatedAt().toString());
            prepStmt.setString(2, car.getName());
            prepStmt.setString(3, car.getColor());
            prepStmt.setString(4, car.getLicensePlate());
            prepStmt.setString(5, car.getId());
            prepStmt.executeUpdate();
        }
    }
}
