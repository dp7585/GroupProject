
/**
 * Main App class for the Nutrition Tracker application
 */

public class NutritionTrackerApp {
    public static void main(String[] args) {
    
    // Rest of your application startup
    Model model = new Model();
    Controller controller = new Controller(model);
    controller.initialize();
}
}