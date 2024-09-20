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
    
        canvas = new Canvas(720,720);
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

        zack = new Zack(100, 100, Color.BLUE); // Initial position and color

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
                System.out.println(getCurrentTile(tiles,zack.getX(),zack.getY()).getColor());
            }
        };
        animationTimer.start();

        primaryStage.setTitle("GraphicsContext Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawTiles() 
    {   

        for (int i = 0; i < tiles.length; i++) 
        {
            for (int j = 0; j < tiles[i].length; j++) 
            {
                Tile tile = tiles[i][j];
     
                    // Set color based on position
                    gc.setFill(tile.getColor());
                    gc.fillRect(j * 80, i * 80, 80, 80); // Draw the tile
                    
               
            }
        }

        //}
    }

    private void draw() 
    {
        // Clear the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Redraw tiles
        drawTiles();

        // Draw Zack
        gc.setFill(zack.getColor());
        gc.fillRect(zack.getX(), zack.getY(), 40, 40); // Assuming Zack is represented as a rectangle
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

        // Update Zack's position
        zack.incrementY(deltaY);
        zack.incrementX(deltaX);
    }

    public Tile getCurrentTile(Tile[][] tiles, int x, int y) 
    {
        int tileX = x / 80; // Assuming tile size is 100x100
        int tileY = y / 80;
        
        //System.out.println("Current Tile: \t" + tileX + " " + tileY);
        return tiles[tileX][tileY];
    }
}
