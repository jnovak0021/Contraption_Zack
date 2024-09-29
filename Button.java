import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.*;

//Buttons are mechanisms with which Zack's can interact.
//They will have the same color as their associated mechanisms
//Property is either circular (C) or square (S)
//A button may be linked to a timer in some room
public class Button extends Mechanism
{
    public Button(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll)
    {
        super(property, activated, x, y, endX, endY, myColor, associatedMechanisms, ll);
    }
    
        
    public void drawMe(GraphicsContext gc){

        //draw a simple square
        gc.setFill(getMyColor());
        gc.fillRect(getX(),getY(), getWidth(), getLength());

    }

    public String toString()
    {
        return "Button{" +
                "property='" + getProperty() + '\'' + // Adjust according to your propertgetY() type
                "activate=" + isActive() +
                ", x=" + getX() +
                ", y=" + getY() +
                ", endX=" + getEndX() +
                ", endY=" + getEndY() +
                ", color=" + getColor() +
                '}';

    }

    //this method will be called from zack, and will activate/deactivate the correct spikes
    public void performFunction(){
        ArrayList <Mechanism> mechs = ll.getAssociatedMechanisms(associatedMechanisms);
        System.out.println("MECHSSSSSSS");

        for(int i=0; i < mechs.size(); i++){
            System.out.print(mechs.get(i)+ " ");
            //dont activate other buttons
            if(!(mechs.get(i) instanceof Button))
                mechs.get(i).activate();//holy spirit activate
        }
        System.out.println();

    }
}

//get int associatedMechanisms
