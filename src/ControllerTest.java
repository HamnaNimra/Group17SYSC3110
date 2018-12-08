import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controller.Game;
import Model.TileMap;
import View.View;

class ControllerTest {
private Game g;
private TileMap map;
private View view;
	@BeforeEach
	void setUp() throws Exception {
		map = new TileMap(5,9);
		view = new View(map);
		g = new Game(map, view);
	}

	@Test
	void testSetup() {
		//Literally every method in my Controller is private.
		//so im just testing the setup
		assertTrue(g != null);
	}

}
