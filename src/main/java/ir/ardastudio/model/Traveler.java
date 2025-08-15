package ir.ardastudio.model;

public class Traveler extends Person {

	private String phone; // ex: +989151234567

	public Traveler(int id, String name, String phone) {
		super(id, name, 5.00);
		this.phone = phone;
	}

	public Traveler(int id, String name, String phone, double score) {
		super(id, name, score);
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return String.format("%s: %s - %.1f Score",
				getClass().getSimpleName(), getName(), Math.round(getScore() * 10) / 10.0);
	}

}
