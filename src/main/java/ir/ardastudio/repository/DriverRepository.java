package ir.ardastudio.repository;

import ir.ardastudio.model.Car;
import ir.ardastudio.model.Driver;
import ir.ardastudio.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverRepository {
    public void addDriver(Driver driver) throws SQLException {
        String personSQL = "INSERT INTO person VALUES(?, ?, ?, ?, ?)";
        String driverSQL = "INSERT INTO driver VALUES(?, ?)";

        try (Connection connection = DBConnection.getConnection()) {
            // Person
            PreparedStatement personStmt = connection.prepareStatement(personSQL);
            personStmt.setInt(1, driver.getId());
            personStmt.setString(2, driver.getName());
            personStmt.setInt(3, driver.getX());
            personStmt.setInt(4, driver.getY());
            personStmt.setDouble(5, driver.getScore());
            personStmt.executeUpdate();

            // Driver
            PreparedStatement driverStmt = connection.prepareStatement(driverSQL);
            driverStmt.setInt(1, driver.getId());
            // FIXME: Only Id? Don't you need to save the car's information?
            driverStmt.setInt(2, driver.getCar().getId());
            driverStmt.executeUpdate();
        }
    }

    public List<Driver> getAllDrivers() throws SQLException {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM person JOIN driver d ON person.id = d.id JOIN car ON d.car_id = car.id";

        try (Connection connection = DBConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Car car = new Car(
                        rs.getString("car.model"),
                        rs.getString("car.color"),
                        rs.getString("car.licensePlate"));
                car.setId(rs.getInt("car.id"));

                Driver driver = new Driver(
                        rs.getString("name"),
                        rs.getDouble("score"),
                        car);
                driver.setId(rs.getInt("person.id"));
                driver.setX(rs.getInt("x"));
                driver.setY(rs.getInt("y"));

                drivers.add(driver);
            }
        }

        return drivers;
    }

    public Driver getDriverById(int id) throws SQLException {
        String sql = "SELECT * FROM person JOIN driver d ON person.id = d.id JOIN car ON d.car_id = car.id WHERE person.id = ?";

        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement preStatement = connection.prepareStatement(sql);

            preStatement.setInt(1, id);
            ResultSet rs = preStatement.executeQuery();

            if (rs.next()) {
                Car car = new Car(
                        rs.getString("car.model"),
                        rs.getString("car.color"),
                        rs.getString("car.licensePlate"));
                car.setId(rs.getInt("car.id"));

                Driver driver = new Driver(
                        rs.getString("name"),
                        rs.getDouble("score"),
                        car);
                driver.setId(rs.getInt("person.id"));
                driver.setX(rs.getInt("x"));
                driver.setY(rs.getInt("y"));

                return driver;
            }
        }

        return null;
    }
}
