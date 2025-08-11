package ir.ardastudio.service;

import ir.ardastudio.model.Driver;
import ir.ardastudio.model.Traveler;

import java.util.List;

public class DriverFinderService {
    private static final int MAX_ACTIVITY_RADIUS = 20;

    public Driver findNearestDriver(Traveler traveler, List<Driver> drivers) {
        if (drivers == null || drivers.isEmpty()) {
            throw new IllegalArgumentException("No drivers available");
        }

        Driver nearest = drivers.getFirst();
        double minDistance = MAX_ACTIVITY_RADIUS + 1;

        for (Driver driver : drivers) {
            double distance = calculateDistance(driver.getX(), driver.getY(), traveler.getX(), traveler.getY());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = driver;
            }
        }

        return nearest;
    }

    private double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
