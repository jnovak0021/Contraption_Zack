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
    // Constructor
    public Wall(int x, int y, int width, int length)
    {
        super("wall", true, x, y, x+width, y+length, Color.PINK, 0);
    }
    
    
    public void drawMe(GraphicsContext gc){
        // Print position information (uncomment if needed)
        // System.out.print(" [x-" + getX() + " y-" + getY() + "]");
   
        // Set fill color for the tile
        gc.setFill(getMyColor());
        // Draw the main tile rectangle
        gc.fillRect(getX(), getY(), getEndX()-getX(), getEndY()-getY());
   
    }
    
    public String toString(){
        return "";
    }
}