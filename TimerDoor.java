import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TimerDoor extends Mechanism {

   private Color currentColor;
   private boolean paused; // Track if the timer is paused
   private double pausedTime; // Time at which the timer was paused
   private int interval; // Control the interval for the timer

   public TimerDoor(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll) {
      super(property, activated, x, y, endX, endY, myColor, associatedMechanisms, ll);
      this.currentColor = myColor; // Start with the initial color
      this.paused = false; // Initially not paused
      this.pausedTime = 0; // Initial paused time
      this.interval = 5; // Initial interval value
   }

   @Override
   public void drawMe(GraphicsContext gc) {
      if (isActive()) {
         gc.setFill(currentColor);
         gc.fillRect(getX(), getY(), getEndX() - getX(), getEndY() - getY());
      } else {
         gc.strokeRect(getX(), getY(), getEndX() - getX(), getEndY() - getY());
      }
   }

   @Override
   public String toString()
   {

      return ("6:" + getProperty() + ":" + isActive() + ":" + getX() + ":" + getY() + ":" + getEndX() + ":" + getEndY() + ":" + getMyColor() + ":" + associatedMechanisms);
   }

   @Override
   public void performFunction() {
      // No specific functionality implemented yet
   }

   public boolean isPaused() {
      return paused;
   }

   // Method to pause the timer
   public void pauseTimer() {
      // Only pause if not active
      if (!paused) {
         paused = true;
         pausedTime = getClock().getElapsedTime(); // Record the time when paused
         System.out.println(getProperty() + " paused");
      }
   }

   // Method to resume the timer
   public void resumeTimer() {
      paused = false;


   }

   @Override
   public void performTimedFunction() {
      if (paused) {
         return; // Do not perform the function if paused
      }
      else
      {
         Clock c = getClock();
         double elapsedTime = c.getElapsedTime();
         int currentInterval = (int) (elapsedTime / interval) % 4; // Use the current interval value

         if (getProperty().equals("1") && currentInterval == 0) {
            setActivated(false);
         } else if (getProperty().equals("2") && currentInterval == 1) {
            setActivated(false);
         } else if (getProperty().equals("3") && currentInterval == 2) {
            setActivated(false);
         } else if (getProperty().equals("4") && currentInterval == 3) {
            setActivated(false);
         } else {
            setActivated(true);
         }
      }
   }
}
