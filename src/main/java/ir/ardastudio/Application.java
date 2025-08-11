package ir.ardastudio;

import ir.ardastudio.app.SystemManagement;
import ir.ardastudio.utils.DBConnection;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        try {
            DBConnection.initialize();
        } catch (SQLException e) {
            System.err.println("Error connecting Database: " + e.getMessage());
            return;
        }

        SystemManagement.launch();
    }
}
