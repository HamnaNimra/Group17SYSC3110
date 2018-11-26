package Model;

//The Cherry Bomb Class
public class CherryBomb extends Entity {
	//Constructor
	int countDown;
	public CherryBomb()
	{
		//CherryBomb stats:
		//HP: 3
		//Defense: 1
		//Attack Damage: 100
		//Attack Range: 0 tiles across, 0 above or below
		//Cost: 150 sun
		super(3f,1f,100f,0,0,150);
		countDown = 3;
		setType(3);
	}
	@Override
	public String toString() {
		return "CherryBomb \nhealth=" + getHealth() + "\ndefense=" + getDefense() + "\nattackDamage=" + getAttackDamage()+ "\ncountDown="
				+ getCountDown() + "\nrangeX=" + getRangeX() + "\nrangeY=" + getRangeY() + "\ntype=" + getType() + "\nrow=" + getRow() + "\ncolumn=" + getColumn();
	}

	@Override
	public Entity clone()
	{
		CherryBomb retVal = new CherryBomb();
		retVal.setHealth(this.getHealth());
		retVal.countDown = new Integer(countDown);
		retVal.setColumn(this.getColumn());
		retVal.setRow(this.getRow());
		return retVal;
	}
	public int getCountDown() {
		return countDown;
	}
	@Override
	public boolean Attack(Entity target)
	{
		boolean retValue = true;
		
		float targetHP = target.getHealth();
		targetHP = targetHP - (getAttackDamage()/target.getDefense());
		target.setHealth(targetHP);
	
		return retValue;
	}
	@Override
	public String getDisplay()
	{
		String retVal = "C";
	
		if (countDown == 2)
		{
			retVal = "C1";
		}
		else if (countDown == 1)
		{
			retVal = "C2";
		}
		else if (countDown == 0)
		{
			retVal = "C3";
		}
		
		return retVal;
	}
	//Special turnPass to reduce countDown
	@Override
	public void turnPass()
	{
		super.turnPass();
		countDown--;
	}


		
		
		
}
