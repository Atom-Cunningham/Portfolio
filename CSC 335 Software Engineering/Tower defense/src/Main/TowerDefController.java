package Main;
import java.util.ArrayList;
import Enemies.GenericBaddie;

public class TowerDefController {
	
	private TowerDefModel model;	
	public TowerDefController(TowerDefModel model) {
		this.model = model;
	}

	//TODO towertype will need to be a parameter
	//to reflect game rules
	/**
	 * creates a tower, and adds it to the tower data.
	 * updates the player's money based on tower cost,
	 * calls setChanged in model.add,
	 * notifyAll()s observers
	 * @param x from gui
	 * @param y from gui
	 */
	public void buyTower(int x, int y){
		//to represent the towerType
		//and use an appropriate TowerModel subclass
		
		TowerModel tower = new TowerModel(x,y);
		model.add(tower);
		int id = tower.getId();
		model.setMoney(model.getMoney() - model.getCost(id));
		model.notifyAll();
	}

	//half of this function obsolete and maintain modularity
	/**
	 * determines if a tower can be placed in a location tried by the player
	 * compares to a list of towerGraphics objects.
	 * @param tower
	 * @return boolean true if tower can be placed, false if it overlaps another tower
	 */
	public boolean isValidTowerPlacement(TowerGraphics tower){
		//cycle through all existing towers
		ArrayList<TowerModel> towerList = model.getTowerList();
		int width = (int)tower.WIDTH;
		int height = (int)tower.HEIGHT;
		TowerModel towerToPlace = tower.model;
		for (TowerModel towerToCompare : towerList){
			//x is gt than start, or lt x + width
			if(((towerToPlace.getX() <= towerToCompare.getX()+width)
			|| (towerToPlace.getX() >= towerToCompare.getX())) &&
			//y is less than start, or gt len
				((towerToPlace.getY() <= towerToCompare.getY()+height)
			||	(towerToPlace.getY() >= towerToCompare.getY()))
			){
				return false;
			}
			return true;
		}
		return true; //there were no towers placed yet
	}
	
	/**
	 * calculates the distance between two objects using their x,y int coords.
	 * @param x1 the x Integer value of the first object
	 * @param x2 the x Integer value of the second object
	 * @param y1 the y value of the first object
	 * @param y2 the y value of the second object
	 * @return a double. the distance between the objects
	 */
	public Double distance(Integer x1, Integer x2, Integer y1, Integer y2){
		return Math.sqrt((x2 - x1)^2 + (y2 - y1)^2); 
	}

	/**
	 * determines whether or not an enemy is in range of a tower 
	 * @param tower a tower with a range
	 * @param enemy the enemy in question of being in range
	 * @return true if the enemy is in range, else false
	 */
	public boolean enemyInRange(TowerModel tower, GenericBaddie enemy){
		//TODO marry the x,y with tower range so this makes sense
		double distance = distance(tower.getX(), enemy.getX(),
								   tower.getY(), enemy.getY());
		return distance < tower.getRange();
	}

	/**
	 * generates a list of enemies in range of a specific tower
	 * @param tower a towerModel object
	 * @return a list of enemies which can be damaged by the tower
	 */
	public ArrayList<GenericBaddie> getEnemiesInRange(TowerModel tower){
		ArrayList<GenericBaddie> enemiesInRange = new ArrayList<GenericBaddie>();
		for (GenericBaddie enemy : model.getEnemyList()){
			if(enemyInRange(tower, enemy)){
				enemiesInRange.add(enemy);
			}
		}
		return enemiesInRange;
	}

	/**
	 * uses the list of active towers to deal damage to 
	 * enemies in range of the towers
	 * @param all if true, then all enemies in range of a tower
	 * recieve damage, else the first takes damage (false is undeveloped)
	 */
	public void dealDamageToEnemies(boolean all){
		for (TowerModel tower : model.getTowerList()){
			ArrayList<GenericBaddie> enemiesInRange = getEnemiesInRange(tower);
			
			if (all == false){//Deal damage to first enemy
				//Todo implement some meat in getFirstEnemy
				dealDamage(tower, getFirstEnemy(enemiesInRange));

			}else{			  //Deal damage to all enemies
				for (GenericBaddie enemy : enemiesInRange){
					dealDamage(tower, enemy);
				}
			}
		}
	}

	/**
	 * returns the first (pathwise) enemy in a list
	 * @param enemies an arrayList of genericBaddies
	 * @return the first enemy in the list
	 */
	public GenericBaddie getFirstEnemy(ArrayList<GenericBaddie> enemies){
		GenericBaddie first = null;
		for (GenericBaddie enemy : enemies){
			//TODO if enemy is before curr first
			//first = enemy
		}
		return first;
	}

	/**
	 * returns the enemy closest to the given tower using the distance formula
	 * @param tower TowerModel object
	 * @param enemies a list of GenericBaddies
	 * @return
	 */
	public GenericBaddie getClosestEnemy(
				TowerModel tower, ArrayList<GenericBaddie> enemies){
		//This is identical to getFirstEnemy, except
		//it deals with x,y instead of pathing issues
		GenericBaddie first = null;
		double currClosestDistance = Double.MAX_VALUE;
		for(GenericBaddie enemy : enemies){
			if (first.equals(null)){
				first = enemy;
			}
			double distance = distance(enemy.getX(), tower.getX(),
									   enemy.getY(), tower.getY());
			if(distance < currClosestDistance){
				first = enemy;
			}
		}
		return first;
	}

	/**
	 * uses the damageInformation from a towerModel
	 * to deal damage to an enemy
	 */
	public void dealDamage(TowerModel tower, GenericBaddie enemy){
		enemy.takeDamage(tower.getDamage());
	}
	
	public void tick() {
		//TODO check if enemies are dead
			//remove them from enemies list, reward player money
			//animate death in view
		//TODO update the x,y stored in alive enemies
		//TODO deal damage
	}
}
