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

public class Main2 extends Application 
{
    private Set<KeyCode> pressedKeys = new HashSet<>();
    private Zack zack;
    private LoadLevel ll;
    private Tile[][] tiles;
    private Canvas canvas;
    private GraphicsContext gc;

    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) 
    {
        canvas = new Canvas(720, 720);
        gc = canvas.getGraphicsContext2D();

        ll = new LoadLevel();
        tiles = ll.getRoomTiles(0);

        if (tiles != null) 
        {
            drawTiles(); // Draw tiles on the canvas
        } 
        else 
        {
            System.out.println("No tiles were returned.");
        }

        zack = new Zack(400, 400, Color.BLUE); // Initial position and color

        Pane root = new Pane(canvas); // Use Pane to hold the Canvas
        Scene scene = new Scene(root, 720, 720);

        scene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));

        AnimationTimer animationTimer = new AnimationTimer() 
        {
            @Override
            public void handle(long now) 
            {
                updateMovement();
                draw(); // Redraw the scene on each frame
                //getCurrentTile(tiles, zack.getX(), zack.getY(), 80);
            }
        };
        animationTimer.start();

        primaryStage.setTitle("GraphicsContext Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawTiles() 
    {   
        // Loop over tiles and call drawMe
        for (int i = 0; i < tiles.length; i++) 
        {
            for (int j = 0; j < tiles[i].length; j++) 
            {
               //call Tile or Abyss draw
               tiles[i][j].drawMe(gc);   
            }
        }
    }

    private void draw() 
    {
        // Clear the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Redraw tiles
        drawTiles();

        // Draw Zack
        zack.drawMe(gc);
        
        //add method to draw mechanisms later
    }

    

    private void updateMovement() 
    {
        int deltaX = 0;
        int deltaY = 0;

        if (pressedKeys.contains(KeyCode.W)) 
        {
            deltaY = -1; // Move up
        }
        if (pressedKeys.contains(KeyCode.S)) 
        {
            deltaY = 1; // Move down
        }
        if (pressedKeys.contains(KeyCode.A)) 
        {
            deltaX = -1; // Move left
        }
        if (pressedKeys.contains(KeyCode.D)) 
        {
            deltaX = 1; // Move right
        }
        
        //call the getCurrentTile and see if it is traverable -- if it is not, then reverse previous move
        if(!getCurrentTile(tiles, zack.getX(), zack.getY(), 40, 40, 80).isTraversable())
        {
           // Update Zack's position
           zack.setX(zack.getX() - deltaX*2);
           zack.setY(zack.getY() - deltaY*2);
        }
        else
        {
           // Update Zack's position
           zack.setX(zack.getX() + deltaX);
           zack.setY(zack.getY() + deltaY);
        }

       
    }
   
   public Tile getCurrentTile(Tile[][] tiles, int x, int y, int zackWidth, int zackHeight, int tileSize) 
   {
       // Calculate Zack's center position
       int centerX = x + zackWidth / 2;  // Zack's center X position
       int centerY = y + zackHeight / 2; // Zack's center Y position
   
       // Calculate the column and row Zack's center is in, based on the tile size
       int tileColumn = centerX / tileSize;
       int tileRow = centerY / tileSize;
   
       // Boundary check to ensure Zack's center is within the tile grid
       if (tileRow >= 0 && tileRow < tiles.length && tileColumn >= 0 && tileColumn < tiles[tileRow].length) 
       {
           System.out.println("Zack's center is in tile: Row " + tileRow + ", Column " + tileColumn);
           return tiles[tileRow][tileColumn];
       } 
       else 
       {
           System.out.println("Zack is out of bounds!");
           return null; // If Zack is out of the tile grid
       }
   }


}
