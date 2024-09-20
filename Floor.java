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
import javafx.scene.paint.Color;

//Floors are tiles that Zack can walk on.
//Their color depending on what the theme of the room is

public class Floor extends Tile
{

   public Floor(int x, int y, int endX, int endY, Color myColor, boolean traverseable)
   {
      super(x,y, endX, endY, myColor, traverseable);
   }

   public Floor(int x, int y, Color myColor, boolean traverseable)
   {
      super(x, y, myColor, traverseable); //call parent constructor

        
    }
    
    public String toString()
    {
      return "Floor";

   }   
   public void drawMe(Pane pane) 
   {
     // Print position information
     // System.out.print(" [x-" + getX() + " y-" + getY() + "]");
     System.out.println("tile:\n\t" + getX() + "\t" + getY());
   
     // Create the main tile rectangle
      Rectangle rectangle = new Rectangle((getX()), (getY()), 100, 100);
      rectangle.setFill(getMyColor());
   
    // Create the border rectangle
      Rectangle borderRectangle = new Rectangle((getX()), (getY()), 100, 100);
      borderRectangle.setStroke(Color.DARKGRAY);
      borderRectangle.setFill(Color.TRANSPARENT); // Make the border transparent
      borderRectangle.setStrokeWidth(2); // Optional: set the border thickness
   
    // Add both rectangles to the pane
      pane.getChildren().addAll(rectangle, borderRectangle);
   }
}

