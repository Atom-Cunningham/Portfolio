import java.io.Serializable;

public class Connect4MoveMessage implements Serializable {
	public static int YELLOW = 1;
	public static int RED = 2;
	
	private static final long serialVersionUID = 1L;
	
	private int row;
	private int col;
	private int color;
	
	
	/** 
	 * @param row
	 * @param col
	 * @param color
	 * @return 
	 */
	public Connect4MoveMessage(int row, int col, int color) {
		this.row = row;
		this.col = col;
		this.color = color;
	}
	
	
	/** 
	 * @return int
	 */
	public int getRow() {
		return row;
	}
	
	
	/** 
	 * @return int
	 */
	public int getColumn() {
		return col;
	}
	
	
	/** 
	 * @return int
	 */
	public int getColor() {
		return color;
	}
}
