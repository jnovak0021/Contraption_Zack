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
public class OtherItem extends Item 
{
   private String type;
   
   //location of item and associated mechanism (for screwdriver)
   public OtherItem(String type, int x, int y, int endX, int endY, int roomStored, LoadLevel ll)
   {
      super(roomStored, x, y, endX, endY, ll); // Call parent constructor
      this.type = type;
      this.ll = ll;
   }
   
   public String getType(){
      return type;
   }
 
    public void drawMe(GraphicsContext gc){
         if(!isCollected()){
            switch(type){
                
                case "PipeWrench":
                gc.setFill(Color.GRAY);
                gc.fillRect(getX(),getY(), getEndX()- getX(), getEndY()-getY());
                
                    break;
                
                case "Spring":
                gc.setFill(Color.GRAY);
                gc.fillRect(getX(),getY(), getEndX()- getX(), getEndY()-getY());
                    break;
                
                case "Wrench":
                gc.setFill(Color.GRAY);
                gc.fillRect(getX(),getY(), getEndX()- getX(), getEndY()-getY());
                    break;
                
                case "TapeMeasure":
                gc.setFill(Color.GRAY);
                gc.fillRect(getX(),getY(), getEndX()- getX(), getEndY()-getY());
                    break;
                
                case "Pipe":
                gc.setFill(Color.GRAY);
                gc.fillRect(getX(),getY(), getEndX()- getX(), getEndY()-getY());
                    break;
                
                default:
                    break;
            }
         }
         
   }

}
