package Model;

//The PeaShooter Class
//Author: Hamna Nimra
public class SunFlower extends Entity {
	//boolean for determining whether or not it already soaked
	private boolean soaked;
	//Constructor
	public SunFlower()
	{
		//Sunflower stats:
		//HP: 5
		//Defense: 1
		//Attack Damage: 0
		//Attack Range: 0 tiles across, 0 above or below
		//Cost: 75 sun
		super(5f,1f,0f,0,0,75);
		setType(2);
	}
	@Override
	public String toString() {
		return "SunFlower Plant [health=" + getHealth() + ", defense=" + getDefense() + ", attackDamage=" + getAttackDamage() + ", soaked="
				+ hasSoaked() + ", rangeX=" + getRangeX() + ", rangeY=" + getRangeY() + ", type=" + getType() + ", row=" + getRow() + ", column=" + getColumn() + "]";

	}
	@Override
	public String toStringShort() {
		return "SunFlower Plant [health=" + getHealth() + ", soaked="
				+ hasSoaked() + ", row=" + getRow() + ", column=" + getColumn() + "]";

	}

	@Override
	public char getDisplay()
	{
		char retVal = 'S';
		return retVal;
	}
	//Special turnPass to setSoaked
	@Override
	public void turnPass()
	{
		super.turnPass();
		setSoaked(false);
	}
	//Special move to soak
	@Override
	public int special()
	{
		int retVal = 0;
		if (!hasSoaked())
		{
			setSoaked(true);
			retVal = 50;
		}
		return retVal;
	}

	public boolean hasSoaked() {
		return soaked;
	}

	public void setSoaked(boolean soaked) {
		this.soaked = soaked;
	}
	
	
	

}
