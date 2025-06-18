package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Travel {
	private Driver driver;
	private Traveler traveler;
	private String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	private int[] destination;
	private double distance; // 1km scale
	private int time; // Min
	private int cost; // Toman
	
	public Travel(Driver driver, Traveler traveler, int[] destination) {
		this.driver = driver;
		this.traveler = traveler;
		this.destination = destination;
		
		this.distance = calculateDistance();
		this.time = (int) Math.round(distance * 2.5);
		this.cost = (int) Math.round(distance * 10_000);
	}
	
	public Driver getDriver() {
		return driver;
	}
	
	public void setDriver(Driver driver) {
		this.driver = driver;
		
		this.distance = calculateDistance();
		this.time = (int) Math.round(distance * 2.5);
		this.cost = (int) Math.round(distance * 10_000);
	}

	public Traveler getTraveler() {
		return traveler;
	}

	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
		
		this.distance = calculateDistance();
		this.time = (int) Math.round(distance * 2.5);
		this.cost = (int) Math.round(distance * 10_000);
	}

	public int[] getDestination() {
		return destination;
	}

	public void setDestination(int[] destination) {
		this.destination = destination;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public double calculateDistance() {
		distance = Math.pow(Math.pow(destination[0]-traveler.getX(), 2) + Math.pow(destination[1]-traveler.getY(), 2), 0.5);
		if (distance == 0.0) {
			throw new IllegalArgumentException("Distance equals zero");
		}
		return distance;
	}
	
	@Override
	public String toString() {
		return String.format("%s%n%s%nDate: %s%nDistance: %.2fKM%nTime: %dmin%nCost: %d",
				getTraveler(), getDriver(),
				date, getDistance(), getTime(), getCost());
	}
}
