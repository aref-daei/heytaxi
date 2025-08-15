package ir.ardastudio.service;

import ir.ardastudio.model.Car;
import ir.ardastudio.model.Driver;
import ir.ardastudio.repository.CarRepository;
import ir.ardastudio.repository.DriverRepository;
import ir.ardastudio.util.IdGenerator;

import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class DriverGeneratorService {
    private final CarRepository carRepo = new CarRepository();
    private final DriverRepository driverRepo = new DriverRepository();

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
            "Dena", "Soren", "Runna", "Quik", "Peugeot 405"
    };

    private static final String[] CAR_COLORS = {
            "Red", "Blue", "Green", "Yellow", "Orange",
            "Purple", "Brown", "Black", "White", "Gray"
    };

    public void generateRandomDrivers() {
        try {
            int count = ThreadLocalRandom.current().nextInt(10, 21);

            for (int i = 0; i < count; i++) {
                Car car = new Car(
                        IdGenerator.generate(),
                        randomCarModel(),
                        randomCarColor(),
                        generatePlate()
                );
                carRepo.addCar(car);

                double score = ThreadLocalRandom.current().nextDouble(4, 5);
                Driver driver = new Driver(
                        IdGenerator.generate(),
                        randomName(),
                        (int) (score * 100) / 100.0,
                        car
                );
                driver.setX(randomCoordinate());
                driver.setY(randomCoordinate());
                driverRepo.addDriver(driver);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching car and driver: " + e.getMessage());
        }
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
