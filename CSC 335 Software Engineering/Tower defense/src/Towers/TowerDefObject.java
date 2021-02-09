package Towers;
import java.util.Objects;

import javafx.scene.image.Image;

/**
 * A parent class which can be used for all
 * towers, enemies, ect.
 */
public abstract class TowerDefObject extends Object{
    protected int x;
    protected int y;
    protected int id;
    protected Image image;

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TowerDefObject() {
    }

    public TowerDefObject(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public TowerDefObject x(int x) {
        this.x = x;
        return this;
    }

    public TowerDefObject y(int y) {
        this.y = y;
        return this;
    }

    public TowerDefObject id(int id) {
        this.id = id;
        return this;
    }

    /**
     * TowerDefenseObjects are equal if
     * they have the same address in mem,
     * or they have the same x,y,id fields
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TowerDefObject)) {
            return false;
        }
        TowerDefObject towerDefObject = (TowerDefObject) o;
        return x == towerDefObject.x && y == towerDefObject.y && id == towerDefObject.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "{" +
            " x='" + getX() + "'" +
            ", y='" + getY() + "'" +
            ", id='" + getId() + "'" +
            "}";
    }

}