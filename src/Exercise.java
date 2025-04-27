

public class Exercise {
    // Exercise name
    @SuppressWarnings("FieldMayBeFinal")
    private String name;
    // Calories burned per hour
    @SuppressWarnings("FieldMayBeFinal")
    private double caloriesPerHour;
    // Duration of the exercise in minutes
    @SuppressWarnings("FieldMayBeFinal")
    private int duration;

    public Exercise(String name, double caloriesPerHour, int duration) {
        this.name = name;
        this.caloriesPerHour = caloriesPerHour;
        this.duration = duration;
    }

    // Getter methods for other classes
    public String getName() {
        return name;
    }

    public double getCaloriesPerHour() {
        return caloriesPerHour;
    }

    @Override
    public String toString() {
        return name;
    }
    public int getDuration() {
        return duration;
    }
}
