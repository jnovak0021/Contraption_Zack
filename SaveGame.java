import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;
import javafx.scene.paint.Color;

//purpose of this class is to create a pass by value version of the game
public class SaveGame
{

    private int currentRoomNumber = 0;

    //associative array for the mechanisms -- 2d arraylist of Mechanisms where each index is a different associative number
    private ArrayList<Mechanism> [] associatedMechanisms;

    private ArrayList<Item> gameItems;
    //store all mechanisms that have a time component in this array for easier handling
    private ArrayList<Mechanism> timedMechanisms;

    //list of RoomObject that is read in using privateReadFile
    private ArrayList<RoomObject> rooms;

    //

}