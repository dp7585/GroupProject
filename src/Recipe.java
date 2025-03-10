import java.util.ArrayList;
import java.util.List;

public class Recipe implements Food {
    // Composite
    private String recipeName;
    private List<Food> ingredients; // List to store the ingredients (which could be FoodItems or other Recipes)

    public Recipe(String recipeName) {
        this.recipeName = recipeName;
        this.ingredients = new ArrayList<>();
    }

    @Override
    public String getFood() {
        return recipeName; // Returns the recipe name
    }

    @Override
    public void addFood(String foodName, float calories, float fat, float carbs, float protein) {
        // Creates a new FoodItem and add it to the ingredients list
        FoodItem newFood = new FoodItem(foodName, calories, fat, carbs, protein);
        ingredients.add(newFood);
    }

    @Override
    public void setName(String name) {
        this.recipeName = name; // Sets the name of the recipe
    }

    // Added unimplemented methods to not get confused by error messages
    @Override
    public void setCalories(float calories) {
        
    }

    @Override
    public void setFat(float fat) {
        
    }

    @Override
    public void setCarbs(float carbs) {
    
    }

    @Override
    public void setProtein(float protein) {
    
    }

    // Displays all ingredients (either FoodItems or sub-recipes)
    @Override
    public void display() {
        System.out.println("Recipe: " + recipeName);
        for (Food ingredient : ingredients) {
            ingredient.display(); 
        }
    }

    // A method to calculate the total nutritional values of the recipe
    public void calculateNutrition() {
        float totalCalories = 0;
        float totalFat = 0;
        float totalCarbs = 0;
        float totalProtein = 0;

        // Loop through all ingredients and sum up their values
        // Firstly, we would need to update the Food class, and then we will calculate the values
        for (Food ingredient : ingredients) {
            
        }

        System.out.println("Total Nutrition for " + recipeName + ":");
        System.out.println("Calories: " + totalCalories);
        System.out.println("Fat: " + totalFat + "g");
        System.out.println("Carbs: " + totalCarbs + "g");
        System.out.println("Protein: " + totalProtein + "g");
    }

    // Method to add a sub-recipe as an ingredient
    public void addSubRecipe(Recipe subRecipe) {
        ingredients.add(subRecipe);  // Adds another recipe to the list of ingredients
    }
}
