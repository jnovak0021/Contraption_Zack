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
import java.util.Random;


//Floors are tiles that Zack can walk on.
//Their color depending on what the theme of the room is

public class Water extends Tile
{
    public Water(int x, int y, Color myColor)
    {
        super(x, y, x + width, y + length, myColor, false);
    }



    public String toString()
    {
        return "Water";

    }
    public void drawMe(GraphicsContext gc)
    {
        // Print position information (uncomment if needed)
        // System.out.print(" [x-" + getX() + " y-" + getY() + "]");

        // Set fill color for the tile
        gc.setFill(getMyColor());
        // Draw the main tile rectangle
        gc.fillRect(getX(), getY(), getEndX() - getX(), getEndY() - getY());

        // Set stroke color for the border
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2); // Optional: set the border thickness
        // Draw the border rectangle
        gc.strokeRect(getX(), getY(), getEndX() - getX(), getEndY() - getY());

        // Set up the random number generator
        Random random = new Random();
        double largeRectHeight = getEndY() - getY();
        double smallRectHeight = largeRectHeight / 40.0;

        for (int i = 0; i < 20; i++) {
            // Generate random x and y coordinates for the top-left corner of the small rectangle
            double randX = getX() + random.nextDouble() * (getEndX() - getX());
            double randY = getY() + random.nextDouble() * (largeRectHeight - smallRectHeight);

            // Generate random width that fits within the larger rectangle's width
            double maxWidth = getEndX() - randX;
            double randWidth = random.nextDouble() * maxWidth;

            // Set random color for each small rectangle
            gc.setFill(new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1.0));

            // Draw the small rectangle
            gc.fillRect(randX, randY, randWidth, smallRectHeight);
        }
    }


}

