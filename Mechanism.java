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
//Mechanisms are objects that zack interacts with, and are sometimes linked to other mechanisms.
//Usually a mechanism will occupy more than one tile, and will be drawn as such by its graphics method.
//Mechanism is still an abstract class because only children classes (i.e. spikes, buttons, springs) can be insantiated

public abstract class Mechanism extends GameObject 
{
   // Private variable to store the property of the object
   private String property;
   //boolean for if mechanism is activated
   boolean activated = false;

   //store the array value that mechanism is apart of
   private int associatedMechanisms;

   //takes in a property which is specific to each mechanism, the starting and ending x,y, color of object, and the int value of the mechanisms that are associated with the array
   public Mechanism(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms)
   {
      super(x, y, endX, endY, myColor); // Call parent constructor
      this.property = property;
      this.activated = activated;
      this.associatedMechanisms = associatedMechanisms;
   }

   /*
   //method to determine collision
   public boolean collides(int zackX, int zackY)
   {
      if((zackX <= getX()) && ( zackY >= getY() && zackY <= ) )

   }
   */
   
   public boolean isActive()
   {
      return activated;
   }
   
   public void activate()
   {
      if(activated)
         activated = false;
      else 
         activated = true;
   }

   // Accessor and mutator for property
   public String getProperty() 
   {
      return property;
   }

   public void setProperty(String property) 
   {
      this.property = property;
   }

   // Getter and setter methods for GameObject's properties
   public int getX() 
   {
      return super.getX(); // Assuming getX() is defined in GameObject
   }

   public void setX(int x) 
   {
      super.setX(x); // Assuming setX() is defined in GameObject
   }

   public int getY() 
   {
      return super.getY(); // Assuming getY() is defined in GameObject
   }

   public void setY(int y) 
   {
      super.setY(y); // Assuming setY() is defined in GameObject
   }

   public int getEndX() 
   {
      return super.getEndX(); // Assuming getEndX() is defined in GameObject
   }

   public void setEndX(int endX) 
   {
      super.setEndX(endX); // Assuming setEndX() is defined in GameObject
   }

   public int getEndY() 
   {
      return super.getEndY(); // Assuming getEndY() is defined in GameObject
   }

   public void setEndY(int endY) 
   {
      super.setEndY(endY); // Assuming setEndY() is defined in GameObject
   }

   public Color getColor() 
   {
      return super.getColor(); // Assuming getColor() is defined in GameObject
   }

   public void setColor(Color color) 
   {
      super.setColor(color); // Assuming setColor() is defined in GameObject
   }

   //getting the int value of the mechanismArray that the item is associated with
   public int getAssociatedMechanisms()
   {
      return associatedMechanisms;
   }

   //don't have a usecase yet but method to set the AssociatedMechanisms
   public void setAssociatedMechanisms(int associatedMechanisms)
   {
      this.associatedMechanisms = associatedMechanisms;
   }
 
   public abstract String toString();

   public abstract void drawMe(GraphicsContext gc);
}
