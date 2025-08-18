package ir.ardastudio.model;

public class Car {

	private String id;
	private String model;
	private String color;
	private String licensePlate;

	public Car(String id, String model, String color, String licensePlate) {
        this.id = id;
		this.model = model;
		this.color = color;
		this.licensePlate = licensePlate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
