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


<array list of game objects>
//string format of objects: <object><property><activated>:<startx>:<starty>:<endx>:<endy>;<linkedObjectListCode>
ex: by0:0:0:3:3;4 sy1:12:0:15:0;4



Example file format:



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
	



**********************************************************************************************************************************
Game Object Legend
**********************************************************************************************************************************




**********************************************************************************************************************************

**********************************************************************************************************************************

