/**
 * Dora Pehar-Ljoljic
 * 
 * An adapter for KelvinTempSensor that implements ITempSensor
 */

public class KelvinTempSensorAdapter implements ITempSensor {
    private final KelvinTempSensor sensor;

    // The constructor for the adapter which takes a sensor object
    public KelvinTempSensorAdapter(KelvinTempSensor sensor) {
        this.sensor = sensor;
    }

    // Gets the temperature
    @Override
    public double getTemperature() {
        return sensor.reading() / 100.0; // Convert from sensor integer reading to double
    }
}
