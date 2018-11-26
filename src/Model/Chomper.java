package Model;

public class Chomper extends Entity {
	Entity targetEaten;
	boolean eating;
	//Constructor
	public Chomper()
	{
		//Chomper stats:
		//HP: 10
		//Defense: 1
		//Attack Damage: 5
		//Attack Range: 1 tiles across, 0 above or below
		//Cost: 150 sun
		super(10f,1f,5f,1,0,150);
		targetEaten = null;
		eating = false;
		setType(5);
	}
	@Override
	public String toString() {
		String add = "";
		if (eating)
		{
			add = "\n\n Currently Eating: " + targetEaten.toString();
		}
		return "Chomper \nhealth=" + getHealth() + "\ndefense=" + getDefense() + "\nattackDamage=" + getAttackDamage() +"\neating=" + getEating()
		+ "\nrangeX=" + getRangeX() + "\nrangeY=" + getRangeY() + "\ntype=" + getType() + "\nrow=" + getRow() + "\ncolumn=" + getColumn() + add;
	}

	@Override
	public Entity clone()
	{
		Chomper retVal = new Chomper();
		retVal.targetEaten = targetEaten.clone();
		retVal.eating = getEating();
		retVal.setHealth(this.getHealth());
		retVal.setColumn(this.getColumn());
		retVal.setRow(this.getRow());
		return retVal;
	}
	@Override
	public String getDisplay()
	{
		String retVal = "R";
		if (eating)
		{
			retVal = "R1";
		}
		return retVal;
	}
	//Special turnPass to setSoaked
	@Override
	public void turnPass()
	{
		super.turnPass();
	}
	//This method is how different entities attack each other
	@Override
	public boolean Attack(Entity target)
	{
		boolean retValue = false;
		if (!eating)
		{
			retValue = true;
			targetEaten = target;
			setEating(true);
		}
		return retValue;
	}
	@Override
	public int special()
	{
		int retVal = 0;
		if (!hasAttacked() && eating)
		{
			float targetHP = targetEaten.getHealth();
			targetHP = targetHP - (getAttackDamage()/targetEaten.getDefense());
			targetEaten.setHealth(targetHP);
			if (targetHP <= 0)
			{
				retVal = targetEaten.getValue();
				setEating(false);
				targetEaten = null;
			}
			setAttacked(true);
		}
		return retVal;
	}
	public boolean getEating()
	{
		return eating;
	}
	public void setEating(boolean val)
	{
		eating = val;
	}
}
