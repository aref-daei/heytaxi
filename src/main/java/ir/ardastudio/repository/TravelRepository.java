package ir.ardastudio.repository;

import ir.ardastudio.model.*;
import ir.ardastudio.model.Driver;
import ir.ardastudio.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TravelRepository {
    public void addTravel(Travel travel) throws SQLException {
        String sql = "INSERT INTO travel VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(sql)
        ) {
            prepStmt.setString(1, travel.getId());
            prepStmt.setString(2, travel.getCreatedAt().toString());
            prepStmt.setString(3, travel.getUpdatedAt().toString());
            prepStmt.setString(4, travel.getDriver().getId());
            prepStmt.setString(5, travel.getUser().getId());
            prepStmt.setInt(6, travel.getX());
            prepStmt.setInt(7, travel.getY());
            prepStmt.setDouble(8, travel.getDistance());
            prepStmt.setInt(9, travel.getTime());
            prepStmt.setInt(10, travel.getCost());
            prepStmt.setString(11, travel.getStatus());
            prepStmt.executeUpdate();
        }
    }

    public List<Travel> getAllTravels() throws SQLException {
        List<Travel> travels = new ArrayList<>();
        String sql = "SELECT " +
                "t.id AS t_id, " +
                "t.createdAt AS t_createdAt, " +
                "t.updatedAt AS t_updatedAt, " +
                "t.x AS t_x, " +
                "t.y AS t_y, " +
                "t.distance AS t_distance, " +
                "t.time AS t_time, " +
                "t.cost AS t_cost, " +
                "t.status AS t_status, " +
                "d.id AS d_id, " +
                "d.createdAt AS d_createdAt, " +
                "d.updatedAt AS d_updatedAt, " +
                "d.name AS d_name, " +
                "d.phone AS d_phone, " +
                "d.x AS d_x, " +
                "d.y AS d_y, " +
                "d.score AS d_score, " +
                "c.id AS c_id, " +
                "c.createdAt AS c_createdAt, " +
                "c.updatedAt AS c_updatedAt, " +
                "c.name AS c_name, " +
                "c.color AS c_color, " +
                "c.licensePlate AS c_licensePlate, " +
                "u.id AS u_id, " +
                "u.createdAt AS u_createdAt, " +
                "u.updatedAt AS u_updatedAt, " +
                "u.name AS u_name, " +
                "u.phone AS u_phone, " +
                "u.x AS u_x, " +
                "u.y AS u_y, " +
                "u.score AS u_score " +
                "FROM travel t " +
                "JOIN driver d ON t.driver_id = d.id " +
                "JOIN car c ON c.id = d.car_id " +
                "JOIN user u ON t.user_id = u.id " +
                "ORDER BY t_createdAt DESC";

        try (
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
        ) {
            while (rs.next()) {
                Car car = new Car(
                        rs.getString("c_id"),
                        rs.getString("c_createdAt"),
                        rs.getString("c_updatedAt"),
                        rs.getString("c_name"),
                        rs.getString("c_color"),
                        rs.getString("c_licensePlate"));

                Driver driver = new Driver(
                        car,
                        rs.getString("d_id"),
                        rs.getString("d_createdAt"),
                        rs.getString("d_updatedAt"),
                        rs.getString("d_name"),
                        rs.getString("d_phone"),
                        rs.getInt("d_x"),
                        rs.getInt("d_y"),
                        rs.getDouble("d_score"));

                User user = new User(
                        rs.getString("u_id"),
                        rs.getString("u_createdAt"),
                        rs.getString("u_updatedAt"),
                        rs.getString("u_name"),
                        rs.getString("u_phone"),
                        rs.getInt("u_x"),
                        rs.getInt("u_y"),
                        rs.getDouble("u_score"));

                Travel travel = new Travel(
                        rs.getString("t_id"),
                        rs.getString("t_createdAt"),
                        rs.getString("t_updatedAt"),
                        driver, user,
                        rs.getInt("t_x"),
                        rs.getInt("t_y"),
                        rs.getDouble("t_distance"),
                        rs.getInt("t_time"),
                        rs.getInt("t_cost"),
                        rs.getString("t_status"));

                travels.add(travel);
            }
        }
        return travels;
    }

    public Travel getTravelById(String id) throws SQLException {
        String sql = "SELECT " +
                "t.id AS t_id, " +
                "t.createdAt AS t_createdAt, " +
                "t.updatedAt AS t_updatedAt, " +
                "t.x AS t_x, " +
                "t.y AS t_y, " +
                "t.distance AS t_distance, " +
                "t.time AS t_time, " +
                "t.cost AS t_cost, " +
                "t.status AS t_status, " +
                "d.id AS d_id, " +
                "d.createdAt AS d_createdAt, " +
                "d.updatedAt AS d_updatedAt, " +
                "d.name AS d_name, " +
                "d.phone AS d_phone, " +
                "d.x AS d_x, " +
                "d.y AS d_y, " +
                "d.score AS d_score, " +
                "c.id AS c_id, " +
                "c.createdAt AS c_createdAt, " +
                "c.updatedAt AS c_updatedAt, " +
                "c.name AS c_name, " +
                "c.color AS c_color, " +
                "c.licensePlate AS c_licensePlate, " +
                "u.id AS u_id, " +
                "u.createdAt AS u_createdAt, " +
                "u.updatedAt AS u_updatedAt, " +
                "u.name AS u_name, " +
                "u.phone AS u_phone, " +
                "u.x AS u_x, " +
                "u.y AS u_y, " +
                "u.score AS u_score " +
                "FROM travel t " +
                "JOIN driver d ON t.driver_id = d.id " +
                "JOIN car c ON c.id = d.car_id " +
                "JOIN user u ON t.user_id = u.id " +
                "WHERE t_id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preStatement = connection.prepareStatement(sql)
        ) {
            preStatement.setString(1, id);
            try (ResultSet rs = preStatement.executeQuery()) {
                if (rs.next()) {
                    Car car = new Car(
                            rs.getString("c_id"),
                            rs.getString("c_createdAt"),
                            rs.getString("c_updatedAt"),
                            rs.getString("c_name"),
                            rs.getString("c_color"),
                            rs.getString("c_licensePlate"));

                    Driver driver = new Driver(
                            car,
                            rs.getString("d_id"),
                            rs.getString("d_createdAt"),
                            rs.getString("d_updatedAt"),
                            rs.getString("d_name"),
                            rs.getString("d_phone"),
                            rs.getInt("d_x"),
                            rs.getInt("d_y"),
                            rs.getDouble("d_score"));

                    User user = new User(
                            rs.getString("u_id"),
                            rs.getString("u_createdAt"),
                            rs.getString("u_updatedAt"),
                            rs.getString("u_name"),
                            rs.getString("u_phone"),
                            rs.getInt("u_x"),
                            rs.getInt("u_y"),
                            rs.getDouble("u_score"));

                    return new Travel(
                            rs.getString("t_id"),
                            rs.getString("t_createdAt"),
                            rs.getString("t_updatedAt"),
                            driver, user,
                            rs.getInt("t_x"),
                            rs.getInt("t_y"),
                            rs.getDouble("t_distance"),
                            rs.getInt("t_time"),
                            rs.getInt("t_cost"),
                            rs.getString("t_status"));
                }
            }
        }
        return null;
    }

    public void updateTravel(Travel travel) throws SQLException {
        String sql = "UPDATE travel " +
                "SET " +
                "updatedAt = ?, " +
                "driver_id = ?, " +
                "user_id = ?, " +
                "x = ?, " +
                "y = ?, " +
                "distance = ?, " +
                "time = ?, " +
                "cost = ?, " +
                "status = ? " +
                "WHERE id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement prepStmt = connection.prepareStatement(sql)
        ) {
            prepStmt.setString(1, travel.getUpdatedAt().toString());
            prepStmt.setString(2, travel.getDriver().getId());
            prepStmt.setString(3, travel.getUser().getId());
            prepStmt.setInt(4, travel.getX());
            prepStmt.setInt(5, travel.getY());
            prepStmt.setDouble(6, travel.getDistance());
            prepStmt.setInt(7, travel.getTime());
            prepStmt.setInt(8, travel.getCost());
            prepStmt.setString(9, travel.getStatus());
            prepStmt.setString(10, travel.getId());
            prepStmt.executeUpdate();
        }
    }
}
