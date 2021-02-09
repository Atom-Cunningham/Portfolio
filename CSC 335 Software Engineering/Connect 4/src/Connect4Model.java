
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Random;

public class Connect4Model extends Observable {
	
	private Integer ROWS = 6;
	private Integer COLS = 7;
	private int[][] board;

	/** 
	 * constructor for Connect4Model objects
	 * @author Adam, John
	 * @return none
	 * @param none
	 * @throws none
	 */
	public Connect4Model() {
		board = new int[ROWS][COLS];
	}

	
	/**
	 * places a players identifying piece type (int)
	 * in the first free spot of a given col 
	 * @author Adam, John
	 * @param col an int, the column in which the piece will be placed
	 * @param player an int, the type of piece to place
	 * @throws none
	 */
	public void placePiece(int col, int player) {
		int row = firstFreeSpace(col);			//get empty row
		board[row][col] = player;				//place piece
		setChanged();
		notifyObservers(
			new Connect4MoveMessage(row, col, player));
	}

	
	/** 
	 * getter for the number of rows on the board
	 * @author Adam
	 * @param none
	 * @return int the number of rows
	 * @throws none
	 */
	public int getRowCount(){
		return board.length;
	}
	
	/** 
	 * @author Adam
	 * @param none
	 * @return int the number of columns on the board
	 * @throws none
	 */
	public int getColCount(){
		return board[0].length;
	}

	
	/**
	 * returns row at given int
	 * @author Adam cunningham 
	 * @param row int the index of the row on the board
	 * @return ArrayList<Integer> the contents of the row as a dynamic size list
	 * @throws none
	 */
	//returns row at given int
	public ArrayList<Integer> getRow(int row){
		ArrayList<Integer> list = new ArrayList<>();
		for (Integer piece : board[row]){
			list.add(piece);
		}
		return list;
	}

	
	/**
	 * getter for a column at a given row index
	 * @author Adam Cunningham 
	 * @param col int the index of the requested col
	 * @return ArrayList<Integer> a variable size collection of col values
	 * @throws none
	 */
	//returns col at given int
	public ArrayList<Integer> getCol(int col){
		assert col < board[0].length;
		ArrayList<Integer> list = new ArrayList<>();
		for (int[] row : board){			//for each row
			list.add(row[col]);				//add the piece at that col to
		}
		return list;
	}

	
	/** 
	 * returns a diagonal starting at a given row.
	 * going from the left side of the board to the top
	 * start is the index of the starting row
	 * @author Adam Cunningham
	 * @param start int an index from the left col
	 * @return ArrayList<Integer> the diagonal as a var length collection
	 * @throws none
	 */
	public ArrayList<Integer> getDiagLeftTop(int start){
		ArrayList<Integer> list = new ArrayList<>();
		int col = 0;
		for (int row = start; row >= 0; row--){
			list.add(board[row][col]);
			col++;
		}
		return list;
	}
	
	
	/** 
	 * returns diagonal starting at left col, going to bottom row
	 * for a given starting row
	 * start is the index of the row where the diagonal is constructed
	 * @author Adam Cunningham
	 * @param start int an index from the left col
	 * @return ArrayList<Integer> the diagonal as a var length collection
	 * @throws none
	 */
	
	public ArrayList<Integer> getDiagLeftBottom(int start){
		ArrayList<Integer> list = new ArrayList<>();
		int col = 0;
		for (int row = start; row < board.length; row++){
			list.add(board[row][col]);
			col++;
		}
		return list;
	}

	
	/** 
	 * returns diagonal starting at bottom row, going to right col
	 * for a given starting col
	* @author Adam Cunningham
	 * @param start int an index from the bottom row
	 * @return ArrayList<Integer> the diagonal as a var length collection
	 * @throws none
	 */
	
	public ArrayList<Integer> getDiagBottomRight(int start){
		ArrayList<Integer> list = new ArrayList<>();
		int row = board.length - 1;
		for (int col = start; col < board.length; col++){
			list.add(board[row][col]);
			row--;
			if(row < 0) break;
		}
		return list;
	}

	
	/** 
	 * returns diagonal starting at top row, going to right col
	 * for a given starting col
	 * @author Adam Cunningham
	 * @param start int an index from the top row
	 * @return ArrayList<Integer> the diagonal as a var length collection
	 * @throws none
	 */
	public ArrayList<Integer> getDiagTopRight(int start){
		ArrayList<Integer> list = new ArrayList<>();
		int row = 0;
		for (int col = start; col < board.length; col++){
			list.add(board[row][col]);
			row++;
			if(row == 6) break;
		}
		return list;
	}


	
	/** 
	 * @author Adam Cunningham
	 * @param col int the column being checked for space
	 * @return Integer
	 */
	//returns the first free row in a given col
	//itterates backwards up the board for the first free space
	//returns -1 if the col is full
	public Integer firstFreeSpace(int col){
		for (int row = board.length - 1; row >= 0; row--){
			if (board[row][col] == 0){
				return row;
			}
		} 
		return -1;					//no free space
	}

	
	/** 
	 * @author Adam Cunningham
	 * @param row int the x coord
	 * @param col int the y coord
	 * @return Integer the piece at x,y
	 */
	//returns the value of the piece at given row,col
	public Integer getPiece(int row, int col){
		return board[row][col];
	}
	
	public void printBoard() {
		for(int i =0 ; i < ROWS; i++) {
			for(int j =0 ; j < COLS; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println("");
		}
	}
}
