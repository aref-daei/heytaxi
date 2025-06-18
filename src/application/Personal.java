package application;

public class Personal {
	private String name;
	private int x = 0, y = 0; // 1km scale
	private double score;
	
	public Personal(String name, double score) {
		if (score < 0.0 || score > 5.0) {
			throw new IllegalArgumentException("Score must be >= 0.0 and <= 5.0");
		}
		
		this.setName(name);
		this.setScore(score);
	}

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
		this.score = score;
	}
}
