import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Screw extends Mechanism {
   private double activatedStartTime;
   private double angle = 0; // Angle for rotation
   private AnimationTimer animationTimer;

   public Screw(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll) {
      super(property, activated, x, y, endX, endY, myColor, associatedMechanisms, ll);
   }

   public void drawMe(GraphicsContext gc) {
      double screwWidth = 20;
      double screwHeight = getEndY() - getY();
      double headDiameter = 30;
   
    // Set color for the screw body based on activation state
      if (isActive()) {
         gc.setFill(Color.GREENYELLOW);
      } else {
         gc.setFill(Color.RED);
      }
   
    // Save the current transformation state
      gc.save();
   
    // Translate to the center of the screw and rotate
      gc.translate(getX() + screwWidth / 2, getY() + screwHeight / 2);
      gc.rotate(angle);
      gc.translate(-(getX() + screwWidth / 2), -(getY() + screwHeight / 2));
   
    // Draw the screw body (rectangle)
      gc.fillRect(getX(), getY(), screwWidth, screwHeight);
   
    // Draw the screw head (circle)
      gc.setFill(Color.DARKGRAY);
      gc.fillOval(getX() - (headDiameter - screwWidth) / 2, getY() - headDiameter / 2, headDiameter, headDiameter);
   
    // Draw a single line in the middle of the screw
      gc.setStroke(Color.BLACK);
      gc.setLineWidth(2);
      double lineY = getY() + screwHeight / 2; // Middle of the screw height
      gc.strokeLine(getX(), lineY, getX() + screwWidth, lineY); // Draw one line
   
    // Restore the previous transformation state
      gc.restore();
   }

   @Override
    public void performFunction() {
      if (!isActive()) {
         activate();
         startSpinning();
      
            // Stop spinning after 3 seconds
            new AnimationTimer() {
               private double elapsedTime = 0;
            
               @Override
                public void handle(long now) {
                  elapsedTime += 1.0 / 60; // Assume 60 FPS
                  if (elapsedTime >= 3) {
                     stopSpinning();
                     stop(); // Stop this timer
                  }
               }
            }.start();
      
         ArrayList<Mechanism> mechs = ll.getAssociatedMechanisms(associatedMechanisms);
         for (Mechanism mech : mechs) {
            if (mech instanceof Treadmill) {
               mech.setActivated(isActive());
            } else if (mech instanceof Spring) {
               mech.setActivated(!isActive());
            }
         }
      }
   }

   private void startSpinning() {
      if (animationTimer == null) {
         animationTimer = 
            new AnimationTimer() {
               @Override
                public void handle(long now) {
                  angle += 2; // Adjust the speed of rotation
                  if (angle >= 360) {
                     angle = 0; // Reset angle to avoid overflow
                  }
               }
            };
         animationTimer.start();
      }
   }

   private void stopSpinning() {
      if (animationTimer != null) {
         animationTimer.stop();
         animationTimer = null; // Reset to allow future spinning if needed
      }
   }

   @Override
    public String toString() {
      return ("11:" + getProperty() + ":" + isActive() + ":" + getX() + ":" + getY() + ":" + getEndX() + ":" + getEndY() + ":" + getMyColor() + ":" + associatedMechanisms);
   }

   @Override
    public void performTimedFunction() {
      if (activatedStartTime == 0) {
         activatedStartTime = getClock().getElapsedTime();
         performFunction();
      } else if (getClock().getElapsedTime() - activatedStartTime > 1) {
         activatedStartTime = 0;
      }
   }
}
