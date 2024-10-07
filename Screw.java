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
public class Screw extends Mechanism
{
    //store the start time
   private double activatedStartTime;

   public Screw(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll)
   {
      super(property, activated, x, y, endX, endY, myColor, associatedMechanisms, ll);
   }


   public void drawMe(GraphicsContext gc) {
      double screwWidth = 20;
      double screwHeight = getEndY() - getY(); // Use the existing height from properties
      double headDiameter = 30;
      double threadHeight = 10;
   
    // Set color for the screw body based on activation state
      if (isActive()) {
         gc.setFill(Color.GREENYELLOW); // Active color
      } else {
         gc.setFill(Color.RED); // Inactive color
      }
   
    // Draw the screw body (rectangle)
      gc.fillRect(getX(), getY(), screwWidth, screwHeight);
   
    // Draw the screw head (circle)
      gc.setFill(Color.DARKGRAY);
      gc.fillOval(getX() - (headDiameter - screwWidth) / 2, getY() - headDiameter / 2, headDiameter, headDiameter);
   
    // Draw the threads (lines)
      gc.setStroke(Color.BLACK);
      gc.setLineWidth(2);
    
      for (int i = 0; i < screwHeight / threadHeight; i++) {
         double threadY = getY() + i * threadHeight;
         gc.strokeLine(getX(), threadY, getX() + screwWidth, threadY);
      }
   }

   public String toString()
   {
      return ("11:" + getProperty() + ":" + isActive() + ":" + getX() + ":" + getY() + ":" + getEndX() + ":" + getEndY() + ":" + getMyColor() + ":" + associatedMechanisms);
        //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
   
   }



   @Override
    public void performTimedFunction() {
   
      if(activatedStartTime == 0) {
         activatedStartTime = getClock().getElapsedTime();
         activate();
         performFunction();
      }
      else if(getClock().getElapsedTime() - activatedStartTime > 1)
         activatedStartTime = 0;
   
   }

    //if zack has the screwdriver set all values isActive
   public void performFunction(){
        //should only run one time
      if(isActive()){}
      else {
         activate();
         ArrayList <Mechanism> mechs = ll.getAssociatedMechanisms(associatedMechanisms);
      
         for(int i=0; i < mechs.size(); i++) {
            if((mechs.get(i) instanceof Treadmill) )
            {
               mechs.get(i).setActivated(isActive());
            }
            else if(mechs.get(i) instanceof Spring)
            {
               mechs.get(i).setActivated(!isActive());
            
            }
         }
      }
   
   
   }
}