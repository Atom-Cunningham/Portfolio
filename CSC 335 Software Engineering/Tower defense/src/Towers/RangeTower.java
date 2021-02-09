package Towers;

public class RangeTower extends AbstractTower {
	
	public static final int RANGE_TOWER_ID = 4;
	public static final int RANGE_TOWER_SPEED = 2;
	public static final int RANGE_TOWER_DAMAGE = 1;
	public static final int RANGE_TOWER_RADIUS = 3;
	public static final int RANGE_TOWER_COST = 250;
	
	public RangeTower (int x, int y) {
		super(x, y, RANGE_TOWER_ID, RANGE_TOWER_SPEED, RANGE_TOWER_DAMAGE, RANGE_TOWER_RADIUS, RANGE_TOWER_COST);
	}

}
