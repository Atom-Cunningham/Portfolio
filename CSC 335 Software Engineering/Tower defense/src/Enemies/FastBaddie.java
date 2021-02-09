package Enemies;

public class FastBaddie extends GenericBaddie {
	
	public static final int FAST_BADDIE_SPEED = 6;
	public static final int FAST_BADDIE_HEALTH = 10;
	public static final int FAST_BADDIE_MONEY = 20;
	
	public FastBaddie(int x, int y) {
		super(x, y, FAST_BADDIE_SPEED, FAST_BADDIE_HEALTH, FAST_BADDIE_MONEY);
	}

}
