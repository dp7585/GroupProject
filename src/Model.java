
import java.util.Date;

// Model.java - The main model class that coordinates between food collection and daily logs
public class Model {
    private FoodCollection foodCollection;
    private DailyLog dailyLog;
    private Date currentDate;

    public Model() {
        this.currentDate = new Date(); // Default to current date
        this.foodCollection = new FoodCollection();
        this.dailyLog = new DailyLog(foodCollection); //added the pass into foodcollection to dailylog
    }

    // Getter methods
    public FoodCollection getFoodCollection() {
        return foodCollection;
    }

    public DailyLog getDailyLog() {
        return dailyLog;
    }

    public void setCurrentDate(Date date) {
        this.currentDate = date;
    }
    
    public Date getCurrentDate() {
        return currentDate;
    }
}