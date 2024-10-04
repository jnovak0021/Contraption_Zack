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


public class Stanchion extends Mechanism
{
    // Constructor
    //public Wall(int x, int y, int width, int length)
    public Stanchion(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll)
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

        // Set orientation based on property ("H" for horizontal, "V" for vertical)
        boolean isVertical = getProperty().equals("V");

        // Adjust coordinates for vertical orientation if necessary
        /*if (isVertical) {
            double temp = width;
            width = height;
            height = temp;
        }*/

        // Set fill color for the poles (assuming the color of poles is black)
        gc.setFill(getMyColor());

        if (isVertical) {
            // Draw the top pole as a circle, centered horizontally
            gc.fillOval(x + (width / 2) - (poleDiameter / 2), y, poleDiameter, poleDiameter);

            // Draw the bottom pole as a circle, centered horizontally
            gc.fillOval(x + (width / 2) - (poleDiameter / 2), y + height - poleDiameter, poleDiameter, poleDiameter);
        } else {
            // Draw the left pole as a circle, centered vertically
            gc.fillOval(x, y + (height / 2) - (poleDiameter / 2), poleDiameter, poleDiameter);

            // Draw the right pole as a circle, centered vertically
            gc.fillOval(x + width - poleDiameter, y + (height / 2) - (poleDiameter / 2), poleDiameter, poleDiameter);
        }

        // Set fill color for the line (red)
        gc.setFill(Color.RED);

        // Increase the middle line width by 10 pixels
        double lineThickness = 12;

        if (isVertical) {
            // For vertical orientation, draw a vertical line between the top and bottom poles
            double lineStartX = x + (width / 2) - (lineThickness / 2); // Centered horizontally
            gc.fillRect(lineStartX, y + poleDiameter, lineThickness, height - 2 * poleDiameter);
        } else {
            // For horizontal orientation, draw a horizontal line between the left and right poles
            double lineY = y + (height / 2) - (lineThickness / 2); // Centered vertically
            gc.fillRect(x + poleDiameter, lineY, width - 2 * poleDiameter, lineThickness);
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