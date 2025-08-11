package ir.ardastudio;

import ir.ardastudio.services.CoreService;
import ir.ardastudio.utils.DBConnection;

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
