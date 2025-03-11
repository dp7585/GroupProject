public class FoodItem extends Food {
    private float calories;
    private float fat;
    private float carbs;
    private float protein;

    public FoodItem(String name, float calories, float fat, float carbs, float protein) {
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
    }

    /*
     * Gets specified nutrional content. 
     */
    public double getNutrition(String nutrition) {
        switch(nutrition) {
            case "calories":
                return calories;
            case "protein":
                return protein;
            case "carbs":
                return carbs;
            case "fat":
                return fat;
            default:
                return 0;
        }
    }

    public void displayInfo() {
        System.out.println(name + " - Calories: " + calories + " | Fat: " + fat + "g | Carbs: " + carbs + "g | Protein: " + protein + "g");
    }
}