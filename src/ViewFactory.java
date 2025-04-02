/**
 * ViewFactory class for creating views based on their type
 */
public class ViewFactory {
    public static View createView(String type, Model model) {
        switch (type.toLowerCase()) {
            case "main":
                return new MainView(model);
            case "food":
                return new FoodView(model);
            case "log":
                return new LogView(model);
            default:
                throw new IllegalArgumentException("Unknown view type: " + type);
        }
    }
}