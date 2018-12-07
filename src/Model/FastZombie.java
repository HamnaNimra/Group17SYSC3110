package Model;

public class FastZombie extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//constructor
	public FastZombie()
	{
		//Fast Zombie stats:
		//HP: 10
		//Defense: 1.2
		//Attack Damage: 3
		//Attack Range: 1 tiles across, 0 above or below
		//Value: 30 sun
		super(10f,1.2f,3f,1,0,30);
		setType(102);
	}
	@Override
	public String toString() {
		return "Fast Zombie \nhealth=" + getHealth() + "\ndefense=" + getDefense() + "\nattackDamage=" + getAttackDamage() 		
		+ "\nrow=" + getRow() + "\ncolumn=" + getColumn() ;

	}
	@Override
	public Entity clone()
	{
		FastZombie retVal = new FastZombie();
		retVal.setHealth(this.getHealth());
		retVal.setColumn(this.getColumn());
		retVal.setRow(this.getRow());
		return retVal;
	}
	@Override
	public String getDisplay()
	{
		String retVal = "FZ";
		return retVal;
	}
	//Zombie specific move forward or attack method
	public int makemove(Entity spaceAhead, Entity spaceTwoAhead)
	{
		int retVal = 0;
		if (spaceAhead.getType() == 100 && spaceTwoAhead.getType() == 100)
		{
			retVal = 2;
		}
		else if (spaceAhead.getType() < 100)
		{
			Attack(spaceAhead);
			if (spaceAhead.getHealth() <= 0)
			{
				retVal = 1;
			}
		}
		else if (spaceAhead.getType() == 100 && spaceTwoAhead.getType() < 100)
		{
			Attack(spaceTwoAhead);
			if (spaceTwoAhead.getHealth() <= 0)
			{
				retVal = 2;
			}
			else
			{
				retVal = 1;
			}
		}
		else if (spaceAhead.getType() > 100 && spaceTwoAhead.getType() == 100)
		{
			retVal = 2;
		}
		else if (spaceTwoAhead.getType() != 100 && spaceAhead.getType() == 100)
		{
			retVal = 1;
		}
		return retVal;
	}
}
