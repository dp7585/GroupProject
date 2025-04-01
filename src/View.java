import javax.swing.JPanel;

public interface View {
    void display();
    void setController(Controller controller);
    public JPanel getPanel();
}
