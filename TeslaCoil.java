import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TeslaCoil extends Mechanism {
    private boolean isFlashing = false;
    private double flashOpacity = 1.0; // Opacity for flashing effect
    private long lastUpdate = 0;

    // Constructor
    public TeslaCoil(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll) {
        super(property, activated, x, y, endX, endY, myColor, associatedMechanisms, ll);
        
        // Start the flashing animation if activated
        if (isActive()) {
            startFlashing();
        }
    }

    public void drawMe(GraphicsContext gc) {
        // Get the main rectangle dimensions
        double x = getX();
        double y = getY();
        double width = getEndX() - getX();
        double height = getEndY() - getY();

        // Calculate pole dimensions
        double poleDiameter = 20;

        // Draw the poles
        gc.setFill(isActive() ? Color.YELLOW : Color.GRAY);
        gc.fillOval(x, y, poleDiameter, poleDiameter);
        gc.fillOval(x + width - poleDiameter, y + height - poleDiameter, poleDiameter, poleDiameter);

        double lineThickness = 12;
        double lineStartX = x + (width / 2) - (lineThickness / 2); // Centered horizontally

        // Draw the flashing rectangle
        if (isActive()) {
            gc.setFill(Color.BLUEVIOLET.deriveColor(0, 1, 1, flashOpacity));
            gc.fillRect(lineStartX, y + poleDiameter, lineThickness, height - 2 * poleDiameter);
        } else {
            gc.strokeRect(lineStartX, y + poleDiameter, lineThickness, height - 2 * poleDiameter);
        }
    }

    private void startFlashing() {
        isFlashing = true;
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isActive()) {
                    // Change flash opacity over time
                    if (now - lastUpdate >= 500_000_000) { // Change every 500 ms
                        flashOpacity = (flashOpacity == 1.0) ? 0.0 : 1.0; // Toggle opacity
                        lastUpdate = now;
                    }
                } else {
                    // Stop flashing when inactive
                    flashOpacity = 1.0; // Reset to fully visible
                    stop(); // Stop the timer
                }
            }
        }.start();
    }

    public String toString() {
        return "Wall{" +
                "property='" + getProperty() + '\'' +
                "activate=" + isActive() +
                ", x=" + getX() +
                ", y=" + getY() +
                ", endX=" + getEndX() +
                ", endY=" + getEndY() +
                ", color=" + getColor() +
                '}';
    }

    public void performFunction() {
        // no function
    }
}
