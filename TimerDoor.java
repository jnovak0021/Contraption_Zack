import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.time.Duration;

public class TimerDoor extends Mechanism {
    private Clock clock;
    private Color currentColor;

    public TimerDoor(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll) {
        super(property, activated, x, y, endX, endY, myColor, associatedMechanisms, ll);
        this.clock = new Clock(); // Initialize the clock when TimerDoor is created
        this.currentColor = myColor; // Start with the initial color
    }

    // Method to draw a rectangle that changes color every 10 seconds
    @Override
    public void drawMe(GraphicsContext gc) {
        // Get the elapsed time from the clock
        Duration elapsedTime = clock.getElapsedTime();
        System.out.println("SEFLJKHESFKLJHSEFLKJHSEF");

        long secondsElapsed = elapsedTime.getSeconds();

        int moduloVal;

        //four doors
        if(getProperty().equals("1"))
        {
            moduloVal = 5;
        }
        else if(getProperty().equals("2"))
        {
            moduloVal = 10;
        }
        else if(getProperty().equals("3"))
        {
            moduloVal = 15;
        }
        else    // property = 4
        {
            moduloVal = 20;
        }
        // Change color every 10 seconds
        if (secondsElapsed % moduloVal == 0) {
            // Change to a different color based on the number of tens of seconds passed
            switch ((int) (secondsElapsed / 10) % 3) {
                case 0:
                    currentColor = Color.GREY;
                    break;
                case 1:
                    currentColor = Color.RED;
                    break;
                case 2:
                    currentColor = Color.BLUE;
                    break;
            }
        }

        // Draw the rectangle with the current color
        gc.setFill(currentColor);
        gc.fillRect(getX(), getY(), getEndX() - getX(), getEndY() - getY());
    }

    @Override
    public String toString() {
        return "TimerDoor{}";
    }

    @Override
    public void performFunction() {
        // No specific functionality implemented yet
    }
}
