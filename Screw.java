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
public class Screw extends Mechanism
{
    //store the start time
    private double activatedStartTime;

    public Screw(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll)
    {
        super(property, activated, x, y, endX, endY, myColor, associatedMechanisms, ll);
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

    //if zack has the screwdriver set all values isActive
    public void performFunction(){
        //should only run one time
        if(isActive()){}
        else {
            activate();
            ArrayList <Mechanism> mechs = ll.getAssociatedMechanisms(associatedMechanisms);

            for(int i=0; i < mechs.size(); i++) {
                if((mechs.get(i) instanceof Treadmill) )
                {
                    mechs.get(i).setActivated(isActive());
                }
                else if(mechs.get(i) instanceof Spring)
                {
                    mechs.get(i).setActivated(!isActive());

                }
            }
        }


    }
}