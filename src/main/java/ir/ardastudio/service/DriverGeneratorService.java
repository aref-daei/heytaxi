package ir.ardastudio.service;

import ir.ardastudio.model.Car;
import ir.ardastudio.model.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DriverGeneratorService {
    private static final int MAX_ACTIVITY_RADIUS = 20;

    private static final String[] FIRST_NAMES = {
            "Ali", "Reza", "Amir", "Hossein", "Mehdi",
            "Sajjad", "Mohammad", "Ramin", "Kasra", "Parsa",
            "Fatemeh", "Zahra", "Maryam", "Sara", "Nazanin",
            "Parisa", "Mahsa", "Hanieh", "Sepideh", "Roya"
    };

    private static final String[] LAST_NAMES = {
            "Ahmadi", "Kazemi", "Karimi", "Rezaei", "Hosseini",
            "Abbasi", "Mohammadi", "Alavi", "Sadr", "Mirzaei"
    };

    private static final String[] CAR_MODELS = {
            "Tondar 90", "Samand", "Pride", "Peugeot 206", "Pars",
            "Dena", "Soren", "Runna", "Khyber", "Arisan"
    };

    private static final String[] CAR_COLORS = {
            "Red", "Blue", "Green", "Yellow", "Orange",
            "Purple", "Brown", "Black", "White", "Gray"
    };

    public List<Driver> generateRandomDrivers() {
        List<Driver> drivers = new ArrayList<>();
        int count = ThreadLocalRandom.current().nextInt(10, 21);

        for (int i = 0; i < count; i++) {
            String plate = generatePlate();
            Driver driver = new Driver(
                    randomName(),
                    ThreadLocalRandom.current().nextDouble(4, 5),
                    new Car(randomCarModel(), randomCarColor(), plate)
            );
            driver.setX(randomCoordinate());
            driver.setY(randomCoordinate());

            drivers.add(driver);
        }
        return drivers;
    }

    private String randomName() {
        return FIRST_NAMES[random(FIRST_NAMES.length)] + " " +
                LAST_NAMES[random(LAST_NAMES.length)];
    }

    private String randomCarModel() {
        return CAR_MODELS[random(CAR_MODELS.length)];
    }

    private String randomCarColor() {
        return CAR_COLORS[random(CAR_COLORS.length)];
    }

    private String generatePlate() {
        String numbers = "123456789";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder plate = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            if (i == 2) {
                plate.append(characters.charAt(random(characters.length())));
            } else {
                plate.append(numbers.charAt(random(numbers.length())));
            }
        }
        return plate.toString();
    }

    private int randomCoordinate() {
        return ThreadLocalRandom.current().nextInt(0, MAX_ACTIVITY_RADIUS + 1) *
                (ThreadLocalRandom.current().nextBoolean() ? 1 : -1);
    }

    private int random(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }
}
