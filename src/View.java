import javax.swing.JPanel;

/**
 * View interface for the view of the application
 * 
 */
public interface View {
    void display();
    void setController(Controller controller);
    public JPanel getPanel();
}
