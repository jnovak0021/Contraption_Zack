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
//Items are objects that Zack can collect from the ground and add to his inventory
//Item is still an abstract class because only children classes (i.e.screwdrivers, wrenches) can be insantiated

//Item.java by Dorian Quimby 05OCT2024
public class Screwdriver extends Item 
{
   
   static int width = 80;
   static int length = 20;
   
   //location of item and associated mechanism (for screwdriver)
   public Screwdriver(int x, int y, int roomStored, LoadLevel ll)
   {
      super(roomStored, x, y, x+width, y+length, ll); // Call parent constructor
      this.ll = ll;
   }

 
public void drawMe(GraphicsContext gc) {
    if (!isCollected()) {
        // Draw a smaller blue rectangle
        gc.setFill(Color.BLUE);
        gc.fillRect(getX(), getY(), width * 0.5, length * 0.5); // Making it 50% smaller

        // Draw a skinnier grey rectangle connected to the blue one
        gc.setFill(Color.GRAY);
        gc.fillRect(getX() + (width * 0.5), getY() + 2, 45, length * 0.3); // 10 pixels wide, same height as the smaller blue rectangle
    }
}

   @Override
   public String toString()
   {
       return ("ScrewDriver:" + getX() + ":" + getY() + ":" + getRoomStored());
   }

}
