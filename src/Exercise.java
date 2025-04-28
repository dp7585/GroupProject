public class Exercise {
    private String name;
    private double caloriesPerHour;
    private int duration;

    public Exercise(String name, double caloriesPerHour, int duration) {
        this.name = name;
        this.caloriesPerHour = caloriesPerHour;
        this.duration = duration;
    }

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