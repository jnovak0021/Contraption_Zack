import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import javafx.scene.paint.Color;


/*
 * Files are read in using the LoadLevel class
 *    this LoadLevelClass is the main class and holds an array of GameRoom Objects
 * each file (room) is stored in a GameRoom object
 *    each Game room object contains:
 *    a 2d arraylist that contains the tiles that are included in each level (i.e tile, wall, rope, or void)
 *    an arraylist that contains the game Objects that are placed on the tile
 */





/*
class to read in the contents of the 10 room levels stored in text files.
The class stores each level in a gameRoom object which contains a 2d array with the tile locations 
as well as an arraylist that contains the GameBbjects that are stored in each level
*/
public class LoadLevel
{
   //store the current room
   private int currentRoomNumber = 0;
   
   //store color of tiles in level
   Color primaryColor, secondaryColor;
   
   //constructur that calls readFile to load in the game objects
   public LoadLevel()
   {
      //call readFile method to load the data into the level
      readFile();
   }
   //list of RoomObject that is read in using privateReadFile
   ArrayList<RoomObject> rooms = new ArrayList<RoomObject>();
   
   //method to read in each room level
   //for loop that calls privatereadFileMethod for each room in level 1
   //returns a 3d arraylist of arraylist of gameObjects
   public void readFile()
   {
     
      //note -- this needs to be changed later when all levles exist
      //loop through each of the 10 rooms 
      for( int i = 0; i < 1; i++ )
      {
         //set index of arrayIn to return value of privateReadFile
         System.out.println("reading in file " + i);
         
         //get room color
         setRoomColor();
         rooms.add(privateReadFile("room" + i + ".txt"));
       
         currentRoomNumber++; //increment currentRoomNUmber for specifics on color
      } 
      //reset currentRoomNumber
      currentRoomNumber = 0;

   }
   
   //private readFile method to load each room  
   //returns a RoomObject that contains the 2darray and the tiles 
   private RoomObject privateReadFile(String roomFileName)
   {
      //RoomObject to return to list
      RoomObject tempRoomObject = new RoomObject();
      
      //color object to store the color of the tiles in the level
      Color c;

      //num rows and columns in the tile array
      int rows, columns;
      
      //temp 2d array of ints to hold values
      Tile [][] temp2d;
      
      //temp ArrayList of GameObjects
      ArrayList <GameObject> tempGameObjectArray = new ArrayList<GameObject>();
         
      
      try   //FileNotFoundException
      {
         //read in file
         Scanner scanner = new Scanner(new File(roomFileName));
         
         c = Color.GREEN;
         
         //System.out.println("color: " + c);   //for checking color read in
         rows = scanner.nextInt();
      
         columns = scanner.nextInt();

         temp2d = new Tile[rows][columns];
         
         //if the current room number is 0, alternate tile colors
         if(currentRoomNumber == 0)
         {
            // System.out.println(rows + " " + columns); 
            for (int i = 0; i < rows; i++) 
            {
                for (int j = 0; j < columns; j++) 
                {
                    int currentIndex = scanner.nextInt();
            
                    // ADD CASES HERE FOR CREATING TILE OBJECTS
                    // decision tree to determine which type of object to create based off int value read in.
                    if (currentIndex == 1) 
                    {
                        // Alternate colors
                        if ((i + j) % 2 == 0) // Corrected: Parentheses around (i + j)
                        {
                            temp2d[i][j] = new Floor(i * 80, j * 80, i * 80 + 80, j * 80 + 80, primaryColor, true);
                        } 
                        else 
                        {
                            temp2d[i][j] = new Floor(i * 80, j * 80, i * 80 + 80, j * 80 + 80, secondaryColor, true);
                        }
                    } 
                    // Edge case: if no matching values, make it abyss
                    else 
                    {
                        temp2d[i][j] = new Abyss(i * 80, j * 80, i * 80 + 80, j * 80 + 80, Color.BLACK, true);
                    }
                }
            }
         }
         else
         {
            //System.out.println(rows + " " + columns); 
            for(int i = 0; i < rows; i++)
            {
               for(int j = 0; j < columns; j++)
               {
                  int currentIndex = scanner.nextInt();
   
                  //ADD CASES HERE FOR CREATING TILE OBJECTS
                  //decision tree to determine which type of object to create based off int value read in.
                  if( currentIndex == 1)
                  {
                     //temp2d[i][j] = new Floor(i,j,c, true);
                     temp2d[i][j] = new Floor(i*50, j*50, i*50+50, j*50+50, c, true); 
                  }
                  //edge case: if no matching values, make it abyss
                  else 
                  {
                     //temp2d[i][j] = (new Abyss(i,j,Color.BLACK, false));
                     temp2d[i][j] = new Abyss(i*50, j*50, i*50+50, j*50+50, Color.BLACK, false); 
                  }
               }
            }
         }
         
         //CODE FOR GETTING THE PRINT FORMAT OF EACH FILE READ IN
         System.out.println("X: " + temp2d.length);
         System.out.println("Y: " + temp2d[1].length);
         for(int i = 0; i < temp2d.length; i++)
         {
            for(int j = 0; j < temp2d[i].length; j++)
            {
               System.out.print("[" + i + "][" + j + "]" + temp2d[i][j].getColor() + " ");
            }
            System.out.println();
            //string to store the line of the file
         }
         
   
         //read in the GameObject array at the end
         while(scanner.hasNext())
         {
            //store the gameString
            String gameStr = scanner.next();
            
            //loop over the gameString and call the constructor reading in each 
            //<object><property><activated>:<startx>:<starty>:<endx>:<endy>
            //GameObject g = new GameObject(gameStr.charAt(0), gameStr.charAt(1), gameStr.charAt(2), gameStr.charAt(3), gameStr.charAt(4), gameStr.charAt(5), gameStr.charAt(6));
            //tempGameObjectArray.append(g);
            tempGameObjectArray.add(new Spike(0,0,0,0,Color.GREEN));
            //System.out.println(gameStr.charAt(0) + " " + gameStr.charAt(1) + " " + gameStr.charAt(2) + " " +  gameStr.charAt(3) + " " + gameStr.charAt(4) + " " + gameStr.charAt(5) + " " + gameStr.charAt(6));
         }
         
         //set member variables of Roomobject
         tempRoomObject.setGameBoard2d(temp2d);
         tempRoomObject.setGameObjectArray(tempGameObjectArray);
         
         //close scanner
         scanner.close();
         
         
      
      }
      catch( FileNotFoundException e)
      {
         System.out.println("File Not Found");
      }
      return tempRoomObject;
      
      
   }
   
   //get current room number
   public int getCurrentRoomNumber()
   {
      return currentRoomNumber;
   }

   //set current room number
   public void setCurrentRoomNumber(int currentRoomNumber)
   {
      this.currentRoomNumber = currentRoomNumber;
   }
   //method to take in a level at index i and to 
   public Tile[][] getRoomTiles(int i)
   {
      return rooms.get(i).getGameBoard2d();
   }


   //print out the whole game board
   public void printGame()
   {
      //call all RoomObjects print method
      for(int i = 0; i < rooms.size(); i++)
      {
         System.out.println("\n");
         System.out.println(rooms.get(i).toString());
      }
   }
   
   
   /*
   colors
   room 1
      60 60 60
      227 227 227
   room 2
      231 231 231

   */
   //select the color of the tiles based off roomNumber
   public void setRoomColor()
   {
      if(currentRoomNumber == 0)
      {
         primaryColor = Color.rgb(227,227,227,1);
         secondaryColor = Color.rgb(60,60,60,1);
      }
      if(currentRoomNumber == 1)
      {
         primaryColor = Color.rgb(232,231,231,1);
         secondaryColor = Color.rgb(0,0,0,1);
      }
   }

}


/*
 * Class RoomObject -- stores the content read in by each individual file
 * Member Variables:
 *    contains a 2d array list of the game tiles
 *    contains an arraylist of GameObjects that represnt things such as buttons...
 * methods: 
 *    accessor mutators for the 2d array and arraylist of GameObjects
*/
class RoomObject
{
   //2d array list of the game tiles
   private Tile [][] gameBoard2d;
   //arraylist of GameObjects that represnt things such as buttons...
   private ArrayList<GameObject> gameObjectArray;

//    public RoomObject(int[][] gameBoard2d, ArrayList<GameObject> gameObjectArray)
//    {
//       this.gameBoard2d = gameBoard2d;
//       this.gameObjectArray = gameObjectArray;
//    }

   //accessors and mutator for gameBoard2dArray variables
   public Tile[][] getGameBoard2d()
   {
      return gameBoard2d;
   }
   public void setGameBoard2d(Tile[][] gameBoard2d)
   {
      this.gameBoard2d = gameBoard2d;
   }

   //gameObject accesor, mutator
   public ArrayList<GameObject> getGameObjectArray()
   {
      return gameObjectArray;
   }
   
   //mutator
   public void setGameObjectArray(ArrayList<GameObject> gameObjectArray)
   {
      this.gameObjectArray = gameObjectArray;
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
      
      //print gameObject array
      for ( int i = 0; i < gameObjectArray.size(); i++ )
      {
         temp += gameObjectArray.get(i).toString() + " ";
      }
      temp += "\n";
      return temp;
   }
}