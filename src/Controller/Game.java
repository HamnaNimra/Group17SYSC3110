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
				
			}
			if (Map.didWin())
			{
				Turn = -1;
			}
		}
	}
	
	//This is what happens when the status command is called
	//1. series of Ifs to parse command for validity
	//2. output the toString of the selected entity
	private void status(String[] command) {

		
	}
	//This is what happens when the soak command is called
	//1. check each entity to see if its a sunflower
	//2. if it is, add up the sun gained from the soak
	//3. add the sun to the user's sun
	//4. reload the map so the user's new sun value gets updated
	private void soak() {
	
	}
	//This is what happens when the plant command is called
	//1. series of Ifs to parse the command for validity
	//2. if the user has enough money and the plants not on cooldown, it will add it and reduce their sun
	private boolean plant(String[] command) {
		retVal = false;
		return retVal;
	}
	//This is what happens when the attack command is called
	//1. series of Ifs to parse the command for validity
	//2. if the user is attacking a valid entity/hasn't already attacked, check if it died to remove it.
	private boolean attack(String[] command) {
		boolean retVal = false;
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
			System.out.println(commands.get(i) + ": " + descriptions.get(i));
		}
		
	}
	
	

}
