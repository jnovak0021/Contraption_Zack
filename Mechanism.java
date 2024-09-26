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

   private LoadLevel ll;   //store ll

   //takes in a property which is specific to each mechanism, the starting and ending x,y, color of object, and the int value of the mechanisms that are associated with the array
   public Mechanism(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll)
   {
      super(x, y, endX, endY, myColor); // Call parent constructor
      this.ll = ll;
      this.property = property;
      this.activated = activated;
      this.associatedMechanisms = associatedMechanisms;
   }


   //loadlevel get/set
   public LoadLevel getLL()
   {
      return ll;
   }



   //stores whether the
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

   //abstract method to perform the functoin of each mechanism
   //i.e -- do nothing for wall, but for door, read in property and go to next room
   public abstract void performFunction();
}
