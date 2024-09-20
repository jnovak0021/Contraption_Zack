import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// Zack is a special game object that the player controls
public class Zack extends GameObject {
    private Rectangle shape; // Shape representation

    public Zack(int x, int y, Color myColor) {
        super(x, y, myColor); // Parent constructor
        shape = new Rectangle(x, y, 30, 30); // Example size for the visual representation
        shape.setFill(myColor);
    }

    public Rectangle getShape() {
        return shape;
    }

    public void updatePosition() {
        // Update the rectangle's position based on GameObject's coordinates
        shape.setX(getX());
        shape.setY(getY());
    }

    // Getters for x and y positions
    public int getX() {
        return super.getX(); // Get x position from GameObject
    }

    public int getY() {
        return super.getY(); // Get y position from GameObject
    }

    // Setters for x and y positions
    public void setX(int newX) {
        super.setX(newX); // Set x position in GameObject
        updatePosition(); // Update the shape's position
    }

    public void setY(int newY) {
        super.setY(newY); // Set y position in GameObject
        updatePosition(); // Update the shape's position
    }
    public Color getColor()
    {
      return super.getColor();
    }
    
}