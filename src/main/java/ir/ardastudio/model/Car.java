package ir.ardastudio.model;

public class Car {

	// Instance variable
	private int id;
	private String model; // Car name
	private String color; // Car color
	private String licensePlate; // Car license plate
	
	// Constructor
	public Car(String model, String color, String licensePlate) {
		this.model = model;
		this.color = color;
		this.licensePlate = licensePlate;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	// Override toString
	@Override
	public String toString() {
		return String.format("%s %s, %s IR", getModel(), getColor(), getLicensePlate());
	}

}
