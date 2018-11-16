package View;
import Model.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import Model.TileMap;


//The View class
//This class controls output / input  to / from the user and also outputs the map
//Author: Omar Azam
//EDIT: Made changes for milestone 2
public class View extends JFrame{
	//The actual model that the view is displaying
	private TileMap currentMap;
	private JButton[][] buttonMap;
	private JButton[] plantsMenu;
	private JLabel infoLabel;
	public State state;
	public enum State {
		viewing, plantingS, plantingP
	
	}

	//Constructor
	public View(TileMap newMap)
	{
		super("Plants vs. Zombies");
		setCurrentMap(newMap);
		buttonMap = new JButton[currentMap.getRows()][currentMap.getColumns()];
		plantsMenu = new JButton[currentMap.getColumns()];
		//Init GUI
		setGUIState(0);//Viewing State
		setupGUI();
	}
	//
	private void setupGUI() {
	
		//The Bar where you can end turn and select the plant to plant
		JPanel topPanel = new JPanel();
		//The actual map and stuff
		JPanel bottomPanel = new JPanel();
		//Chose grid layout because its easy to use and all we need for this milestone
		topPanel.setLayout(new GridLayout(1,currentMap.getColumns()));
		//This is pretty much hardcoded, we chose to to it as buttons so we could have the image
		// of the plant they were trying to plant.
		for (int i = 0; i < currentMap.getColumns(); i++)
		{
			if (i == 0)
			{
				plantsMenu[i] = new JButton(getSprite("S"));
			}
			else if (i == 1)
			{
				plantsMenu[i] = new JButton(getSprite("P"));

			}
			else if (i == currentMap.getColumns()-2)
			{
				plantsMenu[i] = new JButton("Restart");
			}
			else if (i == currentMap.getColumns()-1)
			{
				plantsMenu[i] = new JButton("End Turn");
			}
			else
			{
				plantsMenu[i] = new JButton("Locked");
				plantsMenu[i].setEnabled(false);
			}
			topPanel.add(plantsMenu[i]);
		}
		//created its own action Listener because it only affects internal state
		plantsMenu[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (state == State.plantingS)
				{
					setGUIState(0);
					enableAllBoxes();
				}
				else
				{
					setGUIState(1);
					disableOccupiedBoxes();
				}
			}
		});
		//created its own action Listener because it only affects internal state
		plantsMenu[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getGUIState() == State.plantingP)
				{
					setGUIState(0);
					enableAllBoxes();
				}
				else
				{
					setGUIState(2);
					disableOccupiedBoxes();
				}
			}
		});
		//grid layout because it looks like the board.
		bottomPanel.setLayout(new GridLayout(currentMap.getRows()+1,currentMap.getColumns()+1));
		//Used a bit of html so i could use <br>'s, it is it's own field so it can be updated easily
		infoLabel = new JLabel("<html>Turn: 0<br>Sun: 0<br>Sunflower CD: 0<br> Peashooter CD: 0</html>");
		//This centers the text inside the label
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoLabel.setVerticalAlignment(SwingConstants.CENTER);
		bottomPanel.add(infoLabel);
		
		for (int i = 0; i < currentMap.getColumns(); i++)
		{
			//This populates the extra row in the grid with the value of the row (in recognition of previous versions/ debugging use. Can be removed as long as info label is moved)
			JLabel headr = new JLabel(Integer.toString(i));
			headr.setHorizontalAlignment(SwingConstants.CENTER);
			headr.setVerticalAlignment(SwingConstants.CENTER);
			bottomPanel.add(headr);
		}

		for (int i = 0; i < currentMap.getRows(); i++)
		{
			//Makes the headers for the additional column
			JLabel headr = new JLabel(Integer.toString(i));
			headr.setHorizontalAlignment(SwingConstants.CENTER);
			headr.setVerticalAlignment(SwingConstants.CENTER);
			bottomPanel.add(headr);

			for (int j = 0; j < currentMap.getColumns(); j++)
			{
				//Makes the buttons and initializes them to the blank sprites
				buttonMap[i][j] = new JButton();
				bottomPanel.add(buttonMap[i][j]);
				buttonMap[i][j].setIcon(getSprite(currentMap.getEntity(i, j).getDisplay()));
			}
			
		}
		//Used border layout to put our two grids together and it looked nice so we left it
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(bottomPanel, BorderLayout.CENTER);
		//This is the size we found looks best
		this.setSize(1100, 800);
		this.setVisible(true);
	}
	//Used for switching from a planting state to the viewing state
	public void enableAllBoxes() {
		for (int i = 0; i < currentMap.getRows(); i++)
		{
			for (int j = 0; j < currentMap.getColumns(); j++)
			{
				buttonMap[i][j].setEnabled(true);
			}
		}
		
	}
	//Used for switching from a viewing state to the planting state
	public void disableOccupiedBoxes() {
		for (int i = 0; i < currentMap.getRows(); i++)
		{
			for (int j = 0; j < currentMap.getColumns(); j++)
			{
				if (currentMap.getEntity(i, j).getType() != 100)
				{
					buttonMap[i][j].setEnabled(false);
				}
			}
		}
		
	}
	//Methods so game class can attach it's listeners
	public void addButtonListener(ActionListener listener, int x, int y)
	{
		buttonMap[x][y].addActionListener(listener);
	}
	public void addEndTurnListener(ActionListener listener)
	{
		plantsMenu[currentMap.getColumns()-1].addActionListener(listener);
	}
	public void addRestartListener(ActionListener listener)
	{
		plantsMenu[currentMap.getColumns()-2].addActionListener(listener);
	}
	
	//Prints the Map to the Console, Figure out a nice way to display each entity
	//Assume all entities have the method getDisplay() that in this milestone will
	//Return a char, representing what they look like.
	public void updateView(int sun, int turn, int sunflowerCD, int peashooterCD)
	{
		if (sunflowerCD > 0 || sun < 75)
		{
			plantsMenu[0].setEnabled(false);
		}
		else
		{
			plantsMenu[0].setEnabled(true);
		}
		
		if (peashooterCD > 0 || sun < 125)
		{
			plantsMenu[1].setEnabled(false);
		}
		else
		{
			plantsMenu[1].setEnabled(true);
		}
		
		
		infoLabel.setText("<html>Turn: "+turn+"<br>Sun: "+sun+"<br>Sunflower CD: "+sunflowerCD+"<br> Peashooter CD: "+peashooterCD+"</html>");
		for (int i = 0; i < currentMap.getRows(); i++)
		{
			for (int j = 0; j < currentMap.getColumns(); j++)
			{
				buttonMap[i][j].setIcon(getSprite(currentMap.getEntity(i, j).getDisplay()));
			}
		}
	}
	//Displays a string for the Game Class
	public void showMessage(String message)
	{
		JOptionPane.showMessageDialog(this, message);
	}
	//Animation/Sprite related methods
	private ImageIcon getSprite(String identifier) {
		//This swaps the image, currently setup to only work with the tile files
		ImageIcon retVal = new ImageIcon("./res/tile" + identifier + ".png");
		return retVal;
	}
	//This method makes a new thread to run the animation without freezing gui on pause
	public void doAnimation(Entity target, String[] identifiers)
	{
		//identifiers are the little strings after tile used in the file architecture
		Thread r = new Thread() {
			public void run()
			{
				//gets the original icon for switching back to
				Icon original = getSprite(identifiers[0]);
				//uses a for loop to go through each image and pauses for 1/4th second on it (AKA. 4fps)
				for (int i = 1; i < identifiers.length; i++)
				{
					try {
						TimeUnit.MILLISECONDS.sleep(250);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					buttonMap[target.getRow()][target.getColumn()].setIcon(getSprite(identifiers[i]));
				}
				//Waits after before switching back to original
				try {
					TimeUnit.MILLISECONDS.sleep(250);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				buttonMap[target.getRow()][target.getColumn()].setIcon(original);
				//Animation bugs made us add this last part to ensure the map doesnt have a lingering dead zombie
				//without updating the entire board again
				try {
					TimeUnit.MILLISECONDS.sleep(700);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				if (target.getHealth() <= 0)
				{
					buttonMap[target.getRow()][target.getColumn()].setIcon(getSprite(currentMap.getEntity(target.getRow(), target.getColumn()).getDisplay()));
				}
			}
		};
		r.start();
	}

	//Allows the GameClass to change the currentMap
	public void setCurrentMap(TileMap currentMap) {
		this.currentMap = currentMap;
	}
	//used for getting state from game class
	public State getGUIState() {
		return state;
	}
	//used for setting state from game class
	public void setGUIState(int state) {
		this.state = State.values()[state];
	}

}
