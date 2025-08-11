package ir.ardastudio.services;

import ir.ardastudio.app.SystemManagement;
import ir.ardastudio.models.Driver;
import ir.ardastudio.models.Travel;
import ir.ardastudio.models.Traveler;
import ir.ardastudio.shared.SMSAuthentication;
import ir.ardastudio.shared.Screen;
import ir.ardastudio.shared.Telephone;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CoreService {

    // Instance variable
    private final List<Driver> drivers = DriverService.generateRandomDrivers(); // Auto generation of drivers
    private final List<Travel> travels = new ArrayList<>(); // Travel history
    private Traveler session; // Traveler (user) session

    // Application execution method
    public void systemManager() {
        Scanner input = new Scanner(System.in);
        SMSAuthentication auth = new SMSAuthentication("logs.csv");

        boolean menu = true;
        while (menu) {
            // *** Menu ***
            Screen.clear(); // Clear terminal screen

            System.out.println("      *** Welcome to HeyTaxi ***      ");
            System.out.println("Reach your destination with one click!");
            System.out.println("         (c) 2025 - Aref Daei         ");
            System.out.println();

            if (session == null) {
                System.out.println("S) Sign in");
            } else {
                System.out.printf("%s%n%s%n%s%n",
                        "T) Travel " + (travels.isEmpty() || !travels.getLast().getStatus().equals("start") ? "Request" : "Status"),
                        "H) History", "L) Log out");
            }
            System.out.println("Q) Quit");
            System.out.println();

            System.out.print("Choose an option: ");
            String opt = input.nextLine().toLowerCase();

            if (opt.isEmpty() || (opt.charAt(0) == 's' && session != null) ||
                    ((opt.charAt(0) == 't' || opt.charAt(0) == 'h' || opt.charAt(0) == 'l') && session == null)) {
                opt = " ";
            }

            switch (opt.charAt(0)) {
                case 's':
                    // *** Sign in User ***
                    Screen.clear();

                    // Get traveler (user) information
                    String name, phone;
                    while (true) {
                        try {
                            System.out.println("Please enter your phone number.");
                            phone = input.nextLine();
                            if (phone.isEmpty()) {
                                phone = " ";
                            }
                            phone = Telephone.corrector(phone);
                        } catch (IllegalArgumentException e) {
                            System.err.println(e.getMessage());
                            continue;
                        }

                        System.out.printf("Please enter the code sent to your mobile phone.%n%s%n",
                                auth.sendAuthenticationSMS(phone));
                        String code = input.nextLine();
                        if (!auth.verifyCode(code, phone)) {
                            System.err.println("Invalid code! Please re-enter your phone number to get a new one.");
                            continue;
                        }

                        System.out.println("Code confirmed.\nPlease enter your full name.");
                        name = input.nextLine();

                        System.out.println("Do you confirm that the information entered is correct? Y/n");
                        String confirm = input.nextLine().toLowerCase();
                        if (confirm.isEmpty() || confirm.charAt(0) == 'y') {
                            break;
                        } else {
                            System.err.println("So, re-enter the information.");
                        }
                    }

                    // Create a Traveler object and store its reference in the session
                    session = new Traveler(name, phone);

                    break;

                case 't':
                    // *** Travel Request/Status ***
                    Screen.clear();

                    // If not traveling and otherwise
                    if (travels.isEmpty() || !travels.getLast().getStatus().equals("start")) {
                        // Get travel information
                        System.out.println("Do you know your location? Y/n\nDefault location: (0, 0)");
                        String hasLocation = input.nextLine().toLowerCase();
                        if (hasLocation.isEmpty() || hasLocation.charAt(0) == 'y') {
                            while (true) {
                                System.out.println("Please enter your location. ex: 5 13");
                                try {
                                    session.setX(input.nextInt());
                                    session.setY(input.nextInt());
                                    input.nextLine();
                                    break;
                                } catch (Exception e) {
                                    input.nextLine();
                                    System.err.println("An unexpected value received.");
                                }
                            }
                        } else {
                            System.err.println("So, the default location saved.");
                        }

                        int[] destination = new int[2];
                        while (true) {
                            System.out.println("Please enter your location destination. ex: 6 -2");
                            try {
                                destination[0] = input.nextInt();
                                destination[1] = input.nextInt();
                                input.nextLine();

                                if (destination[0] == session.getX() && destination[1] == session.getY()) {
                                    System.err.println("The origin is the same as the destination.\n"
                                            + "Please go through the steps and correct them.");
                                } else {
                                    break;
                                }
                            } catch (Exception e) {
                                input.nextLine();
                                System.err.println("An unexpected value received.");
                            }
                        }

                        // Finding nearest driver
                        Screen.clear();

                        System.out.println("Finding the nearest driver ...");
                        try {
                            TimeUnit.SECONDS.sleep(7);
                            System.out.println("The driver accepted your travel!");
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                        }
                        Driver driver = DriverService.findingNearestDriver(session, drivers);

                        // Create a travel
                        Travel travel = new Travel(driver, session, destination);

                        // Show travel information and add to history
                        Screen.clear();

                        System.out.println("..:: Your Travel Information ::..");
                        System.out.printf("%s%n%n", travel);
                        travels.add(travel);
                        System.out.printf("Your travel will end in %d minutes. Good luck!%n", travel.getTime());

                        // Setting driver to destination coordinates
                        driver.setX(destination[0]);
                        driver.setY(destination[1]);
                    } else {
                        // Show current travel information and options
                        System.out.println("..:: Your Current Travel Information ::..");
                        System.out.printf("%s%n%n", travels.getLast());

                        System.out.printf("%s%n%s%n%s%n%n",
                                "Options:",
                                "Opt 1: Rating the driver",
                                "Opt 2: Cancel the travel");

                        System.out.print("Choose from above options by number or skip [1/2/s] (s): ");
                        String cancel = input.nextLine().toLowerCase();
                        if (!cancel.isEmpty()) {
                            if (cancel.charAt(0) == '1') {
                                System.out.printf("Your travel driver: %s%n%s%n",
                                        travels.getLast().getDriver().getName(),
                                        "From 1 to 5, how much would you rate the driver and the car?");

                                while (true) {
                                    try {
                                        double score = input.nextDouble();
                                        travels.getLast().getDriver().setScore(
                                                (travels.getLast().getDriver().getScore() + score) / 2);

                                        input.nextLine();
                                        break;
                                    } catch (Exception e) {
                                        input.nextLine();
                                        System.err.println(e.getMessage() + " Please try again.");
                                    }
                                }

                            } else if (cancel.charAt(0) == '2') {
                                System.out.println("Canceling this travel will lower your score. Are you sure you want to continue? y/N");
                                String confirm = input.nextLine().toLowerCase();
                                if (!confirm.isEmpty() && confirm.charAt(0) == 'y') {
                                    travels.getLast().setStatus("cancel");
                                    session.setScore(session.getScore() - 0.17);
                                    System.out.println("Your travel has been canceled successfully.");
                                }
                            }
                        }

                    }

                    break;

                case 'h':
                    // *** Travel History ***
                    Screen.clear();

                    if (travels.isEmpty()) {
                        System.out.println("You did not have a travel.");
                        break;
                    }

                    System.out.println("..:: History of Your Travels ::..");
                    for (Travel t : travels) {
                        System.out.printf("%s%n%n", t);
                    }

                    break;

                case 'l':
                    // *** Log out ***
                    Screen.clear();

                    session = null;

                    opt = "s for log out";

                    break;

                case 'q':
                    // *** Quit ***
                    System.err.println("You exited");
                    return;

                default:
                    System.err.println("An unexpected value received.");
                    break;
            }

            if (opt.charAt(0) != 's') {
                System.out.println("Do you want to go back to the menu? Y/n");
                String goToMenu = input.nextLine().toLowerCase();
                if (!goToMenu.isEmpty() && goToMenu.charAt(0) != 'y') {
                    System.err.println("So, you exited.");
                    menu = false;
                }
            }
        }

        input.close();
    }

    public static void launch() {
        new SystemManagement().systemManager();
    }

}
