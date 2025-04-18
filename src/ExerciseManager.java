import java.io.*;
import java.util.*;

public class ExerciseManager {
    // Path to file
    private static final String FILE_NAME = "./data/exercise.csv";
    private final Map<String, Exercise> exerciseMap = new HashMap<>();

    // Load exercises from CSV file
    public void loadFromFile() {
        exerciseMap.clear();
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Must start with e (be an exercise)
                if (!line.startsWith("e,")) continue;

                String[] parts = line.split(",", 3);
                if (parts.length != 3) continue;

                String name = parts[1];
                double calories = Double.parseDouble(parts[2]);

                if (!exerciseMap.containsKey(name)) {
                    exerciseMap.put(name, new Exercise(name, calories));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading file: " + e.getMessage());
        }
    }

    // Save exercises to CSV file
    public void saveToFile() {
        // Writes text to files
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Exercise ex : exerciseMap.values()) {
                pw.println(ex.toString());
            }
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }

    // Add an exercise (must be unique)
    public boolean addExercise(String name, double calories) {
        // Confirms unique, comma-validated format
        if (name.contains(",") || exerciseMap.containsKey(name)) {
            return false;
        }
        exerciseMap.put(name, new Exercise(name, calories));
        return true;
    }

    // Get all exercises
    public Collection<Exercise> getAllExercises() {
        return exerciseMap.values();
    }

    // Get exercise by name
    public Exercise getExercise(String name) {
        return exerciseMap.get(name);
    }
}