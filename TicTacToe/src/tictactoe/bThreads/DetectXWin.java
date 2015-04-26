package tictactoe.bThreads;

import bp.BThread;
import tictactoe.events.X;

import java.util.HashSet;
import java.util.Set;

/**
 * A scenario that identifies wins by player X
 */
public class DetectXWin extends BThread {

    public X _firstSquare;
    public X _secondSquare;
    public X _thirdSquare;

    /**
     * @param x
     * @param x2
     * @param x3
     */
    public DetectXWin(X x, X x2, X x3) {
        super();
        _firstSquare = x;
        _secondSquare = x2;
        _thirdSquare = x3;
        this.setName("DetectXWin(" + _firstSquare + "," + _secondSquare + ","
                + _thirdSquare + ")");
        String source = "bsync(none, _firstSquare, none);\n" +
                "bsync(none, _secondSquare, none);\n" +
                "bsync(none, _thirdSquare, none);\n" +
                "var eset = new EventSet([moves, draw]);\n" +
                "bsync(XWin, none, eset);\n";
        setScript(source);
    }

    /**
     * Construct all instances
     */
    static public Set<BThread> constructInstances() {

        Set<BThread> set = new HashSet<BThread>();

        // All 6 permutations of three elements
        int[][] permutations = new int[][]{new int[]{0, 1, 2},
                new int[]{0, 2, 1}, new int[]{1, 0, 2},
                new int[]{1, 2, 0}, new int[]{2, 0, 1},
                new int[]{2, 1, 0}};

        for (int[] p : permutations) {
            // Run copies for each _row
            for (int row = 0; row < 3; row++) {
                set.add(new DetectXWin(new X(row, p[0]), new X(row, p[1]),
                        new X(row, p[2])));
            }

            // Run copies for each column
            for (int col = 0; col < 3; col++) {

                set.add(new DetectXWin(new X(p[0], col), new X(p[1], col),
                        new X(p[2], col)));
            }

            // Run copies for the main diagonal
            set.add(new DetectXWin(new X(p[0], p[0]), new X(p[1], p[1]), new X(
                    p[2], p[2])));

            // Run copies for the inverse diagonal
            set.add(new DetectXWin(new X(p[0], 2 - p[0]),
                    new X(p[1], 2 - p[1]), new X(p[2], 2 - p[2])));
        }

        return set;
    }

}