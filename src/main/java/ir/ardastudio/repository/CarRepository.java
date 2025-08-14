package ir.ardastudio.repository;

import ir.ardastudio.model.Car;
import ir.ardastudio.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarRepository {
    public void addCar(Car car) throws SQLException {
        String sql = "INSERT INTO car VALUES(?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(sql)
        ) {
            prepStmt.setInt(1, car.getId());
            prepStmt.setString(2, car.getModel());
            prepStmt.setString(3, car.getColor());
            prepStmt.setString(4, car.getLicensePlate());
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
                        rs.getInt("id"),
                        rs.getString("model"),
                        rs.getString("color"),
                        rs.getString("licensePlate"));
                cars.add(car);
            }
        }
        return cars;
    }

    public Car getCarById(int id) throws SQLException {
        String sql = "SELECT * FROM car WHERE id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(sql)
        ) {
            prepStmt.setInt(1, id);
            try (ResultSet rs = prepStmt.executeQuery()) {
                if (rs.next()) {
                    return new Car(
                            rs.getInt("id"),
                            rs.getString("model"),
                            rs.getString("color"),
                            rs.getString("licensePlate"));
                }
            }
        }
        return null;
    }
}
