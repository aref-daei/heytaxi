package ir.ardastudio.model;

public class Person {

	private int id;
	private String name; // First name and Last name
	private int x = 0, y = 0; // 1Km scale
	private double score; // 1 to 5

	public Person(int id, String name, double score) {
		if (score < 0.0 || score > 5.0) {
			throw new IllegalArgumentException("Score must be between 0.0 and 5.0 inclusive");
		}

        this.id = id;
		this.name = name;
		this.score = score;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		if (score < 0.0 || score > 5.0) {
			throw new IllegalArgumentException("Score must be between 0.0 and 5.0 inclusive");
		}
		
		this.score = score;
	}
}
