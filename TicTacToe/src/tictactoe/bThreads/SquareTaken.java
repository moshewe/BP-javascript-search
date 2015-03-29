package tictactoe.bThreads;

import bp.BThread;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import tictactoe.events.Move;

import java.util.Collection;
import java.util.HashSet;

/**
 * BThread for not allowing two symbols in the same square.
 */
public class SquareTaken extends BThread {
    public final int _row;
    public final int _col;
    public final String _moveEventName;

//	public void runBThread() throws BPJException {
//
//		interruptingEvents = new EventSet(gameOver);
//		Move move = new Move(_row, _col);
//		move.setName("(" + _row + "," + _col + ")");
//		// Wait for any move for a given square
//		bsync(none, move, none);
//		bsync(none, none, move);
//
//	}

    public SquareTaken(int row, int col) {
        super("SquareTaken(" + row + "," + col + ")");
        this._row = row;
        this._col = col;
        _moveEventName = "move" + row + col + "Event";
        // Wait for any move for a given square
        String source = jsIdentifier() + ".bsync(none, " +
                _moveEventName + ", none);\n" +
                jsIdentifier() + ".bsync(none, none, " +
                _moveEventName + ")\n;";

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

    @Override
    public void setupScope() {
        Move move = new Move(_row, _col);
        move.setName("(" + _row + "," + _col + ")");
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            _scope.put(_moveEventName, _scope,
                    Context.javaToJS(move, _scope));
        } finally {
            Context.exit();
        }
    }
}
