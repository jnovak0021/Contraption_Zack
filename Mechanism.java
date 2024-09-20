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
//Mechanisms are objects that zack interacts with, and are sometimes linked to other mechanisms.
//Usually a mechanism will occupy more than one tile, and will be drawn as such by its graphics method.
//Mechanism is still an abstract class because only children classes (i.e. spikes, buttons, springs) can be insantiated

public abstract class Mechanism extends GameObject
{
    public Mechanism(int xPos, int yPos, Color myColor)
    {
        super(xPos, yPos, myColor); //call parent constructor
        
    }
    
    //methods for Mechanisms
    /* 
    setIsActive()
    getIsActive()
    getProperty()
    getStartX()
    getStartY()
    getEndX()
    getEndY()
    */
}
