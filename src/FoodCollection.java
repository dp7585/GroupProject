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
        loadFromFile("foods.csv");
    }

    public void addFood(Food food) {
        foodItems.add(food);
        saveToFile(foodItems, "foods.csv");
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
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[0].equals("b")) {
                    
                    // A basic food item, like Hot Dog
                    String name = fields[1];
                    double calories = Double.parseDouble(fields[2]);
                    double fat = Double.parseDouble(fields[3]);
                    double carbs = Double.parseDouble(fields[4]);
                    double protein = Double.parseDouble(fields[5]);

                    // Create a new FoodItem object
                    FoodItem food = new FoodItem(name, calories, fat, carbs, protein);
                    foodItems.add(food);
                }
                // Add more parsing logic for recipes if needed (r for recipes)
            }
        } catch (IOException e) {
            System.out.println("Error reading food file: " + e.getMessage());
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
}
