package Towers;

import java.util.LinkedList;
import Enemies.GenericBaddie;

/**
 * The parent class for all towers. Keeps basic info like speed, damage,
 * and field of view
 *
 */

public abstract class AbstractTower extends TowerModel{
	
	private int speed;
	private int damage;
	private double fieldOfViewRadius;
	private int currentTick;
	private LinkedList<GenericBaddie> queueOfEnemies;//The enemies that are in its field of view
	
	public AbstractTower(int x, int y, int id, int speed, int damage, double radius, int cost) {
		super(x, y, cost);
		this.speed = speed;//how fast the tower can shoot
		this.damage = damage;//how much damage it gives out per shoot
		this.fieldOfViewRadius = radius;// radius of its field of view
		queueOfEnemies = new LinkedList<>();
		currentTick = 0;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getDamage() {
		return damage;
	}
	
	/**
	 * Deals damage every tick to the first enemy in queue.
	 * 
	 */
//	public boolean attack() {
//		if(currentTick == speed) {
//			GenericBaddie baddie = queueOfEnemies.getFirst();
//			baddie.dealtDamage(damage);
//			if(!baddie.isAlive())
//				queueOfEnemies.removeFirst();
//			currentTick++;
//		}else {
//			currentTick = 0;
//		}	
//	}
	
	public double getRadiusOfDamage() {
		return fieldOfViewRadius;
	}
	
	/**
	 * Adds the newest enemy to the last of the list.
	 * 
	 * @param baddie, the newest enemy (GenericBaddie)
	 */
	public void newEnemyAppears(GenericBaddie baddie) {
		queueOfEnemies.addLast(baddie);
	}
	
	

}
