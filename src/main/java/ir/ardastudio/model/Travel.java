package ir.ardastudio.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Travel {

	// Instance variable
	private int id;
	private Driver driver; // Driver object
	private Traveler traveler; // Traveler object
	private String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm")); // ex: 2025/03/25 - 11:56
	private int[] destination; // [x, y] destination coordinates
	private double distance; // 1Km scale
	private int time; // Minutes
	private int cost; // Toman
	private String status; // start/end/cancel

	// Constructor
	public Travel(Driver driver, Traveler traveler, int[] destination) {
		this.driver = driver;
		this.traveler = traveler;
		this.destination = destination;
		
		this.distance = calculateDistance();
		this.time = (int) Math.round(distance * 1.5);
		this.cost = (int) (Math.round(distance * 4) * 1000) + 10_000; // 10_000 Toman for Entrance fee
		
		this.status = "start";
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;

		this.distance = calculateDistance();
		this.time = (int) Math.round(distance * 1.5);
		this.cost = (int) (Math.round(distance * 4) * 1000) + 10_000; // 10_000 Toman for Entrance fee
	}

	public Traveler getTraveler() {
		return traveler;
	}

	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;

		this.distance = calculateDistance();
		this.time = (int) Math.round(distance * 1.5);
		this.cost = (int) (Math.round(distance * 4) * 1000) + 10_000; // 10_000 Toman for Entrance fee
	}

	public String getDate() {
		return date;
	}

	// TODO: Check.
	public void setDate(String date) {
		this.date = date;
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

	public String getStatus() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime destinationTime =
				LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm")).plusMinutes(time);

		if (destinationTime.isBefore(now)) {
			status = "end";
		}
		
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// This method for calculate distance
	public double calculateDistance() {
		distance = Math.pow(Math.pow(destination[0]-traveler.getX(), 2) + Math.pow(destination[1]-traveler.getY(), 2), 0.5);
		if (distance == 0.0) {
			throw new IllegalArgumentException("Distance equals zero");
		}
		return distance;
	}

	// Override toString
	@Override
	public String toString() {
		return String.format("%s%n%s%nDate: %s%nDist: %.2f Km%nTime: %d Min%nCost: %,d Toman%nStatus: %sed",
				getTraveler(), getDriver(),
				date, getDistance(), getTime(), getCost(), getStatus());
	}

}
