package Towers;

public class FireTower extends AbstractTower{
	
	public final static int FIRE_TOWER_ID = 2;
	public final static int FIRE_TOWER_SPEED = 1;
	public final static int FIRE_TOWER_DAMAGE = 3;
	public final static int FIRE_TOWER_RADIUS = 1;
	public final static int FIRE_TOWER_COST = 175;

	public FireTower(int x, int y) {
		super(x, y, FIRE_TOWER_ID, FIRE_TOWER_SPEED, FIRE_TOWER_DAMAGE, FIRE_TOWER_RADIUS, FIRE_TOWER_COST);
	}

}
