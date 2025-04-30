
/**
 * Exercise class to represent an exercise with its name, calories burned per
 * hour, and duration.
 */
public class Exercise {
    private String name;
    private double caloriesPerHour;
    private int duration;

    // Constructor
    public Exercise(String name, double caloriesPerHour, int duration) {
        this.name = name;
        this.caloriesPerHour = caloriesPerHour;
        this.duration = duration;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getCaloriesPerHour() {
        return caloriesPerHour;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return String.format("e,%s,%.1f", name, caloriesPerHour);
    }
}