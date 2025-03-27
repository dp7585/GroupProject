import java.util.Date;

public class TestDailyLog {
    public static void main(String[] args) {
        //cretaung foodCollection and adding some foods for tetsing
        FoodCollection foodCollection = new FoodCollection();

        FoodItem hotDog = new FoodItem("Hot Dog", 147.0 ,13.6, 1.1, 5.1);
        FoodItem hotDogBun = new FoodItem("Hot Dog Bun", 120.0, 1.9, 21.3, 4.1);
        foodCollection.addFood(hotDog);
        foodCollection.addFood(hotDogBun);

        FoodItem apple = new FoodItem("Apple", 95.0, 0.3, 25.1, 0.5);
        FoodItem pizza = new FoodItem("Pizza Slice", 285.0, 10.0, 36.0, 12.0);
        dailyLog.addFood(today, apple);
        dailyLog.addFood(today, pizza);
        dailyLog.accessInfo(today);
        
        //cretaing dailyLog instance 
        DailyLog dailyLog = new DailyLog();

        //creating date for logging
        Date today = new Date(); //using the currnet date

        //add foods to dailylog
        dailyLog.addFood(today, hotDog);
        dailyLog.addFood(today, hotDogBun);

        //get info for the day
        dailyLog.accessInfo(today);

        //change the data
        dailyLog.changeData(today, 2000, 150.0);

        //display log info again after change
        dailyLog.accessInfo(today);

        //delete
        dailyLog.deleteLog(today, "Hot Dog");

        //display info again
        dailyLog.accessInfo(today);
    }
}
