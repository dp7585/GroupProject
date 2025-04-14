// FoodDetailsDialog.java - Dialog window to display detailed nutrition information
import java.awt.*;
import java.util.Map;
import javax.swing.*;

/**
 * A custom dialog window that displays detailed nutritional information
 * for either a basic FoodItem or a Recipe. Shows different information
 * depending on the type of food being displayed.
 */
public class FoodDetailsDialog extends JDialog {
    
    /**
     * Constructs a FoodDetailsDialog for the specified food item.
     * The dialog will automatically configure itself based on whether
     * the food is a basic FoodItem or a Recipe.
     * 
     * @param food The food item to display details for (either FoodItem or Recipe)
     */
    public FoodDetailsDialog(Food food) {
        // Set dialog title with food name
        setTitle(food.getName() + " Details");
        setLayout(new BorderLayout());
        setSize(350, 250);  // Slightly larger to accommodate more info
        setModal(true);      // Make dialog modal by default
        setLocationRelativeTo(null);  // Center on screen

        // Create non-editable text area for display
        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        // Handle display for basic FoodItems
        if (food instanceof FoodItem) {
            FoodItem item = (FoodItem)food;
            detailsArea.append(String.format("Calories: %.1f\n", item.getNutrition("calories")));
            detailsArea.append(String.format("Fat: %.1fg\n", item.getNutrition("fat")));
            detailsArea.append(String.format("Carbs: %.1fg\n", item.getNutrition("carbs")));
            detailsArea.append(String.format("Protein: %.1fg\n", item.getNutrition("protein")));
        } 
        // Handle display for Recipes
        else if (food instanceof Recipe) {
            Recipe recipe = (Recipe)food;
            // Show recipe header with serving size
            detailsArea.append(String.format("Recipe (%.1f servings):\n", recipe.getServings()));
            detailsArea.append("Ingredients:\n");
            
            // List all ingredients with their quantities
            for (Map.Entry<Food, Double> entry : recipe.getIngredients().entrySet()) {
                Food ingredient = entry.getKey();
                double servings = entry.getValue();
                detailsArea.append(String.format("- %.1f servings of %s\n", servings, ingredient.getName()));
            }
            
            // Show calculated nutrition per serving
            detailsArea.append("\nNutrition per serving:\n");
            detailsArea.append(String.format("Calories: %.1f\n", recipe.getNutrition("calories")));
            detailsArea.append(String.format("Fat: %.1fg\n", recipe.getNutrition("fat")));
            detailsArea.append(String.format("Carbs: %.1fg\n", recipe.getNutrition("carbs")));
            detailsArea.append(String.format("Protein: %.1fg\n", recipe.getNutrition("protein")));
        }

        // Add scrollable text area to center of dialog
        add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        // Add close button at bottom
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());  // Close dialog on click
        add(closeButton, BorderLayout.SOUTH);
        
        // Ensure text starts at top
        detailsArea.setCaretPosition(0);
    }
}