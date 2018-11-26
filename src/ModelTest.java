
import junit.framework.TestCase;
import org.junit.*;

import Model.Entity;
import Model.TileMap;


public class ModelTest extends TestCase {
private TileMap k;
    
    
	@Before
	public void setUp() throws Exception {
	    k = new TileMap(5,9);
	    
		
	}
	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testaddEntity() {
		k.addEntity(1, 0, 0);
		k.addEntity(101, 1, 1);
		
		//Shouldnt be able to add an entity on top of one
		assertEquals(false, k.addEntity(2, 0, 0));
		
		assertEquals(1, k.getEntity(0, 0).getType());
	}
	@Test
	public void testResetBoard()
	{
		k.addEntity(101, 0, 0);
		
		k.ResetBoard();
		
		assertEquals(100, k.getEntity(0, 0).getType());
	}
	public void testRemoveEntity()
	{
		k.addEntity(101, 0, 0);
		int expectedValue = k.getEntity(0, 0).getValue();
		
		assertEquals(expectedValue, k.removeEntity(0, 0, false));
		
	}
	@Test
	public void testgetTarget() {
		k.addEntity(1, 0, 0);
		k.addEntity(101, 0, 3);
		Entity target = k.getTarget(0, 0);
		assertTrue(target.getType() == 101 && target.getRow() == 0 && target.getColumn() == 3);

	}
	
	

	public static void main(String[] args) {
		junit.textui.TestRunner.run(ModelTest.class);
		
	}
}
