

public class Exercise {
    // Exercise name
    private String name;
    // Calories burned per hour
    private double caloriesPerHour;

    public Exercise(String name, double caloriesPerHour) {
        this.name = name;
        this.caloriesPerHour = caloriesPerHour;
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
}
