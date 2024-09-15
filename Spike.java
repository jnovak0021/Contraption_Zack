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

//Walls are mechanisms that Impede Zack's progress.
//Their color will depend on which button they are linked to
//Spikes usually occupy three consecutive tiles and can be oriented horizontally or vertically 
public class Spike extends Mechanism
{
    public Spike(int xPos, int yPos, Color myColor)
    {
        super(xPos, yPos, myColor); //call parent constructor
        
    }
    
}
