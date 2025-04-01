import java.awt.*;
import java.util.List;
import javax.swing.*;

public class FoodView implements View {
    private JPanel panel;
    private JList<String> foodList;
    private DefaultListModel<String> listModel;
    private Controller controller;
    private Model model;
    public FoodView(Model model) {
        this.model = model;
        initialize();
    }

    private void initialize() {
        panel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        updateFoodList();

        foodList = new JList<>(listModel);
        panel.add(new JScrollPane(foodList), BorderLayout.CENTER);

        JButton addButton = new JButton("Add Food");
        addButton.addActionListener(e -> showAddFoodDialog());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateFoodList() {
        listModel.clear();
        List<Food> foods = model.getFoodCollection().getAllFoods();
        for (Food food : foods) {
            listModel.addElement(food.getName());
        }
    }

    private void showAddFoodDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Add New Food");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout(10, 10));
        
        // Create form panel with GridBagLayout for better control
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // Name field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        
        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        formPanel.add(nameField, gbc);
    
        // Calories field
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Calories (kcal):"), gbc);
        
        gbc.gridx = 1;
        JTextField caloriesField = new JTextField(10);
        formPanel.add(caloriesField, gbc);
    
        // Fat field
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Fat (g):"), gbc);
        
        gbc.gridx = 1;
        JTextField fatField = new JTextField(10);
        formPanel.add(fatField, gbc);
    
        // Carbs field
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Carbohydrates (g):"), gbc);
        
        gbc.gridx = 1;
        JTextField carbsField = new JTextField(10);
        formPanel.add(carbsField, gbc);
    
        // Protein field
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Protein (g):"), gbc);
        
        gbc.gridx = 1;
        JTextField proteinField = new JTextField(10);
        formPanel.add(proteinField, gbc);
    
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please enter a food name.");
                    return;
                }
                
                double calories = Double.parseDouble(caloriesField.getText());
                double fat = Double.parseDouble(fatField.getText());
                double carbs = Double.parseDouble(carbsField.getText());
                double protein = Double.parseDouble(proteinField.getText());
    
                FoodItem newFood = new FoodItem(name, calories, fat, carbs, protein);
                model.getFoodCollection().addFood(newFood);
                updateFoodList();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Please enter valid numbers for all nutritional values.", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
    
        // Add components to dialog
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Set dialog properties
        dialog.pack();
        dialog.setLocationRelativeTo(panel);
        dialog.setVisible(true);
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