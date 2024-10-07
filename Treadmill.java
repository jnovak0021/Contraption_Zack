import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Treadmill extends Mechanism
{


    //feature/jacob contructor
    public Treadmill(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll)
    {
        super(property, activated, x, y, endX, endY, myColor,associatedMechanisms, ll);
    }

    public void performFunction() {

    }

    public String toString()
    {
        return ("T:" + getProperty() + ":" + isActive() + ":" + getX() + ":" + getY() + ":" + getEndX() + ":" + getEndY() + ":" + getMyColor() + ":" + associatedMechanisms);
        //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>

    }


    @Override
    public void drawMe(GraphicsContext gc) {
        double elapsedTime = getClock().getElapsedTime();
        double width = getEndX() - getX();
        double height = getEndY() - getY();
        double rectHeight = height / 20.0;

        int offset = (int) elapsedTime % 5;

        //if not active, dont have offset
        if(!(isActive()))
            offset = 0;

        Color[] colors = {
                Color.rgb(210, 180, 140, 1), // Original light tan
                Color.rgb(147, 126, 98, 1),  // Original medium tan
                Color.rgb(105, 90, 70, 1),   // Original dark tan
                Color.rgb(80, 65, 50, 1),    // New darker tan
                Color.rgb(60, 50, 40, 1)     // New darkest tan
        };

        for (int i = 0; i < 20; i++) {
            int colorIndex;

            if (getProperty().equals("UP")) {
                colorIndex = (i + offset) % 5;
            } else {
                // Use floorMod to ensure non-negative index
                colorIndex = Math.floorMod((i - offset), 5);
            }

            gc.setFill(colors[colorIndex]);
            gc.fillRect(getX(), getY() + (i * rectHeight), width, rectHeight);
        }
    }



}