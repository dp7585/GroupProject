import java.awt.*;
import javax.swing.*;

public class MainView implements View {
    private JFrame frame;
    private JPanel panel;
    private Controller controller;
    private Model model;
    private JTabbedPane tabbedPane;

    public MainView(Model model) {
        this.model = model;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Nutrition Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        panel = new JPanel(new BorderLayout());
        tabbedPane = new JTabbedPane();

        // Create and store views
        FoodView foodView = (FoodView) ViewFactory.createView("food", model);
        LogView logView = (LogView) ViewFactory.createView("log", model);

        // Add tabs
        tabbedPane.addTab("Food Management", foodView.getPanel());
        tabbedPane.addTab("Daily Log", logView.getPanel());

        // Add change listener
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 1) { // Log tab is selected
                logView.refreshFoodComboBox();
            }
        });

        panel.add(tabbedPane, BorderLayout.CENTER);
        frame.add(panel);
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
    }
}