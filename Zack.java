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

//Zack is a special gameobject that the player controls
public class Zack extends GameObject
{
    public Zack(int xPos, int yPos, Color myColor)
    {
        super(xPos, yPos, myColor); //parent call
    }
}