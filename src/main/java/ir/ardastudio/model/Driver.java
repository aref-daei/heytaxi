package ir.ardastudio.model;

public class Driver extends Person {

	private Car car;

	public Driver(String id, String name, double score, Car car) {
		super(id, name, score);
		this.car = car;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	@Override
	public String toString() {
		return String.format("%s: %s (%s) - %.1f Score",
				getClass().getSimpleName(), getName(), getCar(), Math.round(getScore() * 10) / 10.0);
	}

}
