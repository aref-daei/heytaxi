package application;

public class Personal {

	// Instance variable
	private String name; // First name and Last name
	private int x = 0, y = 0; // 1Km scale
	private double score; // 1 to 5

	// Constructor
	public Personal(String name, double score) {
		if (score < 0.0 || score > 5.0) {
			throw new IllegalArgumentException("Score must be >= 0.0 and <= 5.0");
		}
		
		this.name = name;
		this.score = score;
	}

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		if (score < 0.0 || score > 5.0) {
			throw new IllegalArgumentException("Score must be >= 0.0 and <= 5.0");
		}
		
		this.score = score;
	}
}
