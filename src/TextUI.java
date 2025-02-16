/*
 * Initial Author
 *      Michael J. Lutz
 *
 * Other Contributers
 *      Natalie Frank
 *      Esmari Louw fdixed the needed dependecies 
 * Acknowledgements
 */


import java.util.Observable;
import java.util.Observer;

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
        //created sensores objects to "inject" into the weatherstation  ->Esmari Louw 
        ITempSensor tempSensor = new KelvinTempSensorAdapter(new KelvinTempSensor());   
        IBarometer barometer = new Barometer();
        WeatherStation ws = new WeatherStation(tempSensor, barometer);
        Thread thread = new Thread(ws);
        System.out.println("We land in main.");
        new TextUI(ws);

        thread.start();
    }
}
