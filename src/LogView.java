
// Enhanced LogView.java
import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * The LogView class implements the View interface and provides a graphical interface
 * for tracking daily food consumption. It displays:
 * - A table of logged foods with nutritional information
 * - Summary statistics of daily intake
 * - Controls for adding/removing foods
 * - Settings for weight and calorie limits
 */
public class LogView implements View {
    private JPanel panel;
    private Controller controller;
    private Model model;
    private JTable logTable;
    private DefaultTableModel tableModel;
    private JComboBox<Food> foodComboBox;
    private JTextField servingsField;
    private JLabel calorieStatusLabel;
    private JLabel nutritionBreakdownLabel;

    private Map<String, Double> foodServingsMap = new HashMap<>();

    public LogView(Model model) {
        this.model = model;
        initialize();
    }

     /**
     * Initializes all UI components and layout
     */
    private void initialize() {
        panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create table for log entries
        tableModel = new DefaultTableModel(
                new Object[] { "Food", "Servings", "Calories", "Fat (g)", "Carbs (g)", "Protein (g)" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        logTable = new JTable(tableModel);
        logTable.setFillsViewportHeight(true);
        logTable.setRowHeight(25);
        JScrollPane tableScrollPane = new JScrollPane(logTable);

        // Summary panel
        JPanel summaryPanel = new JPanel(new GridLayout(2, 1));
        calorieStatusLabel = new JLabel(" ", SwingConstants.CENTER);
        calorieStatusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        nutritionBreakdownLabel = new JLabel(" ", SwingConstants.CENTER);
        nutritionBreakdownLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        summaryPanel.add(calorieStatusLabel);
        summaryPanel.add(nutritionBreakdownLabel);

        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        foodComboBox = new JComboBox<>();
        updateFoodComboBox();

        servingsField = new JTextField(5);
        JButton addButton = new JButton("Add to Log");
        addButton.addActionListener(e -> addFoodToLog());

        JButton deleteButton = new JButton("Remove Selected");
        deleteButton.addActionListener(e -> removeSelectedFood());

        inputPanel.add(new JLabel("Add Food:"));
        inputPanel.add(foodComboBox);
        inputPanel.add(new JLabel("Servings:"));
        inputPanel.add(servingsField);
        inputPanel.add(addButton);
        inputPanel.add(deleteButton);

        // Weight and calorie limit controls
        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JTextField weightField = new JTextField(5);
        JTextField calorieLimitField = new JTextField(5);
        JButton updateSettingsButton = new JButton("Update Settings");

        settingsPanel.add(new JLabel("Weight (lbs):"));
        settingsPanel.add(weightField);
        settingsPanel.add(new JLabel("Calorie Limit:"));
        settingsPanel.add(calorieLimitField);
        settingsPanel.add(updateSettingsButton);

        // Combine panels
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(inputPanel, BorderLayout.NORTH);
        southPanel.add(settingsPanel, BorderLayout.SOUTH);

        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(summaryPanel, BorderLayout.NORTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        updateLogDisplay();
    }

    /**
     * Updates the food combo box with current food items from model
     */
    private void updateFoodComboBox() {
        foodComboBox.removeAllItems();
        for (Food food : model.getFoodCollection().getAllFoods()) {
            foodComboBox.addItem(food);
        }
    }

    /**
     * Adds the selected food to the daily log with specified servings
     */
    private void addFoodToLog() {
        try {
            Food selectedFood = (Food) foodComboBox.getSelectedItem();
            double servings = Double.parseDouble(servingsField.getText());
    
            if (servings <= 0) {
                JOptionPane.showMessageDialog(panel, "Servings must be greater than 0");
                return;
            }
    
            // Store the servings for this food
            foodServingsMap.put(selectedFood.getName(), servings);
    
            DailyLogFood logFood;
            if (selectedFood instanceof FoodItem) {
                logFood = new FoodItemAdapter((FoodItem) selectedFood);
            } else {
                logFood = new RecipeAdapter((Recipe) selectedFood);
            }
    
            // Create a wrapper that applies servings
            DailyLogFood scaledFood = new DailyLogFood() {
                @Override
                public String getName() { return logFood.getName(); }
                @Override
                public double getCalories() { return logFood.getCalories() * servings; }
                @Override
                public double getFat() { return logFood.getFat() * servings; }
                @Override
                public double getCarbs() { return logFood.getCarbs() * servings; }
                @Override
                public double getProtein() { return logFood.getProtein() * servings; }
            };
    
            model.getDailyLog().addFood(new Date(), scaledFood);
            updateLogDisplay();  // Make sure this is called after adding
            servingsField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Please enter a valid number for servings.");
        }
    }

    /**
     * Removes the selected food from the daily log
     */
    private void removeSelectedFood() {
        int selectedRow = logTable.getSelectedRow();
        if (selectedRow >= 0) {
            String foodName = (String) tableModel.getValueAt(selectedRow, 0);
            model.getDailyLog().deleteLog(new Date(), foodName);
            foodServingsMap.remove(foodName);  // Remove from our local map
            updateLogDisplay();
        } else {
            JOptionPane.showMessageDialog(panel, "Please select a food to remove");
        }
    }

    /**
     * Refreshes the food combo box with current food items
     */
    public void refreshFoodComboBox() {
    foodComboBox.removeAllItems();
    for (Food food : model.getFoodCollection().getAllFoods()) {
        foodComboBox.addItem(food);
    }
}

/**
     * Updates the log display with current data from model
     */
private void updateLogDisplay() {
    tableModel.setRowCount(0);
    Date today = new Date();

    double totalCalories = 0;
    double totalFat = 0, totalCarbs = 0, totalProtein = 0;

    for (DailyLogFood food : model.getDailyLog().getLogEntriesForDate(today)) {
        // Get servings from our local map (default to 1.0 if not found)
        double servings = foodServingsMap.getOrDefault(food.getName(), 1.0);
        
        Object[] row = {
            food.getName(),
            servings,
            food.getCalories() * servings,
            food.getFat() * servings,
            food.getCarbs() * servings,
            food.getProtein() * servings
        };
        tableModel.addRow(row);

        totalCalories += food.getCalories() * servings;
        totalFat += food.getFat() * servings;
        totalCarbs += food.getCarbs() * servings;
        totalProtein += food.getProtein() * servings;
    }

        // Update summary labels
        int calorieLimit = model.getDailyLog().getCalorieLimit(today);
        calorieStatusLabel.setText(String.format("Total Calories: %.1f / %d (%.1f%%)",
                totalCalories, calorieLimit, (totalCalories / calorieLimit) * 100));

        if (totalCalories > calorieLimit) {
            calorieStatusLabel.setForeground(Color.RED);
        } else {
            calorieStatusLabel.setForeground(Color.GREEN);
        }

        double totalMacros = totalFat + totalCarbs + totalProtein;
        if (totalMacros > 0) {
            int fatPercent = (int) Math.round((totalFat / totalMacros) * 100);
            int carbsPercent = (int) Math.round((totalCarbs / totalMacros) * 100);
            int proteinPercent = 100 - fatPercent - carbsPercent; // Ensure total 100%

            nutritionBreakdownLabel.setText(String.format(
                    "Nutrition Breakdown: %d%% Fat | %d%% Carbs | %d%% Protein",
                    fatPercent, carbsPercent, proteinPercent));
        }
    }

    @Override
    public void display() {
        // Handled by MainView
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}