import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Observer;
/**
 * 
 * @author Adam
 * this class is a graphical user interface for the game Cryptograms;
 * It is an observer which updates when the model is modified
 * The gui can perform hints, show frequencies, and autofill boxes
 * 
 * it is called by main based on user input.
 *
 */

public class CryptogramGUIView extends Application implements Observer{
	public CryptogramController ctrl;
	public Integer LINELEN = 30;	//the maximum number of chars on a line
	public CheckBox check;			//a checkbox
	BorderPane window;				//the stage's main borpain
	
	@Override
	public void start(Stage PrimaryStage) throws Exception{
		ctrl = new MVC(this).getCtrl();					//instantiate controller and model
		LINELEN = 30;
		
		//init the main members of window
		PrimaryStage.setTitle("Cryptograms");
		window = new BorderPane();
		check = new CheckBox("Show Freq");
		setCheckOnAction();
		
		GridPane grid = updateGrid();
		BorderPane panel = updatePanel();
		
		BorderPane freq = updateFrequency();
		panel.setLeft(freq);

		//arrange members
		window.setCenter(grid);
		window.setRight(panel);
		window.setMargin(grid, new Insets(0,60,0,0));

		Scene scene = new Scene(window, 900, 400); // 300 pixels wide, 90 tall
		PrimaryStage.setScene(scene);
		//play
		PrimaryStage.show();
	}
	
	
	public BorderPane updateFrequency() {
		//creates new borderpane and checkbox
		//get alphabet and freqMap
		//parse through alphabet and create a label with newlines
		//put it left
				
		//parse through and create a label with newlines
		//rightbound
		//creates a new borderpane to be used in the mid right
		//below the buttons
		BorderPane frequency = new BorderPane();
		frequency.setTop(check);
		if (check.isSelected()) {
			HashMap<Character, Integer> freqMap = ctrl.getCountMap();
			ArrayList<Character> alphabet = ctrl.getAlphabet();
			
			String am = "";
			String oz = "";
			String str;              //construct a new string for use in the window
			int freq;
			int half = alphabet.size()/2;
			int i = 0;
			for (char c : alphabet) {
				freq = freqMap.get(c);
				str = "" + c + " " + freqMap.get(c) + "\n";
				if (i < half) {am += str;}
				else 		  {oz += str;}
				i++;
			}
			frequency.setLeft(new Label(am));//half on each col
			frequency.setRight(new Label(oz));
		}
		return frequency;
	}
	
	//updates the borderPane to display or remove frequency
	public void setCheckOnAction() {
		check.setOnAction((event) -> {
			BorderPane panel = updatePanel();
			BorderPane freq	= updateFrequency();
			panel.setLeft(freq);
			
			window.setRight(panel);
		});
	}
	
	public VBox getEncryptionGridVbox(char s) {
		//creates a Vbox with a textbox on top
		//and a char label below
		//it also sets the action event
		ListView list = new ListView();
		
		VBox box = new VBox();
		TextField field = getTextField();
		Label label = new Label(Character.toString(s));
		label.setAlignment(Pos.CENTER);			//formatting label
		
		field = setTextFieldOnAction(field, label);//set the action event
		
		box.setVgrow(list, Priority.ALWAYS);
		box.getChildren().add(field);			//add to vbox
		box.getChildren().add(label);
		
		return box;
	}
	
	public TextField getTextField() {
		TextField field = new TextField();
		field.setMaxWidth(22);					//formatting textbox\
		return field;
	}

	
	public TextField setTextFieldOnAction(TextField field, Label label) {
		//uses a lambda to set an action event
		//gets the first two characters from the text (just in case)
		//and sends that to the controller to update the model
		field.setOnKeyReleased((event) -> {
			String guess = field.getText();
			String encr = label.getText();
			char key = firstLetter(encr);
			char val = firstLetter(guess);
			ctrl.replace(key, val);
			field.clear();
			
		});
		return field;
	}
	
	public char firstLetter(String s) {
		//returns the first letter of a string
		char curr;
		for (int i = 0; i < s.length(); i++) {
			curr = s.charAt(i);
			if(Character.isAlphabetic(curr)){
				return curr;
			}
		}return '\0';		//empty box on non-chars
	}
	
	
	public Button getStartButton() {
		//creates a new start button
		//and defines it to reset the game via a new ctrl
		
		Button startButton = new Button("New Puzzle");
		startButton.setOnAction((event) -> {
			ctrl = new MVC(this).getCtrl();
			GridPane grid = updateGrid();
			window.setCenter(grid);
		});
		return startButton;
	}
	
	public Button getHintButton() {
		//creates the hint button, which
		//when pressed tells ctrl to update the model with a random guess
		//all guesses generated are valid on the board
		Button hintButton = new Button("Hint");
		hintButton.setOnAction((event) -> {
			if (!ctrl.isOver()) {ctrl.setNewHint();}
		});
		return hintButton;
	}

	@Override
	public void update(Observable observable, Object arg) {
		// updates when the model has changed
		//CryptogramModel game = (CryptogramModel) observable;
		//TODO win message
		GridPane grid= updateGrid();
		window.setCenter(grid);
		window.setMargin(grid, new Insets(0,60,0,0));
		
		if (ctrl.isOver()) {
			popup();
		}
		else {//get ctrl input and redraw
			BorderPane panel = updatePanel();
			BorderPane freq = updateFrequency();
			
			panel.setLeft(freq);
			window.setRight(panel);
		}
	}
	
	public BorderPane updatePanel() {
		//updates the right panel in the main window
		//does not update the frequency features
		BorderPane panel = new BorderPane();
		Button hint = getHintButton();
		Button start = getStartButton();
		
		BorderPane buttonPane = new BorderPane();
		buttonPane.setTop(start);
		buttonPane.setLeft(hint);
		
		panel.setTop(buttonPane);
		
		return panel;
	}
	
	public GridPane updateGrid() {
		//creates a new gridPane which is returned to update the window
		//the pane is populated with textboxes and labels together in Vboxes
		Integer rows = ctrl.getLineCount(LINELEN);
		ArrayList<char[]> lines =   ctrl.getEncryptedArray(LINELEN);
		HashMap<Character, Character> guessMap = ctrl.getGuessMap();
		GridPane grid = new GridPane();
		grid.setConstraints(new Label("A"), LINELEN, rows);
		
		
		VBox box;
		char[] line;
		char encry;
		char guess;
		Label label;
		TextField field;
		//populate grid with VBox
		for (int i = 0; i < rows; i++) {					//iterate over grid coords
			line = lines.get(i);
			for (int j = 0; j < line.length; j++) {
				encry = line[j];
				
				box = new VBox();
				guess = ' ';
				field = new TextField();
				if (Character.isAlphabetic(encry)) {		//if its alpha char use it in the text
					guess = guessMap.get(encry);
					field = new TextField(Character.toString(guess).toUpperCase());
				}else {
					field = new TextField(Character.toString(encry));//otherwise print and lock
					field.setDisable(true);
				}
				label = new Label(Character.toString(encry));//make an encrypted label
				field.setAlignment(Pos.TOP_CENTER);
				label.setMaxWidth(Integer.MAX_VALUE);
				label.setAlignment(Pos.CENTER);
				
				setTextFieldOnAction(field, label);			//ensure all the text fields talk to ctrl
				
				box.getChildren().add(field);
				box.getChildren().add(label);
				grid.add(box, j, i);						//add to the grid
			}
		}return grid;
	}
	public void popup() {
		//a Modal application popup that congradulates the user
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Message");
		alert.setHeaderText("Congradulations");
		alert.setContentText("You won!");
		
		alert.showAndWait();
	}
}













