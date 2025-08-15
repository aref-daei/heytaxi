package ir.ardastudio.service;

import ir.ardastudio.repository.*;
import ir.ardastudio.model.*;
import ir.ardastudio.shared.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CoreService {
    private final TravelRepository travelRepo = new TravelRepository();
    private final AuthService authService = new AuthService();
    private final TravelRequestService travelRequestService = new TravelRequestService();
    private final TravelStatusService travelStatusService = new TravelStatusService();
    private final TravelHistoryService travelHistoryService = new TravelHistoryService();

    public void systemManager() {
        try (Scanner input = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                List<Travel> travels = null;
                if (authService.getTraveler() != null) {
                    travels = travelRepo.getAllTravels().stream()
                            .filter(t -> t.getTraveler().getId() == authService.getTraveler().getId())
                            .toList();
                }

                Screen.clear();
                System.out.println("      *** Welcome to HeyTaxi ***      ");
                System.out.println("Reach your destination with one click!");
                System.out.println("         (c) 2025 - Aref Daei         ");
                System.out.println();
                if (authService.getTraveler() == null) {
                    System.out.println("S) Sign in");
                } else {
                    System.out.println(
                            "T) Travel " +
                            (travels == null || travels.isEmpty() || !travels.getLast().getStatus().equals("start") ? "Request" : "Status")
                    );
                    System.out.println("H) History");
                    System.out.println("L) Log out");
                }
                System.out.println("Q) Quit");
                System.out.print("Choose an option: ");
                String opt = input.nextLine().toLowerCase();

                switch (opt) {
                    case "s":
                        authService.signIn(input);
                        break;
                    case "t":
                        if (travels == null || travels.isEmpty() || !travels.getLast().getStatus().equals("start")) {
                            travelRequestService.requestTravel(authService.getTraveler(), input);
                        } else {
                            travelStatusService.handleTravelStatus(authService.getTraveler(), input);
                        }
                        break;
                    case "h":
                        travelHistoryService.showHistory(authService.getTraveler());
                        break;
                    case "l":
                        authService.logOut();
                        break;
                    case "q":
                        running = false;
                        break;
                    default:
                        System.err.println("Invalid option.");
                }

                if (!opt.equals("q")) {
                    System.out.println("Press Enter to return to menu...");
                    input.nextLine();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching travels: " + e.getMessage());
        }
    }
}
