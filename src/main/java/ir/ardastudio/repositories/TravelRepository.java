package ir.ardastudio.repositories;

import ir.ardastudio.models.Travel;
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

//    public List<Travel> getAllTravels() throws SQLException {
//        List<Travel> travels = new ArrayList<>();
//        String sql = "SELECT * FROM travel";
//
//        try (Connection connection = DBConnection.getConnection()) {
//            Statement statement = connection.createStatement();
//            ResultSet rs = statement.executeQuery(sql);
//
//            while (rs.next()) {
//                Travel travel = new Travel(
//                        rs.getString("name"),
//                        rs.getString("phone"),
//                        rs.getDouble("score"));
//                travel.setId(rs.getInt("person.id"));
//                travel.setX(rs.getInt("x"));
//                travel.setY(rs.getInt("y"));
//                travels.add(travel);
//            }
//        }
//
//        return travels;
//    }
//
//    public Travel getTravelById(int id) throws SQLException {
//        String sql = "SELECT * FROM travel";
//
//        try (Connection connection = DBConnection.getConnection()) {
//            PreparedStatement preStatement = connection.prepareStatement(sql);
//
//            preStatement.setInt(1, id);
//            ResultSet rs = preStatement.executeQuery();
//
//            if (rs.next()) {
//                Travel travel = new Travel(
//                        rs.getString("name"),
//                        rs.getString("phone"),
//                        rs.getDouble("score"));
//                travel.setId(rs.getInt("person.id"));
//                travel.setX(rs.getInt("x"));
//                travel.setY(rs.getInt("y"));
//                return travel;
//            }
//        }
//
//        return null;
//    }
}
