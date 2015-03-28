package tictactoe.events;

/**
 * An event that is executed when player O makes a move.
 */
public class O extends Move {
	/**
	 * Constructor.
	 */
	public O(int row, int col) {
		super(row, col);
		setName("O(" + row + "," + col + ")");
	}

	/**
	 * @see Object#toString()
	 */
	/**
	 * @see tictactoe.events.Move#displayString()
	 */
	public String displayString() {
		return "O";
	}

	public X converse() {
		return new X(row, col);
	}

}
