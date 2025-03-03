import java.util.Scanner;

public class DailyLog {

    public class Date {

    }

    public void dateValidation(String input) {
        /*
         * If the selected date does not already exist in the log, the information shall be initialized as follows:
         * The food intake is empty.
         * The calorie limit and weight are determined using the rules from the Daily Log section above.
         */
    }

    public void changeData(Date date) {
        /*
         * The program shall allow the user to change the daily calorie limit and weight for the selected date.
         */
    }

    public void addFood(Date date) {
        /*
         * The program shall support adding of new basic foods and recipes in a manner consistent with the specifications under Food Collection.
         */
    }

    public void accessInfo(Date date) {
        /*
         * The program shall, by some means, provide the user access to the following information for the selected date:
         * Each food item consumed, along with the number of servings and total calories.
         * The total number of calories consumed for the day and some indication of whether this exceeds the set limit.
         * The weight for the day.
         * A breakdown of nutrition in terms of the percentage of total grams of fats, carbohydrates, and protein, each to the nearest 1%. The total must be 100%.
         */
    }

    public void addLog(Date date) {
        /*
         * The program shall allow the user to add a food item and the number of servings to the daily log on the currently selected date.
         */
    }

    public void deleteLog(Date date) {
        /*
         * The program shall allow the user to delete food items in the daily log. The user shall be able to unambiguously specify which food to delete even if several entries have the same food name.
         */
    }

    
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to your Wellness Manager.");
        scanner.nextLine();

        System.out.println("Select a date to log activities.");
        System.out.println("Enter as [MM/DD/YYYY].");
        System.out.println("Press enter for today.");
        String date = scanner.nextLine();
        
    }
}
