package ir.ardastudio;

import ir.ardastudio.repository.DriverRepository;
import ir.ardastudio.service.CoreService;
import ir.ardastudio.service.DriverGeneratorService;
import ir.ardastudio.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        try {
            Path dataFolder = Paths.get("data");
            if (Files.notExists(dataFolder)) {
                Files.createDirectory(dataFolder);
            }

            DBConnection.initialize();

            if (new DriverRepository().getAllDrivers().isEmpty()) {
                new DriverGeneratorService().generateRandomDrivers();
            }

            new CoreService().systemManager();
        } catch (SQLException e) {
            logger.error("Database initialization failed: ", e);

        } catch (IOException e) {
            logger.error("Error creating folder: ", e);
        }
    }
}
