import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class Main extends Application 
{

    private Set<KeyCode> pressedKeys = new HashSet<>();
    private Zack zack;
    private LoadLevel ll;
    private Tile[][] tiles;

    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) 
    {
        Pane root = new Pane();

        ll = new LoadLevel();
        tiles = ll.getRoomTiles(0);

        if (tiles != null) 
        {
            boolean isGrey = true; // Start with grey
            for (int i = 0; i < tiles.length; i++) 
            {
                for (int j = 0; j < tiles[i].length; j++) 
                {
                    
                    Tile tile = tiles[i][j];
                    System.out.print(tile);
                    tile.setColor(Color.LIGHTGREY);
                    
                    if (tile != null) 
                    {
                        if (isGrey) 
                        {
                            tile.setColor(Color.LIGHTGREY);
                        } 
                        else 
                        {
                            tile.setColor(Color.BLACK);
                        }
                        // System.out.println("tile:\n\t" + tile.getX() + "\t" + tile.getY());
                        // Flip the color for the next tile
                        isGrey = !isGrey;
                        
                        tile.drawMe(root); // Pass the root pane to draw the tile
                    }
                }
                System.out.println();
            }
        } 
        else 
        {
            System.out.println("No tiles were returned.");
        }

        zack = new Zack(100, 100, Color.BLUE); // Initial position and color
        root.getChildren().add(zack.getShape()); // Add Zack to the root pane

        Scene scene = new Scene(root, 800, 600);

        scene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));

        AnimationTimer animationTimer = new AnimationTimer() 
        {
   
            @Override
            public void handle(long now) 
            {
                updateMovement();
                zack.updatePosition(); // Update Zack's visual position
                getCurrentTile(tiles, zack.getX(), zack.getY());
            }
           
        };
        animationTimer.start();

        primaryStage.setTitle("Tile Layout");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateMovement() 
    {
        int deltaX = 0;
        int deltaY = 0;

        if (pressedKeys.contains(KeyCode.W)) 
        {
            deltaY = -5; // Move up
        }
        if (pressedKeys.contains(KeyCode.S)) 
        {
            deltaY = 5; // Move down
        }
        if (pressedKeys.contains(KeyCode.A)) 
        {
            deltaX = -5; // Move left
        }
        if (pressedKeys.contains(KeyCode.D)) 
        {
            deltaX = 5; // Move right
        }

        // Update Zack's position
        // zack.setXPos(zack.getXPos() + deltaX);
        zack.incrementY(deltaY);
        zack.incrementX(deltaX);

        //System.out.println(zack.getXPos());

        if (zack.getX() % 100 == 0) 
        {
            //System.out.println("MASSIVE PENIS");
        }
    }

    public void getCurrentTile(Tile[][] tiles, int x, int y) 
    {
        x = ((x / 100) / 1);
        y = ((y / 100) / 1);
        System.out.println("Current Tile: \t" + x + " " + y);
    }
}
