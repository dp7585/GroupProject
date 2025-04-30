public abstract class Food {
    protected String name;

    public String getName() {
        return name;
    }

    public abstract double getNutrition(String nutrition);

    public abstract void displayInfo();
}