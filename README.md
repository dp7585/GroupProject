# Wellness Manager - Version 1.0

## Team Members
- Esmari Louw
- Dora Pehar-Ljoljic
- Natalie Frank

## Overview
The Wellness Manager is a software tool designed to help users track their daily food intake, manage their dietary goals, and monitor their weight over time. Users can log food consumption, create and store recipes, and analyze their nutritional intake through various reports and visualizations.

## Features
- Maintain a food database (basic foods and recipes).
- Log daily food consumption with servings.
- Track weight and calorie intake goals.
- Display total calories consumed per day.
- Generate macronutrient distribution percentages.
- Save and load user data from CSV files.
- Simple graphical user interface (GUI) for easy interaction.

## Execution Instructions
### Prerequisites
- Ensure you have Java installed on your system.
- The required CSV files (`foods.csv` and `log.csv`) should be in the working directory.

### Running the Application
1. Clone the repository
2. Navigate to the project directory
3. Compile and run the Java program
4. Use the GUI to log daily food intake, set calorie goals, and track progress

## Known Bugs & Limitations
- **Daily Logs Not Displaying in GUI**: While the daily logs are correctly saved in `log.csv`, they do not appear in the graphical interface.
- **Missing Graphs for Nutritional Statistics**: The application does not yet render graphs for macronutrient distribution.
- **Limited Validation**: Some inputs may not be properly validated, leading to potential inconsistencies in data.

## Team Presentation
Each team will give a 5-10 minute informal yet professional presentation covering:
- Design approach and decision rationale.
- Explanation of diagrams and system structure.
- Team dynamics, challenges, and successes.
- A short demo of completed features.
- Q&A session addressing peer questions.

## Design Principles
- **Composite Pattern**: Used for structuring food and recipes.
- **MVC Architecture**: Separation of model, view, and controller for clean design.
- **Adapter Pattern**: Facilitates integration between different components and ensures compatibility.
- **Factory Pattern**: Simplifies object creation and promotes modularity.
- **Data Persistence**: CSV storage ensures user data is retained between sessions.

## Future Improvements
- Fix GUI display issue for daily logs.
- Implement graphical charts for nutritional statistics.
- Enhance validation and error handling.
- Improve user experience with a more intuitive UI.

## Conclusion
The Wellness Manager Version 1.0 successfully implements a functional dietary tracking system with a structured data model. Although some graphical elements and minor bugs remain, the core features are in place to support user dietary tracking.

