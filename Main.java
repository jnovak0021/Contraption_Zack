import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
   private Canvas canvas;
   private GraphicsContext gc;
   private int selectedPauseOption = 0; // Track the selected pause menu option

   private ArrayList<RoomObject> savedRooms;
   private int savedZackY;
   private int savedZackX;
   private LoadLevel savedLL = new LoadLevel();


   private boolean inMenu = true; // Track if we're in the main menu
   private boolean paused = false; // Track if the game is paused
   private int selectedOption = 0; // Track the selected menu option

   private int INITIAL_ZACK_X = 300; // Starting X position
   private int INITIAL_ZACK_Y = 540; // Starting Y position
   
   private int mouseX = 0;
   private int mouseY = 0;
   
   private int roomCount = 0;


   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) {
      canvas = new Canvas(1368, 728);
      gc = canvas.getGraphicsContext2D();
   
      ll = new LoadLevel();
      tiles = null; // Initially set to null until the game starts
      zack = new Zack(INITIAL_ZACK_X, INITIAL_ZACK_Y, Color.BLUE); // Initial position and color

      //crate clock
      Clock clock = new Clock();

      Pane root = new Pane(canvas); // Use Pane to hold the Canvas
      Scene scene = new Scene(root, 1366, 728);
   
      scene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
      scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));
   
      AnimationTimer animationTimer =
              new AnimationTimer() {
                 @Override
                 public void handle(long now) {
                 
                    //get current tiles and mechanism
                    tiles = ll.getRoomTiles(ll.getCurrentRoomNumber());
                    mechanisms = ll.getRoomMechanisms(ll.getCurrentRoomNumber());
                    
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
                    draw(); // Redraw the scene on each frame
                    Duration elapsedTime = clock.getElapsedTime();
                    System.out.println("Elapsed time: " + elapsedTime.toMillis() + " milliseconds");
                 }
              };
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

   private void draw() {
      // Clear the canvas
      gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
      
   
      if (inMenu) {
         drawMenu();
      } else if (paused) {
         drawPauseMenu();
      } else {
         // Draw tiles and Zack
         drawTiles();
         drawMechanisms();
         zack.drawMe(gc);
      }
      
      gc.setFill(Color.WHITE);
      gc.setFont(javafx.scene.text.Font.font(20));
      gc.fillText("Mouse X: " + mouseX + ", Mouse Y: " + mouseY, 10, 30);
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
               paused = false;
               // Reset Zack's position directly
               zack.setX(INITIAL_ZACK_X);
               zack.setY(INITIAL_ZACK_Y);
               tiles = ll.getRoomTiles(ll.getCurrentRoomNumber()); // Restart current room
               mechanisms = ll.getRoomMechanisms(ll.getCurrentRoomNumber()); // Load the mechanisms
               System.out.println("Restarting current area.");
               break;
            case 2: // Restart Level
               paused = false;
               // Reset Zack's position directly
               zack.setX(INITIAL_ZACK_X);
               zack.setY(INITIAL_ZACK_Y);
               tiles = ll.getRoomTiles(0); // Restart from room 0
               mechanisms = ll.getRoomMechanisms(0); // Load the mechanisms
               ll.setCurrentRoomNumber(0); // Reset room number
               // Call resetLevel from LoadLevel
               ll.resetLevel();
               System.out.println("Restarting level from room 0.");
               break;
            case 3: // Save
               paused = false;
               savedLL = ll;
               savedLL.setSavedRoom(ll.getCurrentRoomNumber());
               
               //savedRooms = ll.saveAllRoomsState();
               savedZackY = zack.getY();
               savedZackX = zack.getX();
               System.out.println("Saving Game.");
               break;
            case 4: // Load
               try {
                  //ll = savedLL; 
                  paused = false;
                  //ll.loadState(savedRooms);
                  zackPositionHandler(savedLL.getSavedRoom());
                  ll.setCurrentRoomNumber(savedLL.getSavedRoom());
                  System.out.println(savedLL.getSavedRoom());
               
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
      zack.move(deltaX, deltaY, tiles, mechanisms);
   
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
   
   public static void zackPositionHandler(int number) {
      switch (number) {
         case 0:
            int INITIAL_ZACK_X = 300;
            int INITIAL_ZACK_Y = 540;
            break;
         case 1:
            INITIAL_ZACK_X = 400;
            INITIAL_ZACK_Y = 660;
            break;
         case 2:
            INITIAL_ZACK_X = 2;
            INITIAL_ZACK_Y = 0;
            break;
         case 3:
            INITIAL_ZACK_X = 3;
            INITIAL_ZACK_Y = 0;
            break;
         case 4:
            INITIAL_ZACK_X = 4;
            INITIAL_ZACK_Y = 0;
            break;
         case 5:
            INITIAL_ZACK_X = 5;
            INITIAL_ZACK_Y = 0;
            break;
         case 6:
            INITIAL_ZACK_X = 6;
            INITIAL_ZACK_Y = 0;
            break;
         case 7:
            INITIAL_ZACK_X = 7;
            INITIAL_ZACK_Y = 0;
            break;
         case 8:
            INITIAL_ZACK_X = 8;
            INITIAL_ZACK_Y = 0;
            break;
         case 9:
            INITIAL_ZACK_X = 9;
            INITIAL_ZACK_Y = 0;
            break;
         case 10:
            INITIAL_ZACK_X = 10;
            INITIAL_ZACK_Y = 0;
            break;
         default:
                // Handle out-of-range case if needed
            break;
      }
   }
   
}
