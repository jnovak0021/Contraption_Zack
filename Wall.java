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

//Walls are tiles that Zack cannot pass over.
//Their color depending on what the theme of the room is

public class Wall extends Tile
{
    public Wall(int xPos, int yPos, Color myColor)
    {
        super(xPos, yPos, myColor); //call parent constructor
        
    }
    
}
