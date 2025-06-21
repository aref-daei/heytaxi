package application;

public class Traveler extends Personal {
	private String phone;
	
	public Traveler(String name, String phone) {
		super(name, 5);
		
		this.phone = phone;
	}
	
	public Traveler(String name, String phone, double score) {
		super(name, score);
		
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
		return String.format("%s: %s - %.2f Score",
				getClass().getSimpleName(), getName(), getScore());
	}
}
