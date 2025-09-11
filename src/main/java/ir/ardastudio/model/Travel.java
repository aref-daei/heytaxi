package ir.ardastudio.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Travel extends Model {

	private Driver driver;
	private User user;
    private int x = 0, y = 0; // 1Km scale
	private double distance; // 1Km scale
	private int time; // Minutes
	private int cost; // Toman & 10_000 Toman for Entrance fee
	private String status; // start/end/cancel

	// Constructor
    public Travel(Driver driver, User user, int x, int y) {
        onId();
        onCreate();

        this.driver = driver;
        this.user = user;
        this.x = x;
        this.y = y;
        this.distance = calculateDistance(user.getX(), user.getY(), x, y);
        this.time = (int) Math.round(distance * 1.5);
        this.cost = (int) Math.round(distance * 4) * 1000 + 10_000;
        this.status = "start";
    }

    public Travel(String id, String createdAt, String updatedAt,
                  Driver driver, User user, int x, int y,
                  double distance, int time, int cost, String status) {
        super(id, LocalDateTime.parse(createdAt), LocalDateTime.parse(updatedAt));

        this.driver = driver;
        this.user = user;
        this.x = x;
        this.y = y;
        this.distance = distance;
        this.time = time;
        this.cost = cost;
        this.status = status;
    }

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;

        this.distance = calculateDistance(user.getX(), user.getY(), x, y);
		this.time = (int) Math.round(distance * 1.5);
		this.cost = (int) (Math.round(distance * 4) * 1000) + 10_000;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;

		this.distance = calculateDistance(user.getX(), user.getY(), x, y);
		this.time = (int) Math.round(distance * 1.5);
		this.cost = (int) (Math.round(distance * 4) * 1000) + 10_000;
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

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getStatus() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime destinationTime = getCreatedAt().plusMinutes(time);

		if (destinationTime.isBefore(now)) {
			status = "end";
		}
		
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// This method for calculate distance
	public static double calculateDistance(int x1, int y1, int x2, int y2) {
		double distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		if (distance == 0.0) {
			throw new IllegalArgumentException("Distance equals zero");
		}
		return distance;
	}

	// Override toString
	@Override
	public String toString() {
		return String.format("%s%n%s%nDate: %s%nDist: %.2f Km%nTime: %d Min%nCost: %,d Toman%nStatus: %sed",
				getUser(), getDriver(),
				getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm")),
                getDistance(), getTime(), getCost(), getStatus());
	}

}
