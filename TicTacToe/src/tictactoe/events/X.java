package tictactoe.events;

import bp.JSIdentifiable;

/**
 * An event that is fired when player X makes a move.
 */
public class X extends Move implements JSIdentifiable {

    /**
     * Constructor.
     */
    public X(int row, int col) {
        super(row, col);
        this.setName("X(" + row + "," + col + ")");
    }

    /**
     * @see tictactoe.events.Move#displayString()
     */
    public String displayString() {
        return "X";
    }

    public O converse() {
        return new O(row, col);
    }

}
