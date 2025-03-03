/*
 * Initial Author
 *      Michael J. Lutz
 *
 * Other Contributers
 *      Natalie Frank
 *
 * Acknowledgements
 */

/*
 * Swing UI class used for displaying the information from the
 * associated weather station object.
 * This is an extension of JFrame, the outermost container in
 * a Swing application.
 */

import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observer;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
 
 import javax.swing.JFrame;
 import javax.swing.JLabel;
 import javax.swing.JPanel;
 import javax.swing.SwingUtilities;
 
 @SuppressWarnings("deprecation")
 public class SwingUI extends JFrame implements Observer {
    private final Map<String, JLabel> labels = new HashMap<>();
    private static final String[] TYPES = {"Kelvin", "Celsius", "Farenheit", "Inches", "Millibars"};
    private final WeatherStation station;
 
    private static Font labelFont = new Font(Font.SERIF, Font.PLAIN, 34);
 
     /* Create and populate the SwingUI JFrame with panels and labels to show readings.
      */
     public SwingUI(WeatherStation station) {
        super("Weather Station");
        this.station = station;
        this.station.addObserver(this);
        this.setLayout(new GridLayout(1, 0));

        for (String type : TYPES) {
            add(setPanel(type));
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
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
    private JPanel setPanel(String type) {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        createLabel(type, panel);
        labels.put(type, createLabel(String.format("%6.2f", station.get(type)), panel));
        return panel;
    }

    /* Create panel for each type and reading.
     * 
     */
    private JLabel createLabel(String text, JPanel panel) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(labelFont);
        panel.add(label);
        return label;
    }
 }
 