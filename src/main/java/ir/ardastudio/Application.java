package ir.ardastudio;

import ir.ardastudio.service.CoreService;
import ir.ardastudio.service.DriverGeneratorService;
import ir.ardastudio.util.DBConnection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) {
        try {
            Path dataFolder = Paths.get("data");
            if (Files.notExists(dataFolder)) {
                Files.createDirectory(dataFolder);
            }

            DBConnection.initialize();

            new DriverGeneratorService().generateRandomDrivers();
            new CoreService().systemManager();
        } catch (SQLException e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error creating folder: " + e.getMessage());
        }
    }
}
