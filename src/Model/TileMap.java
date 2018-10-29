package Model;
//The TileMap class
//This class controls the board and helps move around/perform actions for the entities within
//Outline Author: Kyle Smith
public class TileMap {
	//The actual board
	private Entity[][] board;
	//the board size
	private int Columns, Rows;
	//a boolean the game class can check to see if the user should die
	boolean zombieWon;
	//constructor
	public TileMap(int rows, int columns)
	{
		Columns = columns;
		Rows = rows;
		ResetBoard();
	}
	//This method checks if the user has killed all the zombies
	//Only call this method after the last zombie has been spawned
	public boolean didWin()
	{
		boolean retVal = true;
		return retVal;
	}
	//this method remakes the board and populates it with empty space Entities
	public void ResetBoard()
	{
		zombieWon = false;
	}
	//The getter for the boolean that determines if the user died
	public boolean isZombieWon() {
		return zombieWon;
	}
	//Ensure no errors get raised.
	//If there is an entity in the spot they're trying to add
	// an entity too, retVal = false
	//Otherwise add the entity and retVal = true;
	public boolean addEntity(int type, int row, int column)
	{
		boolean retVal = false; //The returned boolean indicating success or failure
		return retVal;
	}
	//This method is mostly going to be called by the game when a Zombie kills a plant or vice versa
	//ignore isUser for this milestone, but we will later add functionality so the user can salvage 
	//their plants
	public int removeEntity(int row, int column, boolean isUser)
	{
		int retVal = 0;	
		return retVal;
	}
	//This method performs all the end turn methods on each entity
	//this class also handles the logic for what the zombies will do
	public int endTurn()
	{
		int retVal = 0;
		return retVal;
	}
	//Getter for an entity on the board
	public Entity getEntity(int row, int column)
	{
		return board[row][column];
	}
	//gets board width
	public int getColumns() {
		return Columns;
	}
	//gets board height
	public int getRows() {
		return Rows;
	}

	
}
