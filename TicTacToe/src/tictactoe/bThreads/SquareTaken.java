package tictactoe.bThreads;

import bp.BThread;
import tictactoe.events.Move;

import java.util.Collection;
import java.util.HashSet;

/**
 * BThread for not allowing two symbols in the same square.
 */
public class SquareTaken extends BThread {
    public final int _row;
    public final int _col;
    private final Move _move;

//	public void runBThread() throws BPJException {
//
//		interruptingEvents = new EventSet(gameOver);
//		Move _move = new Move(_row, _col);
//		_move.setName("(" + _row + "," + _col + ")");
//		// Wait for any _move for a given square
//		bsync(none, _move, none);
//		bsync(none, none, _move);
//
//	}

    public SquareTaken(int row, int col) {
        super("SquareTaken(" + row + "," + col + ")");
        this._row = row;
        this._col = col;
        _move = new Move(_row, _col);
        _btScopeObjects.add(_move);
        // Wait for any _move for a given square
        String source = jsIdentifier() + ".bsync(none, " +
                _move.jsIdentifier() + ", none);\n" +
                jsIdentifier() + ".bsync(none, none, " +
                _move.jsIdentifier() + ")\n;";

        setScript(source);
    }

    static public Collection<SquareTaken> constructInstances() {

        Collection<SquareTaken> set = new HashSet<SquareTaken>();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                set.add(new SquareTaken(row, col));
            }
        }
        return set;
    }
}
