package tictactoe.bThreads;

import bp.BThread;
import tictactoe.events.O;

import java.util.HashSet;
import java.util.Set;

/**
 * A scenario that identifies wins by player O
 */
public class DetectOWin extends BThread {

	private O firstSquare;
	private O secondSquare;
	private O thirdSquare;

//	public void runBThread() throws BPJException {
//		interruptingEvents = new EventSet(gameOver);
//		// Wait for the first O
//		bsync(none, firstSquare, none);
//		// Wait for the second O
//		bsync(none, secondSquare, none);
//		// Wait for the third O
//		bsync(none, thirdSquare, none);
//		// Announce O win
//		bsync(OWin, none, none);
//	}

	/**
	 * @param o
	 * @param o2
	 * @param o3
	 */
	public DetectOWin(O o, O o2, O o3) {
		super();
		this.firstSquare = o;
		this.secondSquare = o2;
		this.thirdSquare = o3;
		this.setName("DetectOWin(" + o + "," + o2 + ","
				+ o3 + ")");
		_btScopeObjects.add(o);
		_btScopeObjects.add(o2);
		_btScopeObjects.add(o3);
		String source = jsIdentifier() + ".bsync(none, " +
				firstSquare.jsIdentifier() + ", none);\n" +
				jsIdentifier() + ".bsync(none, " +
				secondSquare.jsIdentifier() + ", none);\n" +
				jsIdentifier() + ".bsync(none, " +
				thirdSquare.jsIdentifier() + ", none);\n" +
				jsIdentifier() + ".bsync(OWin, none, none);\n";
		setScript(source);
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
			// Run copies for each _row
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