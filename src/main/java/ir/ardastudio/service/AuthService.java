package ir.ardastudio.service;

import ir.ardastudio.model.Traveler;
import ir.ardastudio.repository.TravelerRepository;
import ir.ardastudio.shared.*;
import ir.ardastudio.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final TravelerRepository travelerRepo = new TravelerRepository();
    private final SMSAuthentication auth = new SMSAuthentication(
            String.format("data/auth_logs_%s.csv",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));

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
                    phone = input.nextLine();
                    if (phone.isEmpty()) {
                        System.out.println("Phone number is empty");
                        continue;
                    }
                    phone = Telephone.corrector(phone);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                System.out.printf("Please enter the code sent to your mobile phone%n%s%n",
                        auth.sendAuthenticationSMS(phone));
                String code = input.nextLine();
                if (!auth.verifyCode(code, phone)) {
                    System.out.println("Invalid code! Please try again");
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
            logger.error("Error fetching travelers: ", e);
        }
    }

    public void logOut() {
        traveler = null;
        Screen.clear();
        System.out.println("You have been logged out");
    }

    public void update() {
        try {
            traveler = travelerRepo.getTravelerById(traveler.getId());
        } catch (SQLException e) {
            logger.error("Error fetching traveler: ", e);
        }
    }
}
