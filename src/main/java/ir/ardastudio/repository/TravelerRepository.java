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
            personStmt.setString(1, traveler.getId());
            personStmt.setString(2, traveler.getName());
            personStmt.setInt(3, traveler.getX());
            personStmt.setInt(4, traveler.getY());
            personStmt.setDouble(5, traveler.getScore());
            personStmt.executeUpdate();

            // Traveler
            travelerStmt.setString(1, traveler.getId());
            travelerStmt.setString(2, traveler.getPhone());
            travelerStmt.executeUpdate();
        }
    }

    public List<Traveler> getAllTravelers() throws SQLException {
        List<Traveler> travelers = new ArrayList<>();
        String sql = "SELECT " +
                "person.id AS person_id, " +
                "person.name AS person_name, " +
                "person.score AS person_score, " +
                "person.x AS person_x, " +
                "person.y AS person_y, " +
                "t.phone AS person_phone " +
                "FROM person " +
                "JOIN traveler t ON person.id = t.id";

        try (
                Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql)
        ) {
            while (rs.next()) {
                Traveler traveler = new Traveler(
                        rs.getString("person_id"),
                        rs.getString("person_name"),
                        rs.getString("person_phone"),
                        rs.getDouble("person_score"));
                traveler.setX(rs.getInt("person_x"));
                traveler.setY(rs.getInt("person_y"));

                travelers.add(traveler);
            }
        }
        return travelers;
    }

    public Traveler getTravelerById(int id) throws SQLException {
        String sql = "SELECT " +
                "person.id AS person_id, " +
                "person.name AS person_name, " +
                "person.score AS person_score, " +
                "person.x AS person_x, " +
                "person.y AS person_y, " +
                "t.phone AS person_phone " +
                "FROM person " +
                "JOIN traveler t ON person.id = t.id " +
                "WHERE person.id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement preStatement = connection.prepareStatement(sql)
        ) {
            preStatement.setInt(1, id);
            try (ResultSet rs = preStatement.executeQuery()) {
                if (rs.next()) {
                    Traveler traveler = new Traveler(
                            rs.getString("person_id"),
                            rs.getString("person_name"),
                            rs.getString("person_phone"),
                            rs.getDouble("person_score"));
                    traveler.setX(rs.getInt("person_x"));
                    traveler.setY(rs.getInt("person_y"));

                    return traveler;
                }
            }
        }
        return null;
    }

    public void updateTraveler(Traveler traveler) throws SQLException {
        String personSQL = "UPDATE person " +
                "SET " +
                "name = ?, " +
                "x = ?, " +
                "y = ?, " +
                "score = ? " +
                "WHERE id = ?";
        String travelerSQL = "UPDATE traveler " +
                "SET " +
                "phone = ? " +
                "WHERE id = ?";

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement personStmt = connection.prepareStatement(personSQL);
                PreparedStatement travelerStmt = connection.prepareStatement(travelerSQL)
        ) {
            // Person
            personStmt.setString(1, traveler.getName());
            personStmt.setInt(2, traveler.getX());
            personStmt.setInt(3, traveler.getY());
            personStmt.setDouble(4, traveler.getScore());
            personStmt.setString(5, traveler.getId());
            personStmt.executeUpdate();

            // Traveler
            travelerStmt.setString(1, traveler.getPhone());
            travelerStmt.setString(2, traveler.getId());
            travelerStmt.executeUpdate();
        }
    }
}
