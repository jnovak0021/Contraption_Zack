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
public abstract class Item extends GameObject 
{
   boolean collected;
   int roomStored;
   //store the array value that mechanism is apart of
   protected LoadLevel ll;   //store ll
   
   //takes in a property which is specific to each mechanism, the starting and ending x,y, color of object, and the int value of the mechanisms that are associated with the array
   public Item(int roomStored, int x, int y, int endX, int endY, LoadLevel ll)
   {
      super(x, y, endX, endY, Color.BLACK); // Call parent constructor
      this.ll = ll;
      collected = false;
      this.roomStored = roomStored;
   }


   //loadlevel get/set
   public LoadLevel getLL()
   {
      return ll;
   }


   public boolean isCollected()
   {
      return collected;
   }
   
   public void collect()
   {
       collected = true;
   }
   
   public int getRoomStored()
   {
       return roomStored;
   }


   public abstract void drawMe(GraphicsContext gc);

}
