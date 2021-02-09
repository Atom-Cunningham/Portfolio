import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;


public class NetworkSetup extends Stage{

	private GridPane main;
	private ToggleGroup firstGroup = new ToggleGroup();
	private ToggleGroup secondGroup = new ToggleGroup();
	private boolean serverType;//false is client, true is server
	private boolean human;// false is AI
	private String server;
	private int port;
	
	public NetworkSetup() {
		createSetup();
	}
	
	/**
	 * returns if there is a server active
	 * @author John
	 * @return bool, the type of server, whether it's a client or server
	 * @throws none
	 * @param none
	 */
	public boolean isServerType() {
		return serverType;
	}
	
	/**
	 * @author John
	 * @return boolean, whether a human or the computer is playing'
	 * @param none
	 * @throws none
	 */
	public boolean isHuman() {
		return human;
	}

	/** 
	 * @author John
	 * @param none
	 * @throws none
	 * @return String servertype
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @author John
	 * @throws none
	 * @param none
	 * @return Integer, the port number that the game is played on
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 
	 * Setups the network setup box for the user to interact with.
	 *@author John
	 @param none
	 @throws none
	 @return void 
	 */
	private void createSetup() {
		main = new GridPane();
		main.setVgap(20);
		main.setHgap(25);
		main.setPadding(new Insets(10));
		HBox first = new HBox(10);
		Label create = new Label("Create:");
		RadioButton server = new RadioButton("Server");
		RadioButton client = new RadioButton("Client");
		server.setToggleGroup(firstGroup);
		client.setToggleGroup(firstGroup);
		first.getChildren().add(create);
		first.getChildren().add(server);
		first.getChildren().add(client);
		HBox second = new HBox(10);
		Label playAs = new Label("Play as:");
		RadioButton human = new RadioButton("Human");
		RadioButton computer = new RadioButton("Computer");
		human.setToggleGroup(secondGroup);
		computer.setToggleGroup(secondGroup);
		second.getChildren().add(playAs);
		second.getChildren().add(human);
		second.getChildren().add(computer);
		HBox third = new HBox(10);
		Label severLabel = new Label("Server");
		TextField name = new TextField("localhost");
		Label port = new Label("Port");
		TextField number = new TextField("4000");
		third.getChildren().add(severLabel);
		third.getChildren().add(name);
		third.getChildren().add(port);
		third.getChildren().add(number);
		HBox last = new HBox(10);
		Button ok = new Button("Ok");
		ok.setOnMouseClicked(new MouseClicked("ok", this));
		Button cancel = new Button("Cancel");
		cancel.setOnMouseClicked(new MouseClicked("cancel", this));
		last.getChildren().add(ok);
		last.getChildren().add(cancel);
		main.add(first, 1, 1);
		main.add(second, 1, 2);
		main.add(third, 1, 3);
		main.add(last, 1, 4);
		Scene scene = new Scene(main, 450, 190);
		this.setScene(scene);
		this.setTitle("Network Setup");
		this.sizeToScene();
		this.setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
	}
	
	private class MouseClicked implements EventHandler<MouseEvent>{
		private String buttonType;
		private Stage current;
		
		/**
		 * @author John
		 * @param string 
		 * @param current
		 * @throws none
		 */
		public MouseClicked(String string, Stage current) {
			buttonType = string;
			this.current = current;
		}

		/**
		 * sets the mouseevent handler for menu buttons
		 * @author John
		 * @param MouseEvent
		 * @throws none
		 * @return void
		 */
		@Override
		public void handle(MouseEvent event) {
			if(buttonType.equals("ok")) {
				getInfo();
			}else {
				current.close();
			}
			event.consume();
			current.close();
		}

		/**
		 * sets up client and server using info from dialogue box
		 * @author John
		 * @param none
		 * @return void
		 * @throws none
		 */
		private void getInfo() {
			HBox third = (HBox)main.getChildren().get(2);
			serverType = ((RadioButton)firstGroup.getSelectedToggle()).getText().equals("Server");
			human = ((RadioButton)secondGroup.getSelectedToggle()).getText().equals("Human");
			server = ((TextField)third.getChildren().get(1)).getText();
			port = Integer.valueOf(((TextField)third.getChildren().get(3)).getText());

		}
		
	}

	
}
