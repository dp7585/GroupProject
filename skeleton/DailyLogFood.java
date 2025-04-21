/*
 * Creating an interface that ensures both FoodItem and Recipe can be used in the same way.
 * This will make sure that DailyLog can treat them both
 */

 public interface DailyLogFood {
    String getName();
    double getCalories();
    double getProtein();
    double getCarbs();
    double getFat();
}