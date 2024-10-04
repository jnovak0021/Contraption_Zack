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

//Spikes are mechanisms that Impede Zack's progress.
//Their color will depend on which button they are linked to
//Spikes usually occupy three consecutive tiles and can be oriented horizontally or vertically 
public class Spike extends Mechanism
{
    public Spike(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll)
    {
        super(property, activated, x, y, endX, endY, myColor,associatedMechanisms, ll);
    }
    
        
    public  void drawMe(GraphicsContext gc){
        if(isActive()){
            drawSpikeTrap(gc,getMyColor());
        }
    }
    
    // Method to draw a spike trap with specified color
    private void drawSpikeTrap(GraphicsContext gc, Color spikeColor){
        // Set the color for the spikes
        gc.setFill(spikeColor);
        
        // Draw three spikes
        if(getProperty().equals("H")){ // Horizontal
            drawSpike(gc, getX(), getEndY());
            drawSpike(gc, getX()+27, getEndY());
            drawSpike(gc, getX()+54, getEndY());
        }
        if(getProperty().equals("V")){//vertical
            drawSpike(gc, getX(), getEndY()-57);
            drawSpike(gc, getX(), getEndY()-27);
            drawSpike(gc, getX(), getEndY());
        }
    }

    // Method to draw a single spike as a triangle
    private void drawSpike(GraphicsContext gc, double xOffset, double height){
        double[] xPoints = {xOffset, xOffset + 10, xOffset + 5};
        double[] yPoints = {height, height, height - 20};
        if(isActive()){
            gc.fillPolygon(xPoints, yPoints, 3);
        }
        else{
            gc.strokePolygon(xPoints, yPoints, 3);
        }
    }

    public String toString()
    {
        return "Spike{" +
                "property='" + getProperty() + '\'' + // Adjust according to your propertgetY() type
                "activate=" + isActive() +
                ", x=" + getX() +
                ", y=" + getY() +
                ", endX=" + getEndX() +
                ", endY=" + getEndY() +
                ", color=" + getColor() +
                '}';

    }

    //do nothing for spike
    public void performFunction(){
    }
}
