
// Enhanced LogView.java
import java.awt.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 * The LogView class implements the View interface and provides a graphical
 * interface
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
    public JLabel nutritionBreakdownLabel;
    private JTable exerciseTable;
    private DefaultTableModel exerciseTableModel;
    private JComboBox<Exercise> exerciseComboBox;
    private JTextField exerciseMinutesField;
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

         // Update the settings panel to properly handle updates
         // Update the settings panel
        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JTextField weightField = new JTextField(5);
        JTextField calorieLimitField = new JTextField(5);
        JButton updateSettingsButton = new JButton("Update Settings");

        // Set initial values
        Date today = model.getCurrentDate();
        weightField.setText(String.format("%.1f", model.getDailyLog().getWeight(today)));
        calorieLimitField.setText(String.valueOf(model.getDailyLog().getCalorieLimit(today)));

        updateSettingsButton.addActionListener(e -> {
            try {
                // Parse weight with more flexible number handling
                String weightText = weightField.getText().trim().replace(",", ".");
                double weight = Double.parseDouble(weightText);
                
                // Parse calorie limit
                String limitText = calorieLimitField.getText().trim();
                int limit = Integer.parseInt(limitText);
                
                // Validate values
                if (weight <= 0) {
                    JOptionPane.showMessageDialog(panel, 
                        "Weight must be a positive number", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    weightField.requestFocus();
                    return;
                }
                
                if (limit <= 0) {
                    JOptionPane.showMessageDialog(panel, 
                        "Calorie limit must be a positive number", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                    calorieLimitField.requestFocus();
                    return;
                }
                
                // Update both values
                model.getDailyLog().setWeight(today, weight);
                model.getDailyLog().setCalorieLimit(today, limit);
                
                // Refresh the display
                updateLogDisplay();
                
                JOptionPane.showMessageDialog(panel, 
                    "Settings updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, 
                    "Please enter valid numbers for weight and calorie limit\n" +
                    "Weight example: 150.5\n" +
                    "Calorie limit example: 2000", 
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        settingsPanel.add(new JLabel("Weight (lbs):"));
        settingsPanel.add(weightField);
        settingsPanel.add(new JLabel("Calorie Limit:"));
        settingsPanel.add(calorieLimitField);
        settingsPanel.add(updateSettingsButton);

        // Exercise panel
        JPanel exercisePanel = new JPanel(new BorderLayout());
        exercisePanel.setBorder(new TitledBorder("Exercise Log"));

        // Exercise table
        exerciseTableModel = new DefaultTableModel(
                new Object[] { "Exercise", "Minutes", "Calories Burned" }, 0);
        exerciseTable = new JTable(exerciseTableModel);
        JScrollPane exerciseScrollPane = new JScrollPane(exerciseTable);

        // Exercise input panel
        JPanel exerciseInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        exerciseComboBox = new JComboBox<>();
        updateExerciseComboBox();

        exerciseMinutesField = new JTextField(5);
        JButton addExerciseButton = new JButton("Add Exercise");
        addExerciseButton.addActionListener(e -> addExerciseToLog());

        JButton removeExerciseButton = new JButton("Remove Selected");
        removeExerciseButton.addActionListener(e -> removeSelectedExercise());

        exerciseInputPanel.add(new JLabel("Exercise:"));
        exerciseInputPanel.add(exerciseComboBox);
        exerciseInputPanel.add(new JLabel("Minutes:"));
        exerciseInputPanel.add(exerciseMinutesField);
        exerciseInputPanel.add(addExerciseButton);
        exerciseInputPanel.add(removeExerciseButton);

        exercisePanel.add(exerciseScrollPane, BorderLayout.CENTER);
        exercisePanel.add(exerciseInputPanel, BorderLayout.SOUTH);

        // Combine panels
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(inputPanel, BorderLayout.NORTH);
        southPanel.add(settingsPanel, BorderLayout.SOUTH);

        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(summaryPanel, BorderLayout.NORTH);
        panel.add(southPanel, BorderLayout.SOUTH);
        panel.add(exercisePanel, BorderLayout.EAST);

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
    
            // Add to the daily log
            Date today = model.getCurrentDate();
            model.getDailyLog().addFood(today, selectedFood); // This should trigger save
            
            // Store the servings for this food
            foodServingsMap.put(selectedFood.getName(), servings);
    
            updateLogDisplay();
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
            foodServingsMap.remove(foodName); // Remove from our local map
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

    // In LogView.java
    private void updateExerciseComboBox() {
        exerciseComboBox.removeAllItems();

        // Load exercises from ExerciseManager
        for (Exercise exercise : model.getExerciseManager().getAllExercises()) {
            exerciseComboBox.addItem(exercise);
        }

        // Add button to add new exercise
        JButton addExerciseButton = new JButton("+");
        addExerciseButton.setToolTipText("Add new exercise");
        addExerciseButton.addActionListener(e -> showAddExerciseDialog());

        // Add the button to the combo box
        exerciseComboBox.setEditable(true);
        JTextField textField = (JTextField) exerciseComboBox.getEditor().getEditorComponent();
        textField.setLayout(new BorderLayout());
        textField.add(addExerciseButton, BorderLayout.EAST);
    }

    private void showAddExerciseDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Add New Exercise");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(350, 200);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField nameField = new JTextField();
        JTextField caloriesField = new JTextField();

        formPanel.add(new JLabel("Exercise Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Calories per hour:"));
        formPanel.add(caloriesField);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please enter an exercise name");
                    return;
                }

                double calories = Double.parseDouble(caloriesField.getText());
                if (calories <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Calories must be greater than 0");
                    return;
                }

                // Add to ExerciseManager which will handle saving to CSV
                if (model.getExerciseManager().addExercise(name, calories)) {
                    updateExerciseComboBox(); // Refresh the combo box
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog,
                            "Exercise already exists or name contains invalid characters");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid number for calories");
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(panel);
        dialog.setVisible(true);
    }

    
private void addExerciseToLog() {
    try {
        Exercise exercise = (Exercise) exerciseComboBox.getSelectedItem();
        double minutes = Double.parseDouble(exerciseMinutesField.getText());

        if (minutes <= 0) {
            JOptionPane.showMessageDialog(panel, "Minutes must be greater than 0");
            return;
        }

        // Calculate calories burned based on user's weight
        double weight = model.getDailyLog().getWeight(new Date());
        double caloriesBurned = exercise.getCaloriesPerHour() * (weight / 100) * (minutes / 60);

        // Create a new exercise with the duration
        Exercise loggedExercise = new Exercise(exercise.getName(), exercise.getCaloriesPerHour(), (int)minutes);
        
        // Add to the daily log's exercise entries
        Date today = model.getCurrentDate();
        model.getDailyLog().addExercise(today, loggedExercise);
        
        // Update UI
        exerciseTableModel.addRow(new Object[] {
                loggedExercise.getName(),
                String.format("%.1f", minutes),
                String.format("%.1f", caloriesBurned)
        });

        exerciseMinutesField.setText("");
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(panel, "Please enter valid minutes");
    }
}

    private void removeSelectedExercise() {
        int selectedRow = exerciseTable.getSelectedRow();
        if (selectedRow >= 0) {
            exerciseTableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(panel, "Please select an exercise to remove");
        }
    }

    /**
     * Updates the log display with current data from model
     */
    public void updateLogDisplay() {
        tableModel.setRowCount(0);
        Date today = model.getCurrentDate();
    
        double totalFat = 0, totalCarbs = 0, totalProtein = 0;
    
        // Calculate totals from food servings map
        for (Map.Entry<String, Double> entry : foodServingsMap.entrySet()) {
            String foodName = entry.getKey();
            double servings = entry.getValue();
            Food food = model.getFoodCollection().findFoodByName(foodName);
    
            if (food != null) {
                double fat = food.getNutrition("fat") * servings;
                double carbs = food.getNutrition("carbs") * servings;
                double protein = food.getNutrition("protein") * servings;
    
                tableModel.addRow(new Object[] {
                    food.getName(),
                    String.format("%.1f", servings),
                    String.format("%.1f", food.getNutrition("calories") * servings),
                    String.format("%.1f", fat),
                    String.format("%.1f", carbs),
                    String.format("%.1f", protein)
                });
    
                totalFat += fat;
                totalCarbs += carbs;
                totalProtein += protein;
            }
        }
    
        // Update summary labels
        int calorieLimit = model.getDailyLog().getCalorieLimit(today);
        double totalCalories = totalFat + totalCarbs + totalProtein;
        calorieStatusLabel.setText(String.format("Total Calories: %.1f / %d (%.1f%%)",
                totalCalories, calorieLimit, (totalCalories / calorieLimit) * 100));
    
        // Calculate percentages
        double totalMacros = totalFat + totalCarbs + totalProtein;
        if (totalMacros > 0) {
            int fatPercent = (int) Math.round((totalFat / totalMacros) * 100);
            int carbsPercent = (int) Math.round((totalCarbs / totalMacros) * 100);
            int proteinPercent = 100 - fatPercent - carbsPercent;
            
            nutritionBreakdownLabel.setText(String.format(
                    "Nutrition Breakdown: %d%% Fat | %d%% Carbs | %d%% Protein",
                    fatPercent, carbsPercent, proteinPercent));
    
            // Update graph through controller
            if (controller != null) {
                controller.updateNutritionGraph(fatPercent, carbsPercent, proteinPercent);
            }
        } else {
            nutritionBreakdownLabel.setText("No nutrition data available");
            if (controller != null) {
                controller.updateNutritionGraph(0, 0, 0);
            }
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