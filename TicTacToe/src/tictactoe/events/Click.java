package tictactoe.events;

import bp.BEvent;

/**
 * An event that is requested (with high priority) whenever the user presses a
 * button on the game board.
 */
public class Click extends BEvent {

	/**
	 * Row of the pressed button
	 */
	public int row;

	/**
	 * Column of the pressed button
	 */
	public int col;

	/**
	 * Constructor.
	 * 
	 * @param row
	 *            Row of the pressed button
	 * @param col
	 *            Column of the pressed button
	 */
	public Click(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.setName("Click(" + row + "," + col + ")");
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Click) {
			Click other = (Click) obj;
			return other.row == row && other.col == col;
		}
		
		return false;
	}
}
