/*
README.txt
Version 1.0
*initial discussion*
Version 1.0.1
*I added this  top part*
Version
1.1.1
*I added the maujority of the documentation for the game and renamed to README* 
Version 2.1.1
Edited how the game works
Version 3.1.1
Added 5 levels, added undo/redo, added CherryBomb, WallNut, Chomper, Fast Zombie, Pole zombie, and giant Zombie entities.
Version 3.1.2
We forgot to add the descriptions of the plants and zombies
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
The CherryBomb:
	HP: 3
	Defense: 1
	Attack Damage: 100
	Attack Range: 0 tiles across, 0 above or below
	Cost: 150 sun
	Special: Explodes for massive damage in a 4 * 3 rectangle centered around it
The WallNut:
	HP: 15
	Defense: 2
	Attack Damage: 0
	Attack Range: 0 tiles across, 0 above or below
	Cost: 50 sun
The Chomper:
	HP: 10
	Defense: 1
	Attack Damage: 5
	Attack Range: 1 tiles across, 0 above or below
	Cost: 150 sun
	Special: Can eat a zombie and chew on it till it dies
The Zombie:
	Default plant stats:
	HP: 10
	Defense: 1.2
	Attack Damage: 2
	Attack Range: 1 tiles across, 0 above or below
	Value for killing: 15 sun
The Fast Zombie:
	HP: 10
	Defense: 1.2
	Attack Damage: 3
	Attack Range: 1 tiles across, 0 above or below
	Value: 30 sun
The Pole Zombie:
	HP: 10
	Defense: 1.2
	Attack Damage: 5 (when not disarmed)
	Attack Range: 1 tiles across, 0 above or below
	Value: 30 sun
The Giant Zombie:
	HP: 100
	Defense: 1
	Attack Damage: 10
	Attack Range: 1 tiles across, 0 above or below
	Value: 100 sun

	
The way the game works is simple, you are given a few turns to prepare for the firt wave of zombies. During each turn you can use the hotbar at the top
to plant either a sunflower or peashooter, end your turn, or restart entirely. To plant, you first click the picture of the plant in the hotbar, this will
bring you into planting mode, which disables any spots with plants already on it. You must then click a spot where you want to plant it which will plant it
and take you out of planting mode. See the above reference for plant cost. You can also view the status of any entity by clicking it on the board.

After ending your turn, all your plants will attack and soak up sun while all the zombies will try and move forward, attacking anything in their path. If a single zombie makes it to the end you lose.
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
-View: View class AS OF V2.1.1 it is a javax.swing gui (Omar Azam)


View Class:(Omar Azam)
	The View Class is now a Java Swing GUI. The GUI uses a state enum to determine when the user is planting
	or just viewing. The view has a couple of methods so that the Game class can attach its listeners to the 
	different button presses. Other than that, all the view class does is update itself to show the correct info
	and modify the pictures on the buttons to make animations.

TileMap/Entities Class:(Omar Azam / HamnaNimra)
	The TileMap class holds a 2d array of Entities called board[][]. The TileMap contains the 
	following methods for the game to use for updating / checking the status of the board;
	ResetBoard(): This method resets the board to a bunch of empty spaces.
	didWin(): This method checks if there are any zombies left, and returns True if there isn't
	addEntity(type, row, column): this method works so the game class can add entities to the board
	removeEntity(row, column): this method is so the game class can remove the entity at the given 
	position and returns an integer representing the sun gained from the removal.
	getTarget(int X, int Y): this method is used to find the first target of an entity, and returns that
	target.
	Road Map: This class is in a good point now, even the method to find a target seemingly works for all
	entities. In the future, it may be modified slightly to also execute the attack.
Game Class:(Kyle Smith)
	Upon creating a new Game object it will prompt the user with a printout about the game then prompt the
	user to press enter to start. This calls the Start() method. The start method is esentially an infinite 
	loop that gets user input from the view, and parses the commands before executing the command.
	Road Map: The game class currently contains some of the more important variables like Sun, Turn and cooldowns,
	these may get migrated over to the Model class to more closely embody an MVC setup.
	
	

Design decision explanations:
	Following typical MVC setup, we have a Main class that inits the Model(tileMap), View(View) and Controller(Game).

	The Model class (tileMap) has little to no idea what goes on outside it. However, it provides a useful method for finding
	an Entities target. Having full access to the board, finding a target is most easily done here, otherwise doing 
	it in the game class might be easier. We made a method ResetBoard() to easily set every piece of the board to
	an empty space. The method addEntity requires knowledge of the different types of entities, requiring a field 
	called type. Type is setup in the entities so that all plants are below 100, all zombies are above 100 and an
	empty space is 100. This is so we can easily seperate our ifs as < 100 and > 100 to know if its a zombie or plant.
	in addEntity, this type is required. As such, the game class has full knowledge of this field, and uses it for 
	a multitude of things, not just adding entities. Likewise, the View method only knows that type 100 is an empty box.
	The view class uses this to quickly disable occupied boxes when the user is attempting to plant. Removing an entity
	returns the value of that entity for removing it. Plants return 15% of their cost, while zombies just return their value.
	
	The View class extends JFrame as it is the swing based GUI. It contains the Model class so it can easily display it's
	contents. We decided to use a hotbar type array of buttons at the top for executing commands, and a gridLayout 2d array 
	of buttons for representing the tileMap. The hotbar of buttons lets us show a picture, unlike our original idea to use
	a file menu. The gridlayout of buttons works perfect for representing the tileMap and is easy to use so we stuck with that.
	Internal to view, is a GUIState. This state represents whether the user is trying to plant something or not. We decided to
	use this to not only notify the game class what the user is trying to do when they click on the grid, but also to decern 
	when to disable / enable buttons on the grid. Seeing as its not an actual state in the game, it mkaes sense for it to be 
	in the view and not the game class.

	The Controller class (Game) has almost complete knowledge of everything. The tileMap, the View, the Entities and even the
	names of the files in the res folder. It contains a few of the fields that make the game work like currency, turn, level,
	and cooldowns. It creates 3 types of listeners to which it attaches to the View class to run. The first
	type is the buttonListener. This is the general listener for each button on the grid. It checks the state of the view, and uses
	the position of the button to make changes to the Model and Game variables like Sun and planting cooldowns. The EndTurn button
	activates the EndTurn method in this class which we decided will do most of the work for the game. Basically, it attacks and 
	soaks for every entity and adjusts Sun accordingly. Lastly, is the restart button. This doesn't do much but reset turn and 
	apply level logic. We also decided that all the logic for the levels will be handled here, as it doesn't make sense for that
	stuff to be stored in the tileMap.

Design Changes in Milestone 3 explanations:
	Overall design has not changed much from the last milestone however, we chose to seperate the endTurn function into smaller methods
	for general clarity and readability. The Undo and Redo functions save state immediately before an action is executed. This is so the undo
	doesn't load up the state youre currently in when undo is pressed. levelLogic in the game class was hand written so as to keep balance in check
	while a new level generation technique is being developed. We chose to leave it manual for now mainly because it is going to be completely reworked
	in the next milestone anyways. The View class was slightly modified to allow the updating of just the buttons (and not the info label) to be done 
	privately for the use of the animation method. The animation runs on a seperate, delayed thread, and as such would often not display the correct 
	sprite after an animation happened. This work around to update all the buttons seems to work as intended for fixing the sprite desync. Lastly,
	the TileMap and Entity classes all got some nifty methods for clonely, namely, Entity.clone() and TileMap.cloneBoard(). These were made in order to
	ensure that saved states weren't saved references to the objects.


