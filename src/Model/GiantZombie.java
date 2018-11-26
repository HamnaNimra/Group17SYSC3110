package Model;

public class GiantZombie extends Entity {
	//constructor
		public GiantZombie()
		{
			//Giant Zombie stats:
			//HP: 100
			//Defense: 1
			//Attack Damage: 10
			//Attack Range: 1 tiles across, 0 above or below
			//Value: 100 sun
			super(100f,1f,10f,1,0,100);
			setType(104);
		}
		@Override
		public String toString() {
			return "Giant Zombie \nhealth=" + getHealth() + "\ndefense=" + getDefense() + "\nattackDamage=" + getAttackDamage() 		
			+ "\nrow=" + getRow() + "\ncolumn=" + getColumn();

		}
		@Override
		public Entity clone()
		{
			GiantZombie retVal = new GiantZombie();
			retVal.setHealth(this.getHealth());
			retVal.setColumn(this.getColumn());
			retVal.setRow(this.getRow());
			return retVal;
		}
		@Override
		public String getDisplay()
		{
			String retVal = "GZ";
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
