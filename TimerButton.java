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
public class TimerButton extends Mechanism
{
    //store the start of the activated time
    private double activatedStartTime;
    public TimerButton(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll)
    {
        super(property, activated, x, y, endX, endY, myColor, associatedMechanisms, ll);
        activatedStartTime = 0;

    }


    public void drawMe(GraphicsContext gc){

        //draw a simple square
        gc.setFill(Color.GREY);
        gc.fillRect(getX(),getY(), getWidth(), getLength());
        gc.setFill(getMyColor());
        gc.fillOval(getX(),getY(), getWidth(), getLength());
    }

    public String toString()
    {
        return ("7:" + getProperty() + ":" + isActive() + ":" + getX() + ":" + getY() + ":" + getEndX() + ":" + getEndY() + ":" + getMyColor() + ":" + associatedMechanisms);
        //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>

    }

    //if button is notActive (i.e pressed) -- call this method
    //this method will be called from zack, and will activate/deactivate the correct spikes
    public void performFunction(){
        activatedStartTime = getClock().getElapsedTime();
        ArrayList <Mechanism> mechs = ll.getAssociatedMechanisms(associatedMechanisms);
        for(int i=0; i < mechs.size(); i++){
            //dont activate other buttons
            if(!(mechs.get(i) instanceof TimerButton))
                mechs.get(i).activate();//holy spirit activate
        }
    }
    //use the clock and the property to determine if it should be active or not and draw accordingly
    @Override
    public void performTimedFunction() {
        //check if the elapsed time is > 30 for the activatedStartTime
        //if it is greater, then set all active to true and set this active to true else

        //if button is still up, do nothing
        double elapsedTime = getClock().getElapsedTime();

        if (this.isActive()){}
        else if((elapsedTime - activatedStartTime > 30) && activatedStartTime != 0)
        {
            ArrayList <Mechanism> mechs = ll.getAssociatedMechanisms(associatedMechanisms);

            for(int i=0; i < mechs.size(); i++){
                //dont activate other buttons
                if(!(mechs.get(i) instanceof TimerButton))
                    mechs.get(i).activate();//holy spirit activate
            }
            this.setActivated(true);
        }

    }
}

//get int associatedMechanisms
