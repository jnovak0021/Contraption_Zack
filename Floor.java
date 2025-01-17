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

//Floors are tiles that Zack can walk on.
//Their color depending on what the theme of the room is

public class Floor extends Tile
{
   public Floor(int x, int y, Color myColor, Color secondaryColor)
   {
      super(x, y, x + width, y + length, myColor, secondaryColor, true);
   }

    public Floor(int x, int y, Color myColor)
    {
        super(x, y, x + width, y + length, myColor, true);
    }




    public String toString()
    {
      return "1";

   }   
   public void drawMe(GraphicsContext gc) 
   {
       // Print position information (uncomment if needed)
       // System.out.print(" [x-" + getX() + " y-" + getY() + "]");

       // Set fill color for the tile
       gc.setFill(getMyColor());
       // Draw the main tile rectangle
       gc.fillRect(getX(), getY(), 80, 80);
   
       // Set stroke color for the border
       gc.setStroke(getSecondaryColor());
       gc.setLineWidth(2); // Optional: set the border thickness
       // Draw the border rectangle
       gc.strokeRect(getX(), getY(), 80, 80);
   }


}

