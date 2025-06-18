package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SystemManagement {
	private static List<Driver> drivers = randomDriverGenerator();

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please entire your name:");
		String name = input.nextLine();
		System.out.println("Please entire your location:");
		int xTraveler = input.nextInt();
		int yTraveler = input.nextInt();
		System.out.println("Please entire your location destination:");
		int xDestination = input.nextInt();
		int yDestination = input.nextInt();
		input.nextLine();

		input.close();
		
		Traveler traveler = new Traveler(name);
		traveler.setX(xTraveler);
		traveler.setY(yTraveler);
		
		Travel travel = new Travel(drivers.get(2), traveler, new int[]{xDestination, yDestination});
		System.out.println(travel.getTime());
		
//		int porpus = (int)(Math.random()*(500-5+1)+5) * (int)Math.pow(-1,(int)(Math.random()*2));
	}
	
	public static List<Driver> randomDriverGenerator() {
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
		
		String[] carLP = {
				"RN6ZJR", "73V53I", "4COTB5", "3BQS60", "BE381G",
				"P5DSZZ", "YTHFGB", "U6YP1G", "Z8JP7E", "LDL7F8"};
		
		List<Driver> drivers = new ArrayList<>();
		
		for (int i = 0; i < (int)(Math.random()*(15-5+1)+5); i++) {
			Driver driver = new Driver(
					String.format("%s %s",
							firstNames[(int)(Math.random()*firstNames.length)],
							lastNames[(int)(Math.random()*lastNames.length)]),
					Math.random()*(5-4)+4,
					new Car(carModels[(int)(Math.random()*carModels.length)],
							carColors[(int)(Math.random()*carColors.length)],
							carLP[(int)(Math.random()*carLP.length)]));
			
			driver.setX((int)(Math.random()*3) * (int)Math.pow(-1,(int)(Math.random()*2)));
			driver.setY((int)(Math.random()*3) * (int)Math.pow(-1,(int)(Math.random()*2)));
			
			drivers.add(driver);
		}
		
		return drivers;
	}

}
