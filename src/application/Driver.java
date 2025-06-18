package application;

public class Driver extends Personal {
	private Car car;
	
	public Driver(String name, double score, Car car) {
		super(name, score);
		this.setCar(car);
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	
	@Override
	public String toString() {
		return String.format("The driver: %s%nThe score: %.2f%nThe car: %s",
				getName(), getScore(), getCar());
	}
}
