package Enemies;

public class EasyBaddie extends GenericBaddie {
	
	public static final int EASY_BADDIE_SPEED = 2;
	public static final int EASY_BADDIE_HEALTH = 5;
	public static final int EASY_BADDIE_MONEY = 10;	

	public EasyBaddie(int xPos, int yPos) {
		super(xPos, yPos, EASY_BADDIE_SPEED, EASY_BADDIE_HEALTH, EASY_BADDIE_MONEY);
	}

}
