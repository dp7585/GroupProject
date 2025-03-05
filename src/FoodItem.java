public class FoodItem implements Food {
    // Leaf
    
    private String food;

    public FoodItem(String food) {
        this.food = food;
    }

    @Override
    public String getFood() {
        return food.toString();
    }

    @Override
    public void addFood(String name) {
        this.food = food;
    }

    @Override
    public void display() {
        System.out.println("Food: " + food);
    }
}
