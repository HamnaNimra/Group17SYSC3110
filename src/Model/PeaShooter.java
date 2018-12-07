package Model;

//The PeaShooter Class
//Author: Hamna Nimra
public class PeaShooter extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		return "Pea Shooter Plant \nhealth=" + getHealth() + "\ndefense=" + getDefense() + "\nattackDamage=" + getAttackDamage() + "\nattacked="
				+ hasAttacked() + "\nrangeX=" + getRangeX() + "\nrangeY=" + getRangeY() + "\ntype=" + getType()
				+ "\nrow=" + getRow() + "\ncolumn=" + getColumn();
	}
	@Override
	public Entity clone()
	{
		PeaShooter retVal = new PeaShooter();
		retVal.setHealth(this.getHealth());
		retVal.setColumn(this.getColumn());
		retVal.setRow(this.getRow());
		return retVal;
	}
	@Override
	public String getDisplay()
	{
		String retVal = "P";
		return retVal;
	}

}
