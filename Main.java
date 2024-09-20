import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class Main extends Application {

    private static final double WINDOW_WIDTH = 800;
    private static final double WINDOW_HEIGHT = 600;
    private static final double PLAYER_SIZE = 20;
    private static final double MOVE_SPEED = 1;

    private static final int GRID_SIZE_X = 6;  // Number of squares in the X (width) direction
    private static final int GRID_SIZE_Y = 4;  // Number of squares in the Y (height) direction
    private static final double SQUARE_SIZE = 40;
    private static final double ROOM_WIDTH = GRID_SIZE_X * SQUARE_SIZE;
    private static final double ROOM_HEIGHT = GRID_SIZE_Y * SQUARE_SIZE;
    private static final double ROOM_START_X = (WINDOW_WIDTH - ROOM_WIDTH) / 2;  // Centering the room horizontally
    private static final double ROOM_START_Y = (WINDOW_HEIGHT - ROOM_HEIGHT) / 2;  // Centering the room vertically

    private Set<KeyCode> activeKeys = new HashSet<>();
    private List<Rectangle> blockedSquares = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: lightblue;");  // Background color to simulate floating

        // Create the room with 4x3 squares
        for (int i = 0; i < GRID_SIZE_X; i++) {
            for (int j = 0; j < GRID_SIZE_Y; j++) {
                Rectangle square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, Color.GREY);
                square.setStroke(Color.DARKSLATEGRAY);  // Dark border around each square
                square.setX(ROOM_START_X + i * SQUARE_SIZE);
                square.setY(ROOM_START_Y + j * SQUARE_SIZE);
                pane.getChildren().add(square);
            }
        }

        // Define some blocked squares
        addBlockedSquare(pane, 1, 1); 
        addBlockedSquare(pane, 2, 2); 
        
        // Create the player as a blue square
        Rectangle player = new Rectangle(PLAYER_SIZE, PLAYER_SIZE, Color.BLUE);
        player.setX(ROOM_START_X + SQUARE_SIZE / 2);
        player.setY(ROOM_START_Y + SQUARE_SIZE / 2);

        pane.getChildren().add(player);

        // Handle key presses to add to active keys
        pane.setOnKeyPressed(event -> activeKeys.add(event.getCode()));

        // Handle key releases to remove from active keys
        pane.setOnKeyReleased(event -> activeKeys.remove(event.getCode()));

        // AnimationTimer for smooth movement
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updatePlayerPosition(player);
            }
        };
        timer.start();

        // Set up the scene and stage
        Scene scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Floating Room with Blocked Squares");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Request focus on the pane to receive key events
        pane.requestFocus();
    }

    private void addBlockedSquare(Pane pane, int gridX, int gridY) {
        Rectangle blockedSquare = new Rectangle(SQUARE_SIZE, SQUARE_SIZE, Color.BLACK);
        blockedSquare.setX(ROOM_START_X + gridX * SQUARE_SIZE);
        blockedSquare.setY(ROOM_START_Y + gridY * SQUARE_SIZE);
        blockedSquares.add(blockedSquare);
        pane.getChildren().add(blockedSquare);
    }

    private void updatePlayerPosition(Rectangle player) {
        double x = player.getX();
        double y = player.getY();

        double nextX = x;
        double nextY = y;

        // Separate checks for each direction
        if (activeKeys.contains(KeyCode.W)) {
            nextY -= MOVE_SPEED; // Move up
            if (isWithinBounds(nextX, nextY) && !isCollidingWithBlockedSquare(nextX, nextY)) {
                player.setY(nextY);
            }
        }
        if (activeKeys.contains(KeyCode.A)) {
            nextX -= MOVE_SPEED; // Move left
            if (isWithinBounds(nextX, y) && !isCollidingWithBlockedSquare(nextX, y)) {
                player.setX(nextX);
            }
        }
        if (activeKeys.contains(KeyCode.S)) {
            nextY += MOVE_SPEED; // Move down
            if (isWithinBounds(nextX, nextY) && !isCollidingWithBlockedSquare(nextX, nextY)) {
                player.setY(nextY);
            }
        }
        if (activeKeys.contains(KeyCode.D)) {
            nextX += MOVE_SPEED; // Move right
            if (isWithinBounds(nextX, y) && !isCollidingWithBlockedSquare(nextX, y)) {
                player.setX(nextX);
            }
        }
    }

    private boolean isWithinBounds(double nextX, double nextY) {
        return nextX >= ROOM_START_X && nextX <= ROOM_START_X + GRID_SIZE_X * SQUARE_SIZE - PLAYER_SIZE &&
               nextY >= ROOM_START_Y && nextY <= ROOM_START_Y + GRID_SIZE_Y * SQUARE_SIZE - PLAYER_SIZE;
    }

    private boolean isCollidingWithBlockedSquare(double nextX, double nextY) {
        for (Rectangle blockedSquare : blockedSquares) {
            if (nextX < blockedSquare.getX() + SQUARE_SIZE &&
                nextX + PLAYER_SIZE > blockedSquare.getX() &&
                nextY < blockedSquare.getY() + SQUARE_SIZE &&
                nextY + PLAYER_SIZE > blockedSquare.getY()) {
                return true;  // Collision detected
            }
        }
        return false;  // No collision
    }

    public static void main(String[] args) {
        launch(args);
        LoadLevel ll = new LoadLevel();
        
        Tile[][] tiles = ll.getRoomTile(0);
        
  
        
        
    }
}
