package ir.ardastudio.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String URL = "jdbc:sqlite:data/appdata.db";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }

    public static void initialize() throws SQLException {
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            String[] queries = {
                    // Car Table
                    "CREATE TABLE IF NOT EXISTS car (" +
                            "id TEXT PRIMARY KEY," +
                            "createdAt TEXT PRIMARY KEY," +
                            "updatedAt TEXT PRIMARY KEY," +
                            "name TEXT NOT NULL," +
                            "color TEXT NOT NULL," +
                            "licensePlate TEXT NOT NULL);",

                    // User Table
                    "CREATE TABLE IF NOT EXISTS user (" +
                            "id TEXT PRIMARY KEY," +
                            "createdAt TEXT PRIMARY KEY," +
                            "updatedAt TEXT PRIMARY KEY," +
                            "name TEXT NOT NULL," +
                            "phone TEXT NOT NULL," +
                            "x INTEGER NOT NULL," +
                            "y INTEGER NOT NULL," +
                            "score REAL NOT NULL);",

                    // Driver Table
                    "CREATE TABLE IF NOT EXISTS driver (" +
                            "id TEXT PRIMARY KEY," +
                            "createdAt TEXT PRIMARY KEY," +
                            "updatedAt TEXT PRIMARY KEY," +
                            "name TEXT NOT NULL," +
                            "phone TEXT NOT NULL," +
                            "x INTEGER NOT NULL," +
                            "y INTEGER NOT NULL," +
                            "score REAL NOT NULL," +
                            "car_id TEXT NOT NULL," +
                            "FOREIGN KEY(car_id) REFERENCES car(id));",

                    // Travel Table
                    "CREATE TABLE IF NOT EXISTS travel (" +
                            "id TEXT PRIMARY KEY," +
                            "createdAt TEXT PRIMARY KEY," +
                            "updatedAt TEXT PRIMARY KEY," +
                            "driver_id TEXT NOT NULL," +
                            "user_id TEXT NOT NULL," +
                            "x INTEGER NOT NULL," +
                            "y INTEGER NOT NULL," +
                            "distance REAL NOT NULL," +
                            "time INTEGER NOT NULL," +
                            "cost INTEGER NOT NULL," +
                            "status TEXT NOT NULL," +
                            "FOREIGN KEY(driver_id) REFERENCES driver(id)," +
                            "FOREIGN KEY(user_id) REFERENCES user(id));"
            };

            for (String query : queries) {
                stmt.execute(query);
            }
        }
    }
}
