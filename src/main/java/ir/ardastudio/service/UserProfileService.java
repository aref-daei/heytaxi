package ir.ardastudio.service;

import ir.ardastudio.model.User;
import ir.ardastudio.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class UserProfileService {
    private static final Logger logger = LoggerFactory.getLogger(UserProfileService.class);

    private final UserRepository userRepo = new UserRepository();

    public void updateProfile(User user, Scanner input) {
    }
}
