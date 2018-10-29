/*
README.txt
Version 1.0
*initial discussion*
Version 1.0.1
*I added this  top part*
Version
1.1.1
*I added the maujority of the documentation for the game and renamed to README* 

README Author and all supporting diagrams author: Manel Oudjida

This is the overview of game mechanics. Feel free to add more just make sure you update the version like i did at the top.
All versions 1.x.y is for the first milestone. Increase Y if only minor changes were made, X for major.

*/

How To Play:
The game consists of two types of plants, the sunflower and the peashooter. The objective is to fend off hordes of zombies using your plants.

The SunFlower:
	HP: 5
	Defense: 1
	Attack Damage: 0
	Attack Range: 0 tiles across, 0 above or below
	Cost: 75 sun
	Cooldown to plant: 2 turns
The PeaShooter:
	HP: 10
	Defense: 1
	Attack Damage: 3.5
	Attack Range: 9 tiles across, 1 above or below
	Cost: 125 sun
	Cooldown to plant: 1 turn
The Zombie:
	Default plant stats:
	HP: 10
	Defense: 1.2
	Attack Damage: 2
	Attack Range: 1 tiles across, 0 above or below
	Value for killing: 15 sun
	
The way the game works is simple, you are given a few turns to prepare for the firt wave of zombies. During each turn you can execute the following commands:

help: displays this dialog
restart: resets the game to turn 0
end: finishes your turn
attack: attacks a target zombie (ex. attack plantRow plantColumn zombieRow zombieColumn)
plant: plants a plant (ex. plant [sunflower|peashooter] row column)
soak: makes all sunflowers collect sunlight
status: shows the status of any entity (ex. status row column)
show: clears the display and shows the map

After doing the end command all the zombies will try and move forward, attacking anything in their path. If a single zombie makes it to the end you lose.
If you successfully defeat all the zombies you will win! 





DEVELOPER LOG:

Original Overview:
	-Turned based
	-A number of turns between levels
	-Difficulty will rise as each level is completed
	-If they die, back to level 1

	Each Level
	-User is told what zombies will come
	-They have a starting amount of "sun"
	-Zombies make a move forward once per turn (distance based on type)
	-User has their turn where they can plant plants and/or upgrade plants
	-Plants have a cooldown before they can be planted again

Features implemented:
General Architecture By: Kyle Smith
-Controller: Game class (Kyle Smith)
-Model: Entity, PeaShooter, SunFlower, Zombie (All Hamna Nimra), and TileMap(Omar Azam) classes
-View: View class (Omar Azam)
-Commands: help, restart, end, attack, plant, soak, status, show

View Class:(Omar Azam)
	The View Class handles all input and output from the user, aswell as loading the visual	
	representation of the the Model, the TileMap. It interacts with the Game Class (Controller)
	using commands like showMessage for output and getInput for getting input. It also contains
	the method showMap, which renders a new map into the console, aswell as a small summary of 
	the Entities within.
	Road Map: This class will be completely reworked in milestone 2 and as such I didn't break down
	each method

TileMap/Entities Class:(Omar Azam / HamnaNimra)
	The TileMap class holds a 2d array of Entities called board[][]. The TileMap contains the 
	following methods for the game to use for updating / checking the status of the board;
	ResetBoard(): This method resets the board to a bunch of empty spaces.
	didWin(): This method checks if there are any zombies left, and returns True if there isn't
	isZombieWon(): This method is so the game class can check when the zombies have won.
	addEntity(type, row, column): this method works so the game class can add entities to the board
	removeEntity(row, column): this method is so the game class can remove the entity at the given 
	position and returns an integer representing the sun gained from the removal.
	endTurn(): calls the turnPass method on every entity, and does the logic for zombie movement/attack
	getEntity(row, column): this returns the entity at the specified position on the Board, for use by
	the game class
	Road Map: This class is currently pretty tightly coupled with the specific types of zombies, in the
	future I'd like to make sure that it will support any type of zombie.
	
Game Class:(Kyle Smith)
	Upon creating a new Game object it will prompt the user with a printout about the game then prompt the
	user to press enter to start. This calls the Start() method. The start method is esentially an infinite 
	loop that gets user input from the view, and parses the commands before executing the command.
	
	Road Map: The game class will likely change alot as we move to the next Milestone. In the future it 
	will use observors to more loosely couple it from the view and model.
	
	



