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

/*jacob Novak - 9/18
*changed properties to be more broad to fit the role as parent for all three child classes
*/

//Abstract game object type from which ALL game elements will inherit (can't be instantiated on its own)
public abstract class GameObject
{
    //Member data
    private int x = 0, y = 0; //coordinates on the game board 
    private int endX = 0, endY = 0;
    private Color myColor; //Color of the tile/room theme
   
    
    //new constructor for Tiles
    //MAIN constructor
    public GameObject(int x, int y, int endX, int endY, Color myColor)
    {
        this.x = x;
        this.y = y;
        this.endX = endX;
        this.endY = endY;
        this.myColor = myColor;
    }

    //parent constructor
    //Dorians -- made to accept color 
    public GameObject(int x, int y, Color myColor)
    {
       this.x = x;
       this.y = y;
       this.myColor = myColor;
    }


    //public abstract void draw();


    //Absract methods to implement:
    /*
    draw()
    getLinkedObjectsList()	//returns linkedObjects Arraylist
    setLinkedObjectList(ArrayList linkedObjectIn	)	//on second loop, 
    */
    
    /* add a method for drawing/graphics? */
    
    //Inherited acessors and mutators:

    public Color getMyColor()
    {
        return myColor;
    }

    // We wont ever need to increment x or y
    public void incrementX(int dX)
    {
        x += dX;
    }
    public void incrementY(int dY)
    {
        y += dY;
    }
    
    public int getX()
    {
        return x;
    }
    public void setX(int newX)
    {
        x = newX;
    }

    public int getY()
    {
        return y;
    }
    public void setY(int newY)
    {
        y = newY;
    }


    public int getEndX()
    {
        return endX;
    }
    public void setEndX(int endX)
    {
        this.endX = endX;
    }

    public int getEndY()
    {
        return endY;
    }
    public void setEndY(int endY)
    {
        this.endY = endY;
    }

    public void setColor(Color newColor)
    {
        myColor = newColor;
    }
    public Color getColor()
    {
      return myColor;
    }
    
    
    
}