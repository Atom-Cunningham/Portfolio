import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends AbstractSocket{
	/**
	 * @author John
	 * @param controller Connect4Controller
	 * @param port the connection address
	 * @param host hostype
	 * @param human true if not AI
	 * @throws IOException
	 * @return this is a constructor
	 */
	public Client(Connect4Controller controller, int port, String host, boolean human) throws IOException {
		super(controller, human);
		turn = false;
		server = new Socket(host, port);
		output = new ObjectOutputStream(server.getOutputStream());
		input = new ObjectInputStream(server.getInputStream());
		player = 1;
	}

	/**
	 * super constructor
	 * @author John
	 * @param controller Connect4Controller
	 * @param human AI if false
	 * @return this is a super constructor
	 * @throws none
	 */
	public Client(Connect4Controller controller, boolean human) {
		super(controller, human);
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
					e.printStackTrace();
				}
			message = (Connect4MoveMessage)input.readObject();
			turn = true;
			controller.processMessage(message);
			}while(!controller.endOfGame());
			
		} catch (ClassNotFoundException | IOException e) {
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
