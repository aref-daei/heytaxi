package ir.ardastudio.service;

import ir.ardastudio.repository.*;
import ir.ardastudio.model.*;
import ir.ardastudio.shared.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CoreService {
    private static final Logger logger = LoggerFactory.getLogger(CoreService.class);

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
                            .filter(t -> t.getTraveler().getId().equals(authService.getTraveler().getId()))
                            .toList();
                }

                Screen.clear();
                System.out.printf("%s%n%s%n%s%n%s%n%s%n",
                        "   __ __           ______            _ ",
                        "  / // /___  __ __/_  __/___ ___ __ (_)",
                        " / _  // -_)/ // / / /  / _ `/\\ \\ // / ",
                        "/_//_/ \\__/ \\_, / /_/   \\_,_//_\\_\\/_/  ",
                        "           /___/                       ");
                if (authService.getTraveler() != null) {
                    System.out.println("\nUser Information:");
                    System.out.printf("  Name : %s%n  Phone: %s%n  Score: %s%n",
                            authService.getTraveler().getName(),
                            authService.getTraveler().getPhone(),
                            authService.getTraveler().getScore());
                }
                System.out.println("\nMenu:");
                if (authService.getTraveler() == null) {
                    System.out.println("  S) Sign in");
                } else {
                    System.out.println(
                            "  T) Travel " +
                            (travels == null || travels.isEmpty() || !travels.getLast().getStatus().equals("start") ? "Request" : "Status")
                    );
                    System.out.println("  H) History");
                    System.out.println("  L) Log out");
                }
                System.out.println("  Q) Quit");
                System.out.print("\nChoose an option: ");
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
            logger.error("Error fetching travels: ", e);
        }
    }
}
