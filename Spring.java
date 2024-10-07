import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Spring extends Mechanism
{


    //feature/jacob contructor
    public Spring(String property, boolean activated, int x, int y, int endX, int endY, Color myColor, int associatedMechanisms, LoadLevel ll)
    {
        super(property, activated, x, y, endX, endY, myColor,associatedMechanisms, ll);
    }

    public void performFunction() {

    }

    public String toString()
    {
        return ("5:" + getProperty() + ":" + isActive() + ":" + getX() + ":" + getY() + ":" + getEndX() + ":" + getEndY() + ":" + getMyColor() + ":" + associatedMechanisms);
        //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>

    }



    @Override
    public void drawMe(GraphicsContext gc) {
        // Draw the overall rectangle with the color from getMyColor()

        gc.setFill(getMyColor());
        double width = getEndX() - getX();
        double height = getEndY() - getY();
        gc.clearRect(getX(), getY(), width, height);
        gc.fillRect(getX(), getY(), width, height);

        // Define the four quadrants
        double halfWidth = width / 2;
        double halfHeight = height / 2;

        // Determine direction based on the property
        String property = getProperty();

        // Arrow color and dimensions
        gc.setFill(Color.BLACK); // Set arrows to black for visibility
        double arrowWidth = halfWidth / 2;
        double arrowHeight = halfHeight / 2;

        // Draw arrows based on the property (w = north, a = west, s = south, d = east)
        if (property.equals("w") || property.equals("ww")) { // North facing
            drawArrow(gc, getX() + halfWidth / 2, getY(), arrowWidth, arrowHeight, "up");
            drawArrow(gc, getX() + halfWidth + halfWidth / 2, getY(), arrowWidth, arrowHeight, "up");
            drawArrow(gc, getX() + halfWidth / 2, getY() + halfHeight, arrowWidth, arrowHeight, "up");
            drawArrow(gc, getX() + halfWidth + halfWidth / 2, getY() + halfHeight, arrowWidth, arrowHeight, "up");
        } else if (property.equals("a") || property.equals("aa")) { // West facing
            drawArrow(gc, getX(), getY() + halfHeight / 2, arrowWidth, arrowHeight, "left");
            drawArrow(gc, getX(), getY() + halfHeight + halfHeight / 2, arrowWidth, arrowHeight, "left");
            drawArrow(gc, getX() + halfWidth, getY() + halfHeight / 2, arrowWidth, arrowHeight, "left");
            drawArrow(gc, getX() + halfWidth, getY() + halfHeight + halfHeight / 2, arrowWidth, arrowHeight, "left");
        } else if (property.equals("s") || property.equals("ss")) { // South facing
            drawArrow(gc, getX() + halfWidth / 2, getY() + halfHeight + halfHeight / 2, arrowWidth, arrowHeight, "down");
            drawArrow(gc, getX() + halfWidth + halfWidth / 2, getY() + halfHeight + halfHeight / 2, arrowWidth, arrowHeight, "down");
            drawArrow(gc, getX() + halfWidth / 2, getY() + halfHeight, arrowWidth, arrowHeight, "down");
            drawArrow(gc, getX() + halfWidth + halfWidth / 2, getY() + halfHeight, arrowWidth, arrowHeight, "down");
        } else if (property.equals("d") || property.equals("dd")) { // East facing
            drawArrow(gc, getX() + halfWidth + halfWidth / 2, getY() + halfHeight / 2, arrowWidth, arrowHeight, "right");
            drawArrow(gc, getX() + halfWidth + halfWidth / 2, getY() + halfHeight + halfHeight / 2, arrowWidth, arrowHeight, "right");
            drawArrow(gc, getX() + halfWidth / 2, getY() + halfHeight / 2, arrowWidth, arrowHeight, "right");
            drawArrow(gc, getX() + halfWidth / 2, getY() + halfHeight + halfHeight / 2, arrowWidth, arrowHeight, "right");
        }

        // If the object is active, draw circles and a thick black line
        if (isActive()) {
            gc.setFill(Color.BLACK);
            double lineThickness = Math.min(width, height) / 8;

            // Draw the thick black line on the edge the rectangle is facing
            if (property.equals("w") || property.equals("ww")) {
                gc.fillRect(getX(), getY(), width, lineThickness); // Top edge (north)
            } else if (property.equals("a") || property.equals("aa")) {
                gc.fillRect(getX(), getY(), lineThickness, height); // Left edge (west)
            } else if (property.equals("s") || property.equals("ss")) {
                gc.fillRect(getX(), getY() + height - lineThickness, width, lineThickness); // Bottom edge (south)
            } else if (property.equals("d") || property.equals("dd")) {
                gc.fillRect(getX() + width - lineThickness, getY(), lineThickness, height); // Right edge (east)
            }

            // Draw three circles along the opposite edge of the direction
            double circleRadius = width / 3; // Each circle is 1/3 the width of the rectangle
            gc.setFill(Color.RED); // You can adjust the color as needed

            if (property.equals("w") || property.equals("ww")) {
                // Draw circles on the west edge (left)
                for (int i = 0; i < 3; i++) {
                    gc.fillOval(getX(), getY() + i * circleRadius, circleRadius, circleRadius);
                }
            } else if (property.equals("a") || property.equals("aa")) {
                // Draw circles on the south edge (bottom)
                for (int i = 0; i < 3; i++) {
                    gc.fillOval(getX() + i * circleRadius, getY() + height - circleRadius, circleRadius, circleRadius);
                }
            } else if (property.equals("s") || property.equals("ss")) {
                // Draw circles on the west edge (left)
                for (int i = 0; i < 3; i++) {
                    gc.fillOval(getX(), getY() + i * circleRadius, circleRadius, circleRadius);
                }
            } else if (property.equals("d") || property.equals("dd")) {
                // Draw circles on the south edge (bottom)
                for (int i = 0; i < 3; i++) {
                    gc.fillOval(getX() + i * circleRadius, getY() + height - circleRadius, circleRadius, circleRadius);
                }
            }
        }
    }

    private void drawArrow(GraphicsContext gc, double x, double y, double width, double height, String direction) {
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);

        // Draw the arrow in the direction specified
        switch (direction) {
            case "up":
                gc.strokeLine(x, y + height, x + width / 2, y); // Draw the shaft
                gc.strokeLine(x + width, y + height, x + width / 2, y); // Draw the other side of the arrowhead
                gc.strokeLine(x, y + height, x + width, y + height); // Draw the base of the arrowhead
                break;
            case "down":
                gc.strokeLine(x, y, x + width / 2, y + height); // Draw the shaft
                gc.strokeLine(x + width, y, x + width / 2, y + height); // Draw the other side of the arrowhead
                gc.strokeLine(x, y, x + width, y); // Draw the base of the arrowhead
                break;
            case "left":
                gc.strokeLine(x + width, y, x, y + height / 2); // Draw the shaft
                gc.strokeLine(x + width, y + height, x, y + height / 2); // Draw the other side of the arrowhead
                gc.strokeLine(x + width, y, x + width, y + height); // Draw the base of the arrowhead
                break;
            case "right":
                gc.strokeLine(x, y, x + width, y + height / 2); // Draw the shaft
                gc.strokeLine(x, y + height, x + width, y + height / 2); // Draw the other side of the arrowhead
                gc.strokeLine(x, y, x, y + height); // Draw the base of the arrowhead
                break;
        }
    }




}