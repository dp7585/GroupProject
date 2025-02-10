/*
 * Initial Author
 *      Michael J. Lutz
 *
 * Other Contributers
 *
 * Acknowledgements
 */

/*
 * AWT UI class used for displaying the information from the
 * associated weather station object.
 * This is an extension of JFrame, the outermost container in
 * a AWT application.
 */

 import java.awt.*;
 import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingUtilities;
 
 @SuppressWarnings("deprecation")
public class AWTUI extends Frame implements Observer {
    private final Map<String, Label> labels = new HashMap<>();
    private static final String[] TYPES = {"Kelvin", "Celsius", "Farenheit", "Inches", "Millibars"};
    private final WeatherStation station;
 
    private static Font labelFont = new Font(Font.SERIF, Font.PLAIN, 34);
 
     /*
      * Create and populate the AWTUI JFrame with panels and labels to
      * show the temperatures.
      */
     public AWTUI(WeatherStation station) {
        super("Weather Station");
        this.station = station;
        this.station.addObserver(this);
        this.setLayout(new GridLayout(1, 0));

        for (String type : TYPES) {
            add(setPanel(type));
        }
 
        addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent) {
                    System.exit(0);
                }
            });
        pack();
        setVisible(true);
     }

    /* Update UI with latest readings.
      * 
      */
     public void update(Observable obs, Object ignore) {
         // Check for spurious updates from unrelated objects.
         if (station != obs) {
             return;
         }
         
         SwingUtilities.invokeLater(() -> {
            for (String type : TYPES) {
                labels.get(type).setText(String.format("%6.2f", station.get(type)));
            }
        });
     }
 
     /* Set panel with updated information.
      * 
      */
    private Panel setPanel(String type) {
        Panel panel = new Panel(new GridLayout(2, 1));
        createLabel(type, panel);
        labels.put(type, createLabel(String.format("%6.2f", station.get(type)), panel));
        return panel;
    }
 
    /* Create panel for each type and reading.
     * 
     */
    private Label createLabel(String text, Panel panel) {
        Label label = new Label(text, Label.CENTER);
        label.setFont(labelFont);
        panel.add(label);
        return label;
    }
 }
 