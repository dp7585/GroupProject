public class FoodItem implements Food {
    // Leaf
    
    private String food;
    private float calories;
    private float fat;
    private float carbs; 
    private float protein;


    public FoodItem(String food,float calories, float fat, float carbs, float protein) {
        this.food = food;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
    }

    @Override
    public String getFood() {
        return food;    //just returns foods name
    }

    // Setter for food
    public void setFood(String food) {
        this.food = food;
    }

    // Getters for the nutritional values
    public float getCalories() {
        return calories;
    }

    public float getFat() {
        return fat;
    }

    public float getCarbs() {
        return carbs;
    }

    public float getProtein() {
        return protein;
    }
 //im getting error here? any input
    @Override
    public void addFood(String food) {
        this.food = food;
    }

    @Override
    public void display() {
        System.out.println(food + ": ");
        System.out.println("Calories: " + calories);
        System.out.println("Fat: " + fat + "g");
        System.out.println("Carbs: " + carbs + "g");
        System.out.println("Protein: " + protein + "g");
    }
}
// this should then display it in the way it asks for