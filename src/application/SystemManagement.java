// Tick. Tock. Tick. Tock. A flawless execution.
// Copyright (c) Aref Daei

package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import module.Telephone;

public class SystemManagement {
	private static List<Travel> travels = new ArrayList<>();
	private static Traveler session;
	private static List<Driver> drivers;
	
	private static final int MAX_ACTIVITY_RADIUS = 20;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		boolean menu = true;
		while (menu) {
			// ** Menu **
			clearScreen();
			
			System.out.println("Welcome to HeyTaxi!");
			System.out.println("Reach your destination with one click!");
			System.out.println("©2025 Aref Daei");
			System.out.println();
			
			if (session == null) {
				System.out.println("S) Sign in");
			} else {

				System.out.printf("%s%n%s%n%s%n", "T) Travel Request", "H) History", "L) Log out");
			}
			System.out.println("Q) Quit");
			System.out.println();
			
			System.out.print("Choose an option: ");
			String opt = input.nextLine().toLowerCase();
			
			if (opt.isEmpty() || (opt.charAt(0) == 's' && session != null) ||
					((opt.charAt(0) == 't' || opt.charAt(0) == 'h' || opt.charAt(0) == 'l') && session == null)) {
				opt = " ";
			}
			
			switch (opt.charAt(0)) {
			case 's':
				// ** Sign in User **
				clearScreen();
				
				drivers = generateRandomDrivers();
				
				String name, phone, password;
				
				while (true) {
					try {
						System.out.println("Please enter your phone.");
						phone = Telephone.corrector(input.nextLine());
					} catch (IllegalArgumentException e) {
						System.err.println(e.getMessage());
						continue;
					}
					System.out.println("Please enter your full name.");
					name = input.nextLine();
					System.out.println("Please enter your password.");
					password = input.nextLine();
					
					System.out.println("Do you confirm that the information entered is correct? Y/n");
					String confirm = input.nextLine().toLowerCase();
					if (confirm.isEmpty() || confirm.charAt(0) == 'y') {
						break;
					} else {
						System.err.println("So, re-enter the information.");
					}
				}

				session = new Traveler(name, phone);
				
				backupManagement(session, password, 'w');
				
				break;

			case 't':
				System.out.println("Do you know your location? Y/n\nDefault location: (0, 0)");
				String hasLocation = input.nextLine().toLowerCase();
				if (hasLocation.isEmpty() || hasLocation.charAt(0) == 'y') {
					while (true) {
						System.out.println("Please enter your location. ex: 5 13");
						try {
							session.setX(input.nextInt());
							session.setY(input.nextInt());
							input.nextLine();
							break;
						} catch (Exception e) {
							System.err.println("An unexpected value received.");
						}
					}
				} else {
					System.err.println("So, the default location saved.");
				}
				
				int[] destination = new int[2];
				
				while (true) {
					System.out.println("Please enter your location destination. ex: 6 -2");
					try {
						destination[0] = input.nextInt();
						destination[1] = input.nextInt();
						input.nextLine();
						
						if (destination[0] == session.getX() && destination[1] == session.getY()) {
							System.err.println("The origin is the same as the destination.\nPlease go through the steps and correct them.");
						} else {
							break;
						}
					} catch (Exception e) {
						System.err.println("An unexpected value received.");
					}
				}
				
				// Finding The Nearest Driver
				clearScreen();
				System.out.println("Finding the nearest driver ...");
				Driver driver = findingNearestDriver();
				try {
					TimeUnit.SECONDS.sleep(7);
					System.out.println("The driver accepted your travel!");
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
				
				Travel travel = new Travel(driver, session, destination);
				
				clearScreen();
				System.out.println("..:: Your Travel Information ::..");
				System.out.printf("%s%n%n", travel);
				
				System.out.println("Do you want to cancel your travel? y/N");
				String cancel = input.nextLine().toLowerCase();
				if (cancel.isEmpty() || cancel.charAt(0) == 'n') {
					travels.add(travel);
					System.out.printf("Your travel will end in %d minutes. Good luck!%n", travel.getTime());
				} else {
					System.out.println("Your travel has been canceled successfully.");
				}
				
				driver.setX(destination[0]);
				driver.setY(destination[1]);
				
				break;

			case 'h':
				// ** Travel History **
				clearScreen();
				
				if (travels.isEmpty()) {
					System.out.println("You did not have a travel.");
					break;
				}
				
				System.out.println("..:: History of Your Travels ::..");
				for (Travel t : travels) {
					System.out.printf("%s%n%n", t);
				}
				
				break;
			
			case 'l':
				// ** Log out **
				clearScreen();
				
				session = null;
				break;

			case 'q':
				// ** Quit **
				System.err.println("You exited");
				return;
				
			default:
				System.err.println("An unexpected value received.");
				break;
			}

			while (opt.charAt(0) != 's') {
				System.out.println("Do you want to go back to the menu? Y/n");
				String goToMenu = input.nextLine().toLowerCase();
				
				if (goToMenu.isEmpty() || goToMenu.charAt(0) == 'y') {
					break;
				} else {
					System.err.println("So, you exited.");
					menu = false;
					break;
				}
			}
		}
		
		input.close();
	}
	
	public static void clearScreen() {
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
	
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
		
		for (int i = 0; i < (int)(Math.random()*(15-5+1)+5); i++) {
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
	
	public static Driver findingNearestDriver() {
		Driver nearestDriver = drivers.get(0);
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
	
	public static boolean backupManagement(Traveler user, String pass, char regex) {
		String filePath = "sessions.bak";
		
		switch (regex) {
		case 'r':
			try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String line;
				while ((line = reader.readLine()) != null) {
					if (line.contains(user.getPhone()))
						return true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		
		case 'w':
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
				writer.write(user.getPhone() + ", " + pass);
				writer.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
		
		return false;
	}

}
