//import static org.junit.Assert.*;
//
//import java.io.IOException;
//
//import org.junit.jupiter.api.Test;
//
//public class Connect4ControllerTests {
//	
//	private Connect4Controller controller;
//	
//	@Test
//	public void TurnTest() {
//		controller = new Connect4Controller(new Connect4Model(), true);
//		assertTrue(controller.placePiece(50, 352));
//		assertTrue(controller.placePiece(50, 352));
//		assertTrue(controller.placePiece(50, 352));
//		assertTrue(controller.placePiece(50, 352));
//		assertTrue(controller.placePiece(50, 352));
//		assertTrue(controller.placePiece(50, 352));
//		assertFalse(controller.placePiece(50, 352));
//		controller.humanTurn(1);
//		controller.computerTurn();
//		assertTrue(controller.colIsFull(0));
//		assertFalse(controller.colIsFull(1));
//		assertFalse(controller.boardIsFull());
//		assertEquals(controller.getCircleVal(0,0),new Integer(1));
//		assertNotNull(controller.randomEmptyCol());
//	}
//	
//	@Test
//	public void GameConditionPlayerTest() {
//		controller = new Connect4Controller(new Connect4Model(), false);
//		assertEquals(controller.getWinner(), new Integer(0));
//		while(controller.placePiece(50, 352));
//		assertEquals(controller.getWinner(), new Integer(1));
//	}
//	
//	@Test
//	public void GameConditionComputerTest() {
//		controller = new Connect4Controller(new Connect4Model(), false);
//		while(!controller.boardIsFull()) {
//			controller.computerTurn();
//		}
//		assertEquals(controller.getWinner(), new Integer(2));
//		assertTrue(controller.boardIsFull());
//		controller.setOpponent(true);
//	}
//	
//	@Test
//	public void SocketTest() throws IOException {
//		controller = new Connect4Controller(new Connect4Model(), false);
//		TestSocket socket1 = new TestSocket(controller, 4000, "localhost", true);
//		controller.recieveSocket(socket1);
//		controller.humanTurn(0);
//		TestSocket socket2 = new TestSocket(controller, 4000, "localhost", false);
//		controller.recieveSocket(socket2);
//		controller.humanTurn(0);
//		controller.processMessage(new Connect4MoveMessage(1,1,1));
//	}
//
//}
