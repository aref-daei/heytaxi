package ir.ardastudio.model;

import java.time.LocalDateTime;

public class Car extends Model {

	private String name;
	private String color;
	private String licensePlate;

	public Car(String name, String color, String licensePlate) {
        onId();
        onCreate();

		this.name = name;
		this.color = color;
		this.licensePlate = licensePlate;
	}

    public Car(String id, String createdAt, String updatedAt,
               String name, String color, String licensePlate) {
        super(id, LocalDateTime.parse(createdAt), LocalDateTime.parse(updatedAt));

        this.name = name;
        this.color = color;
        this.licensePlate = licensePlate;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
        onUpdate();
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
        onUpdate();
		this.color = color;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
        onUpdate();
		this.licensePlate = licensePlate;
	}

	@Override
	public String toString() {
		return String.format("%s %s, %s IR", getName(), getColor(), getLicensePlate());
	}

}
