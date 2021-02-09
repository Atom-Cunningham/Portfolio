package Towers;

public class SpeedTower extends AbstractTower {

	public static final int SPEED_TOWER_ID = 5;
	public static final int SPEED_TOWER_SPEED = 3;
	public static final int SPEED_TOWER_DAMAGE = 1;
	public static final int SPEED_TOWER_RADIUS = 1;
	public static final int SPEED_TOWER_COST = 300;
	
	public SpeedTower(int x, int y) {
		super(x, y, SPEED_TOWER_ID, SPEED_TOWER_SPEED, SPEED_TOWER_DAMAGE, SPEED_TOWER_RADIUS, SPEED_TOWER_COST);
	}

}
