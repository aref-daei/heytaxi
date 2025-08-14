package ir.ardastudio.service;

import ir.ardastudio.model.Traveler;
import ir.ardastudio.repository.TravelerRepository;
import ir.ardastudio.shared.*;
import ir.ardastudio.util.IdGenerator;

import java.sql.SQLException;
import java.util.Scanner;

public class AuthService {
    private final TravelerRepository travelerRepo = new TravelerRepository();
    private final SMSAuthentication auth = new SMSAuthentication("auth_attempts.csv");

    private Traveler traveler;

    public Traveler getTraveler() {
        return traveler;
    }

    public void signIn(Scanner input) {
        try {
            Screen.clear();
            String name = "", phone;

            while (true) {
                try {
                    System.out.println("Please enter your phone number:");
                    phone = Telephone.corrector(input.nextLine());
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                    continue;
                }

                System.out.printf("Please enter the code sent to your mobile phone.%n%s%n",
                        auth.sendAuthenticationSMS(phone));
                String code = input.nextLine();
                if (!auth.verifyCode(code, phone)) {
                    System.err.println("Invalid code! Please try again.");
                    continue;
                }

                for (Traveler user : travelerRepo.getAllTravelers()) {
                    if (phone.equals(user.getPhone())) {
                        traveler = new Traveler(user.getId(), user.getName(), user.getPhone());
                        break;
                    }
                }

                if (traveler == null) {
                    System.out.println("Code confirmed.\nPlease enter your full name:");
                    name = input.nextLine();
                }

                break;
            }

            if (traveler == null) {
                traveler = new Traveler(IdGenerator.generate(), name, phone);
                travelerRepo.addTraveler(traveler);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching travelers: " + e.getMessage());
        }
    }

    public void logOut() {
        traveler = null;
        Screen.clear();
        System.out.println("You have been logged out.");
    }
}
