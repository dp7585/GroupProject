import javax.swing.*;


/**
 * Main class to test the GUI
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Initialize Model with CSV loading built into FoodCollection
            FoodCollection foodCollection = new FoodCollection(); // Automatically loads from CSV
            DailyLog dailyLog = new DailyLog();
            
            // Initialize View
            WellnessManagerView view = new WellnessManagerView();
            
            // Initialize Controller
            new WellnessManagerController(foodCollection, dailyLog, view);
            
            // Display the GUI
            view.display();
            
        });
    }
}