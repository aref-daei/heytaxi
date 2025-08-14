package ir.ardastudio.service;

import ir.ardastudio.model.Traveler;
import ir.ardastudio.repository.TravelerRepository;
import ir.ardastudio.shared.*;
import ir.ardastudio.util.IdGenerator;

import java.sql.SQLException;
import java.util.Scanner;

public class AuthService {
    private final TravelerRepository travelerRepo = new TravelerRepository();
    private final SMSAuthentication auth = new SMSAuthentication("logs.csv");

    private Traveler traveler;

    public Traveler getTraveler() {
        return traveler;
    }

    public void signIn(Scanner input) {
        try {
            Screen.clear();
            String name, phone;

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

                System.out.println("Code confirmed.\nPlease enter your full name:");
                name = input.nextLine();

                System.out.println("Do you confirm the information entered is correct? Y/n");
                String confirm = input.nextLine().toLowerCase();
                if (confirm.isEmpty() || confirm.charAt(0) == 'y') {
                    break;
                }
            }

            traveler = new Traveler(IdGenerator.generate(), name, phone);
            travelerRepo.addTraveler(traveler);
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
