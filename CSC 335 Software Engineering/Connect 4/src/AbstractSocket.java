import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @param none
 * @author Jonathon Nicholls
 * The abstract class that both Server and Client have in common.
 *
 */
public abstract class AbstractSocket implements Runnable {
	
	protected Connect4Controller controller;
	protected boolean human;//false is computer
	protected boolean turn;
	protected ObjectOutputStream output;
	protected ObjectInputStream input;
	protected Socket server;
	protected int player;
	
	public AbstractSocket(Connect4Controller controller, boolean human) {
		this.controller = controller;
		this.human = human;
	}
	
	/**
	 * @author John
	 * @return boolean whether it is this server's turn
	 * @throws none
	 * @param none
	 * 
	 */
	public boolean isTurn() {
		return turn;
	}

	
	/**
	 * sets the turn to the passed val
	 * @param turn bool
	 * @author john
	 * @return void
	 * @throws none
	 */
	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	/**
	 * Inputs the move given by the connection
	 * @return void
	 * @author John
	 * @param none
	 * @throws none
	 */
	protected abstract void inputMove();

	/**
	 * Purpose: Ouputs the made move in a MoveMessage to the connection
	 * @author John
	 * @param col, the column of the currently made move
	 * @param row, the row of the currently made move
	 * @param player, the player who made the move
	 * @return void
	 * @throws none
	 * 
	 */
	protected abstract void outputMove(int col, Integer row, int player);

	/**
	 * getter for the player
	 * @return int the playerType
	 * @param none
	 * @throws none
	 * @author John
	 */
	public int getPlayer() {
		return player;
	}


}
