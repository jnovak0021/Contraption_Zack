import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/*
   Zack really no longer uses any gameobject functionalituy
*/
public class Zack extends GameObject {
    private int x, y, endX, endY;
    private Color color;

    public Zack(int x, int y, Color myColor) {
        super(x, y, myColor);
        this.x = x;
        this.y = y;
        this.color = myColor;
        this.endX = x + 40; // Adjust based on rectangle size
        this.endY = y + 40;
    }

    public void setX(int newX) {
        this.x = newX;
        this.endY = endY;
        this.endX = newX + 40; // Update endX accordingly
    }

    public void setY(int newY) {
        this.y = newY;
        this.endY = y;
        this.endY = newY + 40; // Update endY accordingly
    }
    
    //this will get called from Main{} to move Zack
    public void move(int dX, int dY, Tile[][] tiles)
    {
        incrementX(dX);
        incrementY(dY);
      
        //if there is a collision, revert changes
        if(collides(tiles) == true)
        {
            incrementX(-dX);
            incrementY(-dY);
        }
     
    }
    //internal to zack
    private boolean collides(Tile[][] tiles)
    {
        //check
        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles[i].length; j++) {
                if(overlap(tiles[i][j]) && tiles[i][j] instanceof Abyss)
                    return true;
            } 
        }
        
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
        return ( //thisRight < otherLeft || 
                 //thisLeft > otherRight || 
                 //thisBottom < otherTop || 
                 //thisTop > otherBottom
                 
                thisLeft < otherRight &&
                thisRight > otherLeft &&
                thisTop < otherBottom &&
                thisBottom > otherTop
                );
        
         
        /*if (getEndX() <= o.getX() || o.getEndX() <= getX()) {
            return false;
        }

        // Check if one rectangle is above the other
        if (getEndY() <= o.getY() || o.getEndY() <= getY()) {
             return false;
        }

        return true; // Rectangles overlap
        */
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getEndX() {
        return this.endX;
    }

    public int getEndY() {
        return this.endY;
    }

    public Color getColor() {
        return this.color;
    }

    public void drawMe(GraphicsContext gc) {
        gc.setFill(this.color);
        gc.fillRect(x, y, 40, 40); // Center the rectangle on Zack's position
    }
    
    
    public void incrementX(int dX)
    {
        x += dX;
        endX += dX;
    }
    public void incrementY(int dY)
    {
        y += dY;
        endY += dY;
    }
}
