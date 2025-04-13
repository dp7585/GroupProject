import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

public class SimpleNutritionGraph extends JPanel {
    private double fatPercent = 0;
    private double carbsPercent = 0;
    private double proteinPercent = 0;

    public SimpleNutritionGraph() {
        setPreferredSize(new Dimension(300, 200));
    }

    public void updateData(double fat, double carbs, double protein) {
        this.fatPercent = fat;
        this.carbsPercent = carbs;
        this.proteinPercent = protein;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();
        int barHeight = 20;
        int padding = 30;

        // Draw fat bar
        g2.setColor(Color.RED);
        double fatWidth = (fatPercent / 100) * (width - 50);
        g2.fill(new Rectangle2D.Double(30, padding, fatWidth, barHeight));
        g2.drawString("Fat: " + (int) fatPercent + "%", 5, padding + barHeight/2 + 5);

        // Draw carbs bar
        g2.setColor(Color.BLUE);
        double carbsWidth = (carbsPercent / 100) * (width - 50);
        g2.fill(new Rectangle2D.Double(30, padding * 2, carbsWidth, barHeight));
        g2.drawString("Carbs: " + (int) carbsPercent + "%", 5, padding * 2 + barHeight/2 + 5);

        // Draw protein bar
        g2.setColor(Color.GREEN);
        double proteinWidth = (proteinPercent / 100) * (width - 50);
        g2.fill(new Rectangle2D.Double(30, padding * 3, proteinWidth, barHeight));
        g2.drawString("Protein: " + (int) proteinPercent + "%", 5, padding * 3 + barHeight/2 + 5);

        // Draw title
        g2.setColor(Color.BLACK);
        g2.drawString("Nutrition Breakdown", width/2 - 50, 15);
    }
}