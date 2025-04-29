# Wellness Manager - Version 2.0

## Team Members 
- Esmari Louw 
- Dora Pehar-Ljoljic 
- Natalie Frank

## OverView
The new Wellness Manager version 2.0 builds on the original version with some new feautures for activity tracking, expanded food handeling (including recipes with sub-recipies), and cleaner UI using the MVC architecture. The app now supports daily logging of foof and exercies, visualization of nutritional data, and imporved user interaction. 

## Feautures 
- Add and manage food, recipes
- Log daily consumption and exercises 
- Track calorie intakes
- Weight tracking and nutritional summery
- View maconutrient breakdown via bar-chart - Hope to have this working by then- .
- Load/save data to 'log.csv' 
- Observer-based UI updates
- Streamlined GUI across views 

## Execusion Instructions 
## Prerequisites 
 - make sure you have Java installe don system
 - The required CSV files should be in the working directory. 

 ## Running the App
 1. Clone/download the project
 2. Compile java files (use IDE or terminal)
 3. Run 'NutritionTrackerApp.java' or 'main.java'
 4. Interact via GUI to log data and view summeries. 

 ## Design Patterns Used
 - **MVC** – Clear division of model, views, controller
- **Composite** – Treats `FoodItem` and `Recipe` uniformly
- **Observer** – Views update on model changes
- **Factory (Implicit)** – Views are instantiated through a central location
- **Facade** – `MainView.java` manages subsystem coordination
- **Singleton (Implicit)** – Central `Model` and `Controller` reused

## Notable Classes
- `Model` – Central data manager
- `Controller` – Mediates between views and data
- `FoodCollection` – Handles foods and recipes
- `DailyLog` – Stores food and exercise by date
- `ExerciseManager` – Manages available exercises
- `LogView`, `FoodView`, `MainView` – GUI interfaces
- `SimpleNutritionGraph` – Displays macronutrient chart

## Known Bugs & Limitations
- Graph features still under development
- Editing existing recipes is not yet supported
- No user profile or login functionality

## Challenges faced 
- Refactoring redundant or unused classes (e.g., `FoodItemAdapter`, `ViewFactory`)
- Handling complex file I/O (for nested recipes and logs)
- Integrating GUI and model updates cleanly (manual observer pattern)
- Ensuring no duplicate or corrupt data during logging

## Conclusion
Wellness Manager 2.0 is a robust upgrade, offering full dietary and fitness logging with a more maintainable, scalable design.