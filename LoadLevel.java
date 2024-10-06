import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
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
   private int savedRoom = 0;
   //associative array for the mechanisms -- 2d arraylist of Mechanisms where each index is a different associative number
   private ArrayList<Mechanism> [] associatedMechanisms;
   //store all mechanisms that have a time component in this array for easier handling
   private ArrayList<Mechanism> timedMechanisms;

   //list of RoomObject that is read in using privateReadFile
   private ArrayList<RoomObject> rooms;

   //store color of tiles in level
   Color primaryColor, secondaryColor;

   //constructur that calls readFile to load in the game objects
   public LoadLevel()
   {
      associatedMechanisms = new ArrayList[100];  //may need to up intial capacity
      timedMechanisms = new ArrayList<Mechanism>();
      for(int i = 0; i < associatedMechanisms.length; i++)
      {
         associatedMechanisms[i] = new ArrayList<Mechanism>();
      }
   
      System.out.println("size: " +associatedMechanisms.length);
   
      rooms = new ArrayList<RoomObject>();
      //call readFile method to load the data into the level
      readFile();
   }
   public ArrayList<RoomObject> cloneRooms() {
      ArrayList<RoomObject> clonedRooms = new ArrayList<>();
      for (RoomObject room : rooms) {
         RoomObject clonedRoom = new RoomObject();
         clonedRoom.setGameBoard2d(room.getGameBoard2d()); // Shallow copy of the tile array (if you need deep copy, clone the Tile objects)
         clonedRoom.setRoomMechanismArray(room.cloneMechanismArray()); // Clone the mechanism array
         clonedRooms.add(clonedRoom);
      }
      return clonedRooms;
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
                     temp2d[i][j] = new Floor(j*80, i*80, primaryColor, secondaryColor);
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
            System.out.println("\n\n");
            //split up string read in by colons
            String[] parts = mechanismStr.split(":");
            Mechanism temp = null;
         
         
            //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
         
            System.out.println("part 0 \"" + parts[0] + "\"");
         
            //if statement to determine which mechanism
            //Door
            if(parts[0].equals("3")){
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               temp = new Door(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this);
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               tempMechanismArray.add(temp);
            }
            
            //juke box
            else if(parts[0].toUpperCase().equals("J")){
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               temp = new Jukebox(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this);
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               tempMechanismArray.add(temp);
            }
            
            //Wall
            else if(parts[0].toUpperCase().equals("W")){
            
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               temp = new Wall(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this);
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               tempMechanismArray.add(temp);
            }
            
            //floatingTile
            else if(parts[0].toUpperCase().equals("F")){
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               temp = new FloatingTile(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this);
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               tempMechanismArray.add(temp);
            }
            
            //spike
            else if(parts[0].toUpperCase().equals("S")){
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               temp = new Spike(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this);
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               tempMechanismArray.add(temp);
            }
            
            //button
            else if(parts[0].toUpperCase().equals("B")){
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               temp = new Button(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this);
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               tempMechanismArray.add(temp);
            }
            //floating tile
            else if(parts[0].equals("2"))
            {
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               temp = new FloatingTile(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this);
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               tempMechanismArray.add(temp);
            }
            //srping
            else if(parts[0].equals("5"))
            {
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               temp = new Spring(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this);
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               tempMechanismArray.add(temp);
            }
            //timerdoor
            else if(parts[0].equals("6"))
            {
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               temp = new TimerDoor(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this);
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               tempMechanismArray.add(temp);
               timedMechanisms.add(temp);
            }
            //TimerButton
            else if(parts[0].equals("7"))
            {
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               temp = new TimerButton(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this);
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               tempMechanismArray.add(temp);
               timedMechanisms.add(temp);
            }
            //stanchion
            else if(parts[0].equals("8"))
            {
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               temp = new Stanchion(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this);
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               tempMechanismArray.add(temp);
            }
            //tesla coil
            else if(parts[0].equals("9"))
            {
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               temp = new TeslaCoil(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this);
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               tempMechanismArray.add(temp);
            }
            //wall switch
            else if(parts[0].equals("10"))
            {
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               temp = new WallSwitch(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this);
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               tempMechanismArray.add(temp);
            }
            //pulley
            else if(parts[0].equals("P"))
            {
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               temp = new Pulley(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this);
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               tempMechanismArray.add(temp);
            }
            else if(parts[0].equals("T"))
            {
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               temp = new Treadmill(parts[1], Boolean.parseBoolean(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),Integer.parseInt(parts[6]), Color.web(parts[7]),Integer.parseInt(parts[8]), this);
               //<object>:<property>:<activated>:<startx>:<starty>:<endx>:<endy>:<color>:<associativeNumber>
               tempMechanismArray.add(temp);
            }
         
            System.out.println(tempMechanismArray.get(tempMechanismArray.size()-1));
            System.out.println("adding mechanism to \t" + Integer.parseInt(parts[8]));
         
            //add mechanism to its correct index in associatedMechanisms
            associatedMechanisms[Integer.parseInt(parts[8])].add(temp);
         
         }
         System.out.println(tempMechanismArray.get(0).toString());
      
         //set member variables of Roomobject
         tempRoomObject.setGameBoard2d(temp2d);
         tempRoomObject.setRoomMechanismArray(tempMechanismArray);
      
         //close scanner
         scanner.close();
      
         /*
         System.out.println("\n\nMechs 1 \n\n\n");
      
         for(int i=0; i < associatedMechanisms[1].size(); i++){
            System.out.println(associatedMechanisms[1].get(i));
         }
         System.out.println("\n\nMechs 1 end \n\n\n");
          */
      
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

   //returns all mechanisms that have a time component
   


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
      else if(currentRoomNumber == 1)
      {
         primaryColor = Color.rgb(232,231,231,1);
         secondaryColor = Color.rgb(0,0,0,1);
      }
      else if(currentRoomNumber == 2)
      {
         primaryColor = Color.rgb(110,110,110,1);
         secondaryColor = primaryColor;
      }
      else if(currentRoomNumber == 3 || currentRoomNumber == 4)
      {
         primaryColor = Color.rgb(240,0,0, 1);
         secondaryColor = primaryColor;
      }
      else if(currentRoomNumber == 5)
      {
         primaryColor = Color.rgb(60,60,60,1);
         secondaryColor = primaryColor;
      }
      else if(currentRoomNumber == 6)
      {
         primaryColor = Color.rgb(90,90,90,1);
         secondaryColor = primaryColor;
      }
      else if(currentRoomNumber == 7)
      {
         primaryColor = Color.rgb(100,100,100,1);
         secondaryColor = primaryColor;
      }
      else if(currentRoomNumber == 8)
      {
         primaryColor = Color.rgb(100,100,100,1);
         secondaryColor = primaryColor;
      }
      else if(currentRoomNumber == 9)
      {
         primaryColor = Color.rgb(50,50,50,1);
         secondaryColor = primaryColor;
      }
   }


   //method to reset all game data -- i.e reread it in
   public void resetLevel()
   {
      //call readfile again
      readFile();
   }


   public int getSavedRoom() {
      return savedRoom;
   }

    // Setter for savedRoom
   public void setSavedRoom(int savedRoom) {
      this.savedRoom = savedRoom;
   }


   //method to save all of the rooms in the game to memory
   public ArrayList<RoomObject> saveAllRoomsState() {
      System.out.println("inside.");
   
      return rooms;
   }

   //method to go through and reload all of the rooms in the game back from memory
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
   
   public void resetBoard() {
    // Clear the existing rooms and mechanisms
      rooms.clear();
      timedMechanisms.clear();
      for (ArrayList<Mechanism> mechanisms : associatedMechanisms) {
         if (mechanisms != null) {
            mechanisms.clear();
         }
      }
   
    // Reinitialize the room data
      readFile();
   
    // Reset the current room number to the starting point
   
      currentRoomNumber = 0;
      setRoomColor();
   
      System.out.println("Game board has been reset.");
   }
   
   public void resetCurrentRoom() {
    // Get the current room object
      RoomObject currentRoom = rooms.get(currentRoomNumber);
    
    // Clear the current room's mechanisms
      currentRoom.getRoomMechanismArray().clear();
    
    // Optionally, reset any other specific states related to the current room here
    // For example, reset any specific game objects or state variables associated with this room
    
    // Re-read the current room data
      String roomFileName = "room" + currentRoomNumber + ".txt";
      RoomObject newRoom = privateReadFile(roomFileName);
    
    // Replace the current room with the new one
      rooms.set(currentRoomNumber, newRoom);
   
    // Optionally, reset the current room number if needed (not generally necessary)
    // currentRoomNumber = currentRoomNumber; // This line is just for clarity, not needed
   
      System.out.println("Current room " + currentRoomNumber + " has been reset.");
   }
// Assuming this code is in the class where these variables are declared

// Getter for associatedMechanisms
   public ArrayList<Mechanism>[] getAssociatedMechanisms() {
      return associatedMechanisms;
   }

// Setter for associatedMechanisms
   public void setAssociatedMechanisms(ArrayList<Mechanism>[] associatedMechanisms) {
      this.associatedMechanisms = associatedMechanisms;
   }

// Getter for timedMechanisms
   public ArrayList<Mechanism> getTimedMechanisms() {
      return timedMechanisms;
   }

// Setter for timedMechanisms
   public void setTimedMechanisms(ArrayList<Mechanism> timedMechanisms) {
      this.timedMechanisms = timedMechanisms;
   }

// Getter for rooms
   public ArrayList<RoomObject> getRooms() {
      return rooms;
   }

// Setter for rooms
   public void setRooms(ArrayList<RoomObject> rooms) {
      this.rooms = rooms;
   }



}


