package Main;

import Towers.AbstractTower;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class TowerButton extends Rectangle {
	private TowerDefController controller;
	private static final int HEIGHT = 90;
	public static final int WIDTH = 90;
	public static final int GAP = 10;
	private Color backgroundColor = Color.BLACK;
	private String name;
	private Group root;
	private TowerGraphics tower;
	private boolean dragInProgress;
	private Image picture;
	private AbstractTower relatedTower;
	private Circle radiusOfTower;
	public TowerButton(int x, int y, String name, Group root, Image pic, AbstractTower relatedTower, TowerDefController controller) { //maybe needs to take in a TowerClass later
		super(x, y, TowerButton.WIDTH, TowerButton.HEIGHT);
		this.root = root;
		picture = pic;
		this.setFill(new ImagePattern(pic));
		this.name = name;
		this.dragInProgress = false;
		this.setHandlers();
		this.relatedTower = relatedTower;
		this.controller = controller;
	}
	
	public void setHandlers() {
		this.setCursor(Cursor.HAND);
		
		this.setOnMousePressed((event) -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				System.out.println(this.name);
			}
			else if (event.getButton() == MouseButton.SECONDARY) {
				ContextMenu contextMenu = new ContextMenu();
				MenuItem item1 = new MenuItem("Sell tower");
				MenuItem item2 = new MenuItem("Delete tower");
				contextMenu.getItems().addAll(item1, item2);
				contextMenu.show(this, event.getSceneX(), event.getSceneY());
				System.out.println("This is a right click");
			}
		});
		
		this.setOnDragDetected((event) -> {
			System.out.println("Drag detected");
			// TODO: When you detect a drag your going to have to create a 
			// tower which has its own model, view, and controller maybe
			// TODO: Set variable in TowerButton to keep track of the tower
			// object.

			this.dragInProgress = true;
			this.tower = new TowerGraphics(event.getX(), event.getY(),this.picture);
			this.radiusOfTower = new Circle(event.getX(), event.getY(), relatedTower.getRadiusOfDamage() * 100, Color.GREY);
			this.radiusOfTower.setOpacity(0.5);
			this.root.getChildren().add(this.radiusOfTower);
			this.root.getChildren().add(this.tower);
		});
		
		// This event handler happens whenever a mouse is moved in a drag.
		// This will be used for dragging the picture of the tower onto the 
		// board
		this.setOnMouseDragged((event) -> {
			// TODO: Get the Tower object that was created in the dragDetected
			// handler.
			// TODO: Move that tower's location to the location of the mouse.
			//System.out.println("Drag initialized");
			if (this.dragInProgress) {
				this.tower.setX(event.getX()-40); // hard coded to center mouse on image
				this.tower.setY(event.getY()-40); // hard coded to center mouse on image
				this.radiusOfTower.setCenterX(event.getX());
				this.radiusOfTower.setCenterY(event.getY());
			}								
		});
		
		this.setOnMouseReleased((event) -> {
			if (this.dragInProgress) {
				if (true/* TODO: if current position is valid*/) {
					// TODO: Place a tower in the game board model
					// TODO: Take money out of bank/wallet
					System.out.println(String.format("Tower placed at: %.2f %.2f",
							event.getX(), event.getY()));		
					this.root.getChildren().remove(this.radiusOfTower);
				}
				else {
					this.root.getChildren().remove(this.tower);
					this.tower = null;
				}					
			}
			this.dragInProgress = false;
			System.out.println("Mouse released");
		});
	}		
}