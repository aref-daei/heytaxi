package ir.ardastudio.service;

import ir.ardastudio.model.Travel;
import ir.ardastudio.model.Traveler;
import ir.ardastudio.repository.DriverRepository;
import ir.ardastudio.repository.TravelRepository;
import ir.ardastudio.repository.TravelerRepository;
import ir.ardastudio.shared.Screen;

import java.sql.SQLException;
import java.util.Scanner;

public class TravelStatusService {
    private final TravelRepository travelRepo = new TravelRepository();
    private final DriverRepository driverRepo = new DriverRepository();
    private final TravelerRepository travelerRepo = new TravelerRepository();

    public void handleTravelStatus(Traveler traveler, Scanner input) {
        try {
            Travel travel = travelRepo.getAllTravels().getLast();

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
                        System.err.println(e.getMessage());
                    } catch (Exception e) {
                        input.nextLine();
                        System.err.println("Invalid score.");
                    }
                    break;

                case "2":
                    System.out.println("Canceling will lower your score. Continue? y/N");
                    String confirm = input.nextLine().toLowerCase();
                    if (confirm.startsWith("y")) {
                        travel.setStatus("cancel");
                        travelRepo.updateTravel(travel);
                        traveler.setScore(Math.max(traveler.getScore() - 0.17, 0.0));
                        travelerRepo.updateTraveler(traveler);
                        System.out.println("Travel canceled.");
                    }
                    break;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching travel status: " + e.getMessage());
        }
    }
}
