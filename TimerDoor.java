import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.time.Duration;

public class TimerDoor extends Mechanism {

    private Color currentColor;

    public TimerDoor(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll) {
        super(property, activated, x, y, endX, endY, myColor, associatedMechanisms, ll);
        this.currentColor = myColor; // Start with the initial color
    }

    // Method to draw a rectangle that changes color every 10 seconds
    @Override
    public void drawMe(GraphicsContext gc) {
        if(isActive()) {
            gc.setFill(currentColor);
            gc.fillRect(getX(), getY(), getEndX() - getX(), getEndY() - getY());
        }
        else
        {
            gc.clearRect(getX(), getY(), getEndX() - getX(), getEndY() - getY());
        }

    }

    @Override
    public String toString() {
        return "TimerDoor{}";
    }

    @Override
    public void performFunction() {
        // No specific functionality implemented yet
    }

    //use the clock and the property to determine if it should be active or not and draw accordingly
    @Override
    public void performTimedFunction()
    {

        Clock c = getClock();
        double elapsedTime = c.getElapsedTime();
        int currentInterval = ( int ) ( elapsedTime / 5 ) % 4 ;

        if(getProperty().equals("1") && currentInterval == 0)
        {
            setActivated(false);
        }
        else if(getProperty().equals("2") && currentInterval == 1)
        {
            setActivated(false);
        }
        else if(getProperty().equals("3") && currentInterval == 2)
        {
            setActivated(false);
        }
        else if(getProperty().equals("4") && currentInterval == 3)
        {
            setActivated(false);
        }
        else
        {
            setActivated(true);
        }

    }
}
