package ir.ardastudio.service;

import ir.ardastudio.model.*;
import ir.ardastudio.shared.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoreService {
    private final AuthService authService = new AuthService();
    private final TravelRequestService travelRequestService = new TravelRequestService();
    private final TravelStatusService travelStatusService = new TravelStatusService();
    private final TravelHistoryService travelHistoryService = new TravelHistoryService();
    private final List<Driver> drivers = new DriverGeneratorService().generateRandomDrivers();
    private final List<Travel> travels = new ArrayList<>();
    private Traveler session;

    public void systemManager() {
        try (Scanner input = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                Screen.clear();
                System.out.println("      *** Welcome to HeyTaxi ***      ");
                System.out.println("Reach your destination with one click!");
                System.out.println("         (c) 2025 - Aref Daei         ");
                System.out.println();
                if (session == null) {
                    System.out.println("S) Sign in");
                } else {
                    System.out.println(
                            "T) Travel " +
                            (travels.isEmpty() || !travels.getLast().getStatus().equals("start") ? "Request" : "Status")
                    );
                    System.out.println("H) History");
                    System.out.println("L) Log out");
                }
                System.out.println("Q) Quit");
                System.out.println();
                System.out.print("Choose an option: ");
                String opt = input.nextLine().toLowerCase();

                switch (opt) {
                    case "s":
                        session = authService.signIn(input);
                        break;
                    case "t":
                        if (travels.isEmpty() || !travels.getLast().getStatus().equals("start")) {
                            travels.add(travelRequestService.requestTravel(session, drivers, input));
                        } else {
                            travelStatusService.handleTravelStatus(travels.getLast(), session, input);
                        }
                        break;
                    case "h":
                        travelHistoryService.showHistory(travels);
                        break;
                    case "l":
                        authService.logOut();
                        session = null;
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
        }
    }

    public static void launch() {
        new CoreService().systemManager();
    }
}
