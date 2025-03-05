public interface Food {
    // Component
    
    String getFood();
    void addFood(String food, float calories, float fat, float carbs, float protein);
    void setName(String name);
    void setCalories(float calories);
    void setFat(float fat);
    void setCarbs(float carbs);
    void setProtein(float protein);
    void display();

}