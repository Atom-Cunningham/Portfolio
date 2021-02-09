import java.util.ArrayList;

import javafx.application.Platform;

public class Connect4Controller {
	
	private AbstractSocket socket;
	private boolean human; //false is AI, true is server
	private Connect4Model model;
	private Thread thread;
	
	/** 
	 * Constructor for the Connect4Controller object. Places a blank piece
	 * at the origin to force an update.
	 * @author Adam, John
	 * @param model Connect4Model object
	 * @param opponent boolean, true for human, false for computer
	 * @param socket2 
	 * @return none, this is a constructor method
	 */
	public Connect4Controller(Connect4Model model, boolean opponent) {
		this.model = model;
		this.human = opponent;
	}

	/**
	 * @author Adam, John
	 * @param col the column in the model where the piece is to be placed
	 * @return void
	 * @throws none
	 */
	public void humanTurn(int col){
		sendMoveMessage(col);
		model.placePiece(col, socket.getPlayer());		//update model
	}

	private void sendMoveMessage(int col) {
		if(socket instanceof AbstractSocket) {		//if multiplayer mode			//and its users turn
			socket.outputMove(col, model.firstFreeSpace(col), socket.getPlayer());	//send info to opponent
			
								//otherwise set the playerType to opponent
		}
	}
	
	/**
	 * makes a random legal move with opponent pieceType
	 * @author Adam Cunningham
	 * @param none
	 * @throws none
	 * @return void
	 */
	public void computerTurn(){
		int rand = randomEmptyCol();
		sendMoveMessage(rand);
		model.placePiece(rand, socket.getPlayer());
	}

	/**
	 * @author Adam Cunningham
	 * @param width the current width of the screen
	 * @param player
	 * @return boolean, true if the move was placed, false if the column
	 * is full, and therefore the move was illegal.
	 */
	public boolean placePiece(double xPos, int width){
		int col =(int) ((xPos/width) *model.getColCount());
		if (!colIsFull(col)){
			humanTurn(col);
			if (!human && !endOfGame() && socket == null){	//if no opponent, make a random legal
				computerTurn();				//move for the computer
			}
			return true;
		}
		return false;
	}

	
	/**
	 * generates a random integer between 0 and the models
	 * number of columns -1
	 * @author Adam Cunningham
	 * @param none
	 * @throws none WARNING: if the board is not currently full
	 * then this could create an endless loop
	 * @return Integer
	 */
	public Integer randomEmptyCol(){
		int cols = model.getColCount();
		int rand = (int)(Math.random()*cols);
		if (rand == cols){rand = cols-1;}	//odds are rand will never == 1.0 * 6
		while(colIsFull(rand)){				//runs until an empty col is found
			rand = (int)(Math.random()*cols);
			if (rand == cols){rand = cols-1;}
		}
		return rand;
	}	
	/** 
	 * Determine whether or not the game is over.
	 * the game ends when there is a winner, or no legal move
	 * @author Adam Cunningham
	 * @throws none
	 * @param none
	 * @return boolean: whether or not the game is over
	 */
	public boolean endOfGame(){
		return getWinner() != 0 || boardIsFull();	
		  //getWinner returns 0 iff neither player has won;
	}

	
	/** 
	 * returns an integer representing the winning player
	 * @author Adam Cunningham
	 * @return Integer, 1 for the user, 2 for the opponent, 0 for none
	 * @param none
	 * @throws none
	 * @see wins(int)
	 */
	public Integer getWinner(){
		Integer playerOne = 1;
		Integer playerTwo = 2;

		if (wins(playerOne)){
			return playerOne;
		}
		else if (wins(playerTwo)){
			return playerTwo;
		}
		return 0;	//nobody won;
	}

	
	/** 
	 * returns true if the model has four contiguous or corner-contiguous
	 * pieces of the same type, else false
	 * @author Adam Cunningham
	 * @param piece theinteger representing the player type
	 * @return boolean, whether or not the given playerType won
	 * @throws none
	 */
	private boolean wins(int piece){
		return checkRows(piece) || checkCols(piece) || checkDiags(piece);
	}

	
	/** 
	 * checks for four consecutive pieces in each row in the model
	 * given a specific type of piece, represented by an int
	 * @author Adam Cunningham
	 * @param piece an int which represents the playerType
	 * @return boolean true if found sequence
	 * @throws none
	 * @see findSequence(ArrayList<int>)
	 */
	private boolean checkRows(int piece){
		for (int row = 0; row < model.getRowCount(); row++){	//for each row
			if (findSequence(model.getRow(row), piece)){			//if there are four consecutive pieces
				return true;									//return true
			}
		}
		return false;
	}

	/** 
	 * checks for four consecutive pieces in each column in the model
	 * given a specific type of piece, represented by an int
	 * @author Adam Cunningham
	 * @param piece an int which represents the playerType
	 * @return boolean true if found sequence
	 * @throws none
	 * @see findSequence(ArrayList<int>)
	 */
	private boolean checkCols(int piece){
		for (int col = 0; col < model.getColCount(); col++){	//for each col
			if (findSequence(model.getCol(col), piece)){			//if there are for counsecutive pieces
				return true;									//return true
			}
		}
		return false;
	}

	
	/** 
	 * assembles a list of every diagonal on the board.
	 * checks the list to see if any diagonal has four consecutive pieces
	 * of the given pieceType (the param).
	 * @author Adam Cunningham
	 * @param piece an int which represents the playerType
	 * @return boolean true if found sequence
	 * @throws none
	 * @see findSequence(ArrayList<int>)
	 */
	private boolean checkDiags(int piece){
		ArrayList<ArrayList<Integer>> diagList = new ArrayList<>();

		int start;
		int end = model.getRowCount();						//for rows on board, add diags
		for (start = 0; start < end; start++){
			diagList.add(model.getDiagLeftTop(start));		//start is starting row
			diagList.add(model.getDiagLeftBottom(start));					  //row
		}

		end = model.getColCount();							//for cols on board, add diags
		for (start = 0; start < end; start++){
			diagList.add(model.getDiagTopRight(start));		//start is starting col
			diagList.add(model.getDiagBottomRight(start));					  //col
		}
		for (ArrayList<Integer> list : diagList){			//for diags check for four consecutive
			if (findSequence(list, piece)){
				return true;
			}
		}
		return false;
	}

	
	/** 
	 * determines whether or not there are four consecutive integers of the same type in a list
	 * @author Adam Cunningham
	 * @param list the list being searched
	 * @param player an int of which four in a row are being searched for in the list
	 * @return boolean a sequence of 4 consecutive integers (player) exists in the list
	 * @throws none
	 */
	private boolean findSequence(ArrayList<Integer> list, int player){
		String listString = "";
		String playerString = "";
		for (Integer piece : list){		//convert the list into a string
			listString += piece;
		}
		for (int i = 0; i < 4; i++){	//build string(player)*4
			playerString += player;
		}
		return (listString.indexOf(playerString) != -1);	//look for playerString in colString
	}

	
	/** 
	 * determines wheter or not a column in the model is already full
	 * @author Adam Cunningham
	 * @param col the column being tested for maximum occupancy
	 * @return boolean true if full
	 * @throws none
	 */
	public boolean colIsFull(int col){
		return model.firstFreeSpace(col) == -1;
	}

	
	/** 
	 * checks to see if a legal move cannot be made
	 * @author Adam Cunningham
	 * @param none
	 * @return boolean true if the every int stored in the board is a playerType int
	 * @throws none
	 */
	public boolean boardIsFull(){
		for (int col = 0; col < model.getColCount(); col++){
			if (!colIsFull(col)){
				return false;
			}
		}
		return true;
	}

	
	/**
	 * returns the value of the piece at given coordinates in the model.
	 * @author Adam Cunningham
	 * @param row x coordinate for the board
	 * @param col y coordinate for the board
	 * @return Integer the Playertype int at the x,y
	 */
	public Integer getCircleVal(int row, int col){
		return model.getPiece(row, col);
	}

	/**
	 * @author John Nichols
	 */
	public void recieveSocket(AbstractSocket socket) {
		this.socket = socket;
		thread = new Thread(socket);
		thread.start();	
//		Platform.runLater(socket);
	}

	/**
	 * @author John Nichols
	 * @param message Connect4MoveMessage
	 * @throws none
	 * @return none
	 */
	public void processMessage(Connect4MoveMessage message) {
				model.placePiece(message.getColumn(), message.getColor());
	}

	
	/** 
	 * sets whether or not the user is playing against a human
	 * @author Adam Cunningham
	 * @param opponent boolean should be true if a human opponent exists on the server
	 * @return void
	 * @throws none
	 */
	//
	//or true if playing against a person on a network
	public void setOpponent(boolean opponent){
		human = opponent;
	}
	/**
	 * takes a column space, and places apiece there.
	 * flips the turn bool
	 * @author John
	 * @param col the col to place a piece(if human player)
	 * @return void
	 * @throws none
	 */
	public void updateServer(int col) {
		if(socket.isTurn()) { //sends info to the outputstream and model
			if(human) {
				humanTurn(col);
			}else {
				computerTurn();
			}
			socket.setTurn(false);
			return;
		}else { //recieve info from the inputstream
			//Platform.runLater(() -> {socket.run();});
//			model.printBoard();
		}
	}

	/**
	 * @author John
	 * @return boolean true if is this turn'
	 * @throws none
	 * @param none
	 */
	public boolean getTurn() {
		// TODO Auto-generated method stub
		return socket.isTurn();
	}

}
