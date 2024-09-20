import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


public class Abyss extends Tile
{
   public Abyss(int x, int y, int endX, int endY, Color myColor, boolean traverseable)
   {
      super(x,y, endX, endY, myColor, traverseable);
   }
   
   public String toString()
   {
      return "Abyss";
   }
   
   /*
   public void drawMe(GraphicsContext gc) 
   {
       // Print position information (uncomment if needed)
       // System.out.print(" [x-" + getX() + " y-" + getY() + "]");
   
       // Set fill color for the tile
       gc.setFill(Color.BLACK);
       // Draw the main tile rectangle
       gc.fillRect(getX(), getY(), 100, 100);
   
       // Set stroke color for the border
       gc.setStroke(Color.BLACK);
       gc.setLineWidth(2); // Optional: set the border thickness
       // Draw the border rectangle
       gc.strokeRect(getX(), getY(), 100, 100);
   }
   */
   
   //draw me method that takes in gc and draws black box
   public void drawMe(GraphicsContext gc)
   {
   //draw a black rectangle
   gc.setFill(Color.BLACK);
   gc.fillRect(getX(),getY(), 50, 50);

   }
   
}