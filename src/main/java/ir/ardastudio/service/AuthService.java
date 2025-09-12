package ir.ardastudio.service;

import ir.ardastudio.model.User;
import ir.ardastudio.repository.UserRepository;
import ir.ardastudio.shared.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepo = new UserRepository();
    private final SMSAuthentication auth = new SMSAuthentication(
            String.format("data/auth_logs_%s.csv",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));

    private User user;

    public User getUser() {
        return user;
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

                System.out.printf("Please enter the code sent to your phone number:%n%s%n",
                        auth.sendAuthenticationSMS(phone));
                String code = input.nextLine();
                if (!auth.verifyCode(code, phone)) {
                    System.out.println("Invalid code! Please try again");
                    continue;
                }

                for (User u : userRepo.getAllUsers()) {
                    if (phone.equals(u.getPhone())) {
                        user = u;
                        break;
                    }
                }

                if (user == null) {
                    System.out.println("Code confirmed\nPlease enter your full name:");
                    name = input.nextLine();
                }

                break;
            }

            if (user == null) {
                user = new User(name, phone);
                userRepo.addUser(user);
            }
        } catch (SQLException e) {
            logger.error("Error fetching users: ", e);
        }
    }

    public void logOut() {
        user = null;
        Screen.clear();
        System.out.println("You have been logged out");
    }

    public void update() {
        try {
            user = userRepo.getUserById(user.getId());
        } catch (SQLException e) {
            logger.error("Error fetching user: ", e);
        }
    }
}
