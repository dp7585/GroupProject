/*
 * DailyLogs job: 
 * needs to handle both foodItem and Recipe to store foods. We are using the Adapter Patter for this
 */

/*
 * Ill use the Map<K, V> to store the data structure.
 * the Key(K) = the date- each days log 
 * the Value(V) = list of foods logged for that date
 * 
 * ESMARI LOUW 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DailyLog {

    // a map to store logs. Each date will have list of food entries
    private Map<Date, List<Food>> logEntries = new HashMap<>();
    private Map<Date, Integer> calorieLimits = new HashMap<>();
    private Map<Date, Double> weightLogs = new HashMap<>();
    private static final String LOG_FILE = "src/log.csv"; // adding to log the info to the file with strings

    public DailyLog() {
        logEntries = new HashMap<>();
        weightLogs = new HashMap<>();
        calorieLimits = new HashMap<>();
        loadLogFromCSV(); // load log data on startup
    }

    /**
     * Adds food item to the daily log
     */
    public void addFood(Date date, Food food) {
        logEntries.putIfAbsent(date, new ArrayList<>());
        logEntries.get(date).add(food);
        saveLogToCSV(); // saves straight away after adding
    }

    /**
     * Allows the user to change the daily calorie limit and weight for the selected
     * date
     */
    public void changeData(Date date, int newCalorieLimit, double newWeight) {
        calorieLimits.put(date, newCalorieLimit);
        weightLogs.put(date, newWeight);
        saveLogToCSV(); // save log to csv after data change

        // System.out.println("Updated calorie limit to " + newCalorieLimit + " and
        // weight to " + newWeight + "kg for " + date);
    }

    /**
     * Displays the log info for the given date, including the goodsss
     */
    public void accessInfo(Date date) {
        if (!logEntries.containsKey(date)) {
            System.out.println("No log entry for this date.");
            return;
        }

        System.out.println("Daily log for " + date + ":");
        double totalCalories = 0;
        double totalFat = 0, totalCarbs = 0, totalProtein = 0;

        for (Food food : logEntries.get(date)) {
            System.out.println(food.getName() + " - Calories: " + food.getNutrition("calories"));
            totalCalories += food.getNutrition("calories");
            totalFat += food.getNutrition("fat");
            totalCarbs += food.getNutrition("carbs");
            totalProtein += food.getNutrition("protein");
        }

        // show the calorie limit warning..
        if (calorieLimits.containsKey(date)) {
            int limit = calorieLimits.get(date);
            System.out.println("Total Calories: " + totalCalories + " / Limit: " + limit);
            if (totalCalories > limit) {
                System.out.println("You have reached your calorie limit for this day!");
            }
        }
        // show weight
        if (weightLogs.containsKey(date)) {
            System.out.println("Weight for this day: " + weightLogs.get(date) + "kg");
        }

        // calculate the nutrition breakdown
        double totalMacronutrients = totalFat + totalCarbs + totalProtein;

        if (totalMacronutrients > 0) {
            System.out.printf("Nutrition Breakdown: %.0f%% Fat, %.0f%% Protein\n",
                    (totalFat / totalMacronutrients) * 100,
                    (totalCarbs / totalMacronutrients) * 100,
                    (totalProtein / totalMacronutrients) * 100);
        }
    }

    /**
     * Delete specifc food item from the log on given date
     */
    public void deleteLog(Date date, String foodName) {
        if (!logEntries.containsKey(date)) {
            System.out.println("No log entrie found for this date.");
            return;
        }

        List<Food> foods = logEntries.get(date);
        boolean removed = false;

        Iterator<Food> iterator = foods.iterator();

        while (iterator.hasNext()) {
            Food food = iterator.next();
            if (food.getName().equalsIgnoreCase(foodName)) {
                iterator.remove();
                removed = true;
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

    /**
     * Save log data to CSV (Food, Weight, Calorie Limits)
     * 
     * @param date
     * @return
     */
    private void saveLogToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE))) {
            // save the weight entries
            for (Map.Entry<Date, Double> entry : weightLogs.entrySet()) {
                Date date = entry.getKey();
                writer.write(String.format("%tY,%tm,%td,w,%.1f\n", date, date, date, entry.getValue()));
            }

            // save calorie limit entry
            for (Map.Entry<Date, Integer> entry : calorieLimits.entrySet()) {
                Date date = entry.getKey();
                writer.write(String.format("%tY,%tm,%td,c,%d\n", date, date, date, entry.getValue()));
            }

            // save food log entries
            for (Map.Entry<Date, List<Food>> entry : logEntries.entrySet()) {
                Date date = entry.getKey();
                for (Food food : entry.getValue()) {
                    writer.write(String.format("%tY,%tm,%td,f,%s,1.0\n", date, date, date, food.getName()));
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving log file: " + e.getMessage());
        }
    }

    /**
     * load log data from CSV (Food, Weight, Calorie limits)
     */
    private void loadLogFromCSV() {
        File file = new File(LOG_FILE);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty())
                    continue;

                String[] parts = line.split(",");
                // Skip lines that don't have enough parts
                if (parts.length < 5)
                    continue;

                try {
                    int year = Integer.parseInt(parts[0].trim());
                    int month = Integer.parseInt(parts[1].trim());
                    int day = Integer.parseInt(parts[2].trim());
                    Date date = new GregorianCalendar(year, month - 1, day).getTime();

                    switch (parts[3].trim()) {
                        case "w":
                            if (parts.length >= 5 && !parts[4].trim().isEmpty()) {
                                double weight = Double.parseDouble(parts[4].trim());
                                weightLogs.put(date, weight);
                            }
                            break;
                        case "c":
                            if (parts.length >= 5 && !parts[4].trim().isEmpty()) {
                                int calorieLimit = Integer.parseInt(parts[4].trim());
                                calorieLimits.put(date, calorieLimit);
                            }
                            break;
                        case "f":
                            if (parts.length >= 6 && !parts[4].trim().isEmpty()) {
                                String foodName = parts[4].trim();
                                Food food = new FoodItem(foodName, 0, 0, 0, 0);
                                logEntries.putIfAbsent(date, new ArrayList<>());
                                logEntries.get(date).add(food);
                            }
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Skipping malformed line in log file: " + line);
                    continue;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading the log file: " + e.getMessage());
        }
    }

    // getter methods
    public boolean hasLogEntriesForDate(Date date) {
        return logEntries.containsKey(date);
    }

    public List<Food> getLogEntriesForDate(Date date) {
        return logEntries.getOrDefault(date, new ArrayList<>());
    }

    public int getCalorieLimit(Date date) {
        return calorieLimits.getOrDefault(date, 2000); // Default as per requirements
    }

    public double getWeight(Date date) {
        return weightLogs.getOrDefault(date, 150.0); // Default as per requirements
    }

    public void setCalorieLimit(Date date, int limit) {
        calorieLimits.put(date, limit);
    }

    public void setWeight(Date date, double weight) {
        weightLogs.put(date, weight);
    }
}