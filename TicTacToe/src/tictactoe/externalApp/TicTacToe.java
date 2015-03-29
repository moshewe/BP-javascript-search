package tictactoe.externalApp;

import bp.Arbiter;
import bp.BPJavascriptApplication;
import bp.BThread;
import bp.search.adversarial.BPMinimaxSearch;
import bp.search.adversarial.MinimaxSearchArbiter;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import tictactoe.bThreads.*;
import tictactoe.search.TTTGame;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static tictactoe.events.StaticEvents.*;
//import static tictactoe.events.StaticEvents.XEvents;
//import static tictactoe.events.StaticEvents.OEvents;

/**
 * The main entry point to the TicTacToe program.
 */
public class TicTacToe extends BPJavascriptApplication {
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
    private EnforceTurns _turns;
    private Arbiter arbiter;
    private Collection<SquareTaken> _squaresTaken;
    private DetectDraw _draw;
    private Set<BThread> _xwins;
    private Set<BThread> _owins;
    private DeclareWinner declareWinner;
    private UpdateDisplay _updateDisplay;

    public TicTacToe() {
        _bp.setName("TicTacToe");
        TTTGame game = new TTTGame(_bp, _turns, _squaresTaken, _draw, _xwins, _owins,
                declareWinner, this);
        BPMinimaxSearch search = new BPMinimaxSearch(game);
        arbiter = new MinimaxSearchArbiter(search, game);
        _bp.setArbiter(arbiter);
        // Start the graphical user interface
        gui = new GUI(_bp);

    }

    protected void addBThreads() {
        _updateDisplay = new UpdateDisplay(this);
        _xwins = DetectXWin.constructInstances();
        _owins = DetectOWin.constructInstances();
        _turns = new EnforceTurns();
        _squaresTaken = SquareTaken.constructInstances();
        declareWinner = new DeclareWinner(this);
        ArrayList<BThread> stakenBThreadList = new ArrayList<BThread>(
                _squaresTaken);
        _draw = new DetectDraw();

        _bp.add(_updateDisplay);
        _bp.add(_xwins);
        _bp.add(_owins);
        _bp.add(declareWinner);
        _bp.add(_turns);
        _bp.add(stakenBThreadList);
        _bp.add(_draw);
        // _bp.add(new BlockMiddle());
    }

    @Override
    protected void setupGlobalScope() {
        super.setupGlobalScope();
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            _globalScope.put("ttt", _globalScope,
                    Context.javaToJS(this, _globalScope));
            _globalScope.put("draw", _globalScope,
                    Context.javaToJS(draw, _globalScope));
            _globalScope.put("XWin", _globalScope,
                    Context.javaToJS(XWin, _globalScope));
            _globalScope.put("OWin", _globalScope,
                    Context.javaToJS(OWin, _globalScope));
            _globalScope.put("gameOver", _globalScope,
                    Context.javaToJS(gameOver, _globalScope));
            _globalScope.put("moves", _globalScope,
                    Context.javaToJS(Moves, _globalScope));
            _globalScope.put("xevents", _globalScope,
                    Context.javaToJS(XEvents, _globalScope));
            _globalScope.put("oevents", _globalScope,
                    Context.javaToJS(OEvents, _globalScope));
        } finally {
            Context.exit();
        }
    }

    public static void main(String[] args) throws MalformedURLException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        TicTacToe ttt = new TicTacToe();
        ttt.start();
    }

}
