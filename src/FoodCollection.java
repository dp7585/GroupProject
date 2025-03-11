import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FoodCollection {
    private List<Food> foodItems = new ArrayList<>();

    public void addFood(Food food) {
        foodItems.add(food);
        saveToFile(foodItems, "foods.csv");
    }

    public Food getFood(String name) {
        for (Food food : foodItems) {
            if (food.getName().equals(name)) {
                return food;
            }
        }
        return null;
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
