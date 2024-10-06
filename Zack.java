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

   public Zack(int x, int y, Color myColor, LoadLevel ll) {
      super(x, y, x + WIDTH, y + LENGTH, myColor);
      this.ll = ll;
   }

    // This method is constantly called from Main{}
   public void move(int dX, int dY, Tile[][] tiles, ArrayList<Mechanism> mechs) {
      int adjustedDX = (int) dX;
      int adjustedDY = (int) dY;
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

    // Internal to Zack
   private boolean collides(Tile[][] tiles, ArrayList<Mechanism> mechs) {
      for (Mechanism current : mechs) {
         if (overlap(current)) {
            if (current instanceof Wall || current instanceof Stanchion || current instanceof Jukebox) {
               return true; // Wall, Stanchion, or Jukebox collision
            }
         
                // Door collision handling
            if (current instanceof Door) {
               if (!current.isActive()) {
                  return false;
               }
               ((Mechanism) current).performFunction();
               zackPositionDoor(current.getProperty());
               resetPosition();
               return false; // Return false after handling door
            }
         
                // Button handling
            if (current instanceof Button) {
               if (((Mechanism) current).isActive()) {
                  ((Mechanism) current).activate();
                  ((Mechanism) current).performFunction();
               }
               return false; // Do not block movement
            }
         
                // Wall switch
            if (current instanceof WallSwitch) {
               ((Mechanism) current).performTimedFunction();
               return true;
            }
         
                // TimerButton
            if (current instanceof TimerButton) {
               if (((Mechanism) current).isActive()) {
                  ((Mechanism) current).activate();
                  ((Mechanism) current).performFunction();
               }
               return false;
            }
         
                // Spike handling
            if (current instanceof Spike && current.isActive()) {
               return true; // Spike collision
            }
         
                // FloatingTile
            if (current instanceof FloatingTile) {
               return false; // No collision
            }
         
                // TimerDoor handling
            if (current instanceof TimerDoor) {
               TimerDoor timerDoor = (TimerDoor) current;
               ArrayList<Mechanism> Amechs = ll.getTimedMechanisms();
               if (timerDoor.isActive()) {
                  return true; // Active TimerDoor
               } else {
                  timerDoor.pauseTimer();
                  for (Mechanism mechanism : Amechs) {
                     if (mechanism instanceof TimerDoor) {
                        ((TimerDoor) mechanism).pauseTimer();
                     }
                  }
               }
            }
         
                // Resume TimerDoors if current is not TimerDoor and is hit
            if (!(current instanceof TimerDoor)) {
               ArrayList<Mechanism> Amechs = ll.getTimedMechanisms();
               for (Mechanism mechanism : Amechs) {
                  if (mechanism instanceof TimerDoor) {
                     ((TimerDoor) mechanism).resumeTimer();
                  }
               }
            }
         
                // Spring collision
            if (current instanceof Spring) {
               if (current.isActive()) {
                  return true;
               } else {
                  springMove(current, current.getProperty());
                  if (!isReusableSpring(current.getProperty())) {
                     current.activate();
                  }
                  return false;
               }
            }
         
                // Tesla Coil
            if (current instanceof TeslaCoil && current.isActive()) {
               return true;
            }
         
                // Pulley and Treadmill handling
            if (current instanceof Pulley || (current instanceof Treadmill && current.isActive())) {
               return true; // Pulley collision
            }
         
                // Treadmill movement logic
            if (current instanceof Treadmill) {
               handleTreadmillMovement(current);
            }
         
                // Call performFunction for other mechanisms
            ((Mechanism) current).performFunction();
         }
      }
   
        // Check for collision with tiles
      for (Tile[] row : tiles) {
         for (Tile tile : row) {
            if (overlap(tile) && (tile instanceof Abyss || tile instanceof Water)) {
               return true;
            }
         }
      }
         
    //   if (!collides(tiles, mechs)) {
   //       // No collisions detected, safe to move or interact
   //          ArrayList<Mechanism> Amechs = ll.getTimedMechanisms();
   //          for (Mechanism mechanism : Amechs) {
   //             if (mechanism instanceof TimerDoor) {
   //                ((TimerDoor) mechanism).resumeTimer(); // Resume the TimerDoor
   //             }
   //          }
   //       
   //       }
      
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
               //System.out.println("Resumed TimerDoor: " + mechanism.getProperty());
            }
         }
      }
   
   
   
   
      return false; // No collision
   }

   public void reset() {
      setX(INITIAL_ZACK_X);
      setY(INITIAL_ZACK_Y);
      setEndX(INITIAL_ZACK_X + WIDTH);
      setEndY(INITIAL_ZACK_Y + LENGTH);
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
      System.out.println("Property: " + property);
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

