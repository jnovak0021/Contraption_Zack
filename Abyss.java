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

public class Abyss extends Tile
{

   public Abyss(int x, int y)
   {
      super(x, y, x + width, y + length, Color.BLACK, false);
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
       gc.fillRect(getX(), getY(), 80, 80);
   
       // Set stroke color for the border
       gc.setStroke(Color.BLACK);
       gc.setLineWidth(2); // Optional: set the border thickness
       // Draw the border rectangle
       gc.strokeRect(getX(), getY(), 80, 80);
   }
   */
   
   //draw me method that takes in gc and draws black box
   public void drawMe(GraphicsContext gc)
   {
      //draw a black rectangle
      gc.setFill(getMyColor());
      gc.fillRect(getX(),getY(), width, length);

   }
   //draw me method that takes in gc and draws black box
   public void drawMe(GraphicsContext gc, Color in)
   {
      //draw a black rectangle
      gc.setFill(in);
      gc.fillRect(getX(),getY(), width, length);

   }
   
}