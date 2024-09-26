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



   private boolean inMenu = true; // Track if we're in the main menu
   private boolean paused = false; // Track if the game is paused
   private int selectedOption = 0; // Track the selected menu option
   
   
   private static final int INITIAL_ZACK_X = 400; // Starting X position
   private static final int INITIAL_ZACK_Y = 400; // Starting Y position


   public static void main(String[] args) {
   
      launch(args);
   }

   @Override
    public void start(Stage primaryStage) {
      canvas = new Canvas(720, 720);
      gc = canvas.getGraphicsContext2D();
   
      ll = new LoadLevel();
      tiles = null; // Initially set to null until the game starts
      zack = new Zack(400, 400, Color.BLUE); // Initial position and color
   
      Pane root = new Pane(canvas); // Use Pane to hold the Canvas
      Scene scene = new Scene(root, 720, 720);
   
      scene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
      scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));
   
      AnimationTimer animationTimer = 
         new AnimationTimer() {
            @Override
            public void handle(long now) {
               if (inMenu) {
                  updateMenu();
               } else if (paused) {
                  updatePauseMenu();
               } else {
                  updateMovement();
               }
               draw(); // Redraw the scene on each frame
            
            }
         };
      animationTimer.start();
   
      primaryStage.setTitle("Contraption Zack");
      primaryStage.setScene(scene);
      primaryStage.show();
   }

   private void drawTiles() {
   
   
      // Loop over tiles and call drawMe
      for(int i = 0; i < tiles.length; i++) {
         for (int j = 0; j < tiles[i].length; j++) {
             // Call Tile or Abyss draw
            System.out.print(tiles[j][i] + " ");
            tiles[i][j].drawMe(gc);
         }
         System.out.println();
      }
      System.out.print("\n\n\n\n");
   }
   
   //method to draw the mechanisms in the level
   
   public void drawMechanisms()
   {
      //loop over mechanism and call 
      for(int i = 0; i < mechanisms.size(); i++)
      {
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
         
         //add a draw mechanisms
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
      String[] menuOptions = {"Start Game", "Load Game", "Exit Game"};
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
         if (i == selectedPauseOption) {
            gc.setFill(Color.YELLOW); // Highlight selected option
         } else {
            gc.setFill(Color.WHITE);
         }
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
               tiles = ll.getRoomTiles(0); // Get the first room tiles
               mechanisms = ll.getRoomMechanisms(0);  //load the mechanisms
               System.out.println("Starting the game, loading first room.");
               break;
            case 1: // Load Game
                    // Functionality not implemented
               System.out.println("Load Game option selected, but not functional.");
               break;
            case 2: // Exit Game
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
               mechanisms = ll.getRoomMechanisms(ll.getCurrentRoomNumber());  //load the mechanisms
               System.out.println("Restarting current area.");
               break;
            case 2: // Restart Level
               paused = false;
                // Reset Zack's position directly
               zack.setX(INITIAL_ZACK_X);
               zack.setY(INITIAL_ZACK_Y);
               tiles = ll.getRoomTiles(0); // Restart from room 0
               mechanisms = ll.getRoomMechanisms(0);  //load the mechanisms
               ll.setCurrentRoomNumber(0); // Reset room number
                
                //call resetlevel from loadLevel
               ll.resetLevel();
               System.out.println("Restarting level from room 0.");
               break;
            case 3: // Save
               paused = false;
            
               savedRooms = ll.saveAllRoomsState();
               savedZackY = zack.getY();
               savedZackX = zack.getX();
            
               System.out.println("Saving Game.");
            
               break;
         
            case 4: // Load
               try
               {
                  paused = false;
               
                  ll.loadState(savedRooms); 
                  zack.setX(savedZackX);
                  zack.setY(savedZackY);
               
                  System.out.println("Loading Game.");
               
               }
               catch (Exception e) {
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

   //not being used
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

}
