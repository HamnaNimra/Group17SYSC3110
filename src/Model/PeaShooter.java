package Model;

//The PeaShooter Class
//Author: Hamna Nimra
public class PeaShooter extends Entity {
	//Constructor
	public PeaShooter()
	{
		//Default plant stats:
		//HP: 10
		//Defense: 1
		//Attack Damage: 3.5
		//Attack Range: 9 tiles across, 1 above or below
		//Cost: 125 sun
		super(10f,1f,3.5f,9,1,125);
		setType(1);
	}
	@Override
	public String toString() {
		return "Pea Shooter Plant [health=" + getHealth() + ", defense=" + getDefense() + ", attackDamage=" + getAttackDamage() + ", attacked="
				+ hasAttacked() + ", rangeX=" + getRangeX() + ", rangeY=" + getRangeY() + ", type=" + getType()
				+ ", row=" + getRow() + ", column=" + getColumn() + "]";
	}
	@Override
	public String toStringShort() {
		return "Pea Shooter Plant [health=" + getHealth() + ", attacked="
				+ hasAttacked() 
				+ ", row=" + getRow() + ", column=" + getColumn() + "]";
	}
	

	@Override
	public String getDisplay()
	{
		String retVal = "P";
		return retVal;
	}

}
