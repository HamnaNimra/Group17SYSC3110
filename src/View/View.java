package View;
import Model.*;
import java.util.ArrayList;
import java.util.Scanner;

import Model.TileMap;


//The View class
//This class controls output / input  to / from the user and also outputs the map
//Author: Omar Azam
public class View {
	//The actual model that the view is displaying
	private TileMap currentMap;
	//A scanner for input and output.
	Scanner scanner = new Scanner(System.in);

	//Constructor
	public View(TileMap newMap)
	{
		setCurrentMap(newMap);
	}
	//Prints the Map to the Console, Figure out a nice way to display each entity
	//Assume all entities have the method getDisplay() that in this milestone will
	//Return a char, representing what they look like.
	public void showMap(int sun, int turn, int sunflowerCD, int peashooterCD)
	{
		//I use this to output a list of all the current plants and zombies after the board
		ArrayList<Zombie> zombies = new ArrayList<Zombie>();
		ArrayList<Entity> plants = new ArrayList<Entity>();
		//Clear is called so that the console looks blank
		clear();
		//These prints shows the different game statuses to the user
		System.out.println("Sun: " + sun + "        Turn: " + turn);
		System.out.println("SunFlower Cooldown: " + sunflowerCD + "     PeaShooter Cooldown: " + peashooterCD);
		//The following creates a header for the table, so its easier for the user to interact with the board
		System.out.print("          ");
		for (int i = 0; i < currentMap.getColumns(); i++)
		{
			System.out.print("["+ i + "] ");
		}
		System.out.println();
		//The following prints each row with a header at the front so its easier for the user to interact with the board
		for (int i = 0; i < currentMap.getRows(); i++)
		{
			//Header
			System.out.print("["+i+"]       ");
			for (int j = 0; j < currentMap.getColumns(); j++)
			{
				//prints out each entities Display character for the current row column (i, j)
				System.out.print("["+ currentMap.getEntity(i, j).getDisplay() + "] ");
				if (currentMap.getEntity(i, j).getType() > 100)
				{
					zombies.add((Zombie)currentMap.getEntity(i, j));
				}
				else if (currentMap.getEntity(i, j).getType() < 100)
				{
					plants.add(currentMap.getEntity(i, j));
				}
			}
			System.out.println();
		}
		//Prints a display of all the plants and zombies at the bottom, with their short toString statuses
		System.out.println("Plants:");
		for (int i = 0; i < plants.size(); i++)
		{
			System.out.println(plants.get(i).toStringShort());
		}
		System.out.println("Zombies:");
		for (int i = 0; i < zombies.size(); i++)
		{
			System.out.println(zombies.get(i).toStringShort());
		}
	}
	//This method clears the console
	public void clear()
	{
		for (int i = 0; i < 30; i++) {
			System.out.println();
		}
	}
	//This will print the prompt then hold the console for user input and return whatever they type
	public String getInput(String prompt)
	{
		String retVal = "";
		
		System.out.println(prompt);
		retVal = scanner.nextLine();

		return retVal;
	}
	//What this method does is get the user input for the x and y
	//of the entity, and then gets the entity at that position and 
	//then outputs a string representing the Entity. 
	//Simplified: currentMap.getEntity(x,y).ToString();
	//and then print it to the console.
	public void showEntity(int row, int column)
	{
		System.out.println(currentMap.getEntity(row, column).toString());
	}
	//Displays a string for the Game Class
	public void showMessage(String message)
	{
		System.out.println(message);
	}
	//Allows the GameClass to change the currentMap
	public void setCurrentMap(TileMap currentMap) {
		this.currentMap = currentMap;
	}

}
