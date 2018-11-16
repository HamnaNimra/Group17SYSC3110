package Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.Entity;
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
	//ENUM from view
	public enum State {
		viewing, plantingS, plantingP
	
	}

	
	//Constructor and printout of overview of the game
	public Game(TileMap map, View view)
	{
		//save Model and View
		Map = map;
		View = view;
		//Main Menu of sorts
		view.showMessage("Welcome to Plants vs. Zombies by Group 17Currently the game only features 2 types of plants and one type of zombie\nTo plant something, click its icon on the hotbar at the top, then again on the tile you wish to plant it on.\n\nThe SunFlower:\n" + 
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
		view.addEndTurnListener(new endTurnListener());
		view.addRestartListener(new restartListener());
	}
	//Determines what happens when, most likely will be converted to read from a text file in the future
	//that way it will also open up the ability for the user to create, save and edit their own levels
	private void levelLogic() {
		if (Turn == 0)
		{
			//Clears the view and initializes sun, turn and the board
			Sun = 200;
			Turn = 0;
			Map.ResetBoard();
			updateView();
		}
		if (currentLevel == 1)
		{
			if (Turn == 0)
			{
				View.showMessage("\n\nFive zombies will come in the next wave, you have 3 turns to prepare");
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
					Turn = -1;
					View.showMessage("You Won! Press the restart button to restart");
				}
			}
		}
		
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
		//There are no checks for money because the button is disabled if there isnt enough money
		@Override
		public void actionPerformed(ActionEvent e) {
			if (View.getGUIState() == View.state.viewing)
			{
				View.showMessage(Map.getEntity(X, Y).toString());
			}
			else if (View.getGUIState() == View.state.plantingS)
			{
				Map.addEntity(2, X, Y);
				Sun -= Map.getEntity(X,Y).getValue();
				SunFlowerCD=2;
				View.setGUIState(0);
				View.enableAllBoxes();
				updateView();
			}
			else if (View.getGUIState() == View.state.plantingP)
			{
				Map.addEntity(1, X, Y);
				Sun -= Map.getEntity(X,Y).getValue();
				PeaShooterCD=1;
				View.setGUIState(0);
				View.enableAllBoxes();
				updateView();
			}
		}
	}
	//The listener for the end Button (method endTurn() called)
	public class endTurnListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			endTurn();
		}
		
	}
	//The listener for the restart button (method included)
	public class restartListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Turn = 0;
			SunFlowerCD=0;
			PeaShooterCD=0;
			levelLogic();
		}
		
	}
	//This is what happens when the End command is called
		//1.Increase Turn
		//2.Decrement cooldowns
		//3.reset added
		//EDITED: the EndTurn method from the TileMap was absorbed into here for doing animations
		//4.passesTurn on each (resets attacked and soaked)
		//5.Executes the special move of each plant(only sunflowers have one) and does animation
		//6.looks for a target to attack on each plant, and then does + animation
		//7.makemove on each zombie
		//8.checks to see if the zombies won, if they did reloads the map and tells the user
	private void endTurn() {
		boolean zombieWon = false;
		Turn++;
		if (SunFlowerCD > 0)
		{
			SunFlowerCD--;
		}
		if (PeaShooterCD > 0)
		{
			PeaShooterCD--;
		}
		added = false;

		for (int i = 0; i < Map.getRows(); i++)
		{
			for (int j = 0; j < Map.getColumns(); j++)
			{
				Map.getEntity(i, j).turnPass();
				if (Map.getEntity(i, j).getType() < 100)
				{
					Sun += Map.getEntity(i, j).special();
					if (Map.getEntity(i, j).getType() == 2)
					{
						View.doAnimation(Map.getEntity(i, j), new String[] {"S", "S0","S1","S2"});
					}
					Entity target = Map.getTarget(i, j);
					if (target != null)
					{
						if (Map.getEntity(i, j).getType() == 1)
						{
							View.doAnimation(Map.getEntity(i, j), new String[] {"P", "P0", "P", "P1"});
						}
						Map.getEntity(i, j).Attack(target);
						if (target.getHealth() <= 0)
						{
							if (target.getType() == 101)
							{
								View.doAnimation(target, new String[] {" ", "Z0", "Z1"});
							}
							Sun += Map.removeEntity(target.getRow(), target.getColumn(), false);
						}
						else
						{
							if (target.getType() == 101)
							{
								View.doAnimation(target, new String[] {"Z", "Z0", "Z1"});
							}
						}
					}
				}
				
				if (Map.getEntity(i, j).getType()>100)
				{
					if (j == 0)
					{
						zombieWon = true;
					}
					else if (((Zombie)Map.getEntity(i, j)).makemove(Map.getEntity(i, j-1)))
					{
						 Sun += Map.removeEntity(i,j-1,false);
						 Map.moveLeft(i,j);
					}
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
	//Sends View.updateView with the game's fields
	public void updateView() {
		View.updateView(Sun, Turn, SunFlowerCD, PeaShooterCD);	
	}

	

}
