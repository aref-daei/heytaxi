package application;

public class Car {
	private String model;
	private String color;
	private String licencePlate;
	
	public Car(String model, String color, String licencePlate) {
		this.setModel(model);
		this.setColor(color);
		this.setLicencePlate(licencePlate);
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

	public String getLicencePlate() {
		return licencePlate;
	}

	public void setLicencePlate(String licencePlate) {
		this.licencePlate = licencePlate;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s, %s LP", getModel(), getColor(), getLicencePlate());
	}
}
