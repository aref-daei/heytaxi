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
        String sql = "SELECT " +
                "person.id AS person_id, " +
                "person.name AS person_name, " +
                "person.score AS person_score, " +
                "person.x AS person_x, " +
                "person.y AS person_y, " +
                "car.id AS car_id, " +
                "car.model AS car_model, " +
                "car.color AS car_color, " +
                "car.licensePlate AS car_licensePlate " +
                "FROM person " +
                "JOIN driver d ON person.id = d.id " +
                "JOIN car ON d.car_id = car.id";

        try (
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
        ) {
            while (rs.next()) {
                Car car = new Car(
                        rs.getInt("car_id"),
                        rs.getString("car_model"),
                        rs.getString("car_color"),
                        rs.getString("car_licensePlate"));

                Driver driver = new Driver(
                        rs.getInt("person_id"),
                        rs.getString("person_name"),
                        rs.getDouble("person_score"),
                        car);
                driver.setX(rs.getInt("person_x"));
                driver.setY(rs.getInt("person_y"));

                drivers.add(driver);
            }
        }
        return drivers;
    }

    public Driver getDriverById(int id) throws SQLException {
        String sql = "SELECT " +
                "person.id AS person_id, " +
                "person.name AS person_name, " +
                "person.score AS person_score, " +
                "person.x AS person_x, " +
                "person.y AS person_y, " +
                "car.id AS car_id, " +
                "car.model AS car_model, " +
                "car.color AS car_color, " +
                "car.licensePlate AS car_licensePlate " +
                "FROM person " +
                "JOIN driver d ON person.id = d.id " +
                "JOIN car ON d.car_id = car.id " +
                "WHERE person.id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preStatement = connection.prepareStatement(sql)
        ) {
            preStatement.setInt(1, id);
            try (ResultSet rs = preStatement.executeQuery()) {
                if (rs.next()) {
                    Car car = new Car(
                            rs.getInt("car_id"),
                            rs.getString("car_model"),
                            rs.getString("car_color"),
                            rs.getString("car_licensePlate"));

                    Driver driver = new Driver(
                            rs.getInt("person_id"),
                            rs.getString("person_name"),
                            rs.getDouble("person_score"),
                            car);
                    driver.setX(rs.getInt("person_x"));
                    driver.setY(rs.getInt("person_y"));

                    return driver;
                }
            }
        }
        return null;
    }

    public void updateDriver(Driver driver) throws SQLException {
        String personSQL = "UPDATE person " +
                "SET " +
                "name = ?, " +
                "x = ?, " +
                "y = ?, " +
                "score = ? " +
                "WHERE id = ?";
        String driverSQL = "UPDATE driver " +
                "SET " +
                "car_id = ? " +
                "WHERE id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement personStmt = connection.prepareStatement(personSQL);
                PreparedStatement driverStmt = connection.prepareStatement(driverSQL)
        ) {
            // Person
            personStmt.setString(1, driver.getName());
            personStmt.setInt(2, driver.getX());
            personStmt.setInt(3, driver.getY());
            personStmt.setDouble(4, driver.getScore());
            personStmt.setInt(5, driver.getId());
            personStmt.executeUpdate();

            // Driver
            driverStmt.setInt(1, driver.getCar().getId());
            driverStmt.setInt(2, driver.getId());
            driverStmt.executeUpdate();
        }
    }
}
