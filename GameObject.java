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

//Abstract game object type from which ALL game elements will inherit (can't be instantiated on its own)
public abstract class GameObject
{
    //Member data
    private int xPos, yPos; //coordinates on the game board 
    private Color myColor; //Color of the tile/room theme
    
    private ArrayList<GameObject> linkedObjectsList;
    
    //parent constructor
    public GameObject(int xPos, int yPos, Color myColor)
    {
       this.xPos = xPos;
       this.yPos = yPos;
       this.myColor = myColor;
    }
    
    //Absract methods to implement:
    /*
    draw()
    setActive()	//set value of isActive
    setIsActive()
    getIsActive()
    getProperty()
    getStartX()
    getStartY()
    getEndX()
    getEndY()
    getLinkedObjectsList()	//returns linkedObjects Arraylist
    setLinkedObjectList(ArrayList linkedObjectIn	)	//on second loop, 
    */
    
    /* add a method for drawing/graphics? */
    
    //Inherited acessors and mutators:
    public int getX()
    {
        return xPos;
    }
    public int getY()
    {
        return yPos;
    }
    public Color getMyColor()
    {
        return myColor;
    }
    public void incrementX(int dX)
    {
        xPos += dX;
    }
    public void incrementY(int dY)
    {
        yPos += dY;
    }
    public void setX(int newX)
    {
        xPos = newX;
    }
    public void setY(int newY)
    {
        yPos = newY;
    }
    public void setColor(Color newColor)
    {
        myColor = newColor;
    }
}