import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/*
   Zack really no longer uses any gameobject functionalituy
*/
public class Zack extends GameObject {
    private int x, y, endX, endY;
    private Color color;

    public Zack(int x, int y, Color myColor) {
        super(x, y, myColor);
        this.x = x;
        this.y = y;
        this.color = myColor;
        this.endX = x + 40; // Adjust based on rectangle size
        this.endY = y + 40;
    }

    public void setX(int newX) {
        this.x = newX;
        this.endY = endY;
        this.endX = newX + 40; // Update endX accordingly
    }

    public void setY(int newY) {
        this.y = newY;
        this.endY = y;
        this.endY = newY + 40; // Update endY accordingly
    }
    
    public void move(int newX, int newY, Tiles[][]){
        
        //move zack
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getEndX() {
        return this.endX;
    }

    public int getEndY() {
        return this.endY;
    }

    public Color getColor() {
        return this.color;
    }

    public void drawMe(GraphicsContext gc) {
        gc.setFill(this.color);
        gc.fillRect(x - 20, y - 20, 40, 40); // Center the rectangle on Zack's position
    }
}
