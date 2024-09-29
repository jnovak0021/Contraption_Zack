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

public class Abyss extends Tile
{

   public Abyss(int x, int y, Color myColor)
   {
      super(x, y, x + length, y + width, Color.BLACK, false);
   }
   
   public String toString()
   {
      return "Abyss:" + getX() + ":" + getY() + "\t" ;
   }


   
   //draw me method that takes in gc and draws black box
   public void drawMe(GraphicsContext gc)
   {
      //draw a black rectangle
      gc.setFill(getMyColor());
      gc.fillRect(getX(),getY(), getWidth(), getLength());

   }

   
}