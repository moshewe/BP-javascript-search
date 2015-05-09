package tictactoe.events;

/**
 * An event that is fired when player X makes a move.
 */
public class X extends Move {

    /**
     * Constructor.
     */
    public X(int row, int col) {
        super(row, col);
        this.setName("X(" + row + "," + col + ")");
        _outputEvent = true;
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

    @Override
    public String toString() {
        return _name;
    }
}
