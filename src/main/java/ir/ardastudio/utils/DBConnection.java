package ir.ardastudio.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:app.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initialize() {
        try (Connection connection = getConnection()) {
            // Car Table
            connection.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS car (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "model TEXT NOT NULL," +
                            "color TEXT NOT NULL," +
                            "licensePlate TEXT NOT NULL);"
            );

            // Personal Table
            connection.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS personal (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "name TEXT NOT NULL," +
                            "x INTEGER NOT NULL," +
                            "y INTEGER NOT NULL," +
                            "score REAL NOT NULL);"
            );

            // Traveler Table
            connection.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS traveler (" +
                            "id INTEGER PRIMARY KEY," +
                            "phone TEXT NOT NULL," +
                            "FOREIGN KEY(id) REFERENCES personal(id));"
            );

            // Driver Table
            connection.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS driver (" +
                            "id INTEGER PRIMARY KEY," +
                            "car_id INTEGER NOT NULL," +
                            "FOREIGN KEY(id) REFERENCES personal(id)" +
                            "FOREIGN KEY(car_id) REFERENCES car(id));"
            );

            // Travel Table
            connection.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS travel (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "driver_id INTEGER NOT NULL," +
                            "traveler_id INTEGER NOT NULL," +
                            "date TEXT NOT NULL," +
                            "dest_x INTEGER NOT NULL," +
                            "dest_y INTEGER NOT NULL," +
                            "distance REAL NOT NULL," +
                            "time INTEGER NOT NULL," +
                            "cost INTEGER NOT NULL," +
                            "status TEXT NOT NULL," +
                            "FOREIGN KEY(driver_id) REFERENCES driver(id)" +
                            "FOREIGN KEY(traveler_id) REFERENCES traveler(id));"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
