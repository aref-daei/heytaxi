package ir.ardastudio.repository;

import ir.ardastudio.model.Traveler;
import ir.ardastudio.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TravelerRepository {
    public void addTraveler(Traveler traveler) throws SQLException {
        String personSQL = "INSERT INTO person VALUES(?, ?, ?, ?, ?)";
        String travelerSQL = "INSERT INTO traveler VALUES(?, ?)";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement personStmt = connection.prepareStatement(personSQL);
                PreparedStatement travelerStmt = connection.prepareStatement(travelerSQL)
        ) {
            // Person
            personStmt.setInt(1, traveler.getId());
            personStmt.setString(2, traveler.getName());
            personStmt.setInt(3, traveler.getX());
            personStmt.setInt(4, traveler.getY());
            personStmt.setDouble(5, traveler.getScore());
            personStmt.executeUpdate();

            // Traveler
            travelerStmt.setInt(1, traveler.getId());
            travelerStmt.setString(2, traveler.getPhone());
            travelerStmt.executeUpdate();
        }
    }

    public List<Traveler> getAllTravelers() throws SQLException {
        List<Traveler> travelers = new ArrayList<>();
        String sql = "SELECT * FROM person JOIN traveler t ON person.id = t.id";

        try (
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
        ) {
            while (rs.next()) {
                Traveler traveler = new Traveler(
                        rs.getInt("person.id"),
                        rs.getString("person.name"),
                        rs.getString("person.phone"),
                        rs.getDouble("person.score"));
                traveler.setX(rs.getInt("person.x"));
                traveler.setY(rs.getInt("person.y"));

                travelers.add(traveler);
            }
        }
        return travelers;
    }

    public Traveler getTravelerById(int id) throws SQLException {
        String sql = "SELECT * FROM person JOIN traveler t ON person.id = t.id WHERE person.id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preStatement = connection.prepareStatement(sql)
        ) {
            preStatement.setInt(1, id);
            try (ResultSet rs = preStatement.executeQuery()) {
                if (rs.next()) {
                    Traveler traveler = new Traveler(
                            rs.getInt("person.id"),
                            rs.getString("person.name"),
                            rs.getString("person.phone"),
                            rs.getDouble("person.score"));
                    traveler.setX(rs.getInt("person.x"));
                    traveler.setY(rs.getInt("person.y"));

                    return traveler;
                }
            }
        }
        return null;
    }
}
