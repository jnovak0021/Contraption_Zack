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
255 255 255
2 4
0 0 0 0
0 0 0 0

1g02911 1b28031


**********************************************************************************************************************************
GameObject parent class structure
**********************************************************************************************************************************



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

Abyss: 0 
Floor: 1
FloatingTile: 2
Door: 3
    property: The current room that the door is in and which door it is  (A,B,C,D)
    
Water: 4
Spring: 5
    property: the direction to spring zack
        w: up
        s: down
        a: left
        d: right
        ww: up reusable
        ss: down reusable
        aa: left reusable
        dd: right reusable
TimerDoor: 6
TimerButton: 7
Stanchion: 8
    H: Horizontal
    V: Vertical
TeslaCoil: 9
WallSwitch: 10
Button: B
Wall: W
Spike: S
    H: Horizontal
    V: Vertical



Stanchion: 

   property: room linked to




Mechanisms: Spikes Button(sqaure) Spring Trap Button (circle) Broken Pipe Lift Tesla Gate Switch (wall) Screw (wall) Treadmill Pulleys (wall)

Items: Screwdriver Pipe Wrench Spring Wrench Tape Measure Pipe



**********************************************************************************************************************************
LoadLevel API
**********************************************************************************************************************************
LoadLevel reads in the file, stores and creates the objects

//To use in another class use
//this calls the readFile method which stores all the data about the game
LoadLevel ll = new LoadLevel();


//to get the tile objects of a specific level
ll.getRoomTiles(int roomNumber);

//to get the mechanisms of a specific level
//returns ArrayList<Mechanism>
ll.getRoomMechanisms(int roomNumber);

//to get the room number stored by LoadLevel
ll.getCurrentRoomNumber()

//to update the current room number stored by LoadLevel
ll.setCurrentRoomNumber(int currentRoomNumber)

//to get all the mechanisms that are associated with a Mechanism
//use this on mechanism triggers
//returns ArrayList<Mechanism>
ll.getAssociatedMechanisms(int i)	