package Model;

public class PoleZombie extends Entity {
	boolean disarmed;
	//constructor
	public PoleZombie()
	{
		//Pole Zombie stats:
		//HP: 10
		//Defense: 1.2
		//Attack Damage: 5 (when not disarmed)
		//Attack Range: 1 tiles across, 0 above or below
		//Value: 30 sun
		super(10f,1.2f,5f,1,0,30);
		disarmed = false;
		setType(103);
	}
	@Override
	public String toString() {
		return "Pole Zombie \nhealth=" + getHealth() + "\ndefense=" + getDefense() + "\nattackDamage=" + getAttackDamage() 		
		+ "\nrow=" + getRow() + "\ncolumn=" + getColumn();

	}
	@Override
	public Entity clone()
	{
		PoleZombie retVal = new PoleZombie();
		retVal.setHealth(this.getHealth());
		retVal.disarmed = (this.disarmed);
		retVal.setAttackDamage(this.getAttackDamage());
		retVal.setColumn(this.getColumn());
		retVal.setRow(this.getRow());
		return retVal;
	}
	@Override
	public String getDisplay()
	{
		String retVal = "PZ";
		if (disarmed)
		{
			retVal = "DPZ"; 
		}
		return retVal;
	}
	//Zombie specific move forward or attack method
	public int makemove(Entity spaceAhead, Entity spaceTwoAhead)
	{
		int retVal = 0;
		if (disarmed)
		{
			if (spaceAhead.getType() == 100)
			{
				retVal = 1;
			}
			else if (spaceAhead.getType() < 100)
			{
				Attack(spaceAhead);
				if (spaceAhead.getHealth() <= 0)
				{
					retVal = 1;
				}
			}
		}
		else
		{
			if (spaceTwoAhead.getType() == 100 && spaceAhead.getType() < 100)
			{
				retVal = 2;
				disarmed = true;
				this.setAttackDamage(3);//Lowered attack without pole
			}
			else if (spaceAhead.getType() < 100)
			{
				Attack(spaceAhead);
				if (spaceAhead.getHealth() <= 0)
				{
					retVal = 1;
				}
			}
			else if (spaceAhead.getType() == 100)
			{
				retVal = 1;
			}
		}
		
		return retVal;
	}
}
