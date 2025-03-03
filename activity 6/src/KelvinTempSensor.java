/*
 * Initial Author
 *      Michael J. Lutz
 *
 * Other Contributers
 *
 * Acknowledgements
 */

/*
 * Class for a (simulated) sensor of the temperature. We assume the "real"
 * sensor returns a number in the range 0 .. 65535 which is calibrated to
 * be a Kelvin temperature to the nearest 100th of a degree. That is:
 *
 * 0 = 0 degrees Kelvin = -273.15 degrees Celsius
 * 27315 = 273.15 degrees Kelvin =   0.00 degrees Celsius
 * 29315 = 293.15 degrees Kelvin =  20.00 degrees Celsius
 * 65535 = 655.35 degrees Kelvin = 382.20 degrees Celsius
 */

 import java.util.Random; // to simulate random temperature fluctuations.

 public class KelvinTempSensor {
     /*
      * Min and max readings this sensor will actually report:
      * 23315 = -40 C
      * 38315 = 110 C
      * and the default (initial reading)
      * 29315 = 20 C
      */
     private final int MINREADING = 23315;
     private final int MAXREADING = 38315;
     private final int DEFAULT = 29315;
 
     private int currentReading; // current sensor reading
     private boolean increasing = true; // TRUE if temperature tending up
     private Random rand = new Random(); // simulate random temp, changes.
 
     /*
      * Initialize the sensor to the DEFAULT value.
      */
     public KelvinTempSensor() {
         currentReading = DEFAULT;
     }
 
     /*
      * Simulate a new reading based on the last reading and whether the
      * temperature is trending up or down. We assume that the temperature
      * has a 80% chance of continuing on its current trend and 20%
      * chance of changing direction. Also, we will not allow changes
      * outside of the specific min. and max. temperatures.
      */
     public int reading() {
         final double CUTOFF = 0.8; // 80% chance to continue temp. trend
         final int MAXCHANGE = 200; // maximum change in 1/100ths degree
         final int MINCHANGE = 100; // minimum change in 1/100ths degree
         int temperatureChange; // absolute value of the temp. change
 
         if (rand.nextDouble() > CUTOFF) {
             increasing = !increasing; // switch direction
         }
 
         temperatureChange = rand.nextInt(MAXCHANGE - MINCHANGE)
                 + MAXCHANGE;
         currentReading = currentReading +
                 temperatureChange * (increasing ? 1 : -1);
 
         /*
          * Limit readings to the specified (simulated) range.
          */
         if (currentReading >= MAXREADING) {
             currentReading = MAXREADING;
             increasing = false;
         } else if (currentReading <= MINREADING) {
             currentReading = MINREADING;
             increasing = true;
         }
 
         return currentReading;
     }
 }
 