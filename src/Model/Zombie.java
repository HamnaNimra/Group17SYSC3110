package Model;

//The PeaShooter Class
//Author: Hamna Nimra
public class Zombie extends Entity {
	//constructor
	public Zombie()
	{
		//Zombie stats:
		//HP: 10
		//Defense: 1.2
		//Attack Damage: 3
		//Attack Range: 1 tiles across, 0 above or below
		//Value: 15 sun
		super(10f,1.2f,3f,1,0,15);
		setType(101);
	}
	@Override
	public String toString() {
		return "Zombie \nhealth=" + getHealth() + "\ndefense=" + getDefense() + "\nattackDamage=" + getAttackDamage() 		
		+ "\nrow=" + getRow() + "\ncolumn=" + getColumn();

	}
	@Override
	public Entity clone()
	{
		Zombie retVal = new Zombie();
		retVal.setHealth(this.getHealth());
		retVal.setColumn(this.getColumn());
		retVal.setRow(this.getRow());
		return retVal;
	}
	@Override
	public String getDisplay()
	{
		String retVal = "Z";
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
