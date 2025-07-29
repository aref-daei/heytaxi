package ir.ardastudio.models;

public class Traveler extends Person {

	// Instance variable
	private String phone; // ex: +989151234567

	// Constructors
	public Traveler(String name, String phone) {
		super(name, 5);
		this.phone = phone;
	}

	public Traveler(String name, String phone, double score) {
		super(name, score);
		this.phone = phone;
	}

	// Getters and Setters
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return String.format("%s: %s - %.2f Score",
				getClass().getSimpleName(), getName(), getScore());
	}

}
