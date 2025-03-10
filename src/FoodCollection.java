import java.util.ArrayList;
import java.util.List;

// Composite

public class FoodCollection implements Food {
    private String food;
    private List<Food> foods;

    public FoodCollection(String food) {
        this.food = food;
        this.foods = new ArrayList<>();
    }

    @Override
    public String getFood() {
        return food;
    }

    @Override
    public void addFood(String foodName, float calories, float fat, float carbs, float protein) {
        // Create a new FoodItem and add it to the foods list
        FoodItem newFood = new FoodItem(foodName, calories, fat, carbs, protein);
        foods.add(newFood);
    }

    public void removeTask(Food food) {
        foods.remove(food);
    }

    @Override
    public void display() {
        System.out.println("Task List: " + food);
        for (Food food : foods) {
            food.display();
        }
    }


    // Added all unimplemented methods so we don't get unnecessary errors
    // Again, we'll need to redo the Food class
    @Override
    public void setName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setName'");
    }

    @Override
    public void setCalories(float calories) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCalories'");
    }

    @Override
    public void setFat(float fat) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setFat'");
    }

    @Override
    public void setCarbs(float carbs) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCarbs'");
    }

    @Override
    public void setProtein(float protein) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setProtein'");
    }
}
