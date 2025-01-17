import java.util.ArrayList;

/*
 * Class RoomObject -- stores the content read in by each individual file
 * Member Variables:
 *    contains a 2d array list of the game tiles
 *    contains an arraylist of GameObjects that represnt things such as buttons...
 * methods:
 *    accessor mutators for the 2d array and arraylist of GameObjects
 */

public class RoomObject {
    // 2d array list of the game tiles
    private Tile[][] gameBoard2d;
    // arraylist of GameObjects that represent things such as buttons...
    private ArrayList<Mechanism> mechanismArray;
    private ArrayList<Item> itemArray;

    // Accessors and mutators for gameBoard2d variables
    public Tile[][] getGameBoard2d() {
        return gameBoard2d;
    }

    public void setGameBoard2d(Tile[][] gameBoard2d) {
        this.gameBoard2d = gameBoard2d;
    }

    // Mechanism accessor, mutator
    public ArrayList<Mechanism> getRoomMechanismArray() {
        return mechanismArray;
    }
    
    public ArrayList<Item> getRoomItemArray()
    {
        return itemArray;
    }

    // Mutator
    public void setRoomMechanismArray(ArrayList<Mechanism> mechanismArray) {
        this.mechanismArray = mechanismArray;
    }
    
    public void setRoomItemArray(ArrayList<Item> itemArray)
    {
        this.itemArray = itemArray;
    }
    
    

    // Clone the mechanismArray
    public ArrayList<Mechanism> cloneMechanismArray() {
        ArrayList<Mechanism> clonedList = new ArrayList<>();
        for (Mechanism mechanism : mechanismArray) {
            clonedList.add(mechanism.clone()); // Assuming Mechanism has a clone method
        }
        return clonedList;
    }

    // toString
    public String toString() {
        StringBuilder temp = new StringBuilder(gameBoard2d.length +" " +  gameBoard2d[0].length + "\n");

        for (int i = 0; i < gameBoard2d.length; i++) {
            for (int j = 0; j < gameBoard2d[i].length; j++) {
                temp.append(gameBoard2d[i][j]).append(" ");
            }
            temp.append("\n");
        }
        temp.append("\n\n");

        // Print mechanism array
        for (Mechanism mechanism : mechanismArray) {
            temp.append(mechanism.toString()).append("\n");
        }
        temp.append("\n");

        for (Item item : itemArray) {
            temp.append(item.toString()).append("\n");
        }
        return temp.toString();
    }
}
