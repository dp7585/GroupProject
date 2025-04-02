import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FoodCollection {
    private List<Food> foodItems = new ArrayList<>();

    // loads food when the program starts
    public FoodCollection() {
        loadFromFile("src/foods.csv");
    }

    public void addFood(Food food) {
        if (!foodItems.contains(food)) {  // Prevent duplicates
            foodItems.add(food);
            saveToFile(foodItems, "src/foods.csv");
        }
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
                    // Basic food
                    String name = fields[1].trim();
                    double calories = Double.parseDouble(fields[2].trim());
                    double fat = Double.parseDouble(fields[3].trim());
                    double carbs = Double.parseDouble(fields[4].trim());
                    double protein = Double.parseDouble(fields[5].trim());
    
                    FoodItem food = new FoodItem(name, calories, fat, carbs, protein);
                    tempBasicFoods.add(food);
                } else if (fields[0].equals("r") && fields.length >= 4) {
                    // Recipe - store for later processing
                    recipeLines.add(line);
                }
            }
    
            // Add all basic foods first
            this.foodItems.addAll(tempBasicFoods);
    
            // Process recipes
            for (String recipeLine : recipeLines) {
                String[] fields = recipeLine.split(",");
                String name = fields[1].trim();
                Recipe recipe = new Recipe(name, 1.0); // Default 1 serving
    
                // Process ingredients (pairs of name,servings)
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
