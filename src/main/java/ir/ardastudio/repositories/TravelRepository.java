package ir.ardastudio.repositories;

import ir.ardastudio.models.*;
import ir.ardastudio.models.Driver;
import ir.ardastudio.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TravelRepository {
    public void addTravel(Travel travel) throws SQLException {
        String sql = "INSERT INTO travel VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setInt(1, travel.getId());
            prepStmt.setInt(2, travel.getDriver().getId());
            prepStmt.setInt(3, travel.getTraveler().getId());
            prepStmt.setString(4, travel.getDate());
            prepStmt.setInt(5, travel.getDestination()[0]);
            prepStmt.setInt(6, travel.getDestination()[1]);
            prepStmt.setDouble(7, travel.getDistance());
            prepStmt.setInt(8, travel.getTime());
            prepStmt.setInt(9, travel.getCost());
            prepStmt.setString(10, travel.getStatus());
            prepStmt.executeUpdate();
        }
    }

    public List<Travel> getAllTravels() throws SQLException {
        List<Travel> travels = new ArrayList<>();
        String sql = "SELECT * FROM travel t" +
                "JOIN person pd ON pd.id = t.driver_id" +
                "JOIN driver ON driver.id = person.id" +
                "JOIN person pt ON pt.id = t.traveler_id" +
                "JOIN traveler ON traveler.id = person.id" +
                "JOIN car ON car.id = driver.car_id";

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
                        rs.getString("driver.name"),
                        rs.getDouble("driver.score"),
                        car);
                driver.setId(rs.getInt("pd.id"));
                driver.setX(rs.getInt("pd.x"));
                driver.setY(rs.getInt("pd.y"));

                Traveler traveler = new Traveler(
                        rs.getString("traveler.name"),
                        rs.getString("traveler.phone"),
                        rs.getDouble("traveler.score"));
                traveler.setId(rs.getInt("pt.id"));
                traveler.setX(rs.getInt("pt.x"));
                traveler.setY(rs.getInt("pt.y"));

                Travel travel = new Travel(
                        driver,
                        traveler,
                        new int[]{rs.getInt("t.dest_x"), rs.getInt("t.dest_y")});
                travel.setId(rs.getInt("t.id"));
                travel.setDate(rs.getString("t.date"));
                travel.setDistance(rs.getDouble("t.distance"));
                travel.setTime(rs.getInt("t.time"));
                travel.setStatus(rs.getString("t.status"));

                travels.add(travel);
            }
        }

        return travels;
    }

    public Travel getTravelById(int id) throws SQLException {
        String sql = "SELECT * FROM travel t" +
                "JOIN person pd ON pd.id = t.driver_id" +
                "JOIN driver ON driver.id = person.id" +
                "JOIN person pt ON pt.id = t.traveler_id" +
                "JOIN traveler ON traveler.id = person.id" +
                "JOIN car ON car.id = driver.car_id" +
                "WHERE t.id = ?";

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
                        rs.getString("driver.name"),
                        rs.getDouble("driver.score"),
                        car);
                driver.setId(rs.getInt("pd.id"));
                driver.setX(rs.getInt("pd.x"));
                driver.setY(rs.getInt("pd.y"));

                Traveler traveler = new Traveler(
                        rs.getString("traveler.name"),
                        rs.getString("traveler.phone"),
                        rs.getDouble("traveler.score"));
                traveler.setId(rs.getInt("pt.id"));
                traveler.setX(rs.getInt("pt.x"));
                traveler.setY(rs.getInt("pt.y"));

                Travel travel = new Travel(
                        driver,
                        traveler,
                        new int[]{rs.getInt("t.dest_x"), rs.getInt("t.dest_y")});
                travel.setId(rs.getInt("t.id"));
                travel.setDate(rs.getString("t.date"));
                travel.setDistance(rs.getDouble("t.distance"));
                travel.setTime(rs.getInt("t.time"));
                travel.setStatus(rs.getString("t.status"));

                return travel;
            }
        }

        return null;
    }
}
