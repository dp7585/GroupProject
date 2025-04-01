import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FoodCollection {
    private List<Food> foodItems = new ArrayList<>();

    // loads food when the program starts
    public FoodCollection() {
        loadFromFile("src/foods.csv");
    }

    public void addFood(Food food) {
        foodItems.add(food);
        saveToFile(foodItems, "src/foods.csv");
    }

    public Food getFood(String name) {
        for (Food food : foodItems) {
            if (food.getName().equalsIgnoreCase(name)) {
                return food;
            }
        }
        return null;
    }

    // Load foods from the CSV file
public void loadFromFile(String filename) {
    List<Food> tempBasicFoods = new ArrayList<>(); // First pass - basic foods
    List<String> recipeLines = new ArrayList<>();   // Store recipe lines for second pass

    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            if (fields[0].equals("b")) {
                // Basic food - add immediately
                String name = fields[1];
                double calories = Double.parseDouble(fields[2]);
                double fat = Double.parseDouble(fields[3]);
                double carbs = Double.parseDouble(fields[4]);
                double protein = Double.parseDouble(fields[5]);

                FoodItem food = new FoodItem(name, calories, fat, carbs, protein);
                tempBasicFoods.add(food);
            } else if (fields[0].equals("r")) {
                // Recipe - save for second pass
                recipeLines.add(line);
            }
        }

        // Add all basic foods to main collection first
        this.foodItems.addAll(tempBasicFoods);

        // Second pass - process recipes now that all basic foods are loaded
        for (String recipeLine : recipeLines) {
            String[] fields = recipeLine.split(",");
            String name = fields[1];
            Recipe recipe = new Recipe(name, 1.0); // Default 1 serving

            // Process ingredients (pairs of name,servings starting at index 2)
            for (int i = 2; i < fields.length; i += 2) {
                String ingredientName = fields[i];
                double servings = Double.parseDouble(fields[i + 1]);

                Food ingredient = findFoodByName(ingredientName);
                if (ingredient == null) {
                    System.err.println("Warning: Ingredient not found - " + ingredientName);
                    continue;
                }
                recipe.addIngredient(ingredient, servings); // Now passing servings too
            }

            this.foodItems.add(recipe);
        }
    } catch (IOException e) {
        System.out.println("Error reading food file: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.out.println("Error parsing number in food file: " + e.getMessage());
    }
}

    public void saveToFile(List<Food> foodItems, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Food food : foodItems) {
                FoodItem foodItem = (FoodItem) food;
                writer.write("b," + foodItem.getName() + "," + foodItem.getNutrition("calories") + "," +
                        foodItem.getNutrition("fat") + "," + foodItem.getNutrition("carbs") + "," +
                        foodItem.getNutrition("protein") + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving food items to file: " + e.getMessage());
        }
    }

    public List<Food> getAllFoods() {
        return new ArrayList<>(foodItems); // Return a copy for encapsulation
    }

    public Food findFoodByName(String name) {
        for (Food food : foodItems) {
            if (food.getName().equalsIgnoreCase(name)) {
                return food;
            }
        }
        return null;
    }
}
