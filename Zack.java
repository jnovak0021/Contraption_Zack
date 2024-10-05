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
   private int INITIAL_ZACK_X; // Starting X position
   private int INITIAL_ZACK_Y; // Starting Y position



   public Zack(int x, int y, Color myColor) {
      super(x, y, x + width, y + length, myColor);
   }

   //This method is constantly called from Main{}

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
            System.out.println("WALL");
            return true;
         }
         //stanchion collision
        else if(current instanceof Stanchion && hit){
            return true;
         }
      
         // Door collision handling
         else if (current instanceof Door && hit) {
            ((Mechanism) current).performFunction();
            String doorProperty = current.getProperty(); // Assume this returns an int corresponding to the door number
            zackPositionDoor(doorProperty); // Set Zack's new position based on the door's property
            setX(INITIAL_ZACK_X);
            setY(INITIAL_ZACK_Y);
            setEndX(INITIAL_ZACK_X +20);
            setEndY(INITIAL_ZACK_Y +20);
         
         }
         
         
         //check if object is a jukebox and if it collides
         else if(current instanceof Jukebox && hit){
            System.out.println("Juke");
            return true;
         }
         //button
         else if(current instanceof Button && hit){
            if(((Mechanism) current).isActive()){ //Button is not pressed
               ((Mechanism) current).activate();
               ((Mechanism) current).performFunction();
            }
            System.out.println("BUTTON");
            return false;
         }
         //wallswitch -- unlike the button, wall switch can be toggled on and off by interacting with it
         else if(current instanceof WallSwitch && hit){
            ((Mechanism) current).activate();
            ((Mechanism) current).performFunction();
            System.out.println("Wall Switch");
            return true;

         }
         else if(current instanceof TimerButton && hit){
            if(((Mechanism) current).isActive()){ //Button is not pressed
               ((Mechanism) current).activate();
               ((Mechanism) current).performFunction();
            }
            System.out.println("BUTTON");
            return false;
         }
         
         else if(current instanceof Spike && hit){
            System.out.println("SPIKE");
            if(current.isActive())
               return true;
            else
               return false;
         }
         
         else if(current instanceof FloatingTile && hit){
            System.out.println("FLOAT TILE");
            return false;
         }
         //TimerDoor - if active, return true
         else if(current instanceof TimerDoor && hit){
            System.out.println("TimerDoor");
            if(((Mechanism)current).isActive())
               return true;
            else
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
         else if(current instanceof TeslaCoil && hit){
            if(current.isActive())
            {
               return true;
            }
            else
               return false;
         }
         //for all objects that Zack can collide with, call performFunction method
         else if(hit){
            System.out.println("HIT CODE");
            mechs.get(i).performFunction(); //set that the mechanism is being collided with
         }
      
      
      }
   
      //check
      for (int i = 0; i < tiles.length; i++){
         for (int j = 0; j < tiles[i].length; j++) {
            if(overlap(tiles[i][j]) && (tiles[i][j] instanceof Abyss || tiles[i][j] instanceof Water))
               return true;
         } 
      }
   
      return false;
   }
   
   public void reset () {
      setX(INITIAL_ZACK_X);
      setY(INITIAL_ZACK_Y);
      setEndX(INITIAL_ZACK_X +20);
      setEndY(INITIAL_ZACK_Y +20);
   
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
      if(property.equals("w") || property.equals("ww")) {
         //make it so zack lands in middle of next tile
         //set zacks location to middle of two tiles to the left
         this.setY(((m.getY() + m.getEndY())/2) - 160);
         this.setEndY(this.getY() + length);
         this.setX(((m.getX() + m.getEndX())/2));
         this.setEndX(this.getX() + width);
      }
      //if spring is to launch down
      else if(property.equals("s") || property.equals("ss")) {
         //make it so zack lands in middle of next tile
         //set zacks location to middle of two tiles to the left
         this.setY(((m.getY() + m.getEndY())/2) + 140);
         this.setEndY(this.getY() + length);
         this.setX(((m.getX() + m.getEndX())/2));
         this.setEndX(this.getX() + width);
      }
      //if spring is to launch left
      else if(property.equals("a") || property.equals("aa")) {
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
   
   
   
   public void zackPositionDoor(String property) {
    // Extract the room number (first character) and door (second character)
      System.out.println("Property: " + property);
      int roomNumber = Integer.parseInt(property.substring(0,1)); // Get room number from the string
      char door = property.charAt(1); // Get door identifier
   
      switch (roomNumber) {
         case 0:
            switch (door) {
               case 'A':
                  INITIAL_ZACK_X = 410;
                  INITIAL_ZACK_Y = 600;
                  break;
            }
            break;
            
         case 1:
            switch (door) {
               case 'A':
                  INITIAL_ZACK_X = 350;
                  INITIAL_ZACK_Y = 260;
                  // INITIAL_ZACK_X = 415;
               //                   INITIAL_ZACK_Y = 100;
                  break;
               case 'B':
                  INITIAL_ZACK_X = 380;
                  INITIAL_ZACK_Y = 520;
                  break;
            }
            break;
         case 2:
            switch (door) {
               case 'A':
                  INITIAL_ZACK_X = 390;
                  INITIAL_ZACK_Y = 100;
                  break;
               case 'B':
                  INITIAL_ZACK_X = 590;
                  INITIAL_ZACK_Y = 260;
                  break;
               case 'C':
                  INITIAL_ZACK_X = 230;
                  INITIAL_ZACK_Y = 360;
                  break;
            }
            
            break;
         case 3:
            switch (door) {
               case 'A':
                  INITIAL_ZACK_X = 300;
                  INITIAL_ZACK_Y = 210;
                  break;
               case 'B':
                  INITIAL_ZACK_X = 110;
                  INITIAL_ZACK_Y = 510;
                  break;
               case 'C':
                  INITIAL_ZACK_X = 350;
                  INITIAL_ZACK_Y = 520;
                  break;
            
            }
            break;
         case 4:
            switch (door) {
               case 'A':
                  INITIAL_ZACK_X = 230;
                  INITIAL_ZACK_Y = 260;
                  break;
               case 'B':
                  INITIAL_ZACK_X = 510;
                  INITIAL_ZACK_Y = 260;
                  break;
            }
            break;
         case 5:
            switch (door) {
               case 'A':
                  INITIAL_ZACK_X = 270;
                  INITIAL_ZACK_Y = 200;
                  break;
               case 'B':
                  INITIAL_ZACK_X = 520;
                  INITIAL_ZACK_Y = 500;
                  break;
               case 'C':
                  INITIAL_ZACK_X = 520;
                  INITIAL_ZACK_Y = 350;
                  break;
            
            
            
            }
            break;
         case 6:
            switch (door) {
               case 'A':
                  INITIAL_ZACK_X = 200;
                  INITIAL_ZACK_Y = 430;
                  break;
               case 'B':
                  INITIAL_ZACK_X = 260;
                  INITIAL_ZACK_Y = 270;
                  break;
               case 'C':
                  INITIAL_ZACK_X = 510;
                  INITIAL_ZACK_Y = 520;
                  break;
                  case 'D':
                  INITIAL_ZACK_X = 270;
                  INITIAL_ZACK_Y = 530;
                  break;
            
            
            
            }
            break;
      
         case 7:
            switch (door) {
               case 'A':
                  INITIAL_ZACK_X = 270;
                  INITIAL_ZACK_Y = 260;
                  break;
               case 'B':
                  INITIAL_ZACK_X = 510;
                  INITIAL_ZACK_Y = 260;
                  break;
               case 'C':
                  INITIAL_ZACK_X = 260;
                  INITIAL_ZACK_Y = 510;
                  break;
                  case 'D':
                  INITIAL_ZACK_X = 270;
                  INITIAL_ZACK_Y = 270;
                  break;
            
            
            
            }
            break;
         case 8:
            switch (door) {
               case 'A':
                  INITIAL_ZACK_X = 520;
                  INITIAL_ZACK_Y = 270;
                  break;
               case 'B':
                  INITIAL_ZACK_X = 530;
                  INITIAL_ZACK_Y = 510;
                  break;
               case 'C':
                  INITIAL_ZACK_X = 520;
                  INITIAL_ZACK_Y = 350;
                  break;
            
            
            
            }
            break;
      
         case 9:
            switch (door) {
               case 'A':
                  INITIAL_ZACK_X = 270;
                  INITIAL_ZACK_Y = 200;
                  break;
               case 'B':
                  INITIAL_ZACK_X = 530;
                  INITIAL_ZACK_Y = 510;
                  break;
               case 'C':
                  INITIAL_ZACK_X = 520;
                  INITIAL_ZACK_Y = 350;
                  break;
            
            
            
            }
            break;
      
      
      
        // Continue for other rooms...
         default:
            // Handle out-of-range case if needed
            break;
      }
   }

}

