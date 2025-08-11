package ir.ardastudio.service;

import ir.ardastudio.model.Driver;
import ir.ardastudio.model.Travel;
import ir.ardastudio.model.Traveler;
import ir.ardastudio.shared.Screen;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TravelRequestService {
    private final DriverFinderService driverFinderService = new DriverFinderService();

    public Travel requestTravel(Traveler traveler, List<Driver> drivers, Scanner input) {
        Screen.clear();
        int[] destination = new int[2];

        System.out.println("Do you know your location? Y/n\nDefault: (0, 0)");
        String hasLocation = input.nextLine().toLowerCase();
        if (hasLocation.isEmpty() || hasLocation.charAt(0) == 'y') {
            while (true) {
                try {
                    System.out.println("Enter your location (e.g., 5 13):");
                    traveler.setX(input.nextInt());
                    traveler.setY(input.nextInt());
                    input.nextLine();
                    break;
                } catch (Exception e) {
                    input.nextLine();
                    System.err.println("Invalid location format.");
                }
            }
        }

        while (true) {
            try {
                System.out.println("Enter destination (e.g., 6 -2):");
                destination[0] = input.nextInt();
                destination[1] = input.nextInt();
                input.nextLine();

                if (destination[0] == traveler.getX() && destination[1] == traveler.getY()) {
                    System.err.println("Origin and destination cannot be the same.");
                } else {
                    break;
                }
            } catch (Exception e) {
                input.nextLine();
                System.err.println("Invalid destination format.");
            }
        }

        Screen.clear();
        System.out.println("Finding nearest driver...");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ignored) {}

        Driver driver = driverFinderService.findNearestDriver(traveler, drivers);
        Travel travel = new Travel(driver, traveler, destination);

        Screen.clear();
        System.out.println("..:: Travel Information ::..");
        System.out.println(travel);
        System.out.printf("Estimated time: %d minutes.%n", travel.getTime());

        driver.setX(destination[0]);
        driver.setY(destination[1]);

        return travel;
    }
}
