import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import javafx.scene.paint.Color;
/*
 * Class RoomObject -- stores the content read in by each individual file
 * Member Variables:
 *    contains a 2d array list of the game tiles
 *    contains an arraylist of GameObjects that represnt things such as buttons...
 * methods:
 *    accessor mutators for the 2d array and arraylist of GameObjects
 */
public class RoomObject
{
    //2d array list of the game tiles
    private Tile [][] gameBoard2d;
    //arraylist of GameObjects that represnt things such as buttons...
    private ArrayList<Mechanism> mechanismArray;



    //accessors and mutator for gameBoard2dArray variables
    public Tile[][] getGameBoard2d()
    {
        return gameBoard2d;
    }
    public void setGameBoard2d(Tile[][] gameBoard2d)
    {
        this.gameBoard2d = gameBoard2d;
    }

    //Mechanism accesor, mutator
    public ArrayList<Mechanism> getRoomMechanismArray()
    {
        return mechanismArray;
    }

    //mutator
    public void setRoomMechanismArray(ArrayList<Mechanism> mechanismArray)
    {
        this.mechanismArray = mechanismArray;
    }

    //to string
    public String toString()
    {
        String temp = "Room:\n";
        temp += "tile\n";
        for ( int i = 0; i < gameBoard2d.length; i++)
        {
            for (int j = 0; j < gameBoard2d[i].length; j++)
            {
                temp += gameBoard2d[i][j] + " ";
            }
            temp += "\n";
        }
        temp += "\n\n";

        //print mechanism array
        for ( int i = 0; i < mechanismArray.size(); i++ )
        {
            temp += mechanismArray.get(i).toString() + " ";
        }
        temp += "\n";
        return temp;
    }
}