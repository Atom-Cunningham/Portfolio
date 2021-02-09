package Main;

import java.util.ArrayList;
import java.util.Observable;
import Enemies.GenericBaddie;

/**
 * stores various data structures like a TowerCollection
 */
//TODO implement a way to determine a towerObject's subtype
//and use that to determine where to place/get it from
//in order to generalize add and get
public class TowerDefModel extends Observable{
    private TowerCollection towers;
    private ArrayList<GenericBaddie> enemies;
    private Integer money;
    private int health = 100;


    public TowerDefModel(){
        this.towers = new TowerCollection();
        this.enemies = new ArrayList<GenericBaddie>();
    }

    /**
     * TODO allow for generic adding
     * @param o
     */
    public void add(TowerDefObject o){
        //if o is tower addTower((TowerModel) tower)
        addTower((TowerModel)o);
        setChanged();
    }

    /**
     * gets the towerCollection's list
     * @param none
     * @return A list of tower models
     */
    public ArrayList<TowerModel> getTowerList(){
        return towers.getTowerList();
    }

    /**
     * retrieves the tower with the given id
     * @param id an identifier for TowerDefObjects
     * @return TowerModel
     */
    public TowerModel getTower(int id){
        //see TODO at header
        return towers.get(id);
    }

    //TODO make private
    private void addTower(TowerModel tower){
        towers.add(tower);
    }

    
    public Integer getMoney() {
        return this.money;
        
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public int getCost(int id){
        return towers.getCost(id);
    }


    //enemy management below

    /**
     * adds an enemy to the model's list
     * @param enemy a GenericBaddie
     */
    public void addEnemy(GenericBaddie enemy){
        enemies.add(enemy);
    }

    /**
     * getter for enemies
     * @return arraylist of GenericBaddies
     */
    public ArrayList<GenericBaddie> getEnemyList(){
        return this.enemies;
    }

    
    
}
