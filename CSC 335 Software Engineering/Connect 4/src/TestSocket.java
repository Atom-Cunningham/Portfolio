import java.io.IOException;

public class TestSocket extends AbstractSocket {

	public TestSocket(Connect4Controller controller, int port, String host, boolean human) throws IOException {
		super(controller, human);
		turn = human;
	}
	
	public void outputmove(int col, int row, int player) {
		
	}

	@Override
	protected void inputMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void outputMove(int col, Integer row, int player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	

}
