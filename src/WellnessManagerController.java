import java.util.Date;
import java.util.List;

/**
 * Controller class for the Wellness Manager application.
 * Handles communication between the view and model components.
 */
public class WellnessManagerController {
    // Model components
    private FoodCollection foodCollection;  // Stores all available food items
    private DailyLog dailyLog;             // Tracks daily food entries and nutrition data
    private WellnessManagerView view;       // Handles user interface interactions

    /**
     * Constructs a new WellnessManagerController.
     * @param foodCollection The food collection model
     * @param dailyLog The daily log model
     * @param view The view component
     */
    public WellnessManagerController(FoodCollection foodCollection,
            DailyLog dailyLog,
            WellnessManagerView view) {
        this.foodCollection = foodCollection;
        this.dailyLog = dailyLog;
        this.view = view;

        initializeController();  // Set up event listeners
        loadInitialData();        // Load initial food data into view
    }

    /**
     * Initializes controller by setting up event listeners for view components.
     */
    private void initializeController() {
        // Set up button listeners for various user actions
        view.getAddFoodButton().addActionListener(e -> addFood());
        view.getDeleteFoodButton().addActionListener(e -> deleteFood());
        view.getAddToLogButton().addActionListener(e -> addToLog());
        view.getDeleteLogButton().addActionListener(e -> deleteLogEntry());
        view.getUpdateButton().addActionListener(e -> updateWeightAndLimit());
    }

    /**
     * Loads initial food data from the model into the view.
     */
    private void loadInitialData() {
        // Populate the food list in the view with all available foods
        for (Food food : foodCollection.getAllFoods()) {
            view.addFoodToList(food.getName());
        }
    }

    /**
     * Handles adding a new food item to the collection.
     * Validates input and updates both model and view.
     */
    private void addFood() {
        try {
            // Get food details from view
            String name = view.getFoodName();
            double calories = Double.parseDouble(view.getCalories());
            double fat = Double.parseDouble(view.getFat());
            double carbs = Double.parseDouble(view.getCarbs());
            double protein = Double.parseDouble(view.getProtein());

            // Create new food item and add to model
            FoodItem newFood = new FoodItem(name, calories, fat, carbs, protein);
            foodCollection.addFood(newFood);

            // Update view and show success message
            view.addFoodToList(name);
            view.clearFoodForm();
            view.showMessage("Food added successfully!");
        } catch (Exception e) {
            view.showError("Error adding food: " + e.getMessage());
        }
    }

    /**
     * Handles deleting a selected food item from the collection.
     * Updates both model and view.
     */
    private void deleteFood() {
        String selected = view.getSelectedFood();
        if (selected != null) {
            // Remove food from model and update view
            foodCollection.getAllFoods().removeIf(food -> food.getName().equals(selected));
            view.removeFoodFromList(selected);
        }
    }

    /**
     * Handles adding a food entry to the daily log.
     * Validates input and updates both model and view.
     */
    private void addToLog() {
        try {
            String selectedFood = view.getSelectedFood();
            double servings = Double.parseDouble(view.getServings());

            if (selectedFood == null) {
                view.showMessage("Please select a food first!");
                return;
            }

            // Find the selected food in the collection
            Food food = foodCollection.findFoodByName(selectedFood);
            if (food != null) {
                // Add food to daily log (using adapter pattern if needed)
                DailyLogFood logFood = new FoodItemAdapter((FoodItem) food);
                dailyLog.addFood(new Date(), logFood);
                
                // Update view and clear input field
                view.addLogEntry(selectedFood + " (" + servings + " servings)");
                view.clearServingsField();

                // Refresh nutrition display
                updateNutritionDisplay();
            }
        } catch (Exception e) {
            view.showError("Error adding to log: " + e.getMessage());
        }
    }

    /**
     * Handles deleting a selected log entry.
     * Updates both model and view.
     */
    private void deleteLogEntry() {
        int selectedIndex = view.getSelectedLogIndex();
        if (selectedIndex != -1) {
            // Remove entry from view
            view.removeLogEntry(selectedIndex);
            
            // Remove corresponding entry from model
            List<DailyLogFood> entries = dailyLog.getLogEntriesForDate(new Date());
            if (entries != null && selectedIndex < entries.size()) {
                entries.remove(selectedIndex);
            }
            
            // Refresh nutrition display
            updateNutritionDisplay();
        }
    }

    /**
     * Updates the user's weight and calorie limit.
     * Validates input and updates both model and view.
     */
    private void updateWeightAndLimit() {
        try {
            // Get values from view
            double weight = Double.parseDouble(view.getWeight());
            int limit = Integer.parseInt(view.getCalorieLimit());

            // Update model
            dailyLog.setWeight(new Date(), weight);
            dailyLog.setCalorieLimit(new Date(), limit);

            // Refresh display and show success message
            updateNutritionDisplay();
            view.showMessage("Updated successfully!");
        } catch (Exception e) {
            view.showError("Error updating: " + e.getMessage());
        }
    }

    /**
     * Calculates and updates the nutrition information display.
     * Aggregates data from all log entries for the current date.
     */
    private void updateNutritionDisplay() {
        double totalCalories = 0;
        double totalFat = 0, totalCarbs = 0, totalProtein = 0;

        // Get all log entries for today
        List<DailyLogFood> entries = dailyLog.getLogEntriesForDate(new Date());
        if (entries != null) {
            // Sum up nutrition values from all entries
            for (DailyLogFood food : entries) {
                totalCalories += food.getCalories();
                totalFat += food.getFat();
                totalCarbs += food.getCarbs();
                totalProtein += food.getProtein();
            }
        }

        // Get the daily calorie limit
        int calorieLimit = dailyLog.getCalorieLimit(new Date());
        
        // Update view with calculated values
        view.updateNutritionInfo(totalCalories, totalFat, totalCarbs, totalProtein, calorieLimit);
    }
}