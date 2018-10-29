package Model;

//The Entity Class
//The parent for all children entities, this class is only instantiated for blank spaces
//all other Entities will be children of this class
//Author: Hamna Nimra
public class Entity {
	
	//Entity information
	private float health;
	private float defense;
	private float attackDamage;
	private boolean attacked;
	private int rangeX;
	private int rangeY;
	private int value;
	private int type;
	//Positional information
	private int column;
	private int row;
	
	//Constructor for most subclasses
	public Entity(float Health, float Defense, float AttackDamage, int RangeX,int RangeY, int Value)
	{
		setHealth(Health);
		setDefense(Defense);
		setAttackDamage(AttackDamage);
		setAttacked(false);
		setRangeX(RangeX);
		setRangeY(RangeY);
		setValue(Value);
		
	}
	//Constructor for EMPTY SPACES
	public Entity()
	{
		setType(100);
	}
	//To string method, in case the user calls status on an empty space
	@Override
	public String toString() {
		return "Blank Space";
	}
	//short to string method for the overview of whats on the board
	public String toStringShort()
	{
		return "";
	}
	//special move that will be overidden by children with special moves
	public int special()
	{
		return 0;
	}
	//This method is how different entities attack each other
	public boolean Attack(Entity target)
	{
		boolean retValue = false;
		if (isInRange(target) && !attacked)
		{
			retValue = true;
			float targetHP = target.getHealth();
			targetHP = targetHP - (this.attackDamage/target.getDefense());
			target.setHealth(targetHP);
			setAttacked(true);
		}
		return retValue;
	}
	//Checks if the target is within range of this entity
	public boolean isInRange(Entity target)
	{
		boolean retVal = false;
		
		if (checkX(target.getColumn()) && checkY(target.getRow()))
		{
			retVal = true;
		}
		
		return retVal;
	}
	//checks the range up and down the rows
	private boolean checkY(int targetRow) {
		boolean retVal = false;
		if (targetRow >= this.getRow())
		{
			if ((targetRow - this.getRow()) <= this.getRangeY())
			{
				retVal = true;
			}
		}
		else
		{
			if ((this.getRow() - targetRow) <= this.getRangeY())
			{
				retVal = true;
			}
		}
		return retVal;
	}
	//checks the range across the columns
	private boolean checkX(int targetColumn) {
		boolean retVal = false;
		if (targetColumn >= this.getColumn())
		{
			if((targetColumn - this.getColumn()) <= this.getRangeX())
			{
				retVal = true;
			}
		}
		else
		{
			if((this.getColumn() - targetColumn) <= this.getRangeX())
			{
				retVal = true;
			}
		}
		return retVal;
	}
	//what happens when the turnPasses, children will override this and call super.turnpass
	//then they can also do anything they need to do  I.E sunflower soak reset
	public void turnPass()
	{
		setAttacked(false);
	}
	//Getters---Setters
	public char getDisplay()
	{
		char retVal = ' ';
		return retVal;
	}
	public float getHealth() {
		return health;
	}
	public void setHealth(float health) {
			this.health = health;	
	}
	public float getDefense() {
		return defense;
	}
	public void setDefense(float defense) {
		this.defense = defense;
	}
	public float getAttackDamage() {
		return attackDamage;
	}
	public void setAttackDamage(float attackDamage) {
		this.attackDamage = attackDamage;
	}
	public boolean hasAttacked() {
		return attacked;
	}
	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}
	public int getRangeX() {
		return rangeX;
	}
	public void setRangeX(int range) {
		this.rangeX = range;
	}
	public int getRangeY() {
		return rangeY;
	}
	public void setRangeY(int rangeY) {
		this.rangeY = rangeY;
	}


	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}	
	
	

}
