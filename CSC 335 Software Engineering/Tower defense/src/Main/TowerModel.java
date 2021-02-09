package Main;

/**
 * a representation of a single tower.
 * extends towerDefObject
 */
public class TowerModel extends TowerDefObject{

	//TODO change default values to reflect game rules
	private int damage;
	private int health;
	private int cost = 100;
	private int maxHealth;
	private int range = 10;//defaults until we implement upgrades/

	
	public TowerModel(int x, int y) {
		this.setX(x);
		this.setY(y);
		//TODO make a more descriptive id
		//which could be a string with the first letter being t
		//or something
		this.setId(hashCode());
	}

	public int getDamage() {
		return this.damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getHealth() {
		return this.health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getCost() {
		return this.cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getMaxHealth() {
		return this.maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getRange() {
		return this.range;
	}

	public void setRange(int range) {
		this.range = range;
	}
}
