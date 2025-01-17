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
   
   //size across all tiles
   static protected int length = 80;
   static protected int width = 80;
   private Color secondaryColor;
   
    //constructor for tile to have multiple colors
    public Tile(int x, int y, int endX, int endY, Color myColor, Color secondaryColor, boolean traverseable)
    {
        super(x, y, endX, endY, myColor);
    }
    //new constructor
    public Tile(int x, int y, int endX, int endY, Color myColor, boolean traverseable)
    {
        super(x, y, endX, endY, myColor);
    }
    // Method to see if xIn, yIn (center of Zack) collides with the Tile
    public boolean collides(int xIn, int yIn) {
      return ((xIn >= getX() && xIn <= getEndX()) && (yIn  >= getY() && yIn  <= getEndY()) && (xIn <= getEndX() && xIn >= getX()));

    }
    //accessor and mutator for the secondary Color
    public void setSecondaryColor(Color secondaryColor)
    {
        this.secondaryColor = secondaryColor;
    }

    public Color getSecondaryColor()
    {
        return secondaryColor;
    }

    public boolean isTraversable(){
        return traverseable;
    }
    

    public void setTraversable(boolean newValue){
        traverseable = newValue;
    }
   
   
    public void drawMe(GraphicsContext gc){}
   
    
}
