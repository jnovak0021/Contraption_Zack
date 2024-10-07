import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
public class FloatingTile extends Mechanism
{
    //feature/jacob contructor
    public FloatingTile(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll)
    {
        super(property, activated, x, y, endX, endY, myColor,associatedMechanisms, ll);
    }

    public String toString()
    {
        return ("2:" + getProperty() + ":" + isActive() + ":" + getX() + ":" + getY() + ":" + getEndX() + ":" + getEndY() + ":" + getMyColor() + ":" + associatedMechanisms);
        //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>

    }

    @Override
    public void performFunction() {
        System.out.println("colliding with FloatTile");
    }

    public void drawMe(GraphicsContext gc) {
        // Print position information (uncomment if needed)
        // System.out.print(" [x-" + getX() + " y-" + getY() + "]");

        // Set fill color for the tile
        gc.setFill(getMyColor());
        // Draw the main tile rectangle
        gc.fillRect(getX(), getY(), getEndX()-getX(), getEndY()-getY());
    }
}
