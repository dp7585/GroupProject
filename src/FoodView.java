
// Enhanced FoodView.java
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FoodView implements View {
    private JPanel panel;
    private JList<String> foodList;
    private DefaultListModel<String> listModel;
    private Controller controller;
    private Model model;
    private JButton detailsButton, deleteButton;

    public FoodView(Model model) {
        this.model = model;
        initialize();
    }

    private void initialize() {
        panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create food list
        listModel = new DefaultListModel<>();
        updateFoodList();

        foodList = new JList<>(listModel);
        foodList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        foodList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(foodList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton addBasicButton = new JButton("Add Basic Food");
        addBasicButton.addActionListener(e -> showAddBasicFoodDialog());

        JButton addRecipeButton = new JButton("Add Recipe");
        addRecipeButton.addActionListener(e -> showAddRecipeDialog());

        detailsButton = new JButton("View Details");
        detailsButton.addActionListener(e -> showFoodDetails());

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteSelectedFood());

        buttonPanel.add(addBasicButton);
        buttonPanel.add(addRecipeButton);
        buttonPanel.add(detailsButton);
        buttonPanel.add(deleteButton);

        // Enable/disable buttons based on selection
        foodList.addListSelectionListener(e -> {
            boolean hasSelection = !foodList.isSelectionEmpty();
            detailsButton.setEnabled(hasSelection);
            deleteButton.setEnabled(hasSelection);
        });

        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateFoodList() {
        listModel.clear();
        List<Food> foods = model.getFoodCollection().getAllFoods();
        for (Food food : foods) {
            String displayText = String.format("%-30s", food.getName());
            if (food instanceof FoodItem) {
                displayText += " [Basic Food]";
            } else if (food instanceof Recipe) {
                displayText += " [Recipe]";
            }
            listModel.addElement(displayText);
        }
    }

    private void showAddBasicFoodDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Add New Basic Food");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = createBasicFoodFormPanel();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            try {
                String name = ((JTextField) formPanel.getComponent(1)).getText().trim();
                double calories = Double.parseDouble(((JTextField) formPanel.getComponent(3)).getText());
                double fat = Double.parseDouble(((JTextField) formPanel.getComponent(5)).getText());
                double carbs = Double.parseDouble(((JTextField) formPanel.getComponent(7)).getText());
                double protein = Double.parseDouble(((JTextField) formPanel.getComponent(9)).getText());

                FoodItem newFood = new FoodItem(name, calories, fat, carbs, protein);
                model.getFoodCollection().addFood(newFood);
                updateFoodList();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid numbers for all nutritional values.");
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(panel);
        dialog.setVisible(true);
    }

    private JPanel createBasicFoodFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name field
        addFormField(formPanel, gbc, 0, "Name:", new JTextField(20));

        // Calories field
        addFormField(formPanel, gbc, 1, "Calories (kcal):", new JTextField(10));

        // Fat field
        addFormField(formPanel, gbc, 2, "Fat (g):", new JTextField(10));

        // Carbs field
        addFormField(formPanel, gbc, 3, "Carbohydrates (g):", new JTextField(10));

        // Protein field
        addFormField(formPanel, gbc, 4, "Protein (g):", new JTextField(10));

        return formPanel;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void showAddRecipeDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Create New Recipe");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);

        // Main panel with form
        JPanel formPanel = new JPanel(new BorderLayout(10, 10));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Recipe name and servings
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        topPanel.add(new JLabel("Recipe Name:"));
        JTextField nameField = new JTextField();
        topPanel.add(nameField);
        topPanel.add(new JLabel("Servings:"));
        JTextField servingsField = new JTextField("1.0");
        topPanel.add(servingsField);

        // Ingredients list with servings
        DefaultListModel<String> ingredientsModel = new DefaultListModel<>();
        JList<String> ingredientsList = new JList<>(ingredientsModel);
        JScrollPane listScrollPane = new JScrollPane(ingredientsList);

        // Add ingredient controls
        JPanel addIngredientPanel = new JPanel(new BorderLayout(5, 5));
        JComboBox<Food> foodCombo = new JComboBox<>();
        model.getFoodCollection().getAllFoods().forEach(foodCombo::addItem);
        JTextField ingredientServings = new JTextField("1.0", 5);

        JButton addButton = new JButton("Add Ingredient");
        addButton.addActionListener(e -> {
            try {
                Food selected = (Food) foodCombo.getSelectedItem();
                double servings = Double.parseDouble(ingredientServings.getText());
                ingredientsModel.addElement(String.format("%.1f servings of %s", servings, selected.getName()));
                ingredientServings.setText("1.0");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter valid servings number");
            }
        });

        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        controlsPanel.add(new JLabel("Food:"));
        controlsPanel.add(foodCombo);
        controlsPanel.add(new JLabel("Servings:"));
        controlsPanel.add(ingredientServings);
        controlsPanel.add(addButton);
        addIngredientPanel.add(controlsPanel, BorderLayout.NORTH);

        // Remove ingredient button
        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(e -> {
            int index = ingredientsList.getSelectedIndex();
            if (index >= 0) {
                ingredientsModel.remove(index);
            }
        });
        addIngredientPanel.add(removeButton, BorderLayout.SOUTH);

        // Combine panels
        formPanel.add(topPanel, BorderLayout.NORTH);
        formPanel.add(listScrollPane, BorderLayout.CENTER);
        formPanel.add(addIngredientPanel, BorderLayout.SOUTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save Recipe");
         saveButton.addActionListener(e -> {
        try {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter a recipe name");
                return;
            }

            double servings = Double.parseDouble(servingsField.getText());
            if (servings <= 0) {
                JOptionPane.showMessageDialog(dialog, "Servings must be positive");
                return;
            }

            if (ingredientsModel.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please add at least one ingredient");
                return;
            }

            Recipe recipe = new Recipe(name, servings);
            List<Food> foods = model.getFoodCollection().getAllFoods();
            
            for (int i = 0; i < ingredientsModel.size(); i++) {
                String item = ingredientsModel.get(i);
                try {
                    // Split on "servings of" to handle the text format
                    String[] parts = item.split("servings of");
                    if (parts.length == 2) {
                        // Handle different decimal separators (both . and ,)
                        String servingsStr = parts[0].trim().replace(",", ".");
                        double itemServings = Double.parseDouble(servingsStr);
                        String foodName = parts[1].trim();
                        Food food = model.getFoodCollection().findFoodByName(foodName);
                        if (food != null) {
                            recipe.addIngredient(food, itemServings);
                        }
                    }
                } catch (NumberFormatException ex) {
                    System.err.println("Error parsing ingredient: " + item);
                    ex.printStackTrace();
                }
            }
            model.getFoodCollection().addFood(recipe);
            updateFoodList();
            dialog.dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "Please enter valid numbers");
            ex.printStackTrace(); // This will help debug the issue
        }
    });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(panel);
        dialog.setVisible(true);
    }

    private void showFoodDetails() {
        int selectedIndex = foodList.getSelectedIndex();
        if (selectedIndex >= 0) {
            Food selectedFood = model.getFoodCollection().getAllFoods().get(selectedIndex);
            new FoodDetailsDialog(selectedFood).setVisible(true);
        }
    }

    private void deleteSelectedFood() {
        int selectedIndex = foodList.getSelectedIndex();
        if (selectedIndex >= 0) {
            // Get the selected food using the same index from the model
            Food selectedFood = model.getFoodCollection().getAllFoods().get(selectedIndex);
            
            // Confirm deletion
            int confirm = JOptionPane.showConfirmDialog(
                panel,
                "Are you sure you want to delete '" + selectedFood.getName() + "'?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Remove from both the list model and the underlying collection
                listModel.remove(selectedIndex);
                model.getFoodCollection().getAllFoods().remove(selectedIndex);
                
                // Optional: Show success message
                JOptionPane.showMessageDialog(
                    panel,
                    "Successfully deleted '" + selectedFood.getName() + "'",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        } else {
            JOptionPane.showMessageDialog(
                panel,
                "Please select a food to delete first",
                "No Selection",
                JOptionPane.WARNING_MESSAGE
            );
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

    public JPanel getPanel() {
        return panel;
    }
}