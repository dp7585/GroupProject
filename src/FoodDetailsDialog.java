// FoodDetailsDialog.java - New class to show nutrition details
import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class FoodDetailsDialog extends JDialog {
    public FoodDetailsDialog(Food food) {
        setTitle(food.getName() + " Details");
        setLayout(new BorderLayout());
        setSize(350, 250);  // Slightly larger to accommodate more info

        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        
        if (food instanceof FoodItem) {
            FoodItem item = (FoodItem)food;
            detailsArea.append(String.format("Calories: %.1f\n", item.getCalories()));
            detailsArea.append(String.format("Fat: %.1fg\n", item.getFat()));
            detailsArea.append(String.format("Carbs: %.1fg\n", item.getCarbs()));
            detailsArea.append(String.format("Protein: %.1fg\n", item.getProtein()));
        } else if (food instanceof Recipe) {
            Recipe recipe = (Recipe)food;
            detailsArea.append(String.format("Recipe (%.1f servings):\n", recipe.getServings()));
            detailsArea.append("Ingredients:\n");
            
            for (Map.Entry<Food, Double> entry : recipe.getIngredients().entrySet()) {
                Food ingredient = entry.getKey();
                double servings = entry.getValue();
                detailsArea.append(String.format("- %.1f servings of %s\n", servings, ingredient.getName()));
            }
            
            detailsArea.append("\nNutrition per serving:\n");
            detailsArea.append(String.format("Calories: %.1f\n", recipe.getNutrition("calories")));
            detailsArea.append(String.format("Fat: %.1fg\n", recipe.getNutrition("fat")));
            detailsArea.append(String.format("Carbs: %.1fg\n", recipe.getNutrition("carbs")));
            detailsArea.append(String.format("Protein: %.1fg\n", recipe.getNutrition("protein")));
        }

        add(new JScrollPane(detailsArea), BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);
    }
}