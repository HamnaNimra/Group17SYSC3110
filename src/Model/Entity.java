package Model;

import java.io.Serializable;

//The Entity Class
//The parent for all children entities, this class is only instantiated for blank spaces
//all other Entities will be children of this class
//Author: Hamna Nimra
public class Entity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		attacked=true;
	}
	//Constructor for EMPTY SPACES
	public Entity()
	{
		setType(100);
	}
	//This method is used in order to clone entities since Java passes in references rather than a copy
	//This way my entities get saved properly from state to state
	public Entity clone()
	{
		Entity retVal = new Entity(this.getHealth(),this.getDefense(),this.getAttackDamage(),this.getRangeX(),this.getRangeY(),this.getValue());
		retVal.setType(this.getType());
		retVal.setColumn(this.getColumn());
		retVal.setRow(this.getRow());
		return retVal;
	}
	//To string method, in case the user calls status on an empty space
	@Override
	public String toString() {
		return "Blank Space";
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
		if (!attacked)
		{
			retValue = true;
			float targetHP = target.getHealth();
			targetHP = targetHP - (this.attackDamage/target.getDefense());
			target.setHealth(targetHP);
			setAttacked(true);
		}
		return retValue;
	}
	//what happens when the turnPasses, children will override this and call super.turnpass
	//then they can also do anything they need to do  I.E sunflower soak reset
	public void turnPass()
	{
		setAttacked(false);
	}
	//Getters---Setters
	public String getDisplay()
	{
		String retVal = " ";
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
