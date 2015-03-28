package tictactoe.bThreads;

import static bp.eventSets.EventSetConstants.none;
import static tictactoe.events.StaticEvents.OWin;
import static tictactoe.events.StaticEvents.gameOver;

import java.util.HashSet;
import java.util.Set;

import tictactoe.events.O;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.exceptions.BPJException;

/**
 * A scenario that identifies wins by player O
 */
public class DetectOWin extends BThread {

	private O firstSquare;
	private O secondSquare;
	private O thirdSquare;

	public void runBThread() throws BPJException {
		interruptingEvents = new EventSet(gameOver);
		// Wait for the first O
		bsync(none, firstSquare, none);
		// Wait for the second O
		bsync(none, secondSquare, none);
		// Wait for the third O
		bsync(none, thirdSquare, none);
		// Announce O win
		bsync(OWin, none, none);
	}

	/**
	 * @param firstSquare
	 * @param secondSquare
	 * @param thirdSquare
	 */
	public DetectOWin(O firstSquare, O secondSquare, O thirdSquare) {
		super();
		this.firstSquare = firstSquare;
		this.secondSquare = secondSquare;
		this.thirdSquare = thirdSquare;
		this.setName("DetectOWin(" + firstSquare + "," + secondSquare + ","
				+ thirdSquare + ")");
	}

	/**
	 * Construct all instances
	 */
	static public Set<BThread> constructInstances() {

		Set<BThread> set = new HashSet<BThread>();

		// All 6 permutations of three elements
		int[][] permutations = new int[][] { new int[] { 0, 1, 2 },
				new int[] { 0, 2, 1 }, new int[] { 1, 0, 2 },
				new int[] { 1, 2, 0 }, new int[] { 2, 0, 1 },
				new int[] { 2, 1, 0 } };

		for (int[] p : permutations) {
			// Run copies for each row
			for (int row = 0; row < 3; row++) {
				set.add(new DetectOWin(new O(row, p[0]), new O(row, p[1]),
						new O(row, p[2])));
			}

			// Run copies for each column
			for (int col = 0; col < 3; col++) {

				set.add(new DetectOWin(new O(p[0], col), new O(p[1], col),
						new O(p[2], col)));
			}

			// Run copies for the main diagonal
			set.add(new DetectOWin(new O(p[0], p[0]), new O(p[1], p[1]), new O(
					p[2], p[2])));

			// Run copies for the inverse diagonal
			set.add(new DetectOWin(new O(p[0], 2 - p[0]),
					new O(p[1], 2 - p[1]), new O(p[2], 2 - p[2])));
		}

		return set;
	}

}