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
             PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.setInt(1, car.getId());
            preStatement.setString(2, car.getModel());
            preStatement.setString(3, car.getColor());
            preStatement.setString(4, car.getLicensePlate());
            preStatement.executeUpdate();
        }
    }

    public List<Car> getAllCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM car";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet reSet = statement.executeQuery(sql)) {

            while (reSet.next()) {
                Car car = new Car(
                        reSet.getString("model"),
                        reSet.getString("color"),
                        reSet.getString("licensePlate"));
                car.setId(reSet.getInt("id"));
                cars.add(car);
            }
        }

        return cars;
    }

    public Car getCarById(int id) throws SQLException {
        String sql = "SELECT * FROM car WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.setInt(1, id);
            ResultSet reSet = preStatement.executeQuery();

            if (reSet.next()) {
                Car car = new Car(
                        reSet.getString("model"),
                        reSet.getString("color"),
                        reSet.getString("licensePlate"));
                car.setId(reSet.getInt("id"));
                return car;
            }
        }

        return null;
    }
}
