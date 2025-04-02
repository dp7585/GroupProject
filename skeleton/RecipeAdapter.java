/**
 * Esmari Louw Adapter class for the Recipe class that implements the dailylogfood
 */

public class RecipeAdapter implements DailyLogFood{
    @SuppressWarnings("FieldMayBeFinal")
    private Recipe recipe;

    public RecipeAdapter(Recipe recipe){
        this.recipe = recipe;
    }

    @Override
    public String getName(){
        return recipe.getName();
    }

    @Override
    public double getCalories(){
        return recipe.getNutrition("calories");
    }

    @Override
    public double getProtein(){
        return recipe.getNutrition("protein");
    }

    @Override
    public double getCarbs(){
        return recipe.getNutrition("carbs");
    }

    @Override
    public double getFat(){
        return recipe.getNutrition("fat");
    }
}
