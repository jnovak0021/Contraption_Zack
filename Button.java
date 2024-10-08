import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.*;

//Buttons are mechanisms with which Zack's can interact.
//They will have the same color as their associated mechanisms
//Property is either circular (C) or square (S)
//A button may be linked to a timer in some room
public class Button extends Mechanism
{
    private String property;

   public Button(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll)
   {
      super(property, activated, x, y, endX, endY, myColor, associatedMechanisms, ll);
      this.property = property;
   }
   
   public String getProperty() {
      return property;
   }

       
   public void drawMe(GraphicsContext gc){

      double opacity = 1;

      if(!isActive()){
         opacity = .5;
      }
      else {
         opacity = 1;
      }
      Color colorWithOpacity = new Color(getMyColor().getRed(), getMyColor().getGreen(), getMyColor().getBlue(), opacity); // Adjust the alpha value (0.5 for 50% opacity)
      gc.setFill(colorWithOpacity); // Now set the modified color with opacity
      //draw a simple square
      gc.fillRect(getX(),getY(), getWidth(), getLength());
   
   }

   public String toString()
   {
      return ("B:" + getProperty() + ":" + isActive() + ":" + getX() + ":" + getY() + ":" + getEndX() + ":" + getEndY() + ":" + getMyColor() + ":" + associatedMechanisms);
      //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>

   }

   //this method will be called from zack, and will activate/deactivate the correct spikes
   public void performFunction(){
      ArrayList <Mechanism> mechs = ll.getAssociatedMechanisms(associatedMechanisms);

      if(getAssociatedMechanisms() == 81)
      {
         for(int i=0; i < mechs.size(); i++){
            //dont activate other buttons
            if(!(mechs.get(i) instanceof Button) && !(mechs.get(i) instanceof WallSwitch) && !(mechs.get(i) instanceof Treadmill) && !(mechs.get(i) instanceof Screw) && !(mechs.get(i) instanceof Spring))
               mechs.get(i).activate();//holy spirit activate
         }

      }
      else if(getAssociatedMechanisms() == 90)
      {
         for(int i=0; i < mechs.size(); i++){
            //dont activate other buttons
            if(!(mechs.get(i) instanceof Button) && !(mechs.get(i) instanceof WallSwitch) && !(mechs.get(i) instanceof Treadmill) && !(mechs.get(i) instanceof Screw) && !(mechs.get(i) instanceof Spring && !(mechs.get(i) instanceof Pulley )))
               mechs.get(i).activate();//holy spirit activate
         }

      }
      else {
         for (int i = 0; i < mechs.size(); i++) {

            //dont activate other buttons
            if (!(mechs.get(i) instanceof Button)  && !(mechs.get(i) instanceof Pulley))
               mechs.get(i).activate();//holy spirit activate
         }
      }
   
   }
   
   
}

//get int associatedMechanisms
