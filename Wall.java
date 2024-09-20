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

//Walls are tiles that Zack cannot pass over.
//Their color depending on what the theme of the room is

public class Wall extends Tile
{
    public Wall(int xPos, int yPos, Color myColor)
    {
        super(xPos, yPos, myColor); //call parent constructor
        
    }
    
}
