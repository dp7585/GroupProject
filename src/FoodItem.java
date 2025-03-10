public class FoodItem implements Food {
    // Leaf

    private String food;
    private float calories;
    private float fat;
    private float carbs;
    private float protein;

    public FoodItem(String food, float calories, float fat, float carbs, float protein) {
        this.food = food;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
    }

    @Override
    public String getFood() {
        return food; // just returns foods name
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

    // im getting error here? any input
    // Fixed the error - the method parameters weren't all there, also
    // If we have to have addFood, it should not modify a FoodItem since it is a leaf
    @Override
    public void addFood(String food, float calories, float fat, float carbs, float protein) {
        System.out.println("Cannot add food to a basic food item.");
    }
    @Override
    public void display() {
        System.out.println(food + ": ");
        System.out.println("Calories: " + calories);
        System.out.println("Fat: " + fat + "g");
        System.out.println("Carbs: " + carbs + "g");
        System.out.println("Protein: " + protein + "g");
    }


    // Added the unimplemented methods since there were too many error messages
    // We'll have to redo the Food class
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
// this should then display it in the way it asks for