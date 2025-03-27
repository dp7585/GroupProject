public class RecipeAdapter implements DailyLogFood{
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
