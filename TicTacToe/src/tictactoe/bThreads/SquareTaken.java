package tictactoe.bThreads;

import bp.BThread;
import bp.eventSets.EventSet;
import bp.exceptions.BPJException;
import tictactoe.events.Move;

import java.util.Collection;
import java.util.HashSet;

import static bp.eventSets.EventSetConstants.none;
import static tictactoe.events.StaticEvents.gameOver;

/**
 * BThread for not allowing two symbols in the same square.
 */
public class SquareTaken extends BThread {
	public final int row;
	public final int col;

	public void runBThread() throws BPJException {

		interruptingEvents = new EventSet(gameOver);
		Move move = new Move(row, col);
		move.setName("(" + row + "," + col + ")");
		// Wait for any move for a given square
		bsync(none, move, none);
		bsync(none, none, move);

	}

	public SquareTaken(int row, int col) {
		super("SquareTaken(" + row + "," + col + ")");
		this.row = row;
		this.col = col;

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
