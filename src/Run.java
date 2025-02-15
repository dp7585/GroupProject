import java.util.Arrays;
/*
 * Esmari Louw: changed the the dependency   WeatherStation ws = new WeatherStation() 
 */
public class Run {
    public static void main(String[] args) {
        WeatherStation ws = new WeatherStation(new Barometer(), null); //its null for now because @dora needs to do the ITempSensor and then ill add it to here 
        Thread thread = new Thread(ws);
        String[] validLetters = {"S", "T", "X", "A"};

        if (args.length != 1 || !Arrays.asList(validLetters).contains(args[0])) {
            System.out.println("Enter S, T, X, or A into the command line.");
            return;
        }

        switch (args[0]) {
            case "S" -> new SwingUI(ws);
            case "A" -> new AWTUI(ws);
            case "T" -> new TextUI(ws);
            case "X" -> {
                new TextUI(ws);
                new AWTUI(ws);
                SwingUI UI2 = new SwingUI(ws);
                UI2.setLocation(0,140);
            }
        }
        thread.start();
    }
}
