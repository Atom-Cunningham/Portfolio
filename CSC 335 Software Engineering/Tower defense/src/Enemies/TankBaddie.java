package Enemies;

public class TankBaddie extends GenericBaddie {
	
	public static final int TANK_BADDIE_SPEED = 1;
	public static final int TANK_BADDIE_HEALTH = 25;
	public static final int TANK_BADDIE_MONEY = 50;
	
	public TankBaddie(int x, int y) {
		super(x, y, TANK_BADDIE_SPEED, TANK_BADDIE_HEALTH, TANK_BADDIE_MONEY);
	}

}
