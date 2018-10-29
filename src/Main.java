import Controller.Game;
import Model.TileMap;
import View.View;

public class Main {

	//Plants vs Zombies, Turn-based, by group 17
	//Authors: Kyle, Hamna, Omar, Manel
	//Sysc 3110 L1
	public static void main(String[] args) {
		TileMap map = new TileMap(5,9);
		
		View view = new View(map);
		
		Game game = new Game(map, view);
		

	}

}
