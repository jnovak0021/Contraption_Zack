import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.*;


public class Zack extends GameObject {

   static int length = 40;
   static int width = 40;

    public Zack(int x, int y, Color myColor) {
        super(x, y, x + width, y + length, myColor);
    }

    //this will get called from Main{} to move Zack
    public void move(int dX, int dY, Tile[][] tiles, ArrayList <Mechanism> mechs)
    {
        incrementX(dX);
        incrementY(dY);
      
        //if there is a collision, revert changes
        if(collides(tiles, mechs) == true)
        {
            incrementX(-dX);
            incrementY(-dY);
        }
     
    }
    //internal to zack
    private boolean collides(Tile[][] tiles, ArrayList <Mechanism> mechs)
    {
        //check
        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles[i].length; j++) {
                if(overlap(tiles[i][j]) && tiles[i][j] instanceof Abyss)
                    return true;
            } 
        }
        
        for (int i = 0; i < mechs.size(); i++){
            if(overlap(mechs.get(i)))
               return true;
        }
        //if door
            //
        
        return false;
    }
    
    //this is a collides method that can determine if any two game objects are currently overlapping
    //Zack will implement a different collides method that makes a small change to his poistion and 
    //then uses this method to check collision
    public boolean overlap(GameObject o) {
        
        // Check if the objects are the same
        if (this == o) {
            return false;
        }
   
        // Calculate the borders for both objects
        int thisLeft = getX();
        int thisRight = getEndX();
        int thisTop = getY();
        int thisBottom = getEndY();
    
        int otherLeft = o.getX();
        int otherRight = o.getEndX();
        int otherTop = o.getY();
        int otherBottom = o.getEndY();
   
        // Check for overlap
        return (thisLeft < otherRight &&
                thisRight > otherLeft &&
                thisTop < otherBottom &&
                thisBottom > otherTop
                );
    }


    public void drawMe(GraphicsContext gc) {
        gc.setFill(getColor());
        gc.fillRect(getX(), getY(), length, width); // Center the rectangle on Zack's position
    }
    
    
}
