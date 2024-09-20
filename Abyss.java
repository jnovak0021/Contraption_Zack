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
import javafx.scene.paint.Color;

public class Abyss extends Tile
{
   public Abyss(int x, int y, int endX, int endY, Color myColor, boolean traverseable)
   {
      super(x,y, endX, endY, myColor, traverseable);
   }
   
   public String toString()
   {
      return "Abyss";
   }
   
   public void drawMe(Pane pane)
   {
   }
}