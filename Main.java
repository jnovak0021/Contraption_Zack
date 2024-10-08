import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

import java.util.*;
import java.time.*;


public class Main extends Application {
   private Set<KeyCode> pressedKeys = new HashSet<>();
   private Zack zack;
   private LoadLevel ll;
   private Tile[][] tiles;
   private ArrayList<Mechanism> mechanisms;
   private ArrayList<Item> gameItems;
   private Canvas canvas;
   private GraphicsContext gc;
   private int selectedPauseOption = 0; // Track the selected pause menu option
   private long startTime;  //store the start time of the system for math
   private long pauseTime;  //
   private long elapsedTime;

   //private ArrayList<RoomObject> savedRooms;
   private int savedZackY;
   private int savedZackX;
   private int savedRoomNumber = 0;
   private ArrayList<RoomObject> savedRooms = new ArrayList<>(); // Initialize here



   private boolean inMenu = true; // Track if we're in the main menu
   private boolean paused = false; // Track if the game is paused
   private int selectedOption = 0; // Track the selected menu option

   private int INITIAL_ZACK_X; // Starting X position
   private int INITIAL_ZACK_Y; // Starting Y position


   
   private int previousRoomNumber = -1; // Track the previous room number
   
   private boolean isLoading = false; // Flag to indicate if the game is loading


   
   private int mouseX = 0;
   private int mouseY = 0;
   
   private int roomCount = 0;
   Clock c;


   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) {
   
      canvas = new Canvas(728, 728);
      gc = canvas.getGraphicsContext2D();
   
      ll = new LoadLevel();
      tiles = null; // Initially set to null until the game starts
      zack = new Zack(INITIAL_ZACK_X, INITIAL_ZACK_Y, Color.BLUE, ll); // Initial position and color
   
    // Create a Water tile instance
      Water waterTile = new Water(50, 50, Color.BLUE); // Example position and color
      waterTile.startAnimation(gc); // Start the animation for the water tile
   
      Pane root = new Pane(canvas); // Use Pane to hold the Canvas
      Scene scene = new Scene(root, 728, 728);
   
      scene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
      scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));
   
      AnimationTimer animationTimer =
            new AnimationTimer() {
               @Override
                public void handle(long now) {
               
                    // Get current tiles and mechanism
               
                  tiles = ll.getRoomTiles(ll.getCurrentRoomNumber());
                  mechanisms = ll.getRoomMechanisms(ll.getCurrentRoomNumber());
                  gameItems = ll.getRoomItems(ll.getCurrentRoomNumber());
                 
                    //get the elapsed time since program started in seconds
                  double elapsedTime = (now - startTime) / 1_000_000_000.0;
                  c.setElapsedTime(elapsedTime);
               
                  handleTime();


                  scene.setOnMouseMoved(
                            event -> {
                               mouseX = (int) event.getX();
                               mouseY = (int) event.getY();
                            });

                  if (inMenu) {
                     updateMenu();
                  } else if (paused) {
                     updatePauseMenu();
                  } else {
                     updateMovement();
                  }
               
                  scene.setOnMouseClicked(
                            event -> {
                                // Get the click coordinates and cast them to int
                               int clickX = (int) event.getX();
                               int clickY = (int) event.getY();
                            
                                // Set Zack's position to the clicked coordinates
                               zack.setX(clickX);
                               zack.setY(clickY);
                            
                                // Optionally adjust the end position if you have an animation or bounding box
                               zack.setEndX(zack.getX() + 20); // Adjust as needed
                               zack.setEndY(zack.getY() + 20); // Adjust as needed
                            
                                // Redraw the scene to reflect the new position
                               draw();
                            });
               
                  draw(); // Redraw the scene on each frame
               }
            };
   
      startTime = System.nanoTime();
      c = new Clock(startTime, 0);
   
    // Store the start time of the system to use for math
      animationTimer.start();
   
      primaryStage.setTitle("Contraption Zack");
      primaryStage.setScene(scene);
      primaryStage.show();
   }
   private void drawTiles() {
      // Loop over tiles and call drawMe
      for (int i = 0; i < tiles.length; i++) {
         for (int j = 0; j < tiles[i].length; j++) {
            // Call Tile or Abyss draw
            tiles[i][j].drawMe(gc);
         }
      }
   }

   // Method to draw the mechanisms in the level
   public void drawMechanisms() {
      // Loop over mechanisms and call
      for (int i = 0; i < mechanisms.size(); i++) {
         mechanisms.get(i).drawMe(gc);
      }
   }
   
   // Method to draw the items in the level

   public void drawItems() {
      // Loop over mechanisms and call'
      if(gameItems.size() != 0) {
         for (int i = 0; i < gameItems.size(); i++) {
            gameItems.get(i).drawMe(gc);
         }
      }
   }

   private void draw() {
      // Clear the canvas
      gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
      
      if(ll.getCurrentRoomNumber() == 10)
      {
         gc.setFill(Color.BLACK);
         gc.fillRect(0, 0, 8000, 6000); // Fullscreen black background
      
         gc.setFill(Color.WHITE);
         gc.setFont(javafx.scene.text.Font.font(48)); // Set font size
         String winText = "YOU WIN!";
         double textWidth = gc.getFont().getSize() * winText.length() / 2; // Estimate text width
         double textX = (800 - textWidth) / 2; // Centering the text
         double textY = 300; // Position text vertically
      
         gc.fillText(winText, textX, textY);
         
         
      }
      else
      {
      
         if (inMenu) {
            drawMenu();
         } else if (paused) {
            drawPauseMenu();
         } else {
         
         // Draw tiles and Zack
            drawTiles();
            drawMechanisms();
            if(gameItems.size() != 0)
               drawItems();
            zack.drawMe(gc);
         }
      
         int currentRoomNumber = ll.getCurrentRoomNumber();

        // Set the fill color and font for the room number
         gc.setFill(Color.WHITE);
         gc.setFont(javafx.scene.text.Font.font(20));
        
        // Draw the room number in the top right corner
         String roomNumberText = "Room: " + currentRoomNumber;
         double textWidth = gc.getFont().getSize() * roomNumberText.length() * 0.5; // Approximate width
         gc.fillText(roomNumberText, 100, 100); // Adjusted for top left position
      
         gc.setFill(Color.WHITE);
         gc.setFont(javafx.scene.text.Font.font(20));
         gc.fillText("Mouse X: " + mouseX + ", Mouse Y: " + mouseY, 10, 30);

      }
   }

   private void drawMenu() {
      // Draw background color
      gc.setFill(Color.BLACK);
      gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
   
      // Draw title
      gc.setFill(Color.WHITE);
      gc.setFont(javafx.scene.text.Font.font(40)); // Title font size
      String title = "Contraption Zack";
      double titleWidth = gc.getFont().getSize() * title.length() * 0.5; // Approximate width calculation
      gc.fillText(title, (canvas.getWidth() - titleWidth) / 2, 100); // Center title
   
      // Draw menu options
      String[] menuOptions = {"Start Game", "Exit Game"};
      for (int i = 0; i < menuOptions.length; i++) {
         gc.setFill(i == selectedOption ? Color.YELLOW : Color.WHITE); // Highlight selected option
         double optionWidth = gc.getFont().getSize() * menuOptions[i].length() * 0.5; // Approximate width calculation
         gc.fillText(menuOptions[i], (canvas.getWidth() - optionWidth) / 2, 200 + (i * 40)); // Center options
      }
   }

   private void drawPauseMenu() {
      gc.setFill(Color.BLACK);
      gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
   
      String[] pauseOptions = {"Resume", "Restart Area", "Restart Level", "Save", "Load", "Exit Game"};
      for (int i = 0; i < pauseOptions.length; i++) {
         gc.setFill(i == selectedPauseOption ? Color.YELLOW : Color.WHITE); // Highlight selected option
         gc.fillText(pauseOptions[i], 350, 200 + i * 40); // Adjust y position
      }
   }

   private void updateMenu() {
      if (pressedKeys.contains(KeyCode.W)) {
         if (selectedOption > 0) {
            selectedOption--; // Move up the menu
         }
         pressedKeys.remove(KeyCode.W); // Prevent rapid movement
      }
      if (pressedKeys.contains(KeyCode.S)) {
         if (selectedOption < 2) {
            selectedOption++; // Move down the menu
         }
         pressedKeys.remove(KeyCode.S); // Prevent rapid movement
      }
   
      if (pressedKeys.contains(KeyCode.ENTER)) {
         // Only allow a single press to register
         pressedKeys.remove(KeyCode.ENTER);
      
         switch (selectedOption) {
            case 0: // Start Game
               inMenu = false; // Start the game
               ll.setCurrentRoomNumber(0);
            
               tiles = ll.getRoomTiles(0); // Get the first room tiles
               mechanisms = ll.getRoomMechanisms(0); // Load the mechanisms
               System.out.println("Starting the game, loading first room.");
               zack.setX(300);
               zack.setY(540);
               zack.setEndX(zack.getX() + 20);
               zack.setEndY(zack.getY() + 20);
            
               break;
         
            case 1: // Exit Game
               System.exit(0); // Exit the game
               break;
         }
      }
   }

   private void updatePauseMenu() {
      if (pressedKeys.contains(KeyCode.W)) {
         if (selectedPauseOption > 0) {
            selectedPauseOption--; // Move up the menu
         }
         pressedKeys.remove(KeyCode.W); // Prevent rapid movement
      }
      if (pressedKeys.contains(KeyCode.S)) {
         if (selectedPauseOption < 5) { // Adjusted for 6 options
            selectedPauseOption++; // Move down the menu
         }
         pressedKeys.remove(KeyCode.S); // Prevent rapid movement
      }
   
      if (pressedKeys.contains(KeyCode.ENTER)) {
         pressedKeys.remove(KeyCode.ENTER); // Only allow a single press to register
      
         switch (selectedPauseOption) {
            case 0: // Resume
               paused = false; // Exit the pause menu
               break;
            case 1: // Restart Area
               //i do not know why, it is a mystery, but we have to run the code below twice for it to work
               for(int i = 0; i < 2; i++){
                  paused = false;
                  // Reset Zack's position directly
                  zack.reset();
                  ll.resetCurrentRoom();


                  tiles = ll.getRoomTiles(ll.getCurrentRoomNumber()); // Restart current room
                  mechanisms = ll.getRoomMechanisms(ll.getCurrentRoomNumber()); // Load the mechanisms
                  ll.setRoomColor();


               }




               if(ll.getCurrentRoomNumber() == 0)
               {
                  zack.setX(300);
                  zack.setY(540);
                  zack.setEndX(zack.getX() + 20);
                  zack.setEndY(zack.getY() + 20);
               }
               System.out.println("Restarting current area.");
               break;

            case 2: // Restart Level
               paused = false;
               isLoading = true; // Set loading 
               
                           
               zack.setHasScrewdriver(false);
               //reset all objects in LoadLevel
               ll.resetLevel();
               tiles = ll.getRoomTiles(0); // Restart from room 0
               mechanisms = ll.getRoomMechanisms(0); // Load the mechanisms
               // Reset Zack's position directly
            
            
               zack.setX(300);
               
               zack.setY(540);
               zack.setEndX(zack.getX() + 20);
               zack.setEndY(zack.getY() + 20);
            
            
               System.out.println("Restarting level from room 0.");
               break;
            case 3: // Save
               paused = false;
               savedRoomNumber = ll.getCurrentRoomNumber();
               ll.writeToFile();

               pauseTime = elapsedTime;

               //savedRooms = ll.saveAllRoomsState();
               savedZackY = zack.getY();
               savedZackX = zack.getX();
               System.out.println("Saving Game.");
               break;
            case 4: // Load
               try {
                  paused = false;
                  //startTime = pauseTime;
                  isLoading = true; // Set loading flag
                  ll = new LoadLevel(true);
                  ll.setSaved(true);
                  ll.setCurrentRoomNumber(savedRoomNumber);
                  tiles = ll.getRoomTiles(savedRoomNumber); // Restart from room 0
                  mechanisms = ll.getRoomMechanisms(savedRoomNumber); // Load the mechanisms

                  //loop over timed door to unpause then
                  for(int i = 0; i < ll.getTimedMechanisms().size(); i++)
                  {
                     if( ll.getTimedMechanisms().get(i) instanceof TimerDoor)
                     {
                        ((TimerDoor) ll.getTimedMechanisms().get(i)).resumeTimer();
                     }
                  }
               
                  System.out.println("Current room after load: " + ll.getCurrentRoomNumber());
               
                  zack.setX(savedZackX);
                  zack.setY(savedZackY);
                  zack.setEndX(zack.getX() + 20);
                  zack.setEndY(zack.getY() + 20);
               
               
                  System.out.println("Loading Game.");
               } catch (Exception e) {
                  System.out.println("An error occurred while loading the game: " + e.getMessage());
               }
               break;
         
            default:
               System.out.println("Invalid option.");
               break;
            case 5: // Exit Game
               System.exit(0); // Exit the game
               break;
         }
         
         
      }
   }

   private void updateMovement() {
      int deltaX = 0;
      int deltaY = 0;
   
      if (pressedKeys.contains(KeyCode.W)) {
         deltaY = -1; // Move up
      }
      if (pressedKeys.contains(KeyCode.S)) {
         deltaY = 1; // Move down
      }
      if (pressedKeys.contains(KeyCode.A)) {
         deltaX = -1; // Move left
      }
      if (pressedKeys.contains(KeyCode.D)) {
         deltaX = 1; // Move right
      }
   
      // Zack handles movement
      zack.move(deltaX, deltaY, tiles, mechanisms, gameItems);
   
      // Check for pause input
      if (pressedKeys.contains(KeyCode.ESCAPE)) {
         paused = true; // Set the game to paused state
      }
      
   }

   // Not being used
   public void handle(long now) {
      if (inMenu) {
         updateMenu();
      } else if (paused) {
         updatePauseMenu(); // Update pause menu
         drawPauseMenu(); // Draw pause menu
      } else {
         updateMovement();
      }
      draw(); // Redraw the scene on each frame
   }


   //time handling method that handles all time sensitive operation for mechanisms
   public void handleTime()
   {
      ArrayList<Mechanism> timedMechanisms = ll.getTimedMechanisms();
      //System.out.println("TIMED");
      for(int i = 0; i <  timedMechanisms.size(); i++)
      {
         timedMechanisms.get(i).performTimedFunction();
      }
   
   }

   
}
