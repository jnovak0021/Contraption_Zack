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
   private boolean traverseable;
   public Tile(int xPos, int yPos, Color myColor)
   {
      super(xPos, yPos, myColor); //call parent constructor
        
   }
    
    //abstract methods for Tile
    //public abstract void draw();
    
   public boolean isTraversable(){
      return traverseable;
   }
    
   public void setTraversable(boolean newValue){
      traverseable = newValue;
   }
    
   
public abstract void drawMe(Pane pane);

}
