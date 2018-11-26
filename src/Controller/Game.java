package Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import Model.CherryBomb;
import Model.Chomper;
import Model.Entity;
import Model.FastZombie;
import Model.GiantZombie;
import Model.PoleZombie;
import Model.TileMap;
import Model.Zombie;
import View.View;

//This is the game class
//It handles all functions of how the game flows, and handles all commands
//Author: Kyle Smith
public class Game {
	
	//The Model and View
	private TileMap Map;
	private View View;
	//The current level (there is only 1 right now)
	//A boolean to make sure zombies dont get added multiple times in one turn.
	private boolean added = false;
	//The users Sun, current turn, and level
	private int Sun;
	private int Turn;
	private int currentLevel;
	//The cooldown for planting different plants
	private int SunFlowerCD;
	private int PeaShooterCD;
	private int CherryBombCD;
	private int WallNutCD;
	private int ChomperCD;
	//The actionStack for undoing and redoing
	Stack<Object[]> actionStack;
	Stack<Object[]> redoStack;
	
	//Constructor and printout of overview of the game
	public Game(TileMap map, View view)
	{
		//save Model and View
		Map = map;
		View = view;
		//Main Menu of sorts
		view.showMessage("Welcome to Plants vs. Zombies by Group 17Currently the game features 5 types of plants and four types of zombies\nTo plant something, click its icon on the hotbar at the top, then again on the tile you wish to plant it on.\n\nThe SunFlower:\n" + 
				"	HP: 5\n" + 
				"	Defense: 1\n" + 
				"	Attack Damage: 0\n" + 
				"	Attack Range: 0 tiles across, 0 above or below\n" + 
				"	Cost: 75 sun\n" + 
				"	Cooldown to plant: 2 turns\n" + 
				"\nThe PeaShooter:\n" + 
				"	HP: 10\n" + 
				"	Defense: 1\n" + 
				"	Attack Damage: 3.5\n" + 
				"	Attack Range: 9 tiles across, 1 above or below\n" + 
				"	Cost: 125 sun\n" + 
				"	Cooldown to plant: 1 turn\n" + 
				"\nThe Zombie:\n" + 
				"	Default plant stats:\n" + 
				"	HP: 10\n" + 
				"	Defense: 1.2\n" + 
				"	Attack Damage: 2\n" + 
				"	Attack Range: 1 tiles across, 0 above or below\n" + 
				"	Value for killing: 15 sun");
		currentLevel = 1;
		levelLogic();
		for (int i = 0; i < Map.getRows(); i++)
		{
			for (int j = 0; j < Map.getColumns(); j++)
			{
				buttonListener listener = new buttonListener(i,j);
				View.addButtonListener(listener, i, j);
			}
		}
		//add listeners
		view.addEndTurnListener(new endTurnListener());
		view.addRestartListener(new restartListener());
		view.addRedoListener(new redoListener());
		view.addUndoListener(new undoListener());
		//init undo/redo fields and controls
		actionStack = new Stack<Object[]>();
		redoStack = new Stack<Object[]>();
		view.setUndo(false);
		view.setRedo(false);
		saveState();
	}

		//The listener for every button on the board (method included)
	public class buttonListener implements ActionListener
	{
		//position of the button
		int X;
		int Y;
		//Constructor sets position
		public buttonListener(int x, int y)
		{
			X = x;
			Y = y;
		}
		//This executes a switch based on state of the GUI,
		// viewing : Show Entity.toString()
		// plantingS : plant sunflower
		// plantingP : plant peashooter
		// etc...
		//There are no checks for money because the button is disabled if there isnt enough money
		@Override
		public void actionPerformed(ActionEvent e) {
			if (View.getGUIState() == View.state.viewing)
			{
				View.showMessage(Map.getEntity(X, Y).toString());
			}
			else if (View.getGUIState() == View.state.plantingS)
			{
				saveState();
				Map.addEntity(2, X, Y);
				Sun -= Map.getEntity(X,Y).getValue();
				SunFlowerCD=2;
				View.setGUIState(0);
				View.enableAllBoxes();
				updateView();
			}
			else if (View.getGUIState() == View.state.plantingP)
			{
				saveState();
				Map.addEntity(1, X, Y);
				Sun -= Map.getEntity(X,Y).getValue();
				PeaShooterCD=1;
				View.setGUIState(0);
				View.enableAllBoxes();
				updateView();
			}
			else if (View.getGUIState() == View.state.plantingC)
			{
				saveState();
				Map.addEntity(3, X, Y);
				Sun -= Map.getEntity(X,Y).getValue();
				CherryBombCD=4;
				View.setGUIState(0);
				View.enableAllBoxes();
				updateView();
			}
			else if (View.getGUIState() == View.state.plantingW)
			{
				saveState();
				Map.addEntity(4, X, Y);
				Sun -= Map.getEntity(X,Y).getValue();
				WallNutCD=2;
				View.setGUIState(0);
				View.enableAllBoxes();
				updateView();
			}
			else if (View.getGUIState() == View.state.plantingR)
			{
				saveState();
				Map.addEntity(5, X, Y);
				Sun -= Map.getEntity(X,Y).getValue();
				ChomperCD=2;
				View.setGUIState(0);
				View.enableAllBoxes();
				updateView();
			}
			
		}
	}
	//The listener for the undo button. Fairly straightforward
	//1.Push current state to redo
	//2.loadUp last state
	//3.makesure actionstack always has turn 0 start in it
	//4.updateView
	public class undoListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			redoStack.push(getState());
			
			Object[] temp = actionStack.pop();
			Map.setBoard((Entity[][]) temp[0]);
			Sun = (int) temp[1];
			Turn = (int) temp[2];
			currentLevel = (int) temp[3];
			SunFlowerCD = (int) temp[4];
			PeaShooterCD = (int) temp[5];
			CherryBombCD = (int) temp[6];
			WallNutCD = (int) temp[7];
			ChomperCD = (int) temp[8];
			if (actionStack.empty())
			{
				actionStack.push(getState());
			}
			View.setRedo(true);	
			updateView();
		}
		
	}
	//The listener for the redo button. Fairly straightforward
	//1.Push the current state to undo (so you can undo the redo)
	//2.load up the state of the redo stack
	//3.disable redo if there is nothing left
	//4.updateView
	public class redoListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			actionStack.push(getState());
			Object[] temp = redoStack.pop();
			Map.setBoard((Entity[][]) temp[0]);
			Sun = (int) temp[1];
			Turn = (int) temp[2];
			currentLevel = (int) temp[3];
			SunFlowerCD = (int) temp[4];
			PeaShooterCD = (int) temp[5];
			CherryBombCD = (int) temp[6];
			WallNutCD = (int) temp[7];
			ChomperCD = (int) temp[8];
			if (redoStack.empty())
			{
				View.setRedo(false);
			}
			View.setUndo(true);
			updateView();
		}
		
	}
	//The listener for the end Button (method endTurn() called and also saves the state)
	public class endTurnListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//saves state
			saveState();
			endTurn();
		}
	}
	//Instead of typing this out twice I made a method,
	//I broke it into two methods (getState()) because clearing
	//the redo stack isn't always what I want to do when I save the state
	//e.g Undoing a redo etc...
	private void saveState() {
		Object[] temp = getState();
		actionStack.push(temp);
		redoStack.clear();
		View.setRedo(false);
		View.setUndo(true);
	}
	//get state clones all the objects necessary for ensuring the game functions when I load state
	private Object[] getState() {
		Object[] temp = new Object[] {Map.cloneBoard(), new Integer(Sun), new Integer(Turn), new Integer(currentLevel), new Integer(SunFlowerCD), new Integer(PeaShooterCD),new Integer(CherryBombCD),new Integer(WallNutCD),new Integer(ChomperCD)};
		return temp;
	}
	//The listener for the restart button (method included)
	public class restartListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Turn = 0;
			currentLevel = 1;
			actionStack.empty();
			redoStack.empty();
			View.setUndo(false);
			View.setRedo(false);
			levelLogic();
		}
		
	}
	//This is what happens when the End command is called
		//1.Increase Turn
		//2.Decrement cooldowns
		//3.reset added
		//EDITED: the EndTurn method from the TileMap was absorbed into here for doing animations
		//4.passesTurn on each (resets attacked and soaked)
		//5.Executes the special move of each plant(only sunflowers and Chompers have one) and does animation
		//6.looks for a target to attack on each plant, and then does + animation
		//7.makemove on each zombie
		//8.checks to see if the zombies won, if they did reloads the map and tells the user
	private void endTurn() {
		boolean zombieWon = false;
		Turn++;
		decrementCooldowns();
		added = false;

		for (int i = 0; i < Map.getRows(); i++)
		{
			for (int j = 0; j < Map.getColumns(); j++)
			{
				Map.getEntity(i, j).turnPass();
				if (Map.getEntity(i, j).getType() < 100)
				{
					doPlantMove(i, j);
				}
				
				if (Map.getEntity(i, j).getType()>100)
				{
					zombieWon = doZombieMove(zombieWon, i, j);
				}
			}
		}
		
		if (zombieWon)
		{
			updateView();
			Turn = 0;
			View.showMessage("YOU DIED!!!!! Press OK to restart.");
		}
		levelLogic();
		updateView();
	}
	//Simply decrements the CD's, not below 0
	private void decrementCooldowns() {
		if (SunFlowerCD > 0)
		{
			SunFlowerCD--;
		}
		if (PeaShooterCD > 0)
		{
			PeaShooterCD--;
		}
		if (CherryBombCD > 0)
		{
			CherryBombCD--;
		}
		if (WallNutCD > 0)
		{
			WallNutCD--;
		}
		if(ChomperCD > 0)
		{
			ChomperCD--;
		}
	}
	//Previously inside endTurn() seperated for clarity
	//This basically does all the specialMoves, explosions and attacking of the plants.
	private void doPlantMove(int i, int j) {
		Sun+=Map.getEntity(i, j).special();
		if(Map.getEntity(i, j).getType() == 3)
		{
			if ( ((CherryBomb)Map.getEntity(i, j)).getCountDown() == 0 )
			{
				 doExplosion(i,j);
			}
		}

		if (Map.getEntity(i, j).getType() == 2)
		{
			View.doAnimation(Map.getEntity(i, j), new String[] {"S", "S0","S1","S2"});
		}
		Entity target = null;
		if ((Map.getEntity(i, j).getType() == 5 && !((Chomper) Map.getEntity(i, j)).getEating()) || Map.getEntity(i, j).getType() != 5)
		{
			target = Map.getTarget(i, j);
		}
		if (target != null)
		{
			attackLogic(i, j, target);
		}
	}
	//this is similar to the doPlantMove but for zombies
	private boolean doZombieMove(boolean zombieWon, int i, int j) {
		if (j == 0)
		{
			zombieWon = true;
		}
		else if (Map.getEntity(i, j).getType() == 101)
		{
			if (((Zombie)Map.getEntity(i, j)).makemove(Map.getEntity(i, j-1)))
			{
				if (Map.getEntity(i, j-1).getType() != 5)
				{
					 Sun += Map.removeEntity(i,j-1,false);
					 Map.moveLeft(i,j, 1);
				}
				else
				{
					 Sun += Map.removeEntity(i,j-1,false);
				}
				
			}
		}
		else if (Map.getEntity(i, j).getType() == 102)
		{
			int spacestoMove = 0;
			if (j-2 >=0)
			{
				spacestoMove = ((FastZombie) Map.getEntity(i, j)).makemove(Map.getEntity(i, j-1), Map.getEntity(i, j-2));
			}
			else if (j -1 == 0 && Map.getEntity(i, j-1).getType() == 100)
			{
				zombieWon = true;
				spacestoMove = 1;
			}
			else
			{
				spacestoMove = ((FastZombie) Map.getEntity(i, j)).makemove(Map.getEntity(i, j-1), new Entity());
			}
			if (spacestoMove == 1)
			{
				if (Map.getEntity(i, j-1).getType() != 5)
				{
					 Sun += Map.removeEntity(i,j-1,false);
					 Map.moveLeft(i,j, 1);
				}
				else
				{
					 Sun += Map.removeEntity(i,j-1,false);
				}
			}
			else if (spacestoMove == 2)
			{
				if (j-2 < 0)
				{
					zombieWon = true;
					Sun += Map.removeEntity(i,j-1,false);
					Map.moveLeft(i,j,1);
				}
				else
				{
					if (Map.getEntity(i, j-2).getType() != 5)
					{
						 Sun += Map.removeEntity(i,j-2,false);
						 Map.moveLeft(i,j, 2);
					}
					else
					{
						 Sun += Map.removeEntity(i,j-2,false);
						 Map.moveLeft(i, j, 1);
					}
				}
			}
			
		}
		else if (Map.getEntity(i, j).getType() == 103)
		{
			
			int spacestoMove = 0;
			if (j-2 >=0)
			{
				spacestoMove = ((PoleZombie) Map.getEntity(i, j)).makemove(Map.getEntity(i, j-1), Map.getEntity(i, j-2));
			}
			else if (j -1 == 0 && Map.getEntity(i, j-1).getType() == 100)
			{
				spacestoMove = 1;
			}
			else
			{
				spacestoMove = ((PoleZombie) Map.getEntity(i, j)).makemove(Map.getEntity(i, j-1), new Entity());
			}
			if (spacestoMove == 1)
			{
				if (Map.getEntity(i, j-1).getType() != 5)
				{
					 Sun += Map.removeEntity(i,j-1,false);
					 Map.moveLeft(i,j, 1);
				}
				else
				{
					 Sun += Map.removeEntity(i,j-1,false);
				}
			}
			else if (spacestoMove == 2)
			{
				if (j-2 < 0)
				{
					zombieWon = true;
					Sun += Map.removeEntity(i,j-1,false);
					Map.moveLeft(i,j,1);
				}
				else
				{
					if (Map.getEntity(i, j-2).getType() != 5)
					{
						 Sun += Map.removeEntity(i,j-2,false);
						 Map.moveLeft(i,j, 2);
					}
					else
					{
						 Sun += Map.removeEntity(i,j-2,false);
						 Map.moveLeft(i,j, 1); 
					}
				}
			}
			
		}
		else if (Map.getEntity(i, j).getType() == 104)
		{
			if (((GiantZombie)Map.getEntity(i, j)).makemove(Map.getEntity(i, j-1)))
			{
				if (Map.getEntity(i, j-1).getType() != 5)
				{
					 Sun += Map.removeEntity(i,j-1,false);
					 Map.moveLeft(i,j, 1);
				}
				else
				{
					 Sun += Map.removeEntity(i,j-1,false);
				}
			}
		}
		return zombieWon;
	}
	//This is mainly used for doing animations.
	private void attackLogic(int i, int j, Entity target) {
		if (Map.getEntity(i, j).getType() == 1)
		{
			View.doAnimation(Map.getEntity(i, j), new String[] {"P", "P0", "P", "P1"});
		}
		Map.getEntity(i, j).Attack(target);
		if (target.getHealth() <= 0 || Map.getEntity(i, j).getType() == 5)
		{
			View.doAnimation(target, new String[] {" ", target.getDisplay()+"0", target.getDisplay()+"1"});
			int Val = Map.removeEntity(target.getRow(), target.getColumn(), false);
			if (Map.getEntity(i, j).getType() != 5)
			{
				Sun += Val;
			}
		}
		else
		{
			View.doAnimation(target, new String[] {target.getDisplay(), target.getDisplay()+"0", target.getDisplay()+"1"});
		}
	}
	//Special function for the cherry bombs explosion
	private void doExplosion(int X, int Y) {
		for (int i = X - 1; i <= X + 1; i++)
		{
			for (int j = Y -1; j <= Y + 2; j++)
			{
				if (i >= 0 && j >= 0 && i < Map.getRows() && j < Map.getColumns())
				{
					Entity target = Map.getEntity(i, j);
					if (target.getType() > 100)
					{
						attackLogic(X, Y, target);
					}
				}
			}
		}
		View.doAnimation(Map.getEntity(X, Y), new String[] {"C3", "C2", "C3", "C3"," "});
		Map.removeEntity(X, Y, false);
		
	}
	//Sends View.updateView with the game's fields
	public void updateView() {
		View.updateView(Sun, Turn, SunFlowerCD, PeaShooterCD, CherryBombCD, WallNutCD, ChomperCD);	
	}
	//Determines what happens when, most likely will be converted to read from a text file in the future
	//that way it will also open up the ability for the user to create, save and edit their own levels
	//Please note that we took a manual approach to better control the balance of the game, however, in the next
	//iteration (Milestone 4) this will be changed heavily.
	private void levelLogic() {

		if (currentLevel == 1)
		{
			if (Turn == 0)
			{
				SunFlowerCD = 0;
				PeaShooterCD = 0;
				CherryBombCD = 1000;
				WallNutCD = 1000;
				ChomperCD = 1000;
				Sun = 200;
				Turn = 0;
				Map.ResetBoard();
				updateView();
				View.showMessage("Five zombies will come in the next wave, you have 3 turns to prepare");
			}
			if (!added)
			{
				if (Turn == 3)
				{
					Map.addEntity(101, 0, 8);
					added = true;
				}
				if (Turn == 4)
				{
					Map.addEntity(101, 1, 8);
					added = true;
				}
				if (Turn ==  6)
				{
					Map.addEntity(101, 0, 8);
					Map.addEntity(101, 4, 8);
					added = true;
				}
				if (Turn == 7)
				{
					Map.addEntity(101, 2, 8);
					added = true;
				}
			}
			if (Turn > 7)
			{
				if (Map.didWin())
				{
					Turn = 0;
					currentLevel = 2;
					View.showMessage("You beat level 1!!");
					levelLogic();
				}
			}
		}
		else if (currentLevel == 2)
		{
			if (Turn == 0)
			{
				SunFlowerCD = 0;
				PeaShooterCD = 0;
				CherryBombCD = 0;
				WallNutCD = 1000;
				ChomperCD = 1000;
				Sun = 200;
				Turn = 0;
				Map.ResetBoard();
				updateView();
				View.showMessage("Six zombies, and two fast zombies will come in the next wave, you have 3 turns to prepare\n\nYou've Unlocked the CherryBomb to assist you against the new zombie.\n\n"
						+ "The CherryBomb is a fragile plant that explodes after 3 turns in a 3 wide and 4 long explosion centered on the plant.\n" + 
						"HP: 3\n" + 
						"Defense: 1\n" + 
						"Attack Damage: 100\n" + 
						"Attack Range: 4 tiles across, 3 above or below\n" + 
						"Cost: 150 sun\n\n"
						+ "The Fast Zombie can move two blocks at once if nothing is in its way. "
						+ "HP: 10\n" + 
						"Defense: 1.2\n" + 
						"Attack Damage: 3\n" + 
						"Attack Range: 1 tiles across, 0 above or below\n" + 
						"Value: 30 sun");
			}
			if (!added)
			{
				if (Turn == 3)
				{
					Map.addEntity(101, 3, 8);
					added = true;
				}
				if (Turn == 4)
				{
					Map.addEntity(101, 1, 8);
					added = true;
				}
				if (Turn == 5)
				{
					Map.addEntity(102, 1, 8);
					Map.addEntity(102, 2, 8);
					added = true;
				}
				if (Turn ==  6)
				{
					Map.addEntity(101, 0, 8);
					Map.addEntity(101, 4, 8);
					added = true;
				}
				if (Turn == 7)
				{
					Map.addEntity(101, 2, 8);
					Map.addEntity(101, 0, 8);
					added = true;
				}
			}
			if (Turn > 7)
			{
				if (Map.didWin())
				{
					Turn = 0;
					currentLevel = 3;
					View.showMessage("You beat level 2!!");
					levelLogic();
				}
			}
		}
		else if (currentLevel == 3)
		{
			if (Turn == 0)
			{
				SunFlowerCD = 0;
				PeaShooterCD = 0;
				CherryBombCD = 0;
				WallNutCD = 0;
				ChomperCD = 1000;
				Sun = 200;
				Turn = 0;
				Map.ResetBoard();
				updateView();
				View.showMessage("Eight zombies, and four fast zombies will come in the next wave, you have 3 turns to prepare\n\n You've also unlocked a new type of plant!\n"
						+ "The WallNut is durable but can't attack. Use it to block the path of oncoming zombies!"
						+ "\nHP: 15\n" + 
						"Defense: 2\n" + 
						"Attack Damage: 0\n" + 
						"Attack Range: 0 tiles across, 0 above or below\n" + 
						"Cost: 50 sun");
			}
			if (!added)
			{
				if (Turn == 3)
				{
					Map.addEntity(101, 3, 8);
					added = true;
				}
				if (Turn == 4)
				{
					Map.addEntity(102, 4, 8);
					Map.addEntity(101, 1, 8);
					added = true;
				}
				if (Turn == 5)
				{
					Map.addEntity(102, 1, 8);
					Map.addEntity(102, 2, 8);
					added = true;
				}
				if (Turn ==  6)
				{
					Map.addEntity(101, 0, 8);
					Map.addEntity(101, 4, 8);
					added = true;
				}
				if (Turn == 7)
				{
					Map.addEntity(101, 2, 8);
					Map.addEntity(101, 0, 8);
					added = true;
				}
				if (Turn == 8)
				{
					Map.addEntity(101, 3, 8);
					Map.addEntity(101, 1, 8);
					Map.addEntity(102, 4, 8);
				}
			}
			if (Turn > 7)
			{
				if (Map.didWin())
				{
					Turn = 0;
					currentLevel = 4;
					View.showMessage("You beat level 3!!");
					levelLogic();
				}
			}
		}
		else if (currentLevel == 4)
		{
			if (Turn == 0)
			{
				SunFlowerCD = 0;
				PeaShooterCD = 0;
				CherryBombCD = 0;
				WallNutCD = 0;
				ChomperCD = 0;
				Sun = 200;
				Turn = 0;
				Map.ResetBoard();
				updateView();
				View.showMessage("Eight zombies, three fast zombies and two pole zombies will come in the next wave, you have 3 turns to prepare\n\nYou've unlocked the Chomper Plant to assist you with the new zombie!"
						+ "\nThe Chomper can eat a zombie in one bite! BUT, it needs time to devour it. Careful! if it gets killed while eating, its lunch might eat you!\n The Chomper is also useful for catching pole zombies mid vault!"
						+ "\nHP: 10\n" + 
						"Defense: 1\n" + 
						"Attack Damage: 5\n" + 
						"Attack Range: 1 tiles across, 0 above or below\n" + 
						"Cost: 150 sun"
						+ "\n\n The Pole Zombie is dangerous! Not only does it's pole do extra damage, but it can use it to jump over an isolated plant!"
						+ "\nHP: 10\n" + 
						"Defense: 1.2\n" + 
						"Attack Damage: 5 (when not disarmed)\n" + 
						"Attack Range: 1 tiles across, 0 above or below\n" + 
						"Value: 30 sun");
			}
			if (!added)
			{
				if (Turn == 3)
				{
					Map.addEntity(101, 3, 8);
					added = true;
				}
				if (Turn == 4)
				{
					Map.addEntity(103, 4, 8);
					Map.addEntity(103, 2, 8);
					Map.addEntity(101, 1, 8);
					added = true;
				}
				if (Turn == 5)
				{
					Map.addEntity(102, 1, 8);
					Map.addEntity(102, 2, 8);
					added = true;
				}
				if (Turn ==  6)
				{
					Map.addEntity(101, 0, 8);
					Map.addEntity(101, 4, 8);
					added = true;
				}
				if (Turn == 7)
				{
					Map.addEntity(101, 2, 8);
					Map.addEntity(101, 0, 8);
					added = true;
				}
				if (Turn == 8)
				{
					Map.addEntity(101, 3, 8);
					Map.addEntity(101, 1, 8);
					Map.addEntity(102, 4, 8);
				}
			}
			if (Turn > 7)
			{
				if (Map.didWin())
				{
					Turn = 0;
					currentLevel = 5;
					View.showMessage("You beat level 4!!");
					levelLogic();
				}
			}
		}
		else if (currentLevel == 5)
		{
			if (Turn == 0)
			{
				SunFlowerCD = 0;
				PeaShooterCD = 0;
				CherryBombCD = 0;
				WallNutCD = 0;
				ChomperCD = 0;
				Sun = 200;
				Turn = 0;
				Map.ResetBoard();
				updateView();
				View.showMessage("FINAL ROUND!!!!\n\n Ten zombies, four fast zombies four pole zombies and three Giant Zombies will come in the next wave, you have 5 turns to prepare\n"
						+ "\n BE CAREFUL!!!! The Giant Zombie is tough and strong!"
						+ "\nHP: 100\n" + 
						"Defense: 1\n" + 
						"Attack Damage: 10\n" + 
						"Attack Range: 1 tiles across, 0 above or below\n" + 
						"Value: 100 sun");
			}
			if (!added)
			{
				if (Turn == 5)
				{
					Map.addEntity(101, 3, 8);
					Map.addEntity(101, 1, 8);
					added = true;
				}
				if (Turn == 6)
				{
					Map.addEntity(103, 4, 8);
					Map.addEntity(103, 2, 8);
					Map.addEntity(101, 1, 8);
					added = true;
				}
				if (Turn == 7)
				{
					Map.addEntity(102, 1, 8);
					Map.addEntity(102, 2, 8);
					Map.addEntity(101, 4, 8);
					added = true;
				}
				if (Turn ==  8)
				{
					Map.addEntity(101, 0, 8);
					Map.addEntity(104, 2, 8);
					Map.addEntity(101, 4, 8);
					added = true;
				}
				if (Turn == 9)
				{
					Map.addEntity(101, 2, 8);
					Map.addEntity(104, 3, 8);
					Map.addEntity(101, 0, 8);
					added = true;
				}
				if (Turn == 10)
				{
					Map.addEntity(101, 3, 8);
					Map.addEntity(101, 1, 8);
					Map.addEntity(102, 4, 8);
				}
				if (Turn == 11)
				{
					Map.addEntity(102, 0, 8);
					Map.addEntity(104, 3, 8);
					Map.addEntity(103, 4, 8);
					Map.addEntity(103, 2, 8);
				}
			}
			if (Turn > 11)
			{
				if (Map.didWin())
				{
					Turn = 0;
					currentLevel = 0;
					View.showMessage("You beat the game!! Press ok to restart");
					levelLogic();
				}
			}
		}
		
	}

}
