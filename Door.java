import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.transform.Rotate;

import java.util.HashSet;
import java.util.Set;

public class Door extends Mechanism 
{

   private String property;
   private int roomNum;
   private char doorLetter;

    //feature/jacob contructor
   public Door(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll)
   {
      super(property, activated, x, y, endX, endY, myColor,associatedMechanisms, ll);
      this.property = property;
      this.roomNum = roomNum;
      this.doorLetter = doorLetter;
   }
    
   public String getProperty() {
      return property;
   }

   @Override
    public void performFunction() {
   
      switch(getProperty())
      {
         case "0A":
            getLL().setCurrentRoomNumber(1);
            break;
         case "1A":
            getLL().setCurrentRoomNumber(0);
            break;
         case "1B":
            getLL().setCurrentRoomNumber(2);
            break;
         case "2A":
            getLL().setCurrentRoomNumber(1);
            break;
         case "2C":
            getLL().setCurrentRoomNumber(3);
            break;
         case "2B":
            getLL().setCurrentRoomNumber(5);
            break;
         case "3A":
            getLL().setCurrentRoomNumber(2);
            break;
         case "3B":
            getLL().setCurrentRoomNumber(4);
            break;
         case "3C":
            getLL().setCurrentRoomNumber(4);
            break;
         case "4A":
            getLL().setCurrentRoomNumber(3);
            break;
         case "4B":
            getLL().setCurrentRoomNumber(3);
            break;
         case "5A":
            getLL().setCurrentRoomNumber(2);
            break;
         case "5B":
            getLL().setCurrentRoomNumber(6);
            break;
         case "5C":
            getLL().setCurrentRoomNumber(6);
            break;
         case "6A":
            getLL().setCurrentRoomNumber(5);
            break;
         case "6B":
            getLL().setCurrentRoomNumber(5);
            break;
         case "6C":
            getLL().setCurrentRoomNumber(7);
            break;
         case "6D":
            getLL().setCurrentRoomNumber(7);
            break;
         case "7A":
            getLL().setCurrentRoomNumber(6);
            break;
         case "7B":
            getLL().setCurrentRoomNumber(6);
            break;
         case "7C":
            getLL().setCurrentRoomNumber(8);
            break;
         case "7D":
            getLL().setCurrentRoomNumber(8);
            break;
         case "8A":
            getLL().setCurrentRoomNumber(7);
            break;
         case "8B":
            getLL().setCurrentRoomNumber(7);
            break;
         case "8C":
            getLL().setCurrentRoomNumber(9);
            break;
         case "9A":
            getLL().setCurrentRoomNumber(8);
            break;
         case "9B":
            getLL().setCurrentRoomNumber(10);
            break;
      
      
         default:
            getLL().setCurrentRoomNumber(Integer.parseInt(getProperty()));
            
            //getLL().setCurrentRoomNumber(
            break;
      
      
      
      }
      System.out.print(getLL().getCurrentRoomNumber());
   }

   public String toString()
   {
      return "Door{" +
              "property='" + getProperty() + '\'' + // Adjust according to your propertgetY() type
              "activate=" + isActive() +
              ", x=" + getX() +
              ", y=" + getY() +
              ", endX=" + getEndX() +
              ", endY=" + getEndY() +
              ", color=" + getColor() +
              '}';
   }


   @Override
   public void drawMe(GraphicsContext gc)
   {
      // Save the current state of the GraphicsContext
      gc.save();

      // Calculate the center of the door
      double centerX = getX() + (getEndX() - getX()) / 2;
      double centerY = getY() + (getEndY() - getY()) / 2;

      // Determine the rotation angle based on the property
      double angle = 0;
      if(getProperty().equals("2A") || getProperty().equals("3A") || getProperty().equals("4A") || getProperty().equals("4B") || getProperty().equals("7A") || getProperty().equals("7B") || getProperty().equals("1A") )
      {
         angle = 180;
      }
      else if(getProperty().equals("5A") || getProperty().equals("6A") || getProperty().equals("6B") || getProperty().equals("7C") || getProperty().equals("7D") || getProperty().equals("8C") || getProperty().equals("9B"))
      {
         angle = 90;
      }
      else if(getProperty().equals("2B") || getProperty().equals("5C") || getProperty().equals("5B") || getProperty().equals("8A") || getProperty().equals("8B") || getProperty().equals("9A"))
      {
         angle = 270;
      }
      else
      {
         angle = 0;
      }
         /*
      switch(getProperty()) {
         case "S":
            angle = 180;
            break;
         case "E":
            angle = 90;
            break;
         case "W":
            angle = 270;
            break;
         case "N":
         default:
            angle = 0;
            break;
      }
   */
      // Apply the rotation
      Rotate rotate = new Rotate(angle, centerX, centerY);
      gc.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(), rotate.getTy());

      // Draw the grey rectangle
      gc.setFill(Color.GREY);
      gc.fillRect(getX(), getY(), (getEndX() - getX()), (getEndY() - getY()));

      // Draw the red arrow
      gc.setFill(Color.RED);

      // Calculate width and height of the rectangle
      double width = getEndX() - getX();
      double height = getEndY() - getY();

      // Define the points of the arrow, scaled to fit inside the rectangle
      double[] xPoints = {
              getX() + width * 0.5,  // Tip of the arrow (middle top)
              getX() + width * 1.0,  // Right corner
              getX() + width * 0.75, // Bottom right corner
              getX() + width * 0.75, // Bottom right tail
              getX() + width * 0.25, // Bottom left tail
              getX() + width * 0.25, // Bottom left corner
              getX() + width * 0.0   // Left corner
      };

      double[] yPoints = {
              getY() + height * 0.0,  // Tip of the arrow
              getY() + height * 0.5,  // Right corner
              getY() + height * 0.5,  // Bottom right corner
              getY() + height * 1.0,  // Bottom right tail
              getY() + height * 1.0,  // Bottom left tail
              getY() + height * 0.5,  // Bottom left corner
              getY() + height * 0.5   // Left corner
      };

      // Draw the polygon (arrow) that fits inside the defined rectangle
      gc.fillPolygon(xPoints, yPoints, xPoints.length);

      // Restore the GraphicsContext to its original state
      gc.restore();
   }
}