import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class Connect4View extends Application implements Observer {

	private static final int ROWS = 6;
	private static final int COLUMNS = 7;
	private static final int GAP = 8;
	private static final int RADIUS = 20;
	private static final int WIDTH = 352;
	private static final int HEIGHT = 326;
	private static GridPane pane;
	private static BorderPane mainPane;
	private Scene scene;

	private Connect4Controller controller;
	private boolean turn;
	private NetworkSetup setup = new NetworkSetup();
	
	
	/** 
	 * @author Adam Cunningham, John Nichols
	 * @param arg0 the observable object notifying this
	 * @param moveMsg a serializable which contains piece val and location
	 * @return none
	 * @throws none
	 */
	@Override
	public void update(Observable arg0, Object moveMsg) {
		//gets the circle object from the scene
		//sets it to the colorType passed by the message
		if(pane.isDisable()) {
			pane.setDisable(false);
		}
		Connect4MoveMessage msg = (Connect4MoveMessage) moveMsg;
		int color = msg.getColor();

		if (color != 0){				//0 color means an update was forced, game was reset.
		Circle circle = getCircle(msg.getRow(), msg.getColumn());
		setColor(color, circle);
		checkGameOver();
		}
		if(turn) {
			pane.setDisable(true);
		}else {
			pane.setDisable(false);
		}
		turn = !controller.getTurn();

	
	}

	/**
	 * check to see if a modal win/lose/draw message needs to be applied.
	 * if end of game, disables onclick event
	 * @author Adam Cunningham
	 * @param none
	 * @return void
	 * @throws none
	 */
	private void checkGameOver(){
		if(controller.endOfGame()){
			pane.setDisable(true);
			String title = "Message";
			String text = "";
			if(controller.boardIsFull() && controller.getWinner() == 0){
				text = "It's a draw";
			}else{
				if (turn){
					text = "You won!";
				}else{
					text = "You lost.";
				}
			}
			popup(title, text);
		}
	}
	/**
	 * draws the javafx stage and sets onAction events
	 * @author John Nichols
	 * @param stage javafx stage
	 * @return none
	 * @throws none
	 */
	@Override
	public void start(Stage stage) {
		mainPane = new BorderPane();
		pane = new GridPane();
		pane.setHgap(GAP);
		pane.setVgap(GAP);
		pane.setPadding(new Insets(4));
		pane.setStyle("-fx-background-color: blue");
		EventHandler<MouseEvent> event = new MouseClicked();
		pane.setOnMouseClicked(event);
		makeCircles();
		scene = new Scene(mainPane, WIDTH, HEIGHT);
		makeMenu(mainPane);
		mainPane.setCenter(pane);
		stage.setTitle("Connect 4");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.setResizable(false);
		pane.setDisable(true);
		stage.show();
	}

	/**
	 * sets all game data to a beginning state
	 * @author John Nichols
	 * @param none
	 * @return void
	 * @throws none
	 */
	private void startGame() {
		Connect4Model model = new Connect4Model();
		model.addObserver(this);
		controller = new Connect4Controller(model, false);//false is computer
		controller = new Connect4Controller(model, false);
		makeCircles();
	}

	/**
	 * gets server information from the user and
	 * begins the process of setting up the server client connection
	 * @author John Nichols
	 * @param none
	 * @return void
	 * @throws none
	 */
	private void newGame() {
		pane.setDisable(false);
		setup.showAndWait();
		startGame();
		AbstractSocket socket = null;
		try {
			if(setup.isServerType()) {
				socket = new Server(controller, setup.getPort(), setup.isHuman());
				turn = true;
			}else {
				socket = new Client(controller, setup.getPort(), setup.getServer(), setup.isHuman());
				turn = false;
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		controller.recieveSocket(socket);
		controller.setOpponent(setup.isHuman()); 	
	}

	
	/** 
	 * creates the drop down menu items
	 * @author Adam Cunningham, John Nichols
	 * @param mainPane a javafx BorderPane object
	 * @return void
	 * @throws none
	 */
	private void makeMenu(BorderPane mainPane) {
		Menu menu = new Menu("File");
		MenuItem newGame = new MenuItem("New Game");
		newGame.setOnAction((e) -> {
			newGame();
		});

		MenuBar bar = new MenuBar();
		menu.getItems().add(newGame);
		//menu.getItems().add(AIgame);
		bar.getMenus().add(menu);
		mainPane.setTop(bar);
	}

	/**
	 * Draws circles on the main pane, the colors are by default white
	 * @author Adam Cunningham, John Nichols
	 * @param none
	 * @return void
	 * @throws none
	 */
	public void makeCircles() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				Circle c = new Circle();
				int colorType = 0;//controller.getCircleVal(row, col);
				setColor(colorType, c);
				c.setRadius(RADIUS);
				StackPane temp = new StackPane(c);
				temp.setId("" + row + "" + col); // sets the pane id to "rowcol"
				pane.add(temp, col, row);
			}

		}
	}

	
	/** 
	 * sets the color of a given circle based on the colorType.
	 * colorType is based on a player identifier
	 * @author Adam Cunningham
	 * @param colorType an int, which is interpreted as a color
	 * @param circle javafx Circle which may have its color changed
	 * @return void
	 * @throws none
	 */
	private void setColor(int colorType, Circle circle){
		Color color = Color.WHITE; // default init
			switch (colorType) {
			case 0:
				color = Color.WHITE; // no piece
				break;
			case 1:
				color = Color.YELLOW; // player one
				break;
			case 2:
				color = Color.RED; // player two
			}
			circle.setFill(color);
	}

	
	/**
	 * searches through the gridpane for a stackpane with the given coordinates
	 * pulls the circle out of the stackpane and returns it
	 * @author Adam Cunningham 
	 * @param row the x coord (in the javafx graph)
	 * @param col the y coord (in the javafx graph)
	 * @return Circle javafx Circle object
	 */
	private Circle getCircle(int row, int col) {
		Node circle = null;
		for (Node node : pane.getChildren()) {	//these nodes are stackpanes
			if (pane.getRowIndex(node) == row
			&&	pane.getColumnIndex(node) == col){
				StackPane sp = (StackPane) node;
				circle = sp.getChildren().get(0);//pull out the circle
			}
		}
		return (Circle) circle;
	}

	
	/** 
	 * creates a modal Alert message
	 * @author Adam Cunningham
	 * @param title the string displayed at the header bar of the message
	 * @param text the message displayed for the user
	 */
	public void popup(String title, String text) {
		//a Modal application popup that informs the user of the end of game state
		//or displays an error message for invalid input
		Alert alert = new Alert(AlertType.CONFIRMATION);
		if (title.equals("Error")){
			alert = new Alert(AlertType.ERROR);
		}
		alert.setTitle(title);
		alert.setHeaderText(title);
		alert.setContentText(text);
		alert.showAndWait();
	}
	
	
	private class MouseClicked implements EventHandler<MouseEvent>{
		@Override
		/**
	 	* defines the behavior for mouseclick, used on the main Gridpane
	 	* @author Adam Cunningham, John Nichols
		* @param MouseEvent event
		* @return void
		* @throws none
	 	*/
		public void handle(MouseEvent event) {
			if(controller == null) {
				event.consume();
				return;
			}
			double xPos = event.getX();	//xPos is a double between 0 and WIDTH
			int col = (int) ((xPos/WIDTH) *7);
			if (controller.colIsFull(col)){
				popup("Error", "Column full, pick somewhere else!");
			}else{
				controller.updateServer(col);
			}
			event.consume();
		
		}
	}
}