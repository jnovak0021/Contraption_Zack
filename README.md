**********************************************************************************************************************************
Contraption Zack 
By: Dorian Quimby, Jacob Novak, Ethan Milner
**********************************************************************************************************************************



**********************************************************************************************************************************
About Project
**********************************************************************************************************************************



**********************************************************************************************************************************
File Structure
**********************************************************************************************************************************
filename: room<room_number>.txt

rgbRed rbgGreen grbBlue
<x size of 2d array> <y size of 2d array>

<2d array containing base platform structure>

//loop over code below twice
//first iteration, create game objects and set member variables of object, populate linked object ArrayLists
//second iteration, use setLinkedObjectList(ArrayList linkedObjectIn) to populate the linked object list for each object

/
<array list of game objects>
//string format of objects: <object><property><activated>:<startx>:<starty>:<endx>:<endy>;<linkedObjectListCode>
ex: by0:0:0:3:3;4 sy1:12:0:15:0;4



Example file format:
255 0 0 
2 4
0 0 0 0
0 0 0 0

1g02911 1b28031


**********************************************************************************************************************************
GameObject parent class structure
**********************************************************************************************************************************

Inheritence structure:
                                GameObject
            Tile                Mechanism               Players
    Floor Abyss....          Spike   trapdoor....

Drawing Tiles vs Mechanisms vs Players
    Tiles are drawn based off of a grid layout that is 1 grid per game tile
    Players and mechanisms are drawn based off of pixel counts

//grand parents class -- contains common basic functionality
Public abstract class GameObject
{
    private ArrayList<GameObject> linkedObjectsList;
    private boolean isActive;
    private char property;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
Methods
    draw()
    activate()	//set value of isActive
    setIsActive()
    getIsActive()
    getProperty()
    getStartX()
    getStartY()
    getEndX()
    getEndY()
    getLinkedObjectsList()	//returns linkedObjects Arraylist
    setLinkedObjectList(ArrayList linkedObjectIn	)	//on second loop, 

GameObject
    abstract draw()

Tile
    variables: x, y, color, isTraversable
    abstract methods:
        get/setX
        get/setY
        get/setColor
        isTravesable()

Mechanism
    variables:
        (bool) isActive
        int startX
        int startY
        int endX
        int endY
    Methods:
        get/SetIsActive()
        get/setStartX()
        get/setStartY()
        get/setEndX()
        get/setEndY()
	
Item
    variables:
        (bool) onGround
    Methods:
        get/setOnGround





**********************************************************************************************************************************
GameObject Legend
**********************************************************************************************************************************
Tiles:
0 Void
1 Floor
2 Wall
3 Door(arrow)
4 Stanchion
5 River

Mechanisms:
Spikes
Button(sqaure)
Spring Trap
Button (circle)
Broken Pipe
Lift
Tesla Gate
Switch (wall)
Screw (wall)
Treadmill
Pulleys (wall)

Items:
Screwdriver
Pipe Wrench
Spring
Wrench
Tape Measure
Pipe

**********************************************************************************************************************************
LoadLevel API
**********************************************************************************************************************************
//To use in another class use
//this constructor loads the data into the game
LoadLevel ll = new LoadLevel();

//to get the tile objects of a specific level
//returns 2d array of Tiles
ll.getRoomTiles(int roomNumber);

//to get the mechanisms of a specific level
ll.getRoomMechanisms(int roomNumber);
