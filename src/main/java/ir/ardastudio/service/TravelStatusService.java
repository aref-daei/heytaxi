package ir.ardastudio.service;

import ir.ardastudio.model.Travel;
import ir.ardastudio.model.Traveler;
import ir.ardastudio.shared.Screen;

import java.util.Scanner;

public class TravelStatusService {
    public void handleTravelStatus(Travel travel, Traveler traveler, Scanner input) {
        Screen.clear();
        System.out.println("..:: Current Travel Information ::..");
        System.out.println(travel);

        System.out.println("\nOptions:");
        System.out.println("1) Rate driver");
        System.out.println("2) Cancel travel");
        System.out.print("Choose (1/2/s): ");
        String opt = input.nextLine().toLowerCase();

        switch (opt) {
            case "1":
                System.out.printf("Rate driver %s (1-5):%n", travel.getDriver().getName());
                try {
                    double score = input.nextDouble();
                    input.nextLine();
                    travel.getDriver().setScore(
                            (travel.getDriver().getScore() + score) / 2);
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
                    traveler.setScore(traveler.getScore() - 0.17);
                    System.out.println("Travel canceled.");
                }
                break;
        }
    }
}
