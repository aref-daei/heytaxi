package ir.ardastudio.service;

import ir.ardastudio.model.Driver;
import ir.ardastudio.model.User;
import ir.ardastudio.repository.DriverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class DriverFinderService {
    private static final Logger logger = LoggerFactory.getLogger(DriverFinderService.class);

    private final DriverRepository driverRepo = new DriverRepository();

    private static final int MAX_ACTIVITY_RADIUS = 20;

    public Driver findNearestDriver(User user) {
        try {
            List<Driver> drivers = driverRepo.getAllDrivers();

            if (drivers == null || drivers.isEmpty()) {
                throw new IllegalArgumentException("No drivers available");
            }

            Driver nearest = drivers.getFirst();
            double minDistance = MAX_ACTIVITY_RADIUS + 1;

            for (Driver driver : drivers) {
                double distance = calculateDistance(driver.getX(), driver.getY(), user.getX(), user.getY());
                if (distance < minDistance) {
                    minDistance = distance;
                    nearest = driver;
                }
            }

            return nearest;
        } catch (SQLException e) {
            logger.error("Error fetching drivers: ", e);
        }

        return null;
    }

    private static double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }
}
