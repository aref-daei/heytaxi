package ir.ardastudio.service;

import ir.ardastudio.model.Driver;
import ir.ardastudio.model.Travel;
import ir.ardastudio.model.Traveler;
import ir.ardastudio.repository.DriverRepository;
import ir.ardastudio.repository.TravelRepository;
import ir.ardastudio.shared.Screen;
import ir.ardastudio.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class TravelRequestService {
    private static final Logger logger = LoggerFactory.getLogger(TravelRequestService.class);

    private final TravelRepository travelRepo = new TravelRepository();
    private final DriverRepository driverRepo = new DriverRepository();
    private final DriverFinderService driverFinderService = new DriverFinderService();

    public void requestTravel(Traveler traveler, Scanner input) {
        try {
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
                        System.out.println("Invalid location format");
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
                        System.out.println("Origin and destination cannot be the same");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    input.nextLine();
                    System.out.println("Invalid destination format");
                }
            }

            Screen.clear();
            System.out.println("Finding nearest driver...");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException ignored) {}

            try {
                Driver driver = driverFinderService.findNearestDriver(traveler);

                Travel travel = new Travel(IdGenerator.generate(), driver, traveler, destination);
                travelRepo.addTravel(travel);

                Screen.clear();
                System.out.println("..:: Travel Information ::..");
                System.out.println(travel);
                System.out.printf("Estimated time: %d minutes%n", travel.getTime());

                driver.setX(destination[0]);
                driver.setY(destination[1]);
                driverRepo.updateDriver(driver);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            logger.error("Error fetching travel: ", e);
        }
    }
}
