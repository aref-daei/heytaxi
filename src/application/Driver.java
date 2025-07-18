package application;

public class Driver extends Personal {

	// Instance variable
	private Car car; // Car object

	// Constructor
	public Driver(String name, double score, Car car) {
		super(name, score);
		this.car = car;
	}

	// Getters and Setters
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	// Override toString
	@Override
	public String toString() {
		return String.format("%s: %s (%s) - %.2f Score",
				getClass().getSimpleName(), getName(), getCar(), getScore());
	}

}
