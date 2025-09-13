package ir.ardastudio.service;

import ir.ardastudio.model.User;
import ir.ardastudio.repository.UserRepository;
import ir.ardastudio.shared.SMSAuthentication;
import ir.ardastudio.shared.Screen;
import ir.ardastudio.shared.Telephone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserProfileService {
    private static final Logger logger = LoggerFactory.getLogger(UserProfileService.class);

    private final UserRepository userRepo = new UserRepository();
    private final SMSAuthentication auth = new SMSAuthentication(
            String.format("data/auth_logs_%s.csv",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));


    public void updateProfile(User user, Scanner input) {
        try {
            Screen.clear();
            System.out.println("Your Profile:");
            System.out.printf("  Name : %s%n  Phone: %s%n",
                    user.getName(),
                    user.getPhone());

            System.out.println("\nOptions:");
            System.out.println("1) Change your name");
            System.out.println("2) Change your phone number");
            System.out.print("Enter your choice (1/2/S): ");
            String opt = input.nextLine().toLowerCase();

            switch (opt) {
                case "1":
                    System.out.println("Please enter your full name:");
                    user.setName(input.nextLine());
                    break;

                case "2":
                    String phone;
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

                        boolean exists = false;
                        for (User u : userRepo.getAllUsers()) {
                            if (phone.equals(u.getPhone())) {
                                exists = true;
                                break;
                            }
                        }

                        if (exists) {
                            System.out.println("This phone number is already in use");
                        } else {
                            System.out.println("Code confirmed");
                            user.setPhone(phone);
                        }

                        break;
                    }
                    break;
            }

            userRepo.updateUser(user);
        } catch (SQLException e) {
            logger.error("Error fetching user: ", e);
        }
    }
}
