package ir.ardastudio.repositories;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import ir.ardastudio.models.Car;
import ir.ardastudio.utils.DBConnection;

public class CarRepository {
    public void addCar(Car car) throws SQLException {
        String sql = "INSERT INTO car VALUES(?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement prepStmt = connection.prepareStatement(sql)) {
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

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Car car = new Car(
                        resultSet.getString("model"),
                        resultSet.getString("color"),
                        resultSet.getString("licensePlate"));
                car.setId(resultSet.getInt("id"));
                cars.add(car);
            }
        }

        return cars;
    }

    public Car getCarById(int id) throws SQLException {
        String sql = "SELECT * FROM car WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement prepStmt = connection.prepareStatement(sql)) {
            prepStmt.setInt(1, id);
            ResultSet resultSet = prepStmt.executeQuery();

            if (resultSet.next()) {
                Car car = new Car(
                        resultSet.getString("model"),
                        resultSet.getString("color"),
                        resultSet.getString("licensePlate"));
                car.setId(resultSet.getInt("id"));
                return car;
            }
        }

        return null;
    }
}
