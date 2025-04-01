import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Recipe extends Food {
    private Map<Food, Double> ingredients = new HashMap<>(); // Food -> Servings
    private double servings;

    public Recipe(String name, double servings2) {
        this.name = name;
        this.servings = servings2;
    }

    public void addIngredient(Food food, double servings2) {
        ingredients.put(food, servings2);
        saveToFile("src/foods.csv");
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

    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("r," + this.name);
            for (Map.Entry<Food, Double> entry : ingredients.entrySet()) {
                Food ingredient = entry.getKey();
                double ingredientServings = entry.getValue();
                writer.write("," + ingredient.getName() + "," + ingredientServings);
            }
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Error saving recipe to file: " + e.getMessage());
        }
    }


    public Map<Food, Double> getIngredients() {
        return ingredients;
    }

    public Object getServings() {
        return servings;
    }
}