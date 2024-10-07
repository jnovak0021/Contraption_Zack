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

public class Pulley extends Mechanism
{
    // Constructor
    public Pulley(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll)
    {
        super(property, activated, x, y, endX, endY, myColor, associatedMechanisms, ll);
    }

    public void drawMe(GraphicsContext gc) {
        //animate the pulley so it semi looks like it is moving
        double currentTime = getClock().getElapsedTime();
        Color tanColor = Color.TAN;
        Color darkTanColor = Color.rgb(147, 126, 98, 1);
        Color[] rectangleColors = {tanColor, tanColor, tanColor};

        //if the pulley is active, animate it
        if(isActive()) {
            int animationStep = (int)(currentTime / 0.5) % 6; // Change every 0.5 seconds, cycle through 6 steps
            int darkRectangle = animationStep / 2; // 0-1 first rectangle, 2-3 second rectangle, 4-5 third rectangle
            rectangleColors[darkRectangle] = (animationStep % 2 == 0) ? darkTanColor : tanColor;
        }

        int width = getEndX() - getX();
        int height = getEndY() - getY();

        // Draw the outline rectangle
        gc.setStroke(Color.BLACK);
        gc.strokeRect(getX(), getY(), width, height);

        // Calculate sizes for pulley components
        int greyWidth = width / 6;
        int tanWidth = width / 3;
        int componentHeight = height / 2;

        // Draw first grey rectangle (pulley wheel)
        gc.setFill(Color.GREY);
        gc.fillRect(getX(), getY(), greyWidth, componentHeight);

        // Draw first tan rectangle (pulley)
        gc.setFill(rectangleColors[0]);
        gc.fillRect(getX() + greyWidth / 2, getY() + componentHeight, tanWidth, componentHeight);

        // Draw second tan rectangle
        gc.setFill(rectangleColors[1]);
        gc.fillRect(getX() + greyWidth + tanWidth -75, getY(), tanWidth, componentHeight);

        // Draw third tan rectangle
        gc.setFill(rectangleColors[2]);
        gc.fillRect(getX() + greyWidth + tanWidth, getY() + componentHeight, tanWidth, componentHeight);

        // Draw connecting lines
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        // Top connection
        gc.strokeLine((getX() + greyWidth / 2), getY() + componentHeight / 2,
                getX() + (greyWidth + 2 * tanWidth) - 75, getY() + componentHeight / 2);
        // Bottom connection
        gc.strokeLine(getX() + greyWidth/2, getY() + componentHeight + componentHeight / 2,
                getX() + greyWidth + 2 * tanWidth, getY() + componentHeight + componentHeight / 2);
    }

    public String toString()
    {
        return ("P:" + getProperty() + ":" + isActive() + ":" + getX() + ":" + getY() + ":" + getEndX() + ":" + getEndY() + ":" + getMyColor() + ":" + associatedMechanisms);
        //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>

    }

    public void performFunction(){
        //no function
    }
}