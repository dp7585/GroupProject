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
    public void addFood(String food) {
        foods.add(food);
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
}
