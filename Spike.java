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

//Walls are mechanisms that Impede Zack's progress.
//Their color will depend on which button they are linked to
//Spikes usually occupy three consecutive tiles and can be oriented horizontally or vertically 
public class Spike extends Mechanism
{
    public Spike(int x, int y, int endX, int endY, Color myColor)
    {
        super(x, y, endX, endY,  myColor); //call parent constructor
        
    }
    
        
    public  void drawMe(GraphicsContext gc)
    {
      drawSpikeTrap(gc, Color.YELLOW);
    }
    
    // Method to draw a spike trap with specified color
    private void drawSpikeTrap(GraphicsContext gc, Color spikeColor) 
    {
        // Set the color for the spikes
        gc.setFill(spikeColor);

        // Draw three spikes
        drawSpike(gc, 0, 80);
        drawSpike(gc, 6, 80);
        drawSpike(gc, 12, 80);
    }

    // Method to draw a single spike as a triangle
    private void drawSpike(GraphicsContext gc, double xOffset, double height) 
    {
        double[] xPoints = {xOffset, xOffset + 10, xOffset + 5};
        double[] yPoints = {height, height, height - 20};
        gc.fillPolygon(xPoints, yPoints, 3);
    }
}
