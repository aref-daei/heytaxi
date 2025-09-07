package ir.ardastudio.model;

public class Driver extends User {

	private Car car;

    public Driver(Car car, String name, String phone) {
        this(car, name, phone, 0, 0, 5.00);
    }

    public Driver(Car car, String name, String phone, int x, int y) {
        this(car, name, phone, x, y, 5.00);
    }

    public Driver(Car car, String name, String phone, int x, int y, double score) {
        super(name, phone, x, y, score);
        this.car = car;
    }

    public Driver(Car car, String id, String createdAt, String updatedAt,
                String name, String phone, int x, int y, double score) {
        super(id, createdAt, updatedAt, name, phone, x, y, score);
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
