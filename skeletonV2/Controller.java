
import java.util.Date;
import java.util.List;

/**
 * The Controller class acts as an intermediary between the Model and View
 * in the MVC (Model-View-Controller). It handles user input
 * and updates both Model and View accordingly.
 */
public class Controller {
    // Reference to the Model component
    private Model model;
    
    // Reference to the View component
    private MainView mainView;

    private LogView logView;

    /**
     * Constructs a Controller with specified Model and View.
     * Also establishes the bidirectional relationship between Controller and View.
     * 
     * @param model The Model component of the MVC pattern
     * @param view The View component of the MVC pattern
     */
    public Controller(Model model) {
        this.model = model;
        this.mainView = new MainView(model);
        this.mainView.setController(this);
    }

    /**
     * Initializes the application by displaying the View.
     * This typically starts the user interface and makes it visible.
     */
    public void initialize() {
        mainView.display();
    }

    public void loadDateData(Date date) {
        // Update model with selected date
        model.setCurrentDate(date);
        
        // Calculate nutrition data for the selected date
        double totalFat = 0, totalCarbs = 0, totalProtein = 0;
        List<Food> foods = model.getDailyLog().getLogEntriesForDate(date);
        
        for (Food food : foods) {
            totalFat += food.getNutrition("fat");
            totalCarbs += food.getNutrition("carbs");
            totalProtein += food.getNutrition("protein");
        }
        
        double totalMacros = totalFat + totalCarbs + totalProtein;
        if (totalMacros > 0) {
            int fatPercent = (int) Math.round((totalFat / totalMacros) * 100);
            int carbsPercent = (int) Math.round((totalCarbs / totalMacros) * 100);
            int proteinPercent = 100 - fatPercent - carbsPercent;
            
            updateNutritionGraph(fatPercent, carbsPercent, proteinPercent);
        } else {
            updateNutritionGraph(0, 0, 0);
        }
    }
    
    // Handles date changes
    public void handleDateChange(Date newDate) {
        model.setCurrentDate(newDate);
        refreshViews();
    }
    
    // Refreshes the views
    private void refreshViews() {
        mainView.updateNutritionGraph();
        if (logView != null) {
            logView.updateLogDisplay();
        }
    }
    
    // Updates the nutrition graph
    public void updateNutritionGraph(int fat, int carbs, int protein) {
        if (mainView != null) {
            mainView.updateNutritionGraph(fat, carbs, protein);
        }
    }
    
}