import java.awt.*;
import java.util.Date;
import javax.swing.*;

public class LogView implements View {
    private JPanel panel;
    private Controller controller;
    private Model model;
    private JTextArea logTextArea;
    private JComboBox<Food> foodComboBox;
    private JTextField servingsField;

    public LogView(Model model) {
        this.model = model;
        initialize();
    }

    private void initialize() {
        panel = new JPanel(new BorderLayout());

        // Log display area
        logTextArea = new JTextArea();
        logTextArea.setEditable(false);
        panel.add(new JScrollPane(logTextArea), BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout());

        foodComboBox = new JComboBox<>();
        updateFoodComboBox();

        servingsField = new JTextField(5);
        JButton addButton = new JButton("Add to Log");
        addButton.addActionListener(e -> addFoodToLog());

        inputPanel.add(new JLabel("Food:"));
        inputPanel.add(foodComboBox);
        inputPanel.add(new JLabel("Servings:"));
        inputPanel.add(servingsField);
        inputPanel.add(addButton);

        panel.add(inputPanel, BorderLayout.SOUTH);

        updateLogDisplay();
    }

    private void updateFoodComboBox() {
        foodComboBox.removeAllItems();
        for (Food food : model.getFoodCollection().getAllFoods()) {
            foodComboBox.addItem(food);
        }
    }

    private void addFoodToLog() {
        try {
            Food selectedFood = (Food) foodComboBox.getSelectedItem();
            double servings = Double.parseDouble(servingsField.getText());

            // Create adapter based on food type
            DailyLogFood logFood;
            if (selectedFood instanceof FoodItem) {
                logFood = new FoodItemAdapter((FoodItem) selectedFood);
            } else {
                logFood = new RecipeAdapter((Recipe) selectedFood);
            }

            model.getDailyLog().addFood(new Date(), logFood);
            updateLogDisplay();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Please enter a valid number for servings.");
        }
    }

    private void updateLogDisplay() {
        logTextArea.setText("");
        Date today = new Date();

        // Display weight and calorie limit
        double weight = model.getDailyLog().getWeight(today);
        int calorieLimit = model.getDailyLog().getCalorieLimit(today);
        logTextArea.append(String.format("Weight: %.1f lbs\n", weight));
        logTextArea.append(String.format("Calorie Limit: %d\n\n", calorieLimit));

        // Display food entries
        logTextArea.append("Food Log:\n");
        double totalCalories = 0;
        double totalFat = 0, totalCarbs = 0, totalProtein = 0;

        for (DailyLogFood food : model.getDailyLog().getLogEntriesForDate(today)) {
            logTextArea.append(String.format("- %s (%.1f servings): %.1f calories\n",
                    food.getName(), 1.0, food.getCalories()));

            totalCalories += food.getCalories();
            totalFat += food.getFat();
            totalCarbs += food.getCarbs();
            totalProtein += food.getProtein();
        }

        // Display totals
        logTextArea.append(String.format("\nTotal Calories: %.1f / %d\n", totalCalories, calorieLimit));
        if (totalCalories > calorieLimit) {
            logTextArea.append("Warning: Exceeded calorie limit!\n");
        }

        // Display nutrition breakdown
        double totalMacros = totalFat + totalCarbs + totalProtein;
        if (totalMacros > 0) {
            int fatPercent = (int) Math.round((totalFat / totalMacros) * 100);
            int carbsPercent = (int) Math.round((totalCarbs / totalMacros) * 100);
            int proteinPercent = (int) Math.round((totalProtein / totalMacros) * 100);

            logTextArea.append(String.format("\nNutrition Breakdown:\n"));
            logTextArea.append(String.format("Fat: %d%%\n", fatPercent));
            logTextArea.append(String.format("Carbs: %d%%\n", carbsPercent));
            logTextArea.append(String.format("Protein: %d%%\n", proteinPercent));
        }
    }

    @Override
    public void display() {
        // Handled by MainView's tabbed pane
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public JPanel getPanel() {
        return panel;
    }
}