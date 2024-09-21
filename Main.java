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

public class Main extends Application 
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
        zack = new Zack(400, 400, Color.BLUE); // Initial position and color

        if (tiles != null) 
        {
            drawTiles(); // Draw tiles on the canvas
        } 
        else 
        {
            System.out.println("No tiles were returned.");
        }

        

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

            }
        };
        animationTimer.start();

        primaryStage.setTitle("GraphicsContext Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //method to call drawMe method of all tiles and print them to graphicsContext
    private void drawTiles() 
    {   
        
        // Loop over tiles and call drawMe
        for (int i = 0; i < tiles.length; i++) 
        {
            for (int j = 0; j < tiles[i].length; j++) 
            {
               //call Tile or Abyss draw
               tiles[i][j].drawMe(gc);   
               
               
               //I am calling the collides method in here for efficiency since we are already looping
               if(tiles[i][j].collides(zack.getX(),zack.getY()))
               {
                  tiles[i][j].drawMe(gc, Color.YELLOW);  
               }
                  
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
        
         
         
         //We can use the isTraversable method in Tile to determine if we can walk into block once constructed.s
         zack.setX(zack.getX() + deltaX);
         zack.setY(zack.getY() + deltaY);

         
    }
}
