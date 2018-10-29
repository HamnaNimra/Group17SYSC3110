package View;
import Model.*;
import java.util.ArrayList;
import java.util.Scanner;

import Model.TileMap;


//The View class
//This class controls output / input  to / from the user and also outputs the map
//Outline Author: Kyle Smith
public class View {
	//The actual model that the view is displaying
	private TileMap currentMap;
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
		
	}
	//This method clears the console
	public void clear()
	{
	}
	//This will print the prompt then hold the console for user input and return whatever they type
	public String getInput(String prompt)
	{
		String retVal = "";
		return retVal;
	}
	//What this method does is get the user input for the x and y
	//of the entity, and then gets the entity at that position and 
	//then outputs a string representing the Entity. 
	//Simplified: currentMap.getEntity(x,y).ToString();
	//and then print it to the console.
	public void showEntity(int row, int column)
	{
	}
	//Displays a string for the Game Class
	public void showMessage(String message)
	{
	}
	//Allows the GameClass to change the currentMap
	public void setCurrentMap(TileMap currentMap) {
		this.currentMap = currentMap;
	}

}
