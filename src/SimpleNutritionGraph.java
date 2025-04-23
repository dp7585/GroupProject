import java.awt.*;
import javax.swing.*;

public class SimpleNutritionGraph extends JPanel {
    private int fatPercent = 0;
    private int carbsPercent = 0;
    private int proteinPercent = 0;

    public SimpleNutritionGraph() {
        setPreferredSize(new Dimension(300, 200));
        setBorder(BorderFactory.createTitledBorder("Nutrition Breakdown"));
    }

    public void updateData(double fat, double carbs, double protein) {
        // Ensure percentages add up to 100
        double total = fat + carbs + protein;
        if (total != 0) {  // Only calculate if we have data
            // Calculate percentages
            fatPercent = (int) Math.round((fat / total) * 100);
            carbsPercent = (int) Math.round((carbs / total) * 100);
            proteinPercent = 100 - fatPercent - carbsPercent; // Ensure total is 100%
        } else {
            fatPercent = 0;
            carbsPercent = 0;
            proteinPercent = 0;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight() - 30;
        int barWidth = 60;
        int padding = 20;
        int startX = (width - (3 * barWidth + 2 * padding)) / 2;
        int startY = 30;

        // Draw fat bar (red)
        drawBar(g2, startX, startY, barWidth, height, fatPercent, Color.RED, "Fat");

        // Draw carbs bar (green)
        drawBar(g2, startX + barWidth + padding, startY, barWidth, height, carbsPercent, Color.GREEN, "Carbs");

        // Draw protein bar (blue)
        drawBar(g2, startX + 2 * (barWidth + padding), startY, barWidth, height, proteinPercent, Color.BLUE, "Protein");
    }

    private void drawBar(Graphics2D g2, int x, int y, int width, int totalHeight, int percent, Color color, String label) {
        int barHeight = (int) (percent / 100.0 * (totalHeight - 40));
        g2.setColor(color);
        g2.fillRect(x, y + (totalHeight - 40 - barHeight), width, barHeight);
        
        g2.setColor(Color.BLACK);
        // Draw label at bottom
        g2.drawString(label, x, y + totalHeight - 20);
        // Draw percentage at top of bar
        if (barHeight > 15) {
            g2.drawString(percent + "%", x, y + totalHeight - 40 - barHeight - 5);
        }
    }
}