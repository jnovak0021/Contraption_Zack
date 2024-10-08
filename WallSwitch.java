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
public class WallSwitch extends Mechanism
{
    private String property;
    //store the start of the activated time
    private double activatedStartTime;

    public WallSwitch(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll)
    {
        super(property, activated, x, y, endX, endY, myColor, associatedMechanisms, ll);
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public void drawMe(GraphicsContext gc) {
        int width = getEndX() - getX();
        int height = getEndY() - getY();

        // Set background color based on activation state
        if (isActive()) {
            gc.setFill(Color.GREENYELLOW);
        } else {
            gc.setFill(Color.RED);
        }

        // Draw the background rectangle
        gc.fillRect(getX(), getY(), width, height);

        // Draw the border
        gc.setStroke(Color.BLACK);
        gc.strokeRect(getX(), getY(), width, height);

        // Draw the binary number
        gc.setFill(Color.BLACK);
        gc.setFont(javafx.scene.text.Font.font(height * 0.6)); // Adjust font size based on height
        String binaryNumber = isActive() ? "1" : "0";

        // Calculate text position to center it
        javafx.scene.text.Text text = new javafx.scene.text.Text(binaryNumber);
        text.setFont(gc.getFont());
        double textWidth = text.getLayoutBounds().getWidth();
        double textHeight = text.getLayoutBounds().getHeight();
        double textX = getX() + (width - textWidth) / 2;
        double textY = getY() + (height + textHeight) / 2;

        gc.fillText(binaryNumber, textX, textY);
    }

    public String toString()
    {
        return ("10:" + getProperty() + ":" + isActive() + ":" + getX() + ":" + getY() + ":" + getEndX() + ":" + getEndY() + ":" + getMyColor() + ":" + associatedMechanisms);
        //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>

    }


    //kinda wierd functionality but use 0 as the indicator that button can be used and call performFunction
    //
    @Override
    public void performTimedFunction() {
        if(activatedStartTime == 0) {
            activatedStartTime = getClock().getElapsedTime();
            activate();
            performFunction();
        }
        else if(getClock().getElapsedTime() - activatedStartTime > 1)
            activatedStartTime = 0;
    }

    //this method will be called from zack, and will activate/deactivate the correct spikes
    //perform different function based on what associated array it is apart of. If it is from room 7, or 8 set the other objects in the arrraylist to its value
    public void performFunction(){

        ArrayList <Mechanism> mechs = ll.getAssociatedMechanisms(associatedMechanisms);

        if(getAssociatedMechanisms() == 71)
        {
            for(int i=0; i < mechs.size(); i++){
                System.out.print(mechs.get(i)+ " ");
                //dont activate other buttons
                if(!(mechs.get(i) instanceof WallSwitch))
                    mechs.get(i).setActivated(this.isActive());//holy spirit activate
            }

        }
        //screw must be activated for anything else to work in this room
        else if(getAssociatedMechanisms() == 81) {
            boolean screw = false;  //store the state of the screw
            //loop over to see if the screw was inserted - if so, activate objects
            for (int i = 0; i < mechs.size(); i++) {
                if(mechs.get(i) instanceof Screw && mechs.get(i).isActive())
                {
                    screw = true;
                }

            }
            //only activate if screw is activated
            //if the mechanism is a treadmill set it so that if this isActive, the treadmill goes north and if not active treadmill goes south
            for (int i = 0; i < mechs.size(); i++) {
                //only activate/alter if screw is activated
                if(mechs.get(i) instanceof Treadmill && screw)
                {
                    //if this isActive set property to UP
                    if(this.isActive())
                        mechs.get(i).setProperty("UP");

                    //else set property to DOWN
                    else if (!this.isActive())
                        mechs.get(i).setProperty("DOWN");
                }

            }

        }
        //check if all wall switches are active, if they are, activate all other objects
        else if(getAssociatedMechanisms() == 90) {
            boolean allActive = true;
            for(int i=0; i < mechs.size(); i++) {
                //if it is a wallMechanism and it is not active, set to false
                if(mechs.get(i) instanceof WallSwitch && !(mechs.get(i).isActive()))
                {
                    allActive = false;
                }
            }
            System.out.println(allActive);
            //loop over and set all objects that arent WallSwitch to value of allActive
            for(int i=0; i < mechs.size(); i++) {
                if(!(mechs.get(i) instanceof WallSwitch) && !(mechs.get(i) instanceof Spike))
                {
                    mechs.get(i).setActivated(allActive);
                }
            }
        }
    }
}