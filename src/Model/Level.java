package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {
	private int gracePeriod;

	private int enabledPlants; //1-5
	private int zombies;
	private int fastZombies;
	private int poleZombies;
	private int giantZombies;
	
	public Level(int GracePeriod, int EnabledPlants, int Zombies, int FastZombies, int PoleZombies, int GiantZombies) {
		gracePeriod = GracePeriod;
		enabledPlants = EnabledPlants;
		zombies = Zombies;
		fastZombies = FastZombies;
		poleZombies = PoleZombies;
		giantZombies = GiantZombies;
	}
	public Level(int difficulty)
	{
		Random rand = new Random();
		gracePeriod = 3;
		enabledPlants = 5;
		zombies = rand.nextInt(difficulty*3);
		fastZombies = rand.nextInt(difficulty*2);
		poleZombies = rand.nextInt(difficulty*2);
		giantZombies = rand.nextInt(difficulty);
	}
	public int[] getOptions()
	{
		return new int[] {gracePeriod, enabledPlants, zombies, fastZombies, poleZombies, giantZombies};
	}
	public Integer[] turn0()
	{
		List<Integer> retVal = new ArrayList<Integer>();
		
		for (int i = 1; i <= 5; i++)
		{
			if (enabledPlants >= i)
			{
				retVal.add(0);
			}
			else
			{
				retVal.add(1000);
			}
		}
		
		retVal.add(gracePeriod);
		retVal.add(zombies);
		retVal.add(fastZombies);
		retVal.add(poleZombies);
		retVal.add(giantZombies);
		
		Integer[] retArray = new Integer[retVal.size()];
		retArray = retVal.toArray(retArray);
		return retArray;
		
	}
	public boolean lastAdded()
	{
		boolean retVal = false;
		if (zombies <= 0 && fastZombies <= 0 && poleZombies <= 0 && giantZombies <= 0)
		{
			retVal= true;
		}
		return retVal;
	}
	public List<int[]> levelLogic(int turn)
	{
		Random rand = new Random();
		List<int[]> retVal = new ArrayList<int[]>();
		List<Integer> usedRows = new ArrayList<Integer>();
		if (turn > gracePeriod &! lastAdded())
		{
			if (zombies > 0)
			{
				if (rand.nextInt(2) == 1 && usedRows.size() <= 4)
				{
					int row = rand.nextInt(5);
					while (usedRows.contains(row))
					{
						row = rand.nextInt(5);
					}
					usedRows.add(row);
					retVal.add(new int[] {101, row, 8});
					zombies--;
				}
			}
			if (fastZombies > 0)
			{
				if (rand.nextInt(2) == 1 && usedRows.size() <= 4)
				{
					int row = rand.nextInt(5);
					while (usedRows.contains(row))
					{
						row = rand.nextInt(5);
					}
					usedRows.add(row);
					retVal.add(new int[] {102, row, 8});
					fastZombies--;
				}
			}
			if (poleZombies > 0)
			{
				if (rand.nextInt(2) == 1 && usedRows.size() <= 4)
				{
					int row = rand.nextInt(5);
					while (usedRows.contains(row))
					{
						row = rand.nextInt(5);
					}
					usedRows.add(row);
					retVal.add(new int[] {103, row, 8});
					poleZombies--;
				}
			}
			if (giantZombies > 0)
			{
				if (rand.nextInt(2) == 1 && usedRows.size() <= 4)
				{
					int row = rand.nextInt(5);
					while (usedRows.contains(row))
					{
						row = rand.nextInt(5);
					}
					usedRows.add(row);
					retVal.add(new int[] {104, row, 8});
					giantZombies--;
				}
			}
		}
		

		return retVal;
	}
	

}
