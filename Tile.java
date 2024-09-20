import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.paint.Color;

//Tiles are the basic elementsthat compose the map.
//Tile is still an abstract class because only children classes (i.e. floors, walls, etc) can be insantiated

public abstract class Tile extends GameObject
{
<<<<<<< Updated upstream
    private boolean traverseable;
    public Tile(int xPos, int yPos, Color myColor)
    {
        super(xPos, yPos, myColor); //call parent constructor
        
    }
=======
   private boolean traverseable;

   //new constructor
   public Tile(int x, int y, int endX, int endY, Color myColor, boolean traverseable)
   {
      super(x, y, endX, endY, myColor);
      this.traverseable = traverseable;
   }

   public Tile(int x, int y, Color myColor, boolean traverseable)
   {
      super(x, y, myColor); //call parent constructor
      this.traverseable = traverseable;
   }
>>>>>>> Stashed changes
    
    //abstract methods for Tile
    //public abstract void draw();
    
    public boolean isTraversable(){
        return traverseable;
    }
    
<<<<<<< Updated upstream
    public void setTraversable(boolean newValue){
        traverseable = newValue;
    }
=======
   public void setTraversable(boolean newValue){
      traverseable = newValue;
   }
   
   public int getX()
   {
      return super.getX();
   }
   public void setX(int x)
   {
      super.setX(x);
   }

   public int getY()
   {
      return super.getY();
   }
   public void setY(int y)
   {
      super.setY(y);
   }

   public int getEndX()
   {
      return super.getEndX();
   }
   public void setEndX(int endX)
   {
       super.setEndX(endX);
   }

   public int getEndY()
   {
      return super.getY();
   }
   public void setEndY(int endY)
   {
      super.setEndY(endY);
   }

   
   public abstract void drawMe(Pane pane);

>>>>>>> Stashed changes
}
