package application;

public class Traveler extends Personal {
	public Traveler(String name) {
		super(name, 5);
	}
	
	public Traveler(String name, double score) {
		super(name, score);
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s - %.2f Score",
				getClass().getSimpleName(), getName(), getScore());
	}
}
