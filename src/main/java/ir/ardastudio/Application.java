package ir.ardastudio;

import ir.ardastudio.service.CoreService;
import ir.ardastudio.util.DBConnection;

import java.sql.SQLException;

public class Application extends CoreService {
    public static void main(String[] args) {
        try {
            DBConnection.initialize();
            launch();
        } catch (SQLException e) {
            System.err.println("Error connecting Database: " + e.getMessage());
        }
    }
}
