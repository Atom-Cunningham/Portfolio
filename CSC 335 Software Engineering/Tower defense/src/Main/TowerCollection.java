package Main;
import java.util.ArrayList;

/**
 * a collection of towerModels
 */
//TODO considder storing a map as well (id:towerModel)
public class TowerCollection{
    private ArrayList<TowerModel> towerList;

    /**
     * a collection of towerModels
     */
    public TowerCollection(){
        this.towerList = new ArrayList<TowerModel>();
    }

    public void add(TowerModel tower){
        towerList.add(tower);
    }
    
    public ArrayList<TowerModel> getTowerList(){
        return towerList;
    }

    /**
     * retrieves the TowerModel with matching id
     * @param tower TowerModel
     * @return TowerModel with matching id, else null
     */
    public TowerModel get(int id){
        for(TowerModel tower : towerList){
            if(id == tower.getId()){
                return tower;
            }
        }
        return null;
    }

    public int getCost(int id){
        TowerModel tower = get(id);
        return tower.getCost();
    }
}