/**
 * The Controller class acts as an intermediary between the Model and View
 * in the MVC (Model-View-Controller). It handles user input
 * and updates both Model and View accordingly.
 */
public class Controller {
    // Reference to the Model component
    private Model model;
    
    // Reference to the View component
    private View view;

    /**
     * Constructs a Controller with specified Model and View.
     * Also establishes the bidirectional relationship between Controller and View.
     * 
     * @param model The Model component of the MVC pattern
     * @param view The View component of the MVC pattern
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        // Set this controller as the View's controller
        this.view.setController(this);
    }

    /**
     * Initializes the application by displaying the View.
     * This typically starts the user interface and makes it visible.
     */
    public void initialize() {
        view.display();
    }
}