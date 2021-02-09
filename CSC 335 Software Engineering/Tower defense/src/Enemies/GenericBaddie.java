package Enemies;

import Towers.TowerDefObject;
import javafx.scene.shape.Path;

public abstract class GenericBaddie extends TowerDefObject {
	
	protected int health;
	protected int speed;
	protected int deathMoney;
	protected int xPos;
	protected int yPos;
	protected Path path;
	
	public GenericBaddie(int xPos, int yPos, int speed, int health, int deathMoney) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.speed = speed;
		this.health = health;
		this.deathMoney = deathMoney;
	}
	
	public void takeDamage(int damage) {
		this.health = this.health-damage;
	}
	
	public void changeSpeed(int newSpeed) {
		this.speed = newSpeed;
	}
	
	public boolean hasDied() {
		return this.health <= 0;
	}
	
	public int died() {
		if(this.hasDied()) {
			return this.deathMoney;
		}
		return 0;
	}
}
