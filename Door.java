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
public class Door extends Mechanism 
{


    //feature/jacob contructor
    public Door(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll)
    {
        super(property, activated, x, y, endX, endY, myColor,associatedMechanisms, ll);
    }

    @Override
    public void performFunction() {

        switch(getProperty())
        {
            case "2W":
                System.out.println("SFE:SKJFESF");
                getLL().setCurrentRoomNumber(4);
                break;
            default:
                getLL().setCurrentRoomNumber(Integer.parseInt(getProperty()));
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
    // Method to draw the arrow within a rectangle defined bgetY() (x, y) and (endX, endY)
   public void drawMe(GraphicsContext gc)
   {
       // First, draw the grey rectangle
       gc.setFill(Color.GREY);
       gc.fillRect(getX(), getY(), (getEndX() - getX()), (getEndY() - getY()));

       // Then, draw the red arrow
       gc.setFill(Color.RED);

       // Calculate width and height of the rectangle
       double width = 80;
       double height = 80;

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
   }

}