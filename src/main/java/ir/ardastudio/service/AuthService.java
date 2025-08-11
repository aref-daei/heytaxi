package ir.ardastudio.service;

import ir.ardastudio.model.Traveler;
import ir.ardastudio.shared.SMSAuthentication;
import ir.ardastudio.shared.Screen;
import ir.ardastudio.shared.Telephone;

import java.util.Scanner;

public class AuthService {
    public Traveler signIn(Scanner input) {
        Screen.clear();
        SMSAuthentication auth = new SMSAuthentication("logs.csv");
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

        return new Traveler(name, phone);
    }

    public void logOut() {
        Screen.clear();
        System.out.println("You have been logged out.");
    }
}
