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
public class Wall extends Mechanism
{


    public Door(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms)
    {
        super(property, activated, x, y, endX, endY, myColor,associatedMechanisms);
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


    @Override
    // Method to draw the arrow within a rectangle defined bgetY() (x, y) and (endX, endY)
    public void drawMe(GraphicsContext gc)
    {


}