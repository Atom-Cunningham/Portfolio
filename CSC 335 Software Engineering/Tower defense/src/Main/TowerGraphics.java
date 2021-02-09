package Main;
import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class TowerGraphics extends Rectangle {
	
	
	public Color color = Color.RED;
	public static final int WIDTH = 80;
	public static final int HEIGHT = 80;
	public TowerModel model;
	private int x;
	private int y;
	
	public TowerGraphics(double x, double y,Image pic) {
		super(x, y, TowerGraphics.WIDTH, TowerGraphics.HEIGHT);
		this.setFill(new ImagePattern(pic));
		//this.setFill(color);
		this.model = new TowerModel((int) x, (int) y);
	}
	
	

}
