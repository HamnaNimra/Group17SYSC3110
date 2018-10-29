package Controller;
import java.util.ArrayList;
import java.util.Arrays;

import Model.TileMap;
import View.View;

//This is the game class
//It handles all functions of how the game flows, and handles all commands
//Author: Kyle Smith
public class Game {
	
	//The Model and View
	private TileMap Map;
	private View View;
	//The current level (there is only 1 right now)
	int level = 1;
	//A boolean to make sure zombies dont get added multiple times in one turn.
	private boolean added = false;
	//The users Sun, and the current turn
	private int Sun;
	private int Turn;
	//The cooldown for planting different plants
	private int SunFlowerCD;
	private int PeaShooterCD;
	//All the possible commands
	ArrayList<String> commands = new ArrayList<String>(
		    Arrays.asList("help",
		    		"restart",
		    		"end",
		    		"attack",
		    		"plant",
		    		"soak",
		    		"status",
		    		"show"));
	//All the commands help text
	ArrayList<String> descriptions = new ArrayList<String>(
		    Arrays.asList("displays this dialog",
		    		"resets the game to turn 0",
		    		"finishes your turn",
		    		"attacks a target zombie (ex. attack plantRow plantColumn zombieRow zombieColumn)",
		    		"plants a plant (ex. plant [sunflower|peashooter] row column)",
		    		"makes all sunflowers collect sunlight",
		    		"shows the status of any entity (ex. status row column)",
		    		"clears the display and shows the map"));
	//Constructor and printout of overview of the game
	public Game(TileMap map, View view)
	{
		//save Model and View
		Map = map;
		View = view;
		//Main Menu of sorts
		view.showMessage("Welcome to Plants vs. Zombies by Group 17");
		view.showMessage("Currently the game only features 2 types of plants and one type of zombie");
		view.showMessage("At anytime during the game type 'help' to get a list of commands");
		view.showMessage("The SunFlower:\n" + 
				"	HP: 5\n" + 
				"	Defense: 1\n" + 
				"	Attack Damage: 0\n" + 
				"	Attack Range: 0 tiles across, 0 above or below\n" + 
				"	Cost: 75 sun\n" + 
				"	Cooldown to plant: 2 turns\n" + 
				"The PeaShooter:\n" + 
				"	HP: 10\n" + 
				"	Defense: 1\n" + 
				"	Attack Damage: 3.5\n" + 
				"	Attack Range: 9 tiles across, 1 above or below\n" + 
				"	Cost: 125 sun\n" + 
				"	Cooldown to plant: 1 turn\n" + 
				"The Zombie:\n" + 
				"	Default plant stats:\n" + 
				"	HP: 10\n" + 
				"	Defense: 1.2\n" + 
				"	Attack Damage: 2\n" + 
				"	Attack Range: 1 tiles across, 0 above or below\n" + 
				"	Value for killing: 15 sun");
		view.getInput("Press enter to start the game!");
		start();
	}
	//An infinite loop to read user input and execute the commands they type
	private void start() {
		//Clears the view and initializes sun, turn and the board
		View.clear();
		Sun = 200;
		Turn = 0;
		Map.ResetBoard();
		//Shows the current map and the level 1 message
		View.showMap(Sun, Turn, SunFlowerCD, PeaShooterCD);
		View.showMessage("\n\nFive zombies will come in the next wave, you have 3 turns to prepare");
		//Start of command loop
		while(Turn >= 0)
		{
			//boolean mostly used for determining when the board should be rendered
			boolean commandSuccess = false;
			while (!commandSuccess)
			{
			
				String input = View.getInput("Type 'help' to see a list of commands:");
				//Slices the command for parsing and passes it through to a straightforward set of ifs
				String[] command = input.split(" ");
				if (commands.contains(command[0]))
				{
					if (command[0].equals("help"))
					{
						help();
					}
					else if (command[0].equals("restart"))
					{
						Turn = -1;
						commandSuccess = true;
					}
					else if (command[0].equals("end"))
					{
						commandSuccess = true;
						endTurn();
						
					}
					else if (command[0].equals("attack"))
					{
						if (attack(command))
						{
							commandSuccess = true;
						}
					}
					else if (command[0].equals("plant"))
					{
						if (plant(command))
						{
							commandSuccess = true;
						}

					}
					else if (command[0].equals("soak"))
					{
						soak();						
					}
					else if (command[0].equals("status"))
					{
						status(command);
					}
					else if (command[0].equals("show"))
					{
						commandSuccess = true;
					}
				}
				else
				{
					View.showMessage("Command Error, please check your input and try again");
				}
			
			}
			levelLogic(level);
			View.showMap(Sun, Turn, SunFlowerCD, PeaShooterCD);
			
		}
		start();
		
		
	}
	//Determines what happens when, most likely will be converted to read from a text file in the future
	//that way it will also open up the ability for the user to create, save and edit their own levels
	private void levelLogic(int currentLevel) {
		if (currentLevel == 1)
		{
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
					View.getInput("YOU WON!!!!! Press Enter to restart");
				}
			}
		}
		
	}
	//This is what happens when the status command is called
	//1. series of Ifs to parse command for validity
	//2. output the toString of the selected entity
	private void status(String[] command) {
		if (command.length == 3)
		{
			if (tryParseInt(command[1]) && tryParseInt(command[2]))
			{
				if (Integer.parseInt(command[1]) < Map.getRows() && Integer.parseInt(command[2]) < Map.getColumns())
				{
					View.showEntity(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
				}
				else
				{
					View.showMessage("Selected Row and Column is not within range! Please try again!");
				}
			}
			else
			{
				View.showMessage("Syntax Error!");
			}
		}
		
	}
	//This is what happens when the soak command is called
	//1. check each entity to see if its a sunflower
	//2. if it is, add up the sun gained from the soak
	//3. add the sun to the user's sun
	//4. reload the map so the user's new sun value gets updated
	private void soak() {
		int sunGained = 0;
		
		for (int i = 0; i < Map.getRows(); i++)
		{
			for (int j = 0; j < Map.getColumns(); j++)
			{
				if (Map.getEntity(i, j).getType() == 2)
				{
					sunGained += (Map.getEntity(i, j)).special();
				}
			}
		}
		Sun+=sunGained;
		View.showMap(Sun, Turn, SunFlowerCD, PeaShooterCD);
		View.showMessage("Gained " + sunGained + " Sun");
		
		
	}
	//This is what happens when the plant command is called
	//1. series of Ifs to parse the command for validity
	//2. if the user has enough money and the plants not on cooldown, it will add it and reduce their sun
	private boolean plant(String[] command) {
		boolean retVal = false;
		if (command.length == 4)
		{
			if ( (command[1].equals("sunflower") || command[1].equals("peashooter")) && (tryParseInt(command[2]) && tryParseInt(command[3])))
			{
				if (Integer.parseInt(command[2]) < Map.getRows() && Integer.parseInt(command[3]) < Map.getColumns())
				{
					if (command[1].equals("sunflower") && Sun >= 75 && SunFlowerCD <= 0)
					{
						if (Map.addEntity(2, Integer.parseInt(command[2]), Integer.parseInt(command[3])))
						{
							
							retVal = true;
							Sun -= Map.getEntity(Integer.parseInt(command[2]), Integer.parseInt(command[3])).getValue();
							SunFlowerCD=2;
						}
						else
						{
							View.showMessage("There is already something there!");
						}
					}
					else if (command[1].equals("peashooter") && Sun >= 125 && PeaShooterCD <= 0)
					{
						if (Map.addEntity(1, Integer.parseInt(command[2]), Integer.parseInt(command[3])))
						{
							retVal = true;
							Sun -= Map.getEntity(Integer.parseInt(command[2]), Integer.parseInt(command[3])).getValue();
							PeaShooterCD = 1;
						}
						else
						{
							View.showMessage("There is already something there!");
						}
					}
					else
					{
						View.showMessage("Insufficient Sun OR that plant is on cooldown");
					}
				}
			}
			else
			{
				View.showMessage("Syntax Error!");
			}
		}
		else
		{
			View.showMessage("Syntax Error!");
		}
		return retVal;
	}
	//This is what happens when the attack command is called
	//1. series of Ifs to parse the command for validity
	//2. if the user is attacking a valid entity/hasn't already attacked, check if it died to remove it.
	private boolean attack(String[] command) {
		boolean retVal = false;
		if (command.length == 5)
		{
			if (tryParseInt(command[1]) && tryParseInt(command[2]) && tryParseInt(command[3]) && tryParseInt(command[4]))
			{
				if (Map.getRows() > Integer.parseInt(command[1]) && Map.getColumns() > Integer.parseInt(command[2]) && Map.getRows() > Integer.parseInt(command[3]) && Map.getColumns() > Integer.parseInt(command[4]))
				{
					if (Map.getEntity(Integer.parseInt(command[1]), Integer.parseInt(command[2])).getType() < 100)
					{
						if (Map.getEntity(Integer.parseInt(command[3]), Integer.parseInt(command[4])).getType() > 100)
						{
							if (Map.getEntity(Integer.parseInt(command[1]), Integer.parseInt(command[2])).Attack(Map.getEntity(Integer.parseInt(command[3]), Integer.parseInt(command[4]))))
							{
								retVal = true;
								if (Map.getEntity(Integer.parseInt(command[3]), Integer.parseInt(command[4])).getHealth() <= 0)
								{
									Sun+= Map.removeEntity(Integer.parseInt(command[3]), Integer.parseInt(command[4]), false);
								}
							}
							else
							{
								View.showMessage("That is not within range, or has already attacked");
							}
							
						}
						else
						{
							View.showMessage("That is not a valid target!");
						}
					}
					else
					{
						View.showMessage("That is not a valid plant!");
					}
				}
				else
				{
					View.showMessage("Syntax Error! A value was out of bounds.");
				}
			}
			else
			{
				View.showMessage("Syntax Error!");
			}
					
		}
		else
		{
			View.showMessage("Syntax Error!");
		}
		return retVal;
		
	}
	//This is what happens when the End command is called
	//1.Increase Turn
	//2.Decrement cooldowns
	//3.reset added
	//4.runs the models end turn method, and adds up any sun gained from it
	//5.checks to see if the zombies won, if they did reloads the map and tells the user
	private void endTurn() {
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
		Sun += Map.endTurn();
		if (Map.isZombieWon())
		{
			View.showMap(Sun, Turn, SunFlowerCD, PeaShooterCD);
			Turn = -1;
			View.getInput("YOU DIED!!!!! Press Enter to restart");
		}
		
	}
	//Outputs the command list
	private void help() {

		for (int i = 0; i < commands.size(); i++)
		{
			View.showMessage(commands.get(i) + ": " + descriptions.get(i));
		}
		
	}
	//This method is used for parsing command validity without raising errors
	private boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}
	
	

}
