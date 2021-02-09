package Towers;

public class IceTower extends AbstractTower {

	private final static int ICE_TOWER_ID = 3;
	private final static int ICE_TOWER_SPEED = 2;
	private final static int ICE_TOWER_DAMAGE = 2;
	private final static int ICE_TOWER_RADIUS = 1;
	private final static int ICE_TOWER_COST = 200;
	
	public IceTower(int x, int y) {
		super(x, y, ICE_TOWER_ID, ICE_TOWER_SPEED, ICE_TOWER_DAMAGE, ICE_TOWER_RADIUS, ICE_TOWER_COST);
	}
}
