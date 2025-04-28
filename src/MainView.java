import java.awt.*;
import java.util.Date;
import javax.swing.*;

/**
 * The MainView class implements the View interface and provides a graphical interface
 * for tracking daily food consumption. It displays:
 * - A tab for displaying the current day's food consumption
 * - A tab for displaying the foods and recipes
 * - Controls for adding/removing foods
 * - Settings for weight and calorie limits
 */
public class MainView implements View {
    private JFrame frame;
    private JPanel panel;
    private Controller controller;
    private Model model;
    private JTabbedPane tabbedPane;
    private SimpleNutritionGraph graph;
    private LogView logView;
    private JSpinner dateSpinner;

    public MainView(Model model) {
        this.model = model;
        this.logView = new LogView(model);
        initialize();
    }

    /**
     * Initializes the MainView by setting up the GUI components and adding them
     * to the JFrame
     */
    private void initialize() {
        frame = new JFrame("Nutrition Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        panel = new JPanel(new BorderLayout());
        tabbedPane = new JTabbedPane();

        // Create and store views
        FoodView foodView = new FoodView(model);
        LogView logView = new LogView(model);
        graph = new SimpleNutritionGraph();

        // Add tabs
        tabbedPane.addTab("Food Management", foodView.getPanel());
        tabbedPane.addTab("Daily Log", createLogPanel(logView));

        // Add change listener
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 1) { // Log tab is selected
                logView.refreshFoodComboBox();
            }
        });


        // Add date selector panel at the top
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setValue(new Date()); // Set to current date
        

        dateSpinner.addChangeListener(e -> {
            Date selectedDate = (Date) dateSpinner.getValue();
            controller.handleDateChange(selectedDate);
        });
    
        
        topPanel.add(new JLabel("Select Date:"));
        topPanel.add(dateSpinner);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(tabbedPane, BorderLayout.CENTER);
        panel.add(tabbedPane, BorderLayout.CENTER);
        frame.add(panel);
    }


    private JPanel createLogPanel(LogView logView) {
        JPanel logPanel = new JPanel(new BorderLayout());
        
        // Create a container panel for log view and graph
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(logView.getPanel(), BorderLayout.CENTER);
        
        // Initialize and add the graph
        graph = new SimpleNutritionGraph();
        graph.updateData(0, 0, 0); // Initialize with empty data
        contentPanel.add(graph, BorderLayout.SOUTH);
        
        logPanel.add(contentPanel, BorderLayout.CENTER);
        return logPanel;
    }

   /**
     * Updates the nutrition graph with current data
     */
    public void updateNutritionGraph() {
        // Default values
        int fatPercent = 0;
        int carbsPercent = 0;
        int proteinPercent = 0;
        
        // Get the values from the log view's nutrition breakdown label
        String breakdownText = logView.nutritionBreakdownLabel.getText();
        
        if (breakdownText != null && !breakdownText.equals(" ") && !breakdownText.equals("No nutrition data available")) {
            try {
                // Split the text by pipe character
                String[] parts = breakdownText.split("\\|");
                
                // Only proceed if we got exactly 3 parts
                if (parts.length == 3) {
                    fatPercent = Integer.parseInt(parts[0].replaceAll("[^0-9]", "").trim());
                    carbsPercent = Integer.parseInt(parts[1].replaceAll("[^0-9]", "").trim());
                    proteinPercent = Integer.parseInt(parts[2].replaceAll("[^0-9]", "").trim());
                }
            } catch (Exception e) {
                System.err.println("Error parsing nutrition breakdown: " + e.getMessage());
                // Use default values (0, 0, 0) if parsing fails
            }
        }
        
        // Update the graph with the values
        graph.updateData(fatPercent, carbsPercent, proteinPercent);
    }
    
    /**
     * Updates the nutrition graph with specific values
     */
    public void updateNutritionGraph(int fatPercent, int carbsPercent, int proteinPercent) {
        if (graph != null) {
            graph.updateData(fatPercent, carbsPercent, proteinPercent);
        }
    }

    public LogView getLogView() {
        return this.logView;
    }

    public JSpinner getDateSpinner() {
        return dateSpinner;
    }
    
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void display() {
        frame.setVisible(true);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
        this.logView.setController(controller);
    }
}