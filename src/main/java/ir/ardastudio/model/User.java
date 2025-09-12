package ir.ardastudio.model;

import java.time.LocalDateTime;

public class User extends Model {

	private String name; // First name and Last name
    private String phone; // ex: +989151234567
	private int x = 0, y = 0; // 1Km scale
	private double score; // 1 to 5

	public User(String name, String phone) {
		this(name, phone, 0, 0, 5.00);
	}

    public User(String name, String phone, int x, int y) {
        this(name, phone, x, y, 5.00);
    }

    public User(String name, String phone, int x, int y, double score) {
        if (score < 0.0 || score > 5.0) {
            throw new IllegalArgumentException("Score must be between 0.0 and 5.0 inclusive");
        }

        onId();
        onCreate();

        this.name = name;
        this.phone = phone;
        this.x = x;
        this.y = y;
        this.score = score;
    }

    public User(String id, String createdAt, String updatedAt,
                String name, String phone, int x, int y, double score) {
        super(id, LocalDateTime.parse(createdAt), LocalDateTime.parse(updatedAt));

        if (score < 0.0 || score > 5.0) {
            throw new IllegalArgumentException("Score must be between 0.0 and 5.0 inclusive");
        }

        this.name = name;
        this.phone = phone;
        this.x = x;
        this.y = y;
        this.score = score;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
        onUpdate();
		this.name = name;
	}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        onUpdate();
        this.phone = phone;
    }

	public int getX() {
		return x;
	}

	public void setX(int x) {
        onUpdate();
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
        onUpdate();
		this.y = y;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
        onUpdate();
		if (score < 0.0 || score > 5.0) {
			throw new IllegalArgumentException("Score must be between 0.0 and 5.0 inclusive");
		}
		this.score = score;
	}

    @Override
    public String toString() {
        return String.format("%s: %s - %.1f Score",
                getClass().getSimpleName(), getName(), Math.round(getScore() * 10) / 10.0);
    }
}
