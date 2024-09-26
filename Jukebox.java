import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
public class Jukebox extends Mechanism
{
    //feature/jacob contructor
    public Jukebox(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll)
    {
        super(property, activated, x, y, endX, endY, myColor,associatedMechanisms, ll);
    }

    public String toString()
    {
        return "JukeBox{" +
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
    public void performFunction() {

    }

    public void drawMe(GraphicsContext gc) {
        // Width and height based on the start (top-left) and end (bottom-right) coordinates
        double width = getEndX() - getX();
        double height = getEndY() - getY();

        // Calculate some proportions for elements
        double arcSize = Math.min(width, height) * 0.2; // Rounded corner size relative to jukebox size
        double speakerHeight = height * 0.2;            // Speaker height (20% of total height)
        double controlPanelHeight = height * 0.15;      // Control panel height (15% of total height)
        double buttonRadius = width * 0.05;             // Radius for the buttons (5% of the width)

        // Jukebox Frame (RoundRectangle equivalent)
        gc.setFill(Color.BEIGE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.fillRoundRect(getX(), getY(), width, height, arcSize, arcSize); // Fill with color
        gc.strokeRoundRect(getX(), getY(), width, height, arcSize, arcSize); // Draw border

        // Speaker Grill (Lower section)
        gc.setFill(Color.BLACK);
        gc.fillRect(getX() + width * 0.15, getY() + height * 0.7, width * 0.7, speakerHeight);

        // Control Panel (Upper section)
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(getX() + width * 0.15, getY() + height * 0.2, width * 0.7, controlPanelHeight);

        // Circles for lights or buttons (3 buttons evenly spaced along the control panel)
        gc.setFill(Color.RED);
        gc.fillOval(getX() + width * 0.2 - buttonRadius, getY() + height * 0.25 - buttonRadius, buttonRadius * 2, buttonRadius * 2);

        gc.setFill(Color.YELLOW);
        gc.fillOval(getX() + width * 0.5 - buttonRadius, getY() + height * 0.25 - buttonRadius, buttonRadius * 2, buttonRadius * 2);

        gc.setFill(Color.GREEN);
        gc.fillOval(getX() + width * 0.8 - buttonRadius, getY() + height * 0.25 - buttonRadius, buttonRadius * 2, buttonRadius * 2);
    }
}
