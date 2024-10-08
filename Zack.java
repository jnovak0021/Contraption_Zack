import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Zack extends GameObject {

   static final int LENGTH = 20;
   static final int WIDTH = 20;
   private Tile[][] tiles;
   private ArrayList<Mechanism> mechs;
   private int INITIAL_ZACK_X; // Starting X position
   private int INITIAL_ZACK_Y; // Starting Y position
   private LoadLevel ll;
   private boolean hasScrewdriver = false;

   public Zack(int x, int y, Color myColor, LoadLevel ll) {
      super(x, y, x + WIDTH, y + LENGTH, myColor);
      this.ll = ll;

   }

    // This method is constantly called from Main{}
   public void move(int dX, int dY, Tile[][] tiles, ArrayList<Mechanism> mechs, ArrayList <Item> items) {
      int adjustedDX = (int) dX;
      int adjustedDY = (int) dY;
      this.tiles = tiles;
      this.mechs = mechs;
   
      incrementX(adjustedDX);
      incrementY(adjustedDY);

      //check for items on ground
      for (int i = 0; i < items.size(); i++){
         if(overlap(items.get(i))) {
            items.get(i).collect();
            if(items.get(i) instanceof Screwdriver)
               hasScrewdriver = true;
         }
      }


        // If there is a collision, revert changes
      if (collides(tiles, mechs)) {
         incrementX(-adjustedDX);
         incrementY(-adjustedDY);
      }
   }

    // Internal to Zack

   private boolean collides(Tile[][] tiles, ArrayList<Mechanism> mechs) {
      for (int i = 0; i < mechs.size(); i++) {

         Mechanism current = mechs.get(i);
         boolean hit = overlap(current);

        // Check if the current object is a wall and if it collides
         if (current instanceof Wall && hit) {
            return true;
         }
         //stanchion collision
         else if(current instanceof Stanchion && hit){
            return true;
         }
         
         // Door collision handling
         else if (current instanceof Door && hit) {
            if(!current.isActive())
            {

               return false;
            } else {
               ((Mechanism) current).performFunction();
               String doorProperty = current.getProperty(); // Assume this returns an int corresponding to the door number
               zackPositionDoor(doorProperty); // Set Zack's new position based on the door's property
               setX(INITIAL_ZACK_X);
               setY(INITIAL_ZACK_Y);
               setEndX(INITIAL_ZACK_X + LENGTH);
               setEndY(INITIAL_ZACK_Y + WIDTH);
            }
         }

        // Check if object is a jukebox and if it collides
         if (current instanceof Jukebox && hit) {
            return true;
         }

        // Button
         if (current instanceof Button && hit) {
            if (((Mechanism) current).isActive()) { // Button is not pressed
               ((Mechanism) current).activate();
               ((Mechanism) current).performFunction();
            }
            return false;
         }
         //wallswitch -- unlike the button, wall switch can be toggled on and off by interacting with it
         else if(current instanceof WallSwitch && hit){
         

            ((Mechanism) current).performTimedFunction();
            return true;

         }

        // Timer button
         if (current instanceof TimerButton && hit) {
            if (((Mechanism) current).isActive()) { // Button is not pressed
               ((Mechanism) current).activate();
               ((Mechanism) current).performFunction();
            }
            return false;
         }

        // Spike
         if (current instanceof Spike && hit && current.isActive()) {
            return true;
         }


        // Timer door
         if (current instanceof TimerDoor && hit) {
            //System.out.println("TimerDoor");

            if(((Mechanism)current).isActive()){
            
               
               return true;
            }
            else{

               ArrayList<Mechanism> Amechs = ll.getTimedMechanisms();
               for (Mechanism mechanism : Amechs) {
                  if (mechanism instanceof TimerDoor) {
                     ((TimerDoor) mechanism).pauseTimer(); // Resume the TimerDoor

                  }
               }

               return false;
            }
         }

        // Spring collision
         if (current instanceof Spring && hit) {
            //System.out.println("Spring");
            if (current.isActive()) {
               return true;
            } else {
               springMove(current, current.getProperty());
                // If spring is not a reusable spring, activate it so that it will not be usable
               if (!(current.getProperty().equals("ww") || current.getProperty().equals("aa") ||
                      current.getProperty().equals("ss") || current.getProperty().equals("dd"))) {
                  current.activate();
               }
               return false;
            }
         }

        // Tesla coil
         if (current instanceof TeslaCoil && hit) {
            if (current.isActive()) {
               return true;
            } else {
               return false;
            }
         }

        // Pulley
         if (current instanceof Pulley && hit) {
            return true;
         }

        // Treadmill
         if (current instanceof Treadmill && hit) {
            if (!(current.isActive())) {
               return true;
            } else {
                // Determine if Zack is above or below the treadmill object
               if (getY() >= current.getEndY() && current.getProperty().equals("UP")) {
                  incrementY(-10);
               } else if (getEndY() <= current.getY() && current.getProperty().equals("DOWN")) {
                  incrementY(10);
               } else {
                  if (current.getProperty().equals("UP")) {
                     incrementY(-10);
                  } else if (current.getProperty().equals("DOWN")) {
                     incrementY(10);
                  }
               }
            }
         }

        // Screw
         if (current instanceof Screw && hit) {
            if(hasScrewdriver)
            {
               ((Mechanism) current).performFunction();
            }
            return true;

         }
         // Floating tile
         if (current instanceof FloatingTile && hit) {
            return false;
         }

        // For all objects that Zack can collide with, call performFunction method
         if (hit) {
            //System.out.println("HIT CODE");
            mechs.get(i).performFunction(); // Set that the mechanism is being collided with
         }
      }
   
    // Check tiles
      for (int i = 0; i < tiles.length; i++) {
         for (int j = 0; j < tiles[i].length; j++) {
            if (overlap(tiles[i][j]) && (tiles[i][j] instanceof Abyss || tiles[i][j] instanceof Water)) {
               return true;
            }
         }
      }


      int leftBorder1 = 430;   // First left border
      int rightBorder1 = 500;  // First right border
      int leftBorder2 = 298;   // Second left border
      int rightBorder2 = 343;  // Second right border

    // Adjust as needed

    // Inside your movement or collision handling method
      boolean isWithinBorders = (getX() >= leftBorder1 && getX() <= rightBorder1) ||
                              (getX() >= leftBorder2 && getX() <= rightBorder2);

      if (!isWithinBorders) {
        // Zack is within one of the defined borders, resume TimerDoors

         ArrayList<Mechanism> Amechs = ll.getTimedMechanisms();
         for (Mechanism mechanism : Amechs) {
            if (mechanism instanceof TimerDoor) {
               ((TimerDoor) mechanism).resumeTimer(); // Resume the TimerDoor

            }
         }
      }

      return false;
   }



   public void reset() {
      setX(INITIAL_ZACK_X);
      setY(INITIAL_ZACK_Y);
      setEndX(INITIAL_ZACK_X + WIDTH);
      setEndY(INITIAL_ZACK_Y + LENGTH);
   }

   //accessor for hasScrewdriver
   public void setHasScrewdriver(boolean b)
   {
      hasScrewdriver = b;
   }

    // Check if any two game objects are currently overlapping
   public boolean overlap(GameObject o) {
      if (this == o) {
         return false;
      }

      int thisLeft = getX();
      int thisRight = getEndX();
      int thisTop = getY();
      int thisBottom = getEndY();
   
      int otherLeft = o.getX();
      int otherRight = o.getEndX();
      int otherTop = o.getY();
      int otherBottom = o.getEndY();

      return (thisLeft < otherRight &&
                thisRight > otherLeft &&
                thisTop < otherBottom &&
                thisBottom > otherTop);
   }

   public void springMove(Mechanism m, String property) {
      int dX = 0;
      int dY = 0;
      int centerX = (m.getX() + m.getEndX()) / 2;
      int centerY = (m.getY() + m.getEndY()) / 2;

      if ("w".equals(property) || "ww".equals(property)) {
         setY(centerY - 160);
      } else if ("s".equals(property) || "ss".equals(property)) {
         setY(centerY + 140);
      } else if ("a".equals(property) || "aa".equals(property)) {
         setX(centerX - 160);
      } else {
         setX(centerX + 140);
      }

        // Update the end coordinates
      setEndX(getX() + WIDTH);
      setEndY(getY() + LENGTH);
   }

   public void drawMe(GraphicsContext gc) {
      gc.setFill(getColor());
      gc.fillRect(getX(), getY(), LENGTH, WIDTH); // Center the rectangle on Zack's position
   }
   
   
   
   public void zackPositionDoor(String property) {
    // Extract the room number (first character) and door (second character)
      //System.out.println("Property: " + property);
      int roomNumber = Integer.parseInt(property.substring(0,1)); // Get room number from the string
      char door = property.charAt(1); // Get door identifier
   
      switch (roomNumber) {
         case 0:
            switch (door) {
               case 'A':
                  INITIAL_ZACK_X = 400;
                  INITIAL_ZACK_Y = 680;
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
                  INITIAL_ZACK_X = 530;
                  INITIAL_ZACK_Y = 457;
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
                  INITIAL_ZACK_X = 216;
                  INITIAL_ZACK_Y = 444;
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
                  INITIAL_ZACK_X = 260;
                  INITIAL_ZACK_Y = 270;
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
   private void resetPosition() {
      setX(INITIAL_ZACK_X);
      setY(INITIAL_ZACK_Y);
      setEndX(INITIAL_ZACK_X + WIDTH);
      setEndY(INITIAL_ZACK_Y + LENGTH);
   }

   private boolean isReusableSpring(String property) {
      return "ww".equals(property) || "aa".equals(property) || "ss".equals(property) || "dd".equals(property);
   }

   private void handleTreadmillMovement(Mechanism current) {
      if (getY() >= current.getEndY() && "UP".equals(current.getProperty())) {
         incrementY(-10);
      } else if (getEndY() <= current.getY() && "DOWN".equals(current.getProperty())) {
         incrementY(10);
      } else {
         if ("UP".equals(current.getProperty())) {
            incrementY(-10);
         } else if ("DOWN".equals(current.getProperty())) {
            incrementY(10);
         }
      }
   }


}

