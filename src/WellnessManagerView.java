import java.awt.*;
import javax.swing.*;

/**
 * The view component for the Wellness Manager application following the MVC
 * pattern.
 * Handles all user interface rendering and user interactions, providing:
 * - Food database management interface
 * - Daily nutrition logging functionality
 * - Nutrition information display
 */
public class WellnessManagerView {
    // Constants for default values and UI configuration
    private static final String DEFAULT_WEIGHT = "150.0"; // Default weight in pounds
    private static final String DEFAULT_CALORIE_LIMIT = "2000.0"; // Default daily calorie limit
    private static final Dimension WINDOW_SIZE = new Dimension(800, 600); // Main window size
    private static final int FORM_FIELDS_ROWS = 5; // Number of rows in food input form
    private static final int FORM_FIELDS_COLS = 2; // Number of columns in food input form

    // Main application window components
    private final JFrame mainFrame; // Primary application window

    // List components for displaying data
    private final JList<String> foodList; // Displays available food items
    private final JList<String> dailyLogList; // Displays daily food log entries

    // List models for data management
    private final DefaultListModel<String> foodListModel; // Backing model for food list
    private final DefaultListModel<String> logListModel; // Backing model for log list

    // Input fields for food information
    private final JTextField foodNameField;
    private final JTextField caloriesField; 
    private final JTextField fatField; 
    private final JTextField carbsField; 
    private final JTextField proteinField; 

    // Input fields for daily log operations
    private final JTextField servingsField; // Field for number of servings
    private final JTextField weightField; // Field for user's weight
    private final JTextField calorieLimitField; // Field for daily calorie limit

    // Information display labels
    private final JLabel totalCaloriesLabel; // Displays calorie consumption vs limit
    private final JLabel nutritionLabel; // Displays macro nutrient percentages

    // Action buttons
    private final JButton addFoodButton; 
    private final JButton deleteFoodButton; 
    private final JButton addToLogButton; 
    private final JButton deleteLogButton; 
    private final JButton updateButton; 

    /**
     * Constructs the WellnessManagerView and initializes all UI components.
     */
    public WellnessManagerView() {
        // Initialize data models
        foodListModel = new DefaultListModel<>();
        logListModel = new DefaultListModel<>();

        // Create UI components
        mainFrame = createMainFrame();
        foodList = createFoodList();
        dailyLogList = createDailyLogList();

        // Initialize input fields with specialized numeric fields where appropriate
        foodNameField = new JTextField();
        caloriesField = createNumericField();
        fatField = createNumericField();
        carbsField = createNumericField();
        proteinField = createNumericField();
        servingsField = createNumericField();
        weightField = createNumericField(DEFAULT_WEIGHT);
        calorieLimitField = createNumericField(DEFAULT_CALORIE_LIMIT);

        // Initialize information display labels
        totalCaloriesLabel = new JLabel("Total Calories: 0");
        nutritionLabel = new JLabel("Nutrition: 0% Fat, 0% Carbs, 0% Protein");

        // Initialize action buttons
        addFoodButton = new JButton("Add Food");
        deleteFoodButton = new JButton("Delete Food");
        addToLogButton = new JButton("Add to Log");
        deleteLogButton = new JButton("Delete Entry");
        updateButton = new JButton("Update Weight/Limit");

        // Assemble the complete UI
        initializeUI();
    }

    /**
     * Initializes and arranges the main UI components.
     * Creates a split pane with food database on left and daily log on right.
     */
    private void initializeUI() {
        mainFrame.setLayout(new BorderLayout());

        // Create the two main panels
        JPanel foodPanel = createFoodPanel();
        JPanel logPanel = createLogPanel();

        // Create split pane to separate food and log areas
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                foodPanel,
                logPanel);
        splitPane.setResizeWeight(0.5); 

        mainFrame.add(splitPane, BorderLayout.CENTER);
    }

    /**
     * Creates and configures the main application frame.
     * 
     * @return Configured JFrame instance
     */
    private JFrame createMainFrame() {
        JFrame frame = new JFrame("Wellness Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_SIZE);
        frame.setMinimumSize(new Dimension(600, 400)); // Prevent window from becoming too small
        return frame;
    }

    /**
     * Creates and configures the food list component.
     * 
     * @return Configured JList for food items
     */
    private JList<String> createFoodList() {
        JList<String> list = new JList<>(foodListModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Only allow single selection
        return list;
    }

    /**
     * Creates and configures the daily log list component.
     * 
     * @return Configured JList for log entries
     */
    private JList<String> createDailyLogList() {
        JList<String> list = new JList<>(logListModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Only allow single selection
        return list;
    }

    /**
     * Creates a numeric input field with default empty value.
     * 
     * @return Configured JTextField for numeric input
     */
    private JTextField createNumericField() {
        return createNumericField("");
    }

    /**
     * Creates a numeric input field with specified default value.
     * 
     * @param defaultValue Initial value for the field
     * @return Configured JTextField for numeric input
     */
    private JTextField createNumericField(String defaultValue) {
        JTextField field = new JTextField(defaultValue);
        field.setHorizontalAlignment(JTextField.RIGHT); // Right-align for numeric values
        return field;
    }

    /**
     * Creates the food database panel containing:
     * - Food list display
     * - Food input form
     * - Food management buttons
     * 
     * @return Configured JPanel for food database
     */
    private JPanel createFoodPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Food Database"));

        // Add components in appropriate layout positions
        panel.add(createFoodListScrollPane(), BorderLayout.CENTER);
        panel.add(createFoodInputPanel(), BorderLayout.NORTH);
        panel.add(createFoodButtonPanel(), BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates a scroll pane containing the food list.
     * 
     * @return Configured JScrollPane for food list
     */
    private JScrollPane createFoodListScrollPane() {
        return new JScrollPane(foodList);
    }

    /**
     * Creates the food input form panel with labels and fields.
     * 
     * @return Configured JPanel containing the input form
     */
    private JPanel createFoodInputPanel() {
        JPanel panel = new JPanel(new GridLayout(FORM_FIELDS_ROWS, FORM_FIELDS_COLS, 5, 5));

        // Add labeled fields in grid layout
        panel.add(new JLabel("Name:"));
        panel.add(foodNameField);

        panel.add(new JLabel("Calories:"));
        panel.add(caloriesField);

        panel.add(new JLabel("Fat (g):"));
        panel.add(fatField);

        panel.add(new JLabel("Carbs (g):"));
        panel.add(carbsField);

        panel.add(new JLabel("Protein (g):"));
        panel.add(proteinField);

        return panel;
    }

    /**
     * Creates the panel containing food management buttons.
     * 
     * @return Configured JPanel with action buttons
     */
    private JPanel createFoodButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.add(addFoodButton);
        panel.add(deleteFoodButton);
        return panel;
    }

    /**
     * Creates the daily log panel containing:
     * - Log entry list
     * - Log input fields
     * - Nutrition information display
     * - Log management buttons
     * 
     * @return Configured JPanel for daily log
     */
    private JPanel createLogPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Daily Log"));

        // Add components in appropriate layout positions
        panel.add(createLogListScrollPane(), BorderLayout.CENTER);
        panel.add(createLogInputPanel(), BorderLayout.NORTH);
        panel.add(createLogButtonPanel(), BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates a scroll pane containing the daily log list.
     * 
     * @return Configured JScrollPane for log entries
     */
    private JScrollPane createLogListScrollPane() {
        return new JScrollPane(dailyLogList);
    }

    /**
     * Creates the log input panel with fields for servings, weight, and calorie
     * limit.
     * Also includes nutrition information display labels.
     * 
     * @return Configured JPanel containing log input components
     */
    private JPanel createLogInputPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        // Add labeled fields and information displays
        panel.add(new JLabel("Servings:"));
        panel.add(servingsField);

        panel.add(new JLabel("Weight (lbs):"));
        panel.add(weightField);

        panel.add(new JLabel("Calorie Limit:"));
        panel.add(calorieLimitField);

        panel.add(totalCaloriesLabel);
        panel.add(nutritionLabel);

        return panel;
    }

    /**
     * Creates the panel containing log management buttons.
     * 
     * @return Configured JPanel with action buttons
     */
    private JPanel createLogButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.add(addToLogButton);
        panel.add(deleteLogButton);
        panel.add(updateButton);
        return panel;
    }

    // ======================
    // Public API Methods
    // ======================

    /**
     * Makes the main application window visible.
     */
    public void display() {
        mainFrame.setVisible(true);
    }

    /**
     * Adds a food item to the food list display.
     * 
     * @param foodName The name of the food to add
     */
    public void addFoodToList(String foodName) {
        foodListModel.addElement(foodName);
    }

    /**
     * Removes a food item from the food list display.
     * 
     * @param foodName The name of the food to remove
     */
    public void removeFoodFromList(String foodName) {
        foodListModel.removeElement(foodName);
    }

    /**
     * Adds an entry to the daily log display.
     * 
     * @param entry The formatted log entry to add
     */
    public void addLogEntry(String entry) {
        logListModel.addElement(entry);
    }

    /**
     * Removes an entry from the daily log display.
     * 
     * @param index The index of the entry to remove
     */
    public void removeLogEntry(int index) {
        if (index >= 0 && index < logListModel.size()) {
            logListModel.remove(index);
        }
    }

    /**
     * Clears all fields in the food input form.
     */
    public void clearFoodForm() {
        foodNameField.setText("");
        caloriesField.setText("");
        fatField.setText("");
        carbsField.setText("");
        proteinField.setText("");
    }

    /**
     * Clears the servings input field.
     */
    public void clearServingsField() {
        servingsField.setText("");
    }

    /**
     * Updates the nutrition information display with current values.
     * 
     * @param totalCalories Total calories consumed today
     * @param totalFat      Total fat consumed (grams)
     * @param totalCarbs    Total carbs consumed (grams)
     * @param totalProtein  Total protein consumed (grams)
     * @param calorieLimit  Daily calorie limit
     */
    public void updateNutritionInfo(double totalCalories, double totalFat,
            double totalCarbs, double totalProtein,
            int calorieLimit) {
        MacroPercentages percentages = calculateMacroPercentages(totalFat, totalCarbs, totalProtein);

        // Update labels with formatted values
        totalCaloriesLabel.setText(String.format("Total Calories: %.1f / %d",
                totalCalories, calorieLimit));

        nutritionLabel.setText(String.format("Nutrition: %d%% Fat, %d%% Carbs, %d%% Protein",
                percentages.fat(), percentages.carbs(), percentages.protein()));
    }

    /**
     * Calculates the percentage breakdown of macronutrients.
     * 
     * @param fat     Total fat in grams
     * @param carbs   Total carbs in grams
     * @param protein Total protein in grams
     * @return MacroPercentages record containing the calculated percentages
     */
    private MacroPercentages calculateMacroPercentages(double fat, double carbs, double protein) {
        double totalMacros = fat + carbs + protein;

        // Handle case where no macros have been logged
        if (totalMacros <= 0) {
            return new MacroPercentages(0, 0, 0);
        }

        // Calculate and return percentages
        return new MacroPercentages(
                (int) ((fat / totalMacros) * 100),
                (int) ((carbs / totalMacros) * 100),
                (int) ((protein / totalMacros) * 100));
    }

    /**
     * Record representing the percentage breakdown of macronutrients.
     * 
     * @param fat     Percentage of calories from fat
     * @param carbs   Percentage of calories from carbs
     * @param protein Percentage of calories from protein
     */
    private record MacroPercentages(int fat, int carbs, int protein) {
    }

    // =====================
    // Getters for UI Components
    // (Used by controller to attach event handlers)
    // =====================

    public JButton getAddFoodButton() {
        return addFoodButton;
    }

    public JButton getDeleteFoodButton() {
        return deleteFoodButton;
    }

    public JButton getAddToLogButton() {
        return addToLogButton;
    }

    public JButton getDeleteLogButton() {
        return deleteLogButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    // =====================
    // Getters for Field Values
    // (Used by controller to retrieve user input)
    // =====================

    public String getFoodName() {
        return foodNameField.getText();
    }

    public String getCalories() {
        return caloriesField.getText();
    }

    public String getFat() {
        return fatField.getText();
    }

    public String getCarbs() {
        return carbsField.getText();
    }

    public String getProtein() {
        return proteinField.getText();
    }

    public String getServings() {
        return servingsField.getText();
    }

    public String getWeight() {
        return weightField.getText();
    }

    public String getCalorieLimit() {
        return calorieLimitField.getText();
    }

    // =====================
    // Selection Methods
    // (Used by controller to get user selections)
    // =====================

    public String getSelectedFood() {
        return foodList.getSelectedValue();
    }

    public int getSelectedLogIndex() {
        return dailyLogList.getSelectedIndex();
    }

    // =====================
    // Dialog Methods
    // (Used by controller to show messages to user)
    // =====================

    /**
     * Displays an information message to the user.
     * 
     * @param message The message to display
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(mainFrame, message);
    }

    /**
     * Displays an error message to the user.
     * 
     * @param message The error message to display
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(mainFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}