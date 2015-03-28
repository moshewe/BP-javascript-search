package tictactoe.externalApp;

import bp.Arbiter;
import bp.BProgram;
import bp.BThread;
import bp.search.adversarial.BPMinimaxSearch;
import bp.search.adversarial.MinimaxSearchArbiter;
import tictactoe.bThreads.*;
import tictactoe.search.TTTGame;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * The main entry point to the TicTacToe program.
 */
public class TicTacToe {
    public static class Coordinates implements Comparable<Coordinates> {
        public final int row;
        public final int col;

        public Coordinates(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public int compareTo(Coordinates o) {
            if (row - o.row < 0) {
                return -1;
            }

            if (col - o.col < 0) {
                return -1;
            }

            if (0 < row - o.row) {
                return 1;
            }

            if (0 < col - o.col) {
                return 1;
            }

            return 0;
        }
    }

    public GUI gui;
    private BProgram bp;
    private EnforceTurns turns;
    private Arbiter arbiter;
    private Collection<SquareTaken> squaresTaken;
    private DetectDraw draw;
    private Set<BThread> xwins;
    private Set<BThread> owins;
    private DeclareWinner declareWinner;
    // private List<BThread> recorders = new ArrayList<BThread>();
    // private List<BThread> restorers = new ArrayList<BThread>();
    private UpdateDisplay updateDisplay;

    public TicTacToe() {
        bp = new BProgram();
        bp.setName("TicTacToe");

        // Register scenarios
        addBThreads(bp);

        TTTGame game = new TTTGame(bp, turns, squaresTaken, draw, xwins, owins,
                declareWinner, this);
        BPMinimaxSearch search = new BPMinimaxSearch(game);
        arbiter = new MinimaxSearchArbiter(search, game);
        bp.setArbiter(arbiter);

        // Start the graphical user interface
        gui = new GUI(bp);

    }

    private void addBThreads(BProgram bp) {
        updateDisplay = new UpdateDisplay(this);
        xwins = DetectXWin.constructInstances();
        owins = DetectOWin.constructInstances();
        turns = new EnforceTurns();
        squaresTaken = SquareTaken.constructInstances();
        declareWinner = new DeclareWinner(this);
        ArrayList<BThread> stakenBThreadList = new ArrayList<BThread>(
                squaresTaken);
        // draw = new DetectDraw();
        draw = new DetectDrawShort();

        bp.add(updateDisplay);
        bp.add(xwins);
        bp.add(owins);
        bp.add(declareWinner);
        bp.add(turns);
        bp.add(stakenBThreadList);
        bp.add(draw);
//		bp.add(new BlockMiddle());

        // bp.add(ClickSimulator.constructInstances());

        // removed strategy bthreads
        // for (BThread sc : AddThirdO.constructInstances())
        // bp.add(sc);
        //
        // for (BThread sc : PreventThirdX.constructInstances())
        // bp.add(sc);
        //
        // bp.add(new InterceptCornerCornerFork());
        // bp.add(new InterceptCornerEdgeFork());
        //
        // bp.add(new DefaultMoves());

    }

    public static void main(String[] args) throws MalformedURLException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        TicTacToe ttt = new TicTacToe();
    }

}
