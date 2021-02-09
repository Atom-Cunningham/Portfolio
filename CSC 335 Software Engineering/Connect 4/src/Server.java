import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Jonathon Nicholls,
 * 
 * Purpose: Is the Server class when the player chooses to play as the server.
 * Talks to the controller on any action that has been taken place.
 */

public class Server extends AbstractSocket {
	
	private ServerSocket connection;

	/**
	 * Sets up the server 
	 * @author John
	 * @param controller Connect4Controller
	 * @param port an int connection number
	 * @param human true if not playing against ai
	 * @throws IOException
	 * @return this is a constructor
	 */
	public Server(Connect4Controller controller, int port, boolean human) throws IOException {
		super(controller, human);
		turn = true;
		connection = new ServerSocket(port);
		server = connection.accept();
		output = new ObjectOutputStream(server.getOutputStream());
		input = new ObjectInputStream(server.getInputStream());
		player = 2;
	}

	/**
	 * while its not the end of the game, streams in input
	 * @author John
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @param none
	 * @return void
	 */
	@Override
	protected void inputMove() {
		Connect4MoveMessage message = null;
		try {
			do {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				message = (Connect4MoveMessage)input.readObject();
				turn = true;
				controller.processMessage(message);
				}while(!controller.endOfGame());
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	/**
	 * writes Connect4MoveMessage to output stream
	 * @author John
	 * @throws IOExcpetion
	 * @param col to place the piece in
	 * @param the row to place the piece in
	 * @param player the piecetype
	 * @return void
	 */
	@Override
	protected void outputMove(int col, Integer row, int player) {
		Connect4MoveMessage message = new Connect4MoveMessage(row, col, player);
		try {
			output.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * inputs a move
	 * @author John
	 * @param none
	 * @return void
	 * @throws none
	 */
	@Override
	public void run() {
		inputMove();
	}
	
	
	

}
