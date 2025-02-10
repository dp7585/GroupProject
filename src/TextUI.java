/*
 * Initial Author
 *      Michael J. Lutz
 *
 * Other Contributers
 *      Natalie Frank
 *
 * Acknowledgements
 */


import java.util.Observer;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class TextUI implements Observer {
    private final WeatherStation station;
    public TextUI(WeatherStation station) {
        this.station = station;
        this.station.addObserver(this);
    }

    /* Update UI with latest readings.
      * 
      */
    public void update(Observable obs, Object ignore) {
        // Check for spurious updates from unrelated objects.
        if (station != obs) {
            System.out.println("We land in update.");
            return;
        }
        
        System.out.printf(
                "Reading is %6.2f degrees C, %6.2f degrees K, %6.2f degrees F. %n",
                station.get("Celsius"), station.get("Kelvin"), station.get("Farenheit"));
        System.out.printf(
                "Reading is %6.2f inches of mercury and %6.2f millibars. %n",
                station.get("Inches"), station.get("Millibars"));
    }

    /* Start the application.
     * 
     */
    public static void main(String[] args) {
        WeatherStation ws = new WeatherStation();
        Thread thread = new Thread(ws);
        System.out.println("We land in main.");
        new TextUI(ws);

        thread.start();
    }
}
