package Model;
//The TileMap class
//This class controls the board and helps move around/perform actions for the entities within
//Author: Omar Azam
public class TileMap {
	//The actual board
	private Entity[][] board;
	//the board size
	private int Columns, Rows;
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
		for (int i = 0; i < Rows; i++)
		{
			for (int j = 0; j < Columns; j++)
			{
				if (board[i][j].getType() > 100)
				{
					retVal = false;
				}
			}
		}
		return retVal;
	}
	//Clones the board for use in saving state
	public Entity[][] cloneBoard()
	{
		Entity[][] retVal = new Entity[Rows][Columns];
		for (int i = 0; i < Rows; i++)
		{
			for (int j = 0; j < Columns; j++)
			{
				retVal[i][j] = board[i][j].clone();
			}
		}
		return retVal;
	}
	public void setBoard(Entity[][] boardIn)
	{
		board = boardIn;
	}
	//this method remakes the board and populates it with empty space Entities
	public void ResetBoard()
	{
		board = new Entity[Rows][Columns];
		for (int i = 0; i < Rows; i++)
		{
			for (int j = 0; j < Columns; j++)
			{
				addEntity(100,i,j);
			}
		}
	}
	//Ensure no errors get raised.
	//If there is an entity in the spot they're trying to add
	// an entity too, retVal = false
	//Otherwise add the entity and retVal = true;
	public boolean addEntity(int type, int row, int column)
	{
		boolean retVal = false; //The returned boolean indicating success or failure
		
		if (board[row][column] == null || (board[row][column].getType() == 100 && row <= Rows && column <= Columns))
		{
			retVal = true;
			switch (type)
			{
			case 1:
				board[row][column] = new PeaShooter();
				break;
			case 2:
				board[row][column] = new SunFlower();
				break;
			case 3:
				board[row][column] = new CherryBomb();
				break;
			case 4:
				board[row][column] = new WallNut();
				break;
			case 5:
				board[row][column] = new Chomper();
				break;
			case 100:
				board[row][column] = new Entity();
				break;
			case 101:
				board[row][column] = new Zombie();
				break;
			case 102:
				board[row][column] = new FastZombie();
				break;
			case 103:
				board[row][column] = new PoleZombie();
				break;
			case 104:
				board[row][column] = new GiantZombie();
				break;
			}
		}
		board[row][column].setRow(row);
		board[row][column].setColumn(column);
		
		return retVal;
	}
	//This method is mostly going to be called by the game when a Zombie kills a plant or vice versa
	//ignore isUser for this milestone, but we will later add functionality so the user can salvage 
	//their plants
	public int removeEntity(int row, int column, boolean isUser)
	{
		int retVal = 0;
		
		if (getEntity(row,column).getType() > 100)
		{
			
			retVal = getEntity(row,column).getValue();
		}	
		else
		{
			retVal = (int)(getEntity(row,column).getValue() * 0.15);
		}
		if (board[row][column].getType() == 5 && ((Chomper)board[row][column]).eating)
		{
			board[row][column] = ((Chomper)board[row][column]).targetEaten;
		}
		else
		{
			board[row][column] = new Entity();
		}
		
		return retVal;
	}
	public void moveLeft(int x, int y, int distance)
	{
		 board[x][y-distance] = board[x][y];
		 board[x][y] = new Entity();
		 board[x][y-distance].setColumn(y-distance);
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
	//Scans the range of the plant checking for zombies, returns the first one found.
	public Entity getTarget(int X, int Y) {
		Entity retVal = null;
		for (int i = Y; i <= Y + board[X][Y].getRangeX();i++)
		{
			for (int j = X-board[X][Y].getRangeY(); j <= X + board[X][Y].getRangeY(); j++)
			{
				if (i < Columns && j < Rows && i >= 0 && j >= 0)
				{
					if (board[j][i].getType() > 100)
					{
						retVal = board[j][i];
						//Escape the loop
						i += Rows;
						j += Columns;
					}
				}
			}
			
		}
		return retVal;
	}

	
}
