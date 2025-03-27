/*
 * DailyLogs job: 
 * needs to handle both foodItem and Recipe to store foods. We are using the Adapter Patter for this
 */

/*
 * Ill use the Map<K, V> to store the data structure.
 * the Key(K) = the date- each days log 
 * the Value(V) = list of foods logged for that date
 */


import java.util.*;


public class DailyLog {

    //a map to store logs. Each date will have list of food entries
    private Map<Date, List<DailyLogFood>> logEntries = new HashMap<>();
    private Map<Date, Integer> calorieLimits = new HashMap<>();
    private Map<Date, Double> weightLogs = new HashMap<>();

    /**
     * Adds food item to the daily log
     */
    public void addFood(Date date, DailyLogFood food){
        logEntries.putIfAbsent(date, new ArrayList<>());
        logEntries.get(date).add(food);
        System.out.println(food.getName() + " added to log on the " + date);
    }

    /**
     * Allows the user to change the daily calorie limit and weight for the selected date
     */
    public void changeData(Date date, int newCalorieLimit, double newWeight){
        calorieLimits.put(date, newCalorieLimit);
        weightLogs.put(date, newWeight);
        System.out.println("Updated calorie limit to " + newCalorieLimit + " and weight to " + newWeight + "kg for " + date);
    }
  
    /**
     * Displays the log info for the given date, including the goodsss
     */
    public void accessInfo(Date date){
        if (!logEntries.containsKey(date)) {
            System.out.println("No log entry for this date.");
            return;
        } 
         System.out.println("Daily log for " + date +  ":");
         double totalCalories = 0; 
         double totalFat = 0, totalCarbs = 0, totalProtein= 0;

         for(DailyLogFood food : logEntries.get(date)){
            System.out.println(food.getName() +  " - Calories: " + food.getCalories());
            totalCalories += food.getCalories();
            totalFat += food.getFat();
            totalCarbs += food.getCarbs();
            totalProtein += food.getProtein();
         }

        //show the calorie limit warning..
        if (calorieLimits.containsKey(date)){
            int limit = calorieLimits.get(date);
            System.out.println("Total Calories: " + totalCalories + " / Limit: " + limit);
        if (totalCalories > limit) {
            System.out.println("You have reached your calorie limit for this day!");
        }
    }
    //show weight
        if (weightLogs.containsKey(date)) {
            System.out.println("Weight for this day: " + weightLogs.get(date) + "kg");
        }

        //calculate the nutrition breakdown
        double totalMacronutrients  = totalFat + totalCarbs + totalProtein;

        if (totalMacronutrients > 0 ) {
            System.out.printf("Nutrition Breakdown: %.0f%% Fat, %.0f%% Protein\n",
            (totalFat / totalMacronutrients) * 100,
            (totalCarbs / totalMacronutrients) * 100,
            (totalProtein / totalMacronutrients) * 100);
        }
    }

    /**
     * Delete specifc food item from the log on given date
     */
    public void deleteLog(Date date, String foodName){
        if(!logEntries.containsKey(date)){
            System.out.println("No log entrie found for this date.");
            return;
        }

        List<DailyLogFood> foods = logEntries.get(date);
        boolean removed =false;

        Iterator<DailyLogFood> iterator = foods.iterator();

        while (iterator.hasNext()) {
            DailyLogFood food = iterator.next();
            if (food.getName().equalsIgnoreCase(foodName)) {
                iterator.remove();
                removed =true;
                System.out.println(foodName + " removed from log on " + date);
                break;
            }
        }

        if (!removed) {
            System.out.println("No food matches with this name '" + foodName + "' in the log for this date");
        }

        if (foods.isEmpty()) {
            logEntries.remove(date);
        }
    }
}