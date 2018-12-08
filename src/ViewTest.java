import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.TileMap;
import View.View;
import View.View.State;

class ViewTest {
	private View v;
	private TileMap m;
	
	@BeforeEach
	void setUp() throws Exception {
		m = new TileMap(5,9);
		v = new View(m);
	}
	//Literally all my stuff is events, there is nothing I can test from here
	@Test
	void testGUISateChange() {
		v.setGUIState(2);
		
		assertEquals(State.plantingP, v.getGUIState());
	}
	

}
