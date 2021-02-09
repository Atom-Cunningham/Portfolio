package Towers;

public class GodTower extends AbstractTower {
	
	public static final int GOD_TOWER_ID = 6;
	public static final int GOD_TOWER_SPEED = 4;
	public static final int GOD_TOWER_DAMAGE = 4;
	public static final int GOD_TOWER_RADIUS = 4;
	public static final int GOD_TOWER_COST = 1500;

	public GodTower(int x, int y) {
		super(x, y, GOD_TOWER_ID, GOD_TOWER_SPEED, GOD_TOWER_DAMAGE, GOD_TOWER_RADIUS, GOD_TOWER_COST);
	}

}
