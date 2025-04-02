/*
 * This will wrap the food item object so it behaves like a dailylog food
 */

 public class FoodItemAdapter implements DailyLogFood{
    private FoodItem foodItem;

    public FoodItemAdapter(FoodItem foodItem){
        this.foodItem = foodItem;
    }

    // Getter methods for the food item attributes
    @Override
    public String getName(){
        return foodItem.getName();
    }

    @Override
    public double getCalories(){
        return foodItem.getNutrition("calories");
    }

    @Override
    public double getProtein(){
        return foodItem.getNutrition("protein");
    }
    @Override
    public double getCarbs(){
        return foodItem.getNutrition("carbs");
    }


    @Override
    public double getFat(){
        return foodItem.getNutrition("fat");
    }
 }