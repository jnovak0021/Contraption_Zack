import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;


public class TeslaCoil extends Mechanism
{
    // Constructor
    //public Wall(int x, int y, int width, int length)
    public TeslaCoil(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll)
    {
        super(property, activated, x, y, endX, endY, myColor, associatedMechanisms, ll);

    }


    public void drawMe(GraphicsContext gc) {
        // Get the main rectangle dimensions
        double x = getX();
        double y = getY();
        double width = getEndX() - getX();
        double height = getEndY() - getY();

        // Calculate pole dimensions (poles are 20x20 circles regardless of the total size)
        double poleDiameter = 20;

        // IF TESLA COIL IS ACTIVE, DRAW POLES AS YELLOW
        if (isActive()) {
            gc.setFill(Color.YELLOW);
        } else {
            gc.setFill(Color.GRAY);
        }

        // Draw the left pole at the top-left edge
        gc.fillOval(x, y, poleDiameter, poleDiameter);

        // Draw the right pole at the bottom-right edge
        gc.fillOval(x + width - poleDiameter, y + height - poleDiameter, poleDiameter, poleDiameter);

        double lineThickness = 12;
        double lineStartX = x + (width / 2) - (lineThickness / 2); // Centered horizontally

        // IF TESLA COIL IS ACTIVE, MAKE THE LINE IN THE MIDDLE BLUE
        if (isActive()) {
            // Set fill color for the line
            gc.setFill(Color.BLUEVIOLET);

            // Draw a vertical line between the top and bottom poles
            gc.fillRect(lineStartX, y + poleDiameter, lineThickness, height - 2 * poleDiameter);
        } else {
            gc.strokeRect(lineStartX, y + poleDiameter, lineThickness, height - 2 * poleDiameter);
        }
    }



    public String toString()
    {
        return "Wall{" +
                "property='" + getProperty() + '\'' + // Adjust according to your propertgetY() type
                "activate=" + isActive() +
                ", x=" + getX() +
                ", y=" + getY() +
                ", endX=" + getEndX() +
                ", endY=" + getEndY() +
                ", color=" + getColor() +
                '}';

    }

    public void performFunction(){
        //no function
    }
}