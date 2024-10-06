import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Water extends Tile {
    private static final int TILE_WIDTH = 80; // Width of each rectangle
    private static final int TILE_HEIGHT = 80; // Height of each rectangle
    private static final int NUMBER_OF_RECTANGLES = 4; // Number of rectangles
    private double[] yOffsets; // Y offsets for each rectangle
    private double speed = 1; // Speed of the downward movement
    private Color color; // Color of the water


    // Constructor that matches the expected signature
    public Water(int x, int y, Color myColor) {
        super(x, y, x + TILE_WIDTH, y + TILE_HEIGHT, myColor, false);
        this.color = myColor; // Set the color
        yOffsets = new double[NUMBER_OF_RECTANGLES];

        // Initialize yOffsets to start just above the visible area
        for (int i = 0; i < NUMBER_OF_RECTANGLES; i++) {
            yOffsets[i] = -TILE_HEIGHT + (i * (TILE_HEIGHT / NUMBER_OF_RECTANGLES)); // Start just above
        }
    }

    @Override
    public String toString() {
        return "Water";
    }

    public void drawMe(GraphicsContext gc) {
        // Clear the previous drawing
        gc.clearRect(getX(), getY(), TILE_WIDTH, TILE_HEIGHT);

        // Draw rectangles in shades of blue
        for (int i = 0; i < NUMBER_OF_RECTANGLES; i++) {
            Color shade = color.interpolate(Color.LIGHTBLUE, (double) i / (NUMBER_OF_RECTANGLES - 1));
            gc.setFill(shade);
            gc.fillRect(getX(), getY() + yOffsets[i], TILE_WIDTH, TILE_HEIGHT);

            // Update the position for the next frame
            yOffsets[i] += speed;

            // Reset the position if it goes off the screen
            if (yOffsets[i] > TILE_HEIGHT) {
                yOffsets[i] = -TILE_HEIGHT; // Move back to the top
            }
        }


        // Draw the black rectangle at the specified coordinates
        gc.setFill(Color.BLACK);
        gc.fillRect(390, 70, 90, 89); // Coordinates and dimensions (width: 90, height: 89)

        // Optionally draw the border
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(getX(), getY(), TILE_WIDTH, TILE_HEIGHT);
    }

    public void startAnimation(GraphicsContext gc) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                drawMe(gc); // Redraw the water tile
            }
        };
        timer.start(); // Start the animation timer
    }
}
