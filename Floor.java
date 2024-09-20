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

//Floors are tiles that Zack can walk on.
//Their color depending on what the theme of the room is

import javafx.scene.paint.Color;

public class Floor extends Tile
{
   public Floor(int xPos, int yPos, Color myColor)
   {
      super(xPos, yPos, myColor); //call parent constructor
        
   }
    
   public String toString()
   {
      return "Floor";
   }   
   public void drawMe(Pane pane) {
    // Print position information
     // System.out.print(" [x-" + getX() + " y-" + getY() + "]");
   
    // Create the main tile rectangle
      Rectangle rectangle = new Rectangle((getX() * 100), (getY() * 100), 100, 100);
      rectangle.setFill(getMyColor());
   
    // Create the border rectangle
      Rectangle borderRectangle = new Rectangle((getX() * 100), (getY() * 100), 100, 100);
      borderRectangle.setStroke(Color.DARKGRAY);
      borderRectangle.setFill(Color.TRANSPARENT); // Make the border transparent
      borderRectangle.setStrokeWidth(2); // Optional: set the border thickness
   
    // Add both rectangles to the pane
      pane.getChildren().addAll(rectangle, borderRectangle);
   }}

