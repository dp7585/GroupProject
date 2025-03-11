import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Recipe extends Food {
    private List<Food> ingredients = new ArrayList<>();
    private double servings;

    public Recipe(String name, float servings) {
        this.name = name;
        this.servings = servings;
    }

    public void addIngredient(Food food) {
        ingredients.add(food);
        saveToFile(food, "foods.csv");
    }

    public double getNutrition(String nutrition) {
        float total = 0;

        for (Food food : ingredients) {
            total += food.getNutrition(nutrition);
        }

        return total * servings;
    }

    public void displayInfo() {
        System.out.println(name + " - Servings: " + servings);
        for (Food food : ingredients) {
            food.displayInfo();  // Display info of each ingredient
        }
    }

    public void saveToFile(Food food, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            Recipe recipe = (Recipe) food;
            writer.write("r," + recipe.getName());
            for (Food ingredient : ingredients) {
                writer.write(ingredient + "," + servings);
            }
            writer.write("\n");
        } catch (IOException e) {
            System.out.println("Error saving food items to file: " + e.getMessage());
        }
    }
}