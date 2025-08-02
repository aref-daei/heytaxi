package ir.ardastudio;

import ir.ardastudio.app.SystemManagement;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        SystemManagement.launch();
//        try {
//            SystemManagement.launch();
//        } catch (SQLException e) {
//            System.err.println("Database connection failed: " + e.getMessage());
//            System.exit(1);
//        } catch (Exception e) {
//            System.err.println("Application error: " + e.getMessage());
//            e.printStackTrace();
//            System.exit(1);
//        }
    }
}
