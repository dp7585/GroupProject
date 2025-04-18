// Model.java - The main model class that coordinates between food collection and daily logs
public class Model {
    private FoodCollection foodCollection;
    private DailyLog dailyLog;

    public Model() {
        this.foodCollection = new FoodCollection();
        this.dailyLog = new DailyLog();
    }

    // Getter methods
    public FoodCollection getFoodCollection() {
        return foodCollection;
    }

    public DailyLog getDailyLog() {
        return dailyLog;
    }
}