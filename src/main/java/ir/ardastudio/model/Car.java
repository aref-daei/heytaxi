package ir.ardastudio.model;

public class Car {

	private int id;
	private String model;
	private String color;
	private String licensePlate;

	public Car(int id, String model, String color, String licensePlate) {
        this.id = id;
		this.model = model;
		this.color = color;
		this.licensePlate = licensePlate;
	}

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

	@Override
	public String toString() {
		return String.format("%s %s, %s IR", getModel(), getColor(), getLicensePlate());
	}

}
