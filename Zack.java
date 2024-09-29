import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class Zack extends GameObject {

   static int length = 20;
   static int width = 20;
   private Tile[][] tiles;
   private ArrayList<Mechanism> mechs;



    public Zack(int x, int y, Color myColor) {
        super(x, y, x + width, y + length, myColor);
    }



    // This will get called from Main{} to move Zack
    public void move(int dX, int dY, Tile[][] tiles, ArrayList<Mechanism> mechs) {
        // Apply the speed multiplier
        int adjustedDX = (int) (dX);
        int adjustedDY = (int) (dY);
        this.tiles = tiles;
        this.mechs = mechs;

            incrementX(adjustedDX);
            incrementY(adjustedDY);

            // If there is a collision, revert changes
            if (collides(tiles, mechs)) {
                incrementX(-adjustedDX);
                incrementY(-adjustedDY);
            }

    }




    //internal to zack
    private boolean collides(Tile[][] tiles, ArrayList <Mechanism> mechs)
    {

        for (int i = 0; i < mechs.size(); i++){
            Mechanism current = mechs.get(i);
            boolean hit = overlap(current);
            //check if the current object is a wall and if it collides
            if(current instanceof Wall && hit ) {
                System.out.println("WAL");
                return true;
            }
            //check if object is a jukebox and if it collides
            else if(current instanceof Jukebox && hit){
                System.out.println("Juke");
                return true;
            }
            else if(current instanceof FloatingTile && hit)
            {
                System.out.println("FLOAT TILE");
                return false;
            }
            //spring collision
            else if(current instanceof Spring && hit) {
                System.out.println("Spring");
                if(current.isActive())
                    return true;
                else {
                    springMove(current, current.getProperty());

                    //if spring is not a reusbale spring (aa,ww,dd,ss), activate it so that it will not be usable
                    if(!(current.getProperty().equals("ww") || current.getProperty().equals("aa") || current.getProperty().equals("ss") || current.getProperty().equals("dd")))
                        current.activate();
                    return false;
                }
            }
            //for all objects that Zack can collide with, call performFunction method
            else if(hit)
            {
                System.out.println("HIT CODE");
                mechs.get(i).performFunction(); //set that the mechanism is being collided with
            }


        }

        //check
        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles[i].length; j++) {
                if(overlap(tiles[i][j]) && tiles[i][j] instanceof Abyss)
                    return true;
            } 
        }

        return false;
    }



    //this is a collides method that can determine if any two game objects are currently overlapping
    //Zack will implement a different collides method that makes a small change to his poistion and 
    //then uses this method to check collision
    public boolean overlap(GameObject o) {
        
        // Check if the objects are the same
        if (this == o) {
            return false;
        }
   
        // Calculate the borders for both objects
        int thisLeft = getX();
        int thisRight = getEndX();
        int thisTop = getY();
        int thisBottom = getEndY();
    
        int otherLeft = o.getX();
        int otherRight = o.getEndX();
        int otherTop = o.getY();
        int otherBottom = o.getEndY();
   
        // Check for overlap
        return (thisLeft < otherRight &&
                thisRight > otherLeft &&
                thisTop < otherBottom &&
                thisBottom > otherTop
                );
    }





    //Spring move method for zack to move when the spring is hit
    //takes in the parameter of what direction to move zack
    public void springMove(Mechanism m, String property){
        //store the x, y or zack
        int dX = 0;
        int dY = 0;
        //if spring is to launch up
        if(property.equals("w")) {
            //make it so zack lands in middle of next tile
            //set zacks location to middle of two tiles to the left
            this.setY(((m.getY() + m.getEndY())/2) - 160);
            this.setEndY(this.getY() + length);
            this.setX(((m.getX() + m.getEndX())/2));
            this.setEndX(this.getX() + width);
        }
        //if spring is to launch down
        else if(property.equals("s")) {
            //make it so zack lands in middle of next tile
            //set zacks location to middle of two tiles to the left
            this.setY(((m.getY() + m.getEndY())/2) + 140);
            this.setEndY(this.getY() + length);
            this.setX(((m.getX() + m.getEndX())/2));
            this.setEndX(this.getX() + width);
        }
        //if spring is to launch left
        else if(property.equals("a")) {
            //make it so zack lands in middle of next tile
            //set zacks location to middle of two tiles to the left
            this.setX(((m.getX() + m.getEndX())/2) - 160);
            this.setEndX(this.getX() + width);
            this.setY(((m.getY() + m.getEndY())/2));
            this.setEndY(this.getY() + length);
        }
        //if spring is to launch right
        else{
            //make it so zack lands in middle of next tile
            //set zacks location to middle of two tiles to the left
            this.setX(((m.getX() + m.getEndX())/2) + 140);
            this.setEndX(this.getX() + width);
            this.setY(((m.getY() + m.getEndY())/2));
            this.setEndY(this.getY() + length);
        }
    }






    public void drawMe(GraphicsContext gc) {
        gc.setFill(getColor());
        gc.fillRect(getX(), getY(), length, width); // Center the rectangle on Zack's position
    }
    
    
}

    /*
    public void springMove(Mechanism m, String property) {
        int step = 5;  // The incremental step size (5px per update)

        // We wrap targetX and targetY in a final array so they can be referenced inside the lambda
        final int[] targetX = { getX() };  // Zack's target X position
        final int[] targetY = { getY() };  // Zack's target Y position

        // Calculate target positions based on the direction ('w', 's', 'a', 'd')
        if (property.equals("w")) {
            targetY[0] = ((m.getY() + m.getEndY()) / 2) - 160;
            targetX[0] = ((m.getX() + m.getEndX()) / 2);
        } else if (property.equals("s")) {
            targetY[0] = ((m.getY() + m.getEndY()) / 2) + 160;
            targetX[0] = ((m.getX() + m.getEndX()) / 2);
        } else if (property.equals("a")) {
            targetX[0] = ((m.getX() + m.getEndX()) / 2) - 160;
            targetY[0] = ((m.getY() + m.getEndY()) / 2);
        } else if (property.equals("d")) {
            targetX[0] = ((m.getX() + m.getEndX()) / 2) + 160;
            targetY[0] = ((m.getY() + m.getEndY()) / 2);
        }

        // Create a new Timeline animation to move Zack incrementally
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            // Move Zack horizontally toward the target position
            if (getX() < targetX[0]) {
                incrementX(Math.min(step, targetX[0] - getX()));  // Move right
            } else if (getX() > targetX[0]) {
                incrementX(-Math.min(step, getX() - targetX[0])); // Move left
            }

            // Move Zack vertically toward the target position
            if (getY() < targetY[0]) {
                incrementY(Math.min(step, targetY[0] - getY()));  // Move down
            } else if (getY() > targetY[0]) {
                incrementY(-Math.min(step, getY() - targetY[0])); // Move up
            }

            // Update the end positions after each step
            setEndX(getX() + width);
            setEndY(getY() + length);

            // Stop the Timeline when Zack reaches the destination
            if (getX() == targetX[0] && getY() == targetY[0]) {
                timeline.stop();  // Stop the timeline directly
            }
        }));

        // Set the animation to repeat until Zack reaches the target
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Start the timeline
        timeline.play();
    }
    */