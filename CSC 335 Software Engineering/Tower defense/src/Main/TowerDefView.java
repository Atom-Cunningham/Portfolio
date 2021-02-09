package Main;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import Towers.*;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Towers.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javafx.util.Duration;

/**
 *  
 * @authors Robbie Bagley, Stephen Duran, Adam Cunningham, Jon nichols
 * 
 * 
 */
public class TowerDefView extends Application implements Observer {
	
	private Canvas gameBoardCanvas;
	private Group root;
	private GraphicsContext graphics;
	private static final int CANVAS_WIDTH = 1_000;
	private static final int CANVAS_HEIGHT = 800; // underscore replaces comma for big numbers.
	private static final int TOWER_BAR_WIDTH = CANVAS_WIDTH;
	private static final int TOWER_BAR_HEIGHT = 110;
	private static final int STAGE_WIDTH = CANVAS_WIDTH;
	private static final int STAGE_HEIGHT = CANVAS_HEIGHT - TOWER_BAR_HEIGHT;
	private static final int NUM_TOWER_BUTTONS = 6;
	private MenuItem newGame;
	private TowerDefController controller;
	private Image[] images;
	private Timeline timeLine;
	private List<Image> spriteImg; 
	private Random rnd = new Random(System.nanoTime());
	private Round round1;

	private Label currencyLabel;
	@Override 
	public void start(Stage stage) {
		MenuBar menuBar = new MenuBar();
		Menu file = new Menu("File");
		menuBar.getMenus().add(file);
		this.newGame = new MenuItem("New Game");
		file.getItems().add(this.newGame);  
		menuBar.getMenus().add(new Menu("\u23f8"));
		menuBar.getMenus().add(new Menu("\u25b6"));
		menuBar.getMenus().add(new Menu("\u23e9"));
		TowerDefModel model = new TowerDefModel();
		this.controller = new TowerDefController(model);
		stage.setTitle("Tower Defense");
		this.root = new Group();
		Scene scene = new Scene(this.root);
		stage.setScene(scene);
		
		this.gameBoardCanvas = new Canvas(TowerDefView.CANVAS_WIDTH,
				TowerDefView.CANVAS_HEIGHT); 
		this.gameBoardCanvas.setOnMouseMoved((event) -> {	
			System.out.println("Mouse x = "+event.getX()+" and y "+event.getY());
		});
		this.root.getChildren().add(gameBoardCanvas);
		this.root.getChildren().add(menuBar);
		
		
		this.graphics = this.gameBoardCanvas.getGraphicsContext2D();
		
		this.graphics.setFill(Color.RED);
		this.graphics.setStroke(Color.BLACK);
		this.graphics.setLineWidth(2);
		Font font = Font.font("Times New Roman", FontWeight.BOLD, 48);
		this.graphics.setFont(font);
		//this.graphics.setTextAlign(TextAlignment.CENTER); 
		this.graphics.fillText("Kingdom of Towers", 100, 80);
		this.graphics.strokeText("Kingdom of Towers", 100, 80);
				
		File gameBackground = new File("Resources/game_background.png");		
		Image imageBackground = new Image(gameBackground.toURI().toString(),
				TowerDefView.CANVAS_WIDTH, TowerDefView.CANVAS_HEIGHT, false, false);
		File towerBackground = new File("Resources/misc_tower.png");		
		Image towerImage = new Image(towerBackground.toURI().toString(),
				20, 20, false, false);
		this.graphics.drawImage(imageBackground, 0, 0);
		
		Image newImage = new Image("File:Resources/walking_boy_resized.png",false);
        final ImageView imageView = new ImageView(newImage);
        imageView.setViewport(new Rectangle2D(0, 0, 20, 30));

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(800),
                8, 4,
                0, 0,
                25, 45
        );
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        Path path = new Path();
	    path.getElements().add(new MoveTo(0, 437));
	    path.getElements().add(new LineTo(162, 437));
	    path.getElements().add(new LineTo(162, 202));
	    path.getElements().add(new LineTo(362, 202));
	    path.getElements().add(new LineTo(362, 524));
	    path.getElements().add(new LineTo(625, 524));
	    path.getElements().add(new LineTo(625, 360));
	    path.getElements().add(new LineTo(745, 360));
	    
	    PathTransition pathTransition = new PathTransition();
	    pathTransition.setDuration(Duration.millis(10000));
	    pathTransition.setNode(imageView); // Circle is built above
	    pathTransition.setPath(path);
	    pathTransition.play();
	    round1 = new Round(pathTransition, path, controller);
		this.root.getChildren().add(imageView);
		this.createTowerBar();
		this.createCurrencyBox(500);
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();		
	}
	
	private void createTowerBar() {
		fillImages();
		// These numbers are not set in stone.
		// The numbers I've created can be changed later if needed.
		// TODO change loop to add different types of towers
		int gap = TowerButton.GAP;
		int y = TowerDefView.STAGE_HEIGHT + gap;
		
		for (int i = 0; i < TowerDefView.NUM_TOWER_BUTTONS; i++) {
			int x = gap + (i * (TowerButton.WIDTH + gap));
			TowerButton towerButton = new TowerButton(x, y,
					String.format("Tower: %d", i), this.root, images[i], receiveRelatedTower(i), controller); 
			this.root.getChildren().add(towerButton);			
		}		
	}
	
	/**
	 * Gives tower instance to be used in the TowerButton private class
	 * 
	 * @return, instance of a tower class
	 */
	private AbstractTower receiveRelatedTower(int i) {
		switch (i) {
		case 0:
			return new BasicTower(0,0);
		case 1:
			return new FireTower(0,0);
		case 2:
			return new IceTower(0,0);
		case 3:
			return new RangeTower(0,0);
		case 4:
			return new SpeedTower(0,0);
		case 5:
			return new GodTower(0,0);
		default:
			return null;
		}
	}
	public void fillImages() {
		File towerBackground = new File("Resources/misc_tower-512.png");//basic tower		
		Image imageBackground = new Image(towerBackground.toURI().toString(),
				20, 20, false, false);
		images = new Image[NUM_TOWER_BUTTONS];
		images[0] = imageBackground;
		towerBackground = new File("Resources/pixel_fire.png");//fire tower
		imageBackground = new Image(towerBackground.toURI().toString(),
				20, 20, false, false);
		images[1] = imageBackground;
		towerBackground = new File("Resources/pixel_lightening.png");//ice tower-
		imageBackground = new Image(towerBackground.toURI().toString(),
				20, 20, false, false);
		images[2] = imageBackground;
		towerBackground = new File("Resources/pixel_missile.png");//range tower
		imageBackground = new Image(towerBackground.toURI().toString(),
				20, 20, false, false);
		images[3] = imageBackground;
		towerBackground = new File("Resources/pixel_striker.png");//speed tower
		imageBackground = new Image(towerBackground.toURI().toString(),
				20, 20, false, false);
		images[4] = imageBackground;
		towerBackground = new File("Resources/pixel_cannon.png");//god tower
		imageBackground = new Image(towerBackground.toURI().toString(),
				20, 20, false, false);
		images[5] = imageBackground;
		
	}
	
	/**
	 * Takes in the current currency amount of the game, and shows in on the canvas.
	 * 
	 * @param currency, the amount of money the player has.
	 */
	private void createCurrencyBox(int currency) {
		this.graphics.setFill(Color.TRANSPARENT);
		Image coin = new Image(new File("Resources/coin.png").toURI().toString(), 25, 25, false, false);
		this.graphics.drawImage(coin, 625, CANVAS_HEIGHT-100);
		this.graphics.setFill(Color.BLACK);
		this.graphics.setFont(new Font(40));
		this.graphics.fillText(String.valueOf(currency),655,CANVAS_HEIGHT-60 );
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}