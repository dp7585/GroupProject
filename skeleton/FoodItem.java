public class FoodItem extends Food  implements DailyLogFood{
    private double calories;
    private double fat;
    private double carbs;
    private double protein;

    public FoodItem(String name, double calories, double fat, double carbs, double protein) {
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
        switch(nutrition.toLowerCase()) {
            case "calories":
                return calories;
            case "protein":
                return protein;
            case "carbs":
                return carbs;
            case "fat":
                return fat;
            default:
                return 0.0;
        }
    }

    @Override
    public void displayInfo() {
        System.out.println(name + " - Calories: " + calories + " | Fat: " + fat + "g | Carbs: " + carbs + "g | Protein: " + protein + "g");
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public double getCalories(){
        return calories;
    }

    @Override
    public double getFat(){
        return fat;
    }

    @Override
    public double getCarbs(){
        return carbs;
    }

    @Override
    public double getProtein(){
        return protein;
    }
}