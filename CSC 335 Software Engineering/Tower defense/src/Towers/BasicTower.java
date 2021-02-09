package Towers;

public class BasicTower extends AbstractTower {
	
	public final static int BASIC_TOWER_SPEED = 1;
	public final static int BASIC_TOWER_DAMAGE = 1;
	public final static double BASIC_TOWER_RADIUS = 1.0;
	public final static int BASIC_TOWER_ID = 1;
	public final static int BASIC_TOWER_COST = 100;
	
	public BasicTower(int x, int y) {
		super(x, y, BASIC_TOWER_ID, BASIC_TOWER_SPEED, BASIC_TOWER_DAMAGE, BASIC_TOWER_RADIUS, BASIC_TOWER_COST);
	}

}
