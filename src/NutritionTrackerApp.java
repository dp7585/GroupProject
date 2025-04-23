/**
 * Main App class for the Nutrition Tracker application
 */

public class NutritionTrackerApp {
    public static void main(String[] args) {
        // Create and initialize the model
        Model model = new Model();
        
        // Create the controller
        Controller controller = new Controller(model);
        
        // Start the application
        controller.initialize();
    }
}