package ir.ardastudio.service;

import ir.ardastudio.model.Travel;
import ir.ardastudio.model.User;
import ir.ardastudio.repository.DriverRepository;
import ir.ardastudio.repository.TravelRepository;
import ir.ardastudio.repository.UserRepository;
import ir.ardastudio.shared.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Scanner;

public class TravelStatusService {
    private static final Logger logger = LoggerFactory.getLogger(TravelStatusService.class);

    private final TravelRepository travelRepo = new TravelRepository();
    private final DriverRepository driverRepo = new DriverRepository();
    private final UserRepository userRepo = new UserRepository();

    public void handleTravelStatus(User user, Scanner input) {
        try {
            Travel travel = travelRepo.getAllTravels().stream()
                        .filter(t -> t.getUser().getId().equals(user.getId()))
                        .toList().getFirst();

            Screen.clear();
            System.out.println("..:: Current Travel Information ::..");
            System.out.println(travel);

            System.out.println("\nOptions:");
            System.out.println("1) Rate driver");
            System.out.println("2) Cancel travel");
            System.out.print("Choose (1/2/S): ");
            String opt = input.nextLine().toLowerCase();

            switch (opt) {
                case "1":
                    System.out.printf("Rate driver %s (1-5):%n", travel.getDriver().getName());
                    try {
                        double score = input.nextDouble();
                        input.nextLine();
                        travel.getDriver().setScore(
                                (travel.getDriver().getScore() + score) / 2);
                        driverRepo.updateDriver(travel.getDriver());
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        input.nextLine();
                        System.out.println("Invalid score");
                    }
                    break;

                case "2":
                    System.out.println("Canceling will lower your score. Continue? y/N");
                    String confirm = input.nextLine().toLowerCase();
                    if (confirm.startsWith("y")) {
                        travel.setStatus("cancel");
                        travelRepo.updateTravel(travel);
                        user.setScore(Math.max(user.getScore() - 0.17, 0.0));
                        userRepo.updateUser(user);
                        System.out.println("Travel canceled");
                    }
                    break;
            }
        } catch (SQLException e) {
            logger.error("Error fetching travel status: ", e);
        }
    }
}
