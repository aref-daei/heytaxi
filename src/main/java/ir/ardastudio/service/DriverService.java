package ir.ardastudio.service;

import ir.ardastudio.model.Car;
import ir.ardastudio.model.Driver;
import ir.ardastudio.model.Traveler;

import java.util.ArrayList;
import java.util.List;

public class DriverService {
    private static final int MAX_ACTIVITY_RADIUS = 20; // Max radius of app service activity

    // This method for automatic finding nearest driver
    public static Driver findingNearestDriver(Traveler session, List<Driver> drivers) {
        Driver nearestDriver = drivers.getFirst();
        double distance = MAX_ACTIVITY_RADIUS + 1;

        for (Driver driver : drivers) {
            double temp = Math.pow(
                    Math.pow(driver.getX()-session.getX(), 2) + Math.pow(driver.getY()-session.getY(), 2), 0.5);
            if (distance > temp) {
                distance = temp;
                nearestDriver  = driver;
            }
        }

        return nearestDriver;
    }

    // This method for automatic random generation of drivers
    public static List<Driver> generateRandomDrivers() {
        String[] firstNames = {
                "Ali", "Reza", "Amir", "Hossein", "Mehdi",
                "Sajjad", "Mohammad", "Ramin", "Kasra", "Parsa",
                "Fatemeh", "Zahra", "Maryam", "Sara", "Nazanin",
                "Parisa", "Mahsa", "Hanieh", "Sepideh", "Roya"};

        String[] lastNames = {
                "Ahmadi", "Kazemi", "Karimi", "Rezaei", "Hosseini",
                "Abbasi", "Mohammadi", "Alavi", "Sadr", "Mirzaei"};

        String[] carModels = {
                "Tondar 90", "Samand", "Pride", "Peugeot 206", "Pars",
                "Dena", "Soren", "Runna", "Khyber", "Arisan"};

        String[] carColors = {
                "Red", "Blue", "Green", "Yellow", "Orange",
                "Purple", "Brown", "Black", "White", "Gray"};

        String numbers = "123456789";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        List<Driver> drivers = new ArrayList<>();

        // Random generation of drivers
        for (int i = 0; i < (int)(Math.random()*(20-10+1)+10); i++) {
            // Random generation of car license plate
            StringBuilder carLP = new StringBuilder();
            for (int c = 0; c < 6; c++) {
                int index = (int) (Math.random() * numbers.length());
                if (c == 2) {
                    index = (int) (Math.random() * characters.length());
                    carLP.append(characters.charAt(index));
                } else {
                    carLP.append(numbers.charAt(index));
                }
            }

            // Create a random driver
            Driver driver = new Driver(
                    String.format("%s %s",
                            firstNames[(int)(Math.random()*firstNames.length)],
                            lastNames[(int)(Math.random()*lastNames.length)]),
                    Math.random()*(5-4)+4,
                    new Car(carModels[(int)(Math.random()*carModels.length)],
                            carColors[(int)(Math.random()*carColors.length)],
                            carLP.toString()));

            driver.setX((int)(Math.random()*MAX_ACTIVITY_RADIUS) * (int)Math.pow(-1,(int)(Math.random()*2)));
            driver.setY((int)(Math.random()*MAX_ACTIVITY_RADIUS) * (int)Math.pow(-1,(int)(Math.random()*2)));

            drivers.add(driver);
        }

        return drivers;
    }
}
