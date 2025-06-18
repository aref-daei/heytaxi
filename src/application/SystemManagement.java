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
			System.out.println("©2025");
			
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
				boolean loop = true;
				while (loop) {
					System.out.println("Please enter your full name.");
					session.setName(input.nextLine());
					System.out.println("Are you really yourself? y/n");
					String isYourself = input.nextLine();
					
					if (isYourself.charAt(0) != 'y') {
						System.out.printf("You are not %s. Please try again.%n", session.getName());
					} else {
						loop = false;
					}
				}
				
				System.out.println("Do you know your location? y/n\nIf \"no\", your location (0, 0) will be saved.");
				String hasLocation = input.nextLine();
				
				if (hasLocation.charAt(0) == 'y') {
					System.out.println("Please enter your location as sample. ex: 5 13");
					session.setX(input.nextInt());
					session.setY(input.nextInt());
				}		
				
				System.out.println("Please enter your location destination as sample. ex: 6 -2");
				int xDestination = input.nextInt();
				int yDestination = input.nextInt();
				
				
				Travel travel = new Travel(drivers.get((int)(Math.random()*(drivers.size()-1))), session, new int[]{xDestination, yDestination});
				
				System.out.println("\n..:: Your Travel Information ::..");
				System.out.printf("%s%n%n", travel);
				
				travels.add(travel);
				
				break;
			}
			case '2': {
				// ** Travel History **
				System.out.println("\n..:: History of Your Travels ::..");
				for (Travel travel : travels) {
					System.out.printf("%s%n%n", travel);
				}
				
				break;
			}
			case '3':
				// ** Quit **
				return;
			default:
				throw new IllegalArgumentException("Unexpected value: " + opt);
			}
			
			System.out.println("You want to go back to the menu. true or false ?");
			menu = input.nextBoolean();
			input.nextLine();
			System.out.println();
		}
		
		input.close();
		
//		int porpus = (int)(Math.random()*(500-5+1)+5) * (int)Math.pow(-1,(int)(Math.random()*2));
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
			
			driver.setX((int)(Math.random()*3) * (int)Math.pow(-1,(int)(Math.random()*2)));
			driver.setY((int)(Math.random()*3) * (int)Math.pow(-1,(int)(Math.random()*2)));
			
			drivers.add(driver);
		}
		
		return drivers;
	}

}
