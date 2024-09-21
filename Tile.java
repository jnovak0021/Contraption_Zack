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

//Tiles are the basic elementsthat compose the map.
//Tile is still an abstract class because only children classes (i.e. floors, walls, etc) can be insantiated

public abstract class Tile extends GameObject
{

   private boolean traverseable;
   private int x, y, endX, endY;
   
   //new constructor
   public Tile(int x, int y, int endX, int endY, Color myColor, boolean traverseable)
   {
      super(x, y, endX, endY, myColor);
      this.x = x;
      this.y = y;
      this.endX = endY; // Adjust based on rectangle size
      this.endY = endY;
      this.traverseable = traverseable;
   }
   
   //method to see if x,y coordinate is within Tile
   public boolean collides(int xIn, int yIn)
   {
        // This method checks if the point (xIn, yIn) is inside this tile's bounds
        return ((xIn > x && xIn < x + 80) && (yIn > y && yIn < y + 80));
   
   }
    
    public boolean isTraversable(){
        return traverseable;
    }
    

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

   public Color getColor()
   {
      return super.getColor();
   }
   
   public void setColor(Color myColor)
   {
      super.setColor(myColor);
   }
   
   public void drawMe(GraphicsContext gc){}
   
   //This is a temp method to help with collision
   public abstract void drawMe(GraphicsContext gc, Color in);

}
