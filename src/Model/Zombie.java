package Model;

//The PeaShooter Class
//Author: Hamna Nimra
public class Zombie extends Entity {
	//constructor
	public Zombie()
	{
		//Default plant stats:
		//HP: 10
		//Defense: 1.2
		//Attack Damage: 2
		//Attack Range: 1 tiles across, 0 above or below
		//Value: 15 sun
		super(10f,1.2f,2f,1,0,15);
		setType(101);
	}
	@Override
	public String toString() {
		return "Zombie [health=" + getHealth() + ", defense=" + getDefense() + ", attackDamage=" + getAttackDamage() 		
		+ ", row=" + getRow() + ", column=" + getColumn() + "]";

	}
	
	@Override
	public String toStringShort() {
		return "Zombie [health=" + getHealth() + ", attacked="
				+ hasAttacked()	
				+ ", row=" + getRow() + ", column=" + getColumn() + "]";

	}
	
	@Override
	public char getDisplay()
	{
		char retVal = 'Z';
		return retVal;
	}
	//Zombie specific move forward or attack method
	public boolean makemove(Entity spaceAhead)
	{
		boolean retVal = false;
		if (spaceAhead.getType() == 100)
		{
			retVal = true;
		}
		else if (spaceAhead.getType() < 100)
		{
			Attack(spaceAhead);
			if (spaceAhead.getHealth() <= 0)
			{
				retVal = true;
			}
		}
		return retVal;
	}
}
