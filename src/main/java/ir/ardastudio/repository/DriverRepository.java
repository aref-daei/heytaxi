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

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement personStmt = connection.prepareStatement(personSQL);
                PreparedStatement driverStmt = connection.prepareStatement(driverSQL)
        ) {
            // Person
            personStmt.setInt(1, driver.getId());
            personStmt.setString(2, driver.getName());
            personStmt.setInt(3, driver.getX());
            personStmt.setInt(4, driver.getY());
            personStmt.setDouble(5, driver.getScore());
            personStmt.executeUpdate();

            // Driver
            driverStmt.setInt(1, driver.getId());
            driverStmt.setInt(2, driver.getCar().getId());
            driverStmt.executeUpdate();
        }
    }

    public List<Driver> getAllDrivers() throws SQLException {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM person JOIN driver d ON person.id = d.id JOIN car ON d.car_id = car.id";

        try (
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
        ) {
            while (rs.next()) {
                Car car = new Car(
                        rs.getInt("car.id"),
                        rs.getString("car.model"),
                        rs.getString("car.color"),
                        rs.getString("car.licensePlate"));

                Driver driver = new Driver(
                        rs.getInt("person.id"),
                        rs.getString("person.name"),
                        rs.getDouble("person.score"),
                        car);
                driver.setX(rs.getInt("person.x"));
                driver.setY(rs.getInt("person.y"));

                drivers.add(driver);
            }
        }
        return drivers;
    }

    public Driver getDriverById(int id) throws SQLException {
        String sql = "SELECT * FROM person JOIN driver d ON person.id = d.id JOIN car ON d.car_id = car.id WHERE person.id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preStatement = connection.prepareStatement(sql)
        ) {
            preStatement.setInt(1, id);
            try (ResultSet rs = preStatement.executeQuery()) {
                if (rs.next()) {
                    Car car = new Car(
                            rs.getInt("car.id"),
                            rs.getString("car.model"),
                            rs.getString("car.color"),
                            rs.getString("car.licensePlate"));

                    Driver driver = new Driver(
                            rs.getInt("person.id"),
                            rs.getString("person.name"),
                            rs.getDouble("person.score"),
                            car);
                    driver.setX(rs.getInt("person.x"));
                    driver.setY(rs.getInt("person.y"));

                    return driver;
                }
            }
        }
        return null;
    }
}
