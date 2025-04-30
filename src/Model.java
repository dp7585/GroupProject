// Updated Model.java
import java.util.Date;

/**
 * Model class for the application
 */
public class Model {
    private FoodCollection foodCollection;
    private DailyLog dailyLog;
    private ExerciseManager exerciseManager;
    private Date currentDate;

    public Model() {
        this.currentDate = new Date();
        this.foodCollection = new FoodCollection();
        this.dailyLog = new DailyLog(foodCollection);
        this.exerciseManager = new ExerciseManager();
        this.exerciseManager.loadFromFile();
    }

    // Getters and setters for the model
    public FoodCollection getFoodCollection() {
        return foodCollection;
    }

    public DailyLog getDailyLog() {
        return dailyLog;
    }

    public ExerciseManager getExerciseManager() {
        return exerciseManager;
    }

    public void setCurrentDate(Date date) {
        this.currentDate = date;
    }
    
    public Date getCurrentDate() {
        return currentDate;
    }
}