import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Manages a collection of Food items, including both basic foods and recipes.
 * Provides functionality to load from and save to a CSV file, add new foods,
 * and retrieve food items. Implements persistent storage of food data.
 */
public class FoodCollection {
    // List to store all food items (both basic foods and recipes)
    private List<Food> foodItems = new ArrayList<>();

    /**
     * Constructs a FoodCollection and automatically loads food data from file.
     * The data file should be in CSV format at "src/foods.csv".
     */
    public FoodCollection() {
        loadFromFile("src/foods.csv");
    }

    /**
     * Adds a new food item to the collection if it doesn't already exist.
     * Automatically saves the updated collection to file.
     * @param food The food item to add (either FoodItem or Recipe)
     */
    public void addFood(Food food) {
        if (!foodItems.contains(food)) {  // Prevent duplicates
            foodItems.add(food);
            saveToFile(foodItems, "src/foods.csv");
        }
    }

    /**
     * Retrieves a food item by name (case-insensitive).
     * @param name The name of the food to find
     * @return The Food object if found, null otherwise
     */
    public Food getFood(String name) {
        for (Food food : foodItems) {
            if (food.getName().equalsIgnoreCase(name)) {
                return food;
            }
        }
        return null;
    }

    /**
     * Loads food data from a CSV file, processing both basic foods and recipes.
     * File format:
     * - Basic foods: b,name,calories,fat,carbs,protein
     * - Recipes: r,name,ingredient1,servings1,ingredient2,servings2,...
     * @param filename The path to the CSV file containing food data
     */
    private void loadFromFile(String filename) {
        List<Food> tempBasicFoods = new ArrayList<>();
        List<String> recipeLines = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
    
                String[] fields = line.split(",");
                if (fields.length < 2) continue;
    
                if (fields[0].equals("b") && fields.length == 6) {
                    // Process basic food entry
                    String name = fields[1].trim();
                    double calories = Double.parseDouble(fields[2].trim());
                    double fat = Double.parseDouble(fields[3].trim());
                    double carbs = Double.parseDouble(fields[4].trim());
                    double protein = Double.parseDouble(fields[5].trim());
    
                    FoodItem food = new FoodItem(name, calories, fat, carbs, protein);
                    tempBasicFoods.add(food);
                } else if (fields[0].equals("r") && fields.length >= 4) {
                    // Store recipe lines for processing after basic foods are loaded
                    recipeLines.add(line);
                }
            }
    
            // Add all basic foods first (recipes may depend on them)
            this.foodItems.addAll(tempBasicFoods);
    
            // Process recipes now that all basic foods are available
            for (String recipeLine : recipeLines) {
                String[] fields = recipeLine.split(",");
                String name = fields[1].trim();
                Recipe recipe = new Recipe(name, 1.0); // Default 1 serving
    
                // Process ingredient pairs (name, servings)
                for (int i = 2; i < fields.length; i += 2) {
                    if (i + 1 >= fields.length) break;
    
                    String ingredientName = fields[i].trim();
                    double servings = Double.parseDouble(fields[i+1].trim());
    
                    Food ingredient = findFoodByName(ingredientName);
                    if (ingredient != null) {
                        recipe.addIngredient(ingredient, servings);
                    }
                }
    
                this.foodItems.add(recipe);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading food file: " + e.getMessage());
        }
    }

    /**
     * Saves all food items to a CSV file in the specified format.
     * Basic foods are saved first, followed by recipes.
     * Uses US locale for consistent decimal formatting.
     * @param foodItems The list of food items to save
     * @param filename The destination file path
     */
    public void saveToFile(List<Food> foodItems, String filename) {
        // Create US locale for consistent decimal formatting
        Locale usLocale = new Locale("en", "US");
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // First save all basic foods
            for (Food food : foodItems) {
                if (food instanceof FoodItem) {
                    FoodItem item = (FoodItem) food;
                    writer.write(String.format(usLocale, "b,%s,%.1f,%.1f,%.1f,%.1f%n",
                        item.getName(),
                        item.getCalories(),
                        item.getFat(),
                        item.getCarbs(),
                        item.getProtein()));
                }
            }
            
            // Then save all recipes
            for (Food food : foodItems) {
                if (food instanceof Recipe) {
                    Recipe recipe = (Recipe) food;
                    StringBuilder sb = new StringBuilder();
                    sb.append("r,").append(recipe.getName());
                    
                    // Append each ingredient and its servings
                    for (Map.Entry<Food, Double> entry : recipe.getIngredients().entrySet()) {
                        sb.append(",").append(entry.getKey().getName())
                          .append(",")
                          .append(String.format(usLocale, "%.1f", entry.getValue()));
                    }
                    
                    writer.write(sb.toString() + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving food file: " + e.getMessage());
        }
    }

    /**
     * Returns a copy of all food items in the collection.
     * @return A new List containing all food items
     */
    public List<Food> getAllFoods() {
        return new ArrayList<>(foodItems); // Return a copy for encapsulation
    }

    /**
     * Finds a food item by name (case-insensitive search).
     * @param name The name of the food to find
     * @return The matching Food object, or null if not found
     */
    public Food findFoodByName(String name) {
        for (Food food : foodItems) {
            if (food.getName().equalsIgnoreCase(name)) {
                return food;
            }
        }
        return null;
    }
}