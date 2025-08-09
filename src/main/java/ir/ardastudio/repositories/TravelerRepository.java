package ir.ardastudio.repositories;

import ir.ardastudio.models.Traveler;
import ir.ardastudio.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TravelerRepository {
    public void addTraveler(Traveler traveler) throws SQLException {
        String personSQL = "INSERT INTO person VALUES(?, ?, ?, ?, ?)";
        String travelerSQL = "INSERT INTO traveler VALUES(?, ?)";

        try (Connection connection = DBConnection.getConnection()) {
            // Person
            PreparedStatement personStmt = connection.prepareStatement(personSQL);
            personStmt.setInt(1, traveler.getId());
            personStmt.setString(2, traveler.getName());
            personStmt.setInt(3, traveler.getX());
            personStmt.setInt(4, traveler.getY());
            personStmt.setDouble(5, traveler.getScore());
            personStmt.executeUpdate();

            // Traveler
            PreparedStatement travelerStmt = connection.prepareStatement(travelerSQL);
            travelerStmt.setInt(1, traveler.getId());
            travelerStmt.setString(2, traveler.getPhone());
            travelerStmt.executeUpdate();
        }
    }

    public List<Traveler> getAllTravelers() throws SQLException {
        List<Traveler> travelers = new ArrayList<>();
        String sql = "SELECT * FROM person JOIN traveler ON person.id = traveler.id";

        try (Connection connection = DBConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Traveler traveler = new Traveler(
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getDouble("score"));
                traveler.setId(rs.getInt("person.id"));
                traveler.setX(rs.getInt("x"));
                traveler.setY(rs.getInt("y"));
                travelers.add(traveler);
            }
        }

        return travelers;
    }

    public Traveler getTravelerById(int id) throws SQLException {
        String sql = "SELECT * FROM person JOIN traveler ON person.id = traveler.id WHERE person.id = ?";

        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement preStatement = connection.prepareStatement(sql);

            preStatement.setInt(1, id);
            ResultSet rs = preStatement.executeQuery();

            if (rs.next()) {
                Traveler traveler = new Traveler(
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getDouble("score"));
                traveler.setId(rs.getInt("person.id"));
                traveler.setX(rs.getInt("x"));
                traveler.setY(rs.getInt("y"));
                return traveler;
            }
        }

        return null;
    }
}
