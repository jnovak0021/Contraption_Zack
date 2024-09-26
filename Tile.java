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
   
   static int length = 80;
   static int width = 80;
   
    //new constructor
    public Tile(int x, int y, int endX, int endY, Color myColor, boolean traverseable)
    {
        super(x, y, endX, endY, myColor);
    }
    // Method to see if xIn, yIn (center of Zack) collides with the Tile
    public boolean collides(int xIn, int yIn) {
      return ((xIn >= getX() && xIn <= getEndX()) && (yIn  >= getY() && yIn  <= getEndY()) && (xIn <= getEndX() && xIn >= getX()));

    }

    public boolean isTraversable(){
        return traverseable;
    }
    

    public void setTraversable(boolean newValue){
        traverseable = newValue;
    }
   
   
    public void drawMe(GraphicsContext gc){}
   
    //This is a temp method to help with collision
    public abstract void drawMe(GraphicsContext gc, Color in);
   
    public int getLength(){
        return length;
    }
   
    public int getWidth(){
        return width;
    }
}
