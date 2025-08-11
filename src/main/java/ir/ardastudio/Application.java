package ir.ardastudio;

import ir.ardastudio.service.CoreService;
import ir.ardastudio.util.DBConnection;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        try {
            DBConnection.initialize();
            new CoreService().systemManager();
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }
}
