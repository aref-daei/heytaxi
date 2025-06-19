// Tick. Tock. Tick. Tock. A flawless execution.
// Copyright (c) Aref Daei

package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SystemManagement {
	private static List<Driver> drivers = generateRandomDrivers();
	private static List<Travel> travels = new ArrayList<>();
	private static Traveler session = new Traveler("Unknown");

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		// ** Menu **
		boolean menu = true;
		while (menu) {
			System.out.println("Welcome to HeyTaxi!");
			System.out.println("Reach your destination with one click!");
			System.out.println("©2025 Aref Daei");
			
			System.out.println();
			System.out.println("1. Sign in");
			System.out.println("2. History");
			System.out.println("3. Quit");
			System.out.println();
			
			System.out.print("Choose an option: ");
			String opt = input.nextLine();
			
			switch (opt.charAt(0)) {
			case '1': {
				// ** Sign in User **
				while (true) {
					System.out.println("Please enter your full name.");
					session.setName(input.nextLine());
					System.out.println("Are you really yourself? y/n");
					String isYourself = input.nextLine();

					if (isYourself.charAt(0) == 'y') {
						break;
					} else if (isYourself.charAt(0) == 'n') {
						System.out.printf("You are not %s. Please try again.%n", session.getName());
					} else {
						System.err.println("An unexpected value received. Answer the question again.");
					}
				}
				
				System.out.println("Do you know your location? y/n\nIf \"no\", your location (0, 0) will be saved.");
				String hasLocation = input.nextLine();
				
				if (hasLocation.charAt(0) == 'y') {
					System.out.println("Please enter your location as sample. ex: 5 13");
					try {
						session.setX(input.nextInt());
						session.setY(input.nextInt());
					} catch (Exception e) {
						System.err.println("An unexpected value received. Your location (0, 0) was saved.");
					}
				} else if ((hasLocation.charAt(0) == 'n')) {
				} else {
					System.err.println("An unexpected value received. Your location (0, 0) was saved.");
				}
				
				int[] destination = new int[2];
				
				System.out.println("Please enter your location destination as sample. ex: 6 -2");
				try {
					destination[0] = input.nextInt();
					destination[1] = input.nextInt();
				} catch (Exception e) {
					System.err.println("An unexpected value received. Your location destination (0, 0) was saved.");
					destination[0] = 0;
					destination[1] = 0;
				}
				
				if (destination[0] == session.getX() && destination[1] == session.getY()) {
					System.err.println("The origin is the same as the destination.\nPlease go through the steps and correct them.");
					break;
				}
				
				Travel travel = new Travel(findingNearestDriver(), session, destination);
				
				System.out.println("\n..:: Your Travel Information ::..");
				System.out.printf("%s%n%n", travel);
				
				travels.add(travel);
				
				break;
			}
			case '2': {
				// ** Travel History **
				if (travels.isEmpty()) {
					System.out.println("You did not have a travel.");
					break;
				}
				
				System.out.println("\n..:: History of Your Travels ::..");
				for (Travel travel : travels) {
					System.out.printf("%s%n%n", travel);
				}
				
				break;
			}
			case '3':
				// ** Quit **
				System.err.println("You exited");
				return;
			default:
				System.err.println("An unexpected value received.");
				break;
			}
			
			while (true) {
				System.out.println("Do you want to go back to the menu? y/n");
				String goToMenu = input.nextLine();
				
				if (goToMenu.charAt(0) == 'y') {
					menu = true;
					break;
				} else if (goToMenu.charAt(0) == 'n') {
					menu = false;
					break;
				} else {
					System.err.println("An unexpected value received. Answer the question again.");
				}
			}
			
			System.out.println();
		}
		
		input.close();
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
		
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		
		List<Driver> drivers = new ArrayList<>();
		
		for (int i = 0; i < (int)(Math.random()*(15-5+1)+5); i++) {
			StringBuilder carLP = new StringBuilder();
            for (int c = 0; c < 6; c++) {
                int index = (int) (Math.random() * characters.length());
                carLP.append(characters.charAt(index));
            }
            
			Driver driver = new Driver(
					String.format("%s %s",
							firstNames[(int)(Math.random()*firstNames.length)],
							lastNames[(int)(Math.random()*lastNames.length)]),
					Math.random()*(5-4)+4,
					new Car(carModels[(int)(Math.random()*carModels.length)],
							carColors[(int)(Math.random()*carColors.length)],
							carLP.toString()));
			
			driver.setX((int)(Math.random()*30) * (int)Math.pow(-1,(int)(Math.random()*2)));
			driver.setY((int)(Math.random()*30) * (int)Math.pow(-1,(int)(Math.random()*2)));
			
			drivers.add(driver);
		}
		
		return drivers;
	}
	
	public static Driver findingNearestDriver() {
		Driver nDriver = drivers.get(0);
		double distance = 100; // Max = 100
		for (Driver driver : drivers) {
			double temp = Math.pow(
					Math.pow(driver.getX()-session.getX(), 2) + Math.pow(driver.getY()-session.getY(), 2), 0.5);
			if (distance > temp) {
				distance = temp;
				nDriver  = driver;
			}
		}
		return nDriver;
	}

}
