package ir.ardastudio.repository;

import ir.ardastudio.model.*;
import ir.ardastudio.model.Driver;
import ir.ardastudio.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TravelRepository {
    public void addTravel(Travel travel) throws SQLException {
        String sql = "INSERT INTO travel VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(sql)
        ) {
            prepStmt.setString(1, travel.getId());
            prepStmt.setString(2, travel.getDriver().getId());
            prepStmt.setString(3, travel.getTraveler().getId());
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
        String sql = "SELECT " +
                "t.id AS t_id, " +
                "t.dest_x AS t_dest_x, " +
                "t.dest_y AS t_dest_y, " +
                "t.date AS t_date, " +
                "t.distance AS t_distance, " +
                "t.time AS t_time, " +
                "t.status AS t_status, " +
                "pd.id AS pd_id, " +
                "pd.name AS pd_name, " +
                "pd.score AS pd_score, " +
                "pd.x AS pd_x, " +
                "pd.y AS pd_y, " +
                "pt.id AS pt_id, " +
                "pt.name AS pt_name, " +
                "pt.score AS pt_score, " +
                "pt.x AS pt_x, " +
                "pt.y AS pt_y, " +
                "traveler.phone AS pt_phone, " +
                "car.id AS car_id, " +
                "car.model AS car_model, " +
                "car.color AS car_color, " +
                "car.licensePlate AS car_licensePlate " +
                "FROM travel t " +
                "JOIN person pd ON t.driver_id = pd.id " +
                "JOIN driver ON pd.id = driver.id " +
                "JOIN person pt ON t.traveler_id = pt.id " +
                "JOIN traveler ON pt.id = traveler.id " +
                "JOIN car ON car.id = driver.car_id " +
                "ORDER BY t_time";

        try (
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
        ) {
            while (rs.next()) {
                Car car = new Car(
                        rs.getString("car_id"),
                        rs.getString("car_model"),
                        rs.getString("car_color"),
                        rs.getString("car_licensePlate"));

                Driver driver = new Driver(
                        rs.getString("pd_id"),
                        rs.getString("pd_name"),
                        rs.getDouble("pd_score"),
                        car);
                driver.setX(rs.getInt("pd_x"));
                driver.setY(rs.getInt("pd_y"));

                Traveler traveler = new Traveler(
                        rs.getString("pt_id"),
                        rs.getString("pt_name"),
                        rs.getString("pt_phone"),
                        rs.getDouble("pt_score"));
                traveler.setX(rs.getInt("pt_x"));
                traveler.setY(rs.getInt("pt_y"));

                Travel travel = new Travel(
                        rs.getString("t_id"),
                        driver, traveler,
                        new int[]{rs.getInt("t_dest_x"), rs.getInt("t_dest_y")});
                travel.setDate(rs.getString("t_date"));
                travel.setDistance(rs.getDouble("t_distance"));
                travel.setTime(rs.getInt("t_time"));
                travel.setStatus(rs.getString("t_status"));

                travels.add(travel);
            }
        }
        return travels;
    }

    public Travel getTravelById(String id) throws SQLException {
        String sql = "SELECT " +
                "t.id AS t_id, " +
                "t.dest_x AS t_dest_x, " +
                "t.dest_y AS t_dest_y, " +
                "t.date AS t_date, " +
                "t.distance AS t_distance, " +
                "t.time AS t_time, " +
                "t.status AS t_status, " +
                "pd.id AS pd_id, " +
                "pd.name AS pd_name, " +
                "pd.score AS pd_score, " +
                "pd.x AS pd_x, " +
                "pd.y AS pd_y, " +
                "pt.id AS pt_id, " +
                "pt.name AS pt_name, " +
                "pt.score AS pt_score, " +
                "pt.x AS pt_x, " +
                "pt.y AS pt_y, " +
                "pt.phone AS pt_phone, " +
                "car.id AS car_id, " +
                "car.model AS car_model, " +
                "car.color AS car_color, " +
                "car.licensePlate AS car_licensePlate " +
                "FROM travel t " +
                "JOIN person pd ON t.driver_id = pd.id " +
                "JOIN driver ON pd.id = driver.id " +
                "JOIN person pt ON t.traveler_id = pt.id " +
                "JOIN traveler ON pt.id = traveler.id " +
                "JOIN car ON car.id = driver.car_id " +
                "WHERE t.id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preStatement = connection.prepareStatement(sql)
        ) {
            preStatement.setString(1, id);
            try (ResultSet rs = preStatement.executeQuery()) {
                if (rs.next()) {
                    Car car = new Car(
                            rs.getString("car_id"),
                            rs.getString("car_model"),
                            rs.getString("car_color"),
                            rs.getString("car_licensePlate"));

                    Driver driver = new Driver(
                            rs.getString("pd_id"),
                            rs.getString("pd_name"),
                            rs.getDouble("pd_score"),
                            car);
                    driver.setX(rs.getInt("pd_x"));
                    driver.setY(rs.getInt("pd_y"));

                    Traveler traveler = new Traveler(
                            rs.getString("pt_id"),
                            rs.getString("pt_name"),
                            rs.getString("pt_phone"),
                            rs.getDouble("pt_score"));
                    traveler.setX(rs.getInt("pt_x"));
                    traveler.setY(rs.getInt("pt_y"));

                    Travel travel = new Travel(
                            rs.getString("t_id"),
                            driver, traveler,
                            new int[]{rs.getInt("t_dest_x"), rs.getInt("t_dest_y")});
                    travel.setDate(rs.getString("t_date"));
                    travel.setDistance(rs.getDouble("t_distance"));
                    travel.setTime(rs.getInt("t_time"));
                    travel.setStatus(rs.getString("t_status"));

                    return travel;
                }
            }
        }
        return null;
    }

    public void updateTravel(Travel travel) throws SQLException {
        String sql = "UPDATE travel " +
                "SET " +
                "driver_id = ?, " +
                "traveler_id = ?, " +
                "date = ?, " +
                "dest_x = ?, " +
                "dest_y = ?, " +
                "distance = ?, " +
                "time = ?, " +
                "cost = ?, " +
                "status = ? " +
                "WHERE id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(sql)
        ) {
            prepStmt.setString(1, travel.getDriver().getId());
            prepStmt.setString(2, travel.getTraveler().getId());
            prepStmt.setString(3, travel.getDate());
            prepStmt.setInt(4, travel.getDestination()[0]);
            prepStmt.setInt(5, travel.getDestination()[1]);
            prepStmt.setDouble(6, travel.getDistance());
            prepStmt.setInt(7, travel.getTime());
            prepStmt.setInt(8, travel.getCost());
            prepStmt.setString(9, travel.getStatus());
            prepStmt.setString(10, travel.getId());
            prepStmt.executeUpdate();
        }
    }
}
