package Model;
//The WallNut class
public class WallNut extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Constructor
	public WallNut()
	{
		//WallNut stats:
		//HP: 15
		//Defense: 2
		//Attack Damage: 0
		//Attack Range: 0 tiles across, 0 above or below
		//Cost: 50 sun
		super(15f,2f,0f,0,0,50);
		setType(4);
	}
	@Override
	public String toString() {
		return "WallNut \nhealth=" + getHealth() + "\ndefense=" + getDefense() + "\nattackDamage=" + getAttackDamage() + "\nrangeX=" + getRangeX() + "\nrangeY=" + getRangeY() + "\ntype=" + getType() + "\nrow=" + getRow() + "\ncolumn=" + getColumn();
	}
	@Override
	public Entity clone()
	{
		WallNut retVal = new WallNut();
		retVal.setHealth(this.getHealth());
		retVal.setColumn(this.getColumn());
		retVal.setRow(this.getRow());
		return retVal;
	}
	@Override
	public String getDisplay()
	{
		String retVal = "W";
		return retVal;
	}
	//Special turnPass to setSoaked
	@Override
	public void turnPass()
	{
		super.turnPass();
	}

}
