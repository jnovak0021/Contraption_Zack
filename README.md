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

2 4
0 0 0 0
0 0 0 0

1g02911 1b28031


**********************************************************************************************************************************
GameObject parent class structure
**********************************************************************************************************************************
Public abstract class GameObject
{
    private ArrayList<GameObject> linkedObjectsList;
    private boolean isActive;
    private char property;
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    

    Public abstract void doMe();
    {

    }

    Public void doThing()
    {
	    doMe();
	    for(int=0; i < linkedObjectsList.size(); i++)
`	    {
			linkedObjectsList.get(i).doThing();
		}
	}

	Public void drawMe(GraphicsContext2D gc)
	{

	}
}



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
Game Object Legend
**********************************************************************************************************************************




**********************************************************************************************************************************
LoadLevel API
**********************************************************************************************************************************
//To use in another class use
LoadLevel ll = new LoadLevel();

//to store all data for level 1 of contraption zack
ll.readFile();

//to get the tile objects of a specific level
ll.getRoomTiles(int roomNumber);

//to get the mechanisms of a specific level
ll.getRoomMechanisms(int roomNumber);
