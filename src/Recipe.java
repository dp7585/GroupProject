import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Recipe extends Food {
    private Map<Food, Double> ingredients = new HashMap<>(); // Food -> Servings
    private double servings;

    public Recipe(String name, double servings) {
        this.name = name;
        this.servings = servings;
    }

    // Adds an ingredient to the recipe
    public void addIngredient(Food food, double servings2) {
        ingredients.put(food, servings2);
        saveToFile("data/foods.csv");
    }

    public double getNutrition(String nutrition) {
        double total = 0.0;
        
        // Loop through all foods in the recipe
        for (Food food : ingredients.keySet()) {
            // Get the servings for this food
            double foodServings = ingredients.get(food);
            // Add to total
            total += food.getNutrition(nutrition) * foodServings;
        }
        
        return total;
    }

    // Display recipe information
    public void displayInfo() {
        System.out.println(name + " - Servings: " + servings);
        System.out.println("Ingredients:");
        for (Map.Entry<Food, Double> entry : ingredients.entrySet()) {
            Food food = entry.getKey();
            double ingredientServings = entry.getValue();
            System.out.printf("- %.1f servings of ", ingredientServings);
            food.displayInfo();
        }
    }

    // Save recipe to file
    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            StringBuilder line = new StringBuilder("r," + this.name);
            for (Map.Entry<Food, Double> entry : ingredients.entrySet()) {
                line.append(",").append(entry.getKey().getName())
                   .append(",").append(entry.getValue());
            }
            writer.write(line.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error saving recipe to file: " + e.getMessage());
        }
    }

    // Getters
    public Map<Food, Double> getIngredients() {
        return ingredients;
    }

    public double getServings() {
        return servings;
    }
}