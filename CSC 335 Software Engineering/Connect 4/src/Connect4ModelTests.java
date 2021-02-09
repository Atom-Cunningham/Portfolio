import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Connect4ModelTests {

	private Connect4Model model;
	
	@BeforeEach
	public void init() {
		model = new Connect4Model();
	}
	
	@Test
	public void CreationTest() {
		assertEquals(model.getRowCount(), 6);
		assertEquals(model.getColCount(), 7);
	}
	
	@Test
	public void MoveAndVerifyTest() {
		assertEquals(model.firstFreeSpace(0), new Integer(5));
		model.placePiece(0, 1);
		assertEquals(model.getPiece(5,0), new Integer(1));
	}
	
	@Test
	public void GettingLinesTest() {
		placeColumn();
		placeDia();
		model.printBoard();
		Integer [] temp1 = {3,1,1,1,3,0,0};
		assertEquals(model.getRow(5), Arrays.asList(temp1));
		Integer [] temp2 = {0,3,1,1,2,3};
		assertEquals(model.getCol(0), Arrays.asList(temp2));
		Integer [] temp3 = {3,3,3,3,3,0};
		assertEquals(model.getDiagBottomRight(0), Arrays.asList(temp3));
		assertEquals(model.getDiagLeftTop(5), Arrays.asList(temp3));
		Integer [] temp4 = {0,1,1,1,1,0};
		assertEquals(model.getDiagLeftBottom(0), Arrays.asList(temp4));
		assertEquals(model.getDiagTopRight(0), Arrays.asList(temp4));
	}

	private void placeDia() {
		model.placePiece(1,1);
		model.placePiece(1,3);
		model.placePiece(1,1);
		model.placePiece(1,3);
		model.placePiece(1,1);
		model.placePiece(2,1);
		model.placePiece(2,1);
		model.placePiece(2,3);
		model.placePiece(2,1);
		model.placePiece(2,1);
		model.placePiece(3,1);
		model.placePiece(3,3);
		model.placePiece(3,1);
		model.placePiece(3,3);
		model.placePiece(4,3);
		model.placePiece(4,1);
		model.placePiece(4,1);
		model.placePiece(4,1);
		model.placePiece(4,3);
	}

	private void placeColumn() {
		model.placePiece(0, 3);
		model.placePiece(0, 2);
		model.placePiece(0, 1);
		model.placePiece(0, 1);
		model.placePiece(0, 3);
	}
}
