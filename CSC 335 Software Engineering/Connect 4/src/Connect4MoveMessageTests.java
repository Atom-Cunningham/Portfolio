import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Connect4MoveMessageTests {
	
	private Connect4MoveMessage message;
	
	@BeforeEach
	public void init() {
		message = new Connect4MoveMessage(0,1,2);
	}
	
	@Test
	public void CreateTestAndStaticTest() {
		assertTrue(Connect4MoveMessage.YELLOW == 1);
		assertTrue(Connect4MoveMessage.RED == 2);
	}
	
	@Test
	public void GettersTest() {
		assertEquals(message.getRow(), 0);
		assertEquals(message.getColumn(), 1);
		assertEquals(message.getColor(), 2);
	}

}
