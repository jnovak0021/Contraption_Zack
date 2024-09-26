import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import javafx.scene.paint.Color;

import javax.imageio.stream.MemoryCacheImageInputStream;


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

   //associative array for the mechanisms -- 2d arraylist of Mechanisms where each index is a different associative number
   private ArrayList<Mechanism> [] associatedMechanisms;

   //list of RoomObject that is read in using privateReadFile
   private ArrayList<RoomObject> rooms;
   
   //store color of tiles in level
   Color primaryColor, secondaryColor;

   //constructur that calls readFile to load in the game objects
   public LoadLevel()
   {
      associatedMechanisms = new ArrayList[100];  //may need to up intial capacity
      for(int i = 0; i < associatedMechanisms.length; i++)
      {
         associatedMechanisms[i] = new ArrayList<Mechanism>();
      }
   
      System.out.println("size: " +associatedMechanisms.length);
   
      rooms = new ArrayList<RoomObject>();
      //call readFile method to load the data into the level
      readFile();
   }

   public ArrayList<RoomObject> saveAllRoomsState() {
      System.out.println("inside.");
   
      return rooms;
   }
   
   public void loadState(ArrayList<RoomObject> savedRooms) {
    // Clear the current rooms
      rooms.clear();
   
    // Load the saved rooms
      for (RoomObject room : savedRooms) {
         rooms.add(room);
      }
   
    // Reprint the rooms to show the loaded state
      printGame();
   }

   //method to read in each room level
   //for loop that calls privatereadFileMethod for each room in level 1

   public void readFile()
   {
   
      //note -- this needs to be changed later when all levles exist
      //loop through each of the 10 rooms
      for( int i = 0; i < 10; i++ )
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
      ArrayList <Mechanism> tempMechanismArray = new ArrayList<Mechanism>();
   
   
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
                        temp2d[i][j] = new Floor(j * 80, i * 80, primaryColor);
                     }
                     else
                     {
                        temp2d[i][j] = new Floor(j * 80, i * 80, secondaryColor);
                     }
                  }
                    // Edge case: if no matching values, make it abyss
                  else
                  {
                     temp2d[i][j] = new Abyss(j * 80, i * 80, Color.BLACK);
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
                     temp2d[i][j] = new Floor(j*80, i*80, c);
                  }
                  //draw water
                  else if( currentIndex == 4)
                  {
                     temp2d[i][j] = new Water(j*80, i*80, Color.BLUE);

                  }

                  //edge case: if no matching values, make it abyss
                  else
                  {
                     //temp2d[i][j] = (new Abyss(i,j,Color.BLACK, false));
                     temp2d[i][j] = new Abyss(j*80, i*80, Color.BLACK);
                  }
               }
            }
         }
      
         //read in the GameObject array at the end
         while(scanner.hasNext())
         {
            String mechanismStr = scanner.next();
            //temp variables
            char property;
            int x, y, endX, endY;
            System.out.println("ADFLUHAD");
            //split up string read in by colons
            String[] parts = mechanismStr.split(":");
            Mechanism temp = null;
         
         
         
         
            //if statement to determine which mechanism
            //Door
            if(parts[0].equals("3"))
            {
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               temp = new Door(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this);
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               tempMechanismArray.add(temp);
            }
            
            //juke box
            else if(parts[0].equals("J"))
            {
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               temp = new Jukebox(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this);
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               tempMechanismArray.add(temp);
            }
            
            //Wall
            else if(parts[0].equals("W"))
            {
                tempMechanismArray.add(new Wall(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this));
            }
         
         
            System.out.println("adding mechanism to \t" + Integer.parseInt(parts[8]));
            System.out.println();
            //add mechanism to its correct index in associatedMechanisms
            associatedMechanisms[Integer.parseInt(parts[8])].add(temp);
         
         }
         System.out.println(tempMechanismArray.get(0).toString());
      
         //set member variables of Roomobject
         tempRoomObject.setGameBoard2d(temp2d);
         tempRoomObject.setRoomMechanismArray(tempMechanismArray);
      
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

   //method to return all of the mechanisms that are connected across the entire game
   public ArrayList<Mechanism> getRoomMechanisms(int roomNumber)
   {
      return rooms.get(roomNumber).getRoomMechanismArray();
   }

   //to get all the mechanisms that are associated with a Mechanism
   //returns ArrayList<Mechanism>
   public ArrayList<Mechanism> getAssociatedMechanisms(int i)
   {
      return associatedMechanisms[i];
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


   //method to reset all game data -- i.e reread it in
   public void resetLevel()
   {
      //call readfile again
      readFile();
   }

}


