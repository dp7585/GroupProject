

public class Exercise {
    // Exercise name
    private String name;
    // Calories burned per hour
    private double caloriesPerHour;

    public Exercise(String name, double caloriesPerHour) {
        this.name = name;
        this.caloriesPerHour = 0;
    }

    // Getter methods for other classes
    public String getName() {
        return name;
    }

    public double getCaloriesPerHour() {
        return caloriesPerHour;
    }
}
