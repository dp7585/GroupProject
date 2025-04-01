public class NutritionTrackerApp {
    public static void main(String[] args) {
        // Create and initialize the model
        Model model = new Model();
        
        // Create the main view using factory
        View mainView = ViewFactory.createView("main", model);
        
        // Create the controller
        Controller controller = new Controller(model, mainView);
        
        // Start the application
        controller.initialize();
    }
}