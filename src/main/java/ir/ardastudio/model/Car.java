package ir.ardastudio.model;

import java.time.LocalDateTime;

public class Car extends Model {

	private String model;
	private String color;
	private String licensePlate;

	public Car(String model, String color, String licensePlate) {
        onId();
        onCreate();

		this.model = model;
		this.color = color;
		this.licensePlate = licensePlate;
	}

    public Car(String id, String createdAt, String updatedAt,
               String model, String color, String licensePlate) {
        super(id, LocalDateTime.parse(createdAt), LocalDateTime.parse(updatedAt));

        this.model = model;
        this.color = color;
        this.licensePlate = licensePlate;
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
