package tictactoe.externalApp;

import bp.BEvent;
import bp.BThread;
import bp.search.BPSearchApplication;
import bp.search.adversarial.BPMinimaxSearch;
import bp.search.adversarial.MinimaxSearchArbiter;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import tictactoe.events.Move;
import tictactoe.events.StaticEvents;
import tictactoe.search.TTTGame;

import javax.swing.*;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static tictactoe.events.StaticEvents.*;

/**
 * The main entry point to the TicTacToe _program.
 */
public class TicTacToe extends BPSearchApplication {

    private String initScript;

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
    //set from js
    public BThread _turns;
    public List<BThread> _squaresTaken;
    public BThread _draw;
    private Set<BThread> _xwins;
    private Set<BThread> _owins;
//    private DeclareWinner declareWinner;

    public TicTacToe() {
        super();
        _bp.setName("TicTacToe");
        addBThreads();
//        bplog("bthreads added");
        setupBThreadScopes();
//        bplog("setup bthread scopes");
        TTTGame game = new TTTGame(_bp, _squaresTaken);
        BPMinimaxSearch search = new BPMinimaxSearch(game);
        _arbiter = new MinimaxSearchArbiter(search, game);
        _bp.setArbiter(_arbiter);
//        addBThreads();
        // Start the graphical user interface
        gui = new GUI(_bp);
    }

    protected void addBThreads() {
//        _xwins = DetectXWin.constructInstances();
//        _owins = DetectOWin.constructInstances();
        _squaresTaken = new ArrayList<>();
        evaluateInGlobalScope("out/production/TicTacToe/tictactoe/bThreads/SquareTaken.js");

//        _bp.add(_xwins);
//        _bp.add(_owins);
        evaluateInGlobalScope("out/production/TicTacToe/tictactoe/bThreads/DetectWin.js");

        evaluateInGlobalScope("out/production/TicTacToe/tictactoe/bThreads/EnforceTurns.js");
        evaluateInGlobalScope("out/production/TicTacToe/tictactoe/bThreads/DetectDraw.js");
        evaluateInGlobalScope("out/production/TicTacToe/tictactoe/bThreads/ReqAllMoves.js");
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
            initScript = "out/production/TicTacToe/tictactoe/globalScopeInit.js";
            evaluateInGlobalScope(initScript);
        } finally {
            Context.exit();
        }
    }

    public static void main(String[] args) throws MalformedURLException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        TicTacToe ttt = new TicTacToe();
        ttt.start();
        BEvent outputEvent = ttt._bp.getOutputEvent();
        String msg = null;
        while (outputEvent != StaticEvents.gameOver) {
            switch (outputEvent.getName()) {
                case "O":
                case "X":
                    Move move = (Move) outputEvent;
                    JButton btt = ttt.gui.buttons[move.row][move.col];
                    btt.setText(move.displayString());
                    break;
                case "Draw":
                    msg = "Draw";
                case "OWin":
                    msg = "O Wins";
                case "XWin":
                    msg = "X Wins";
                    break;
            }

            if (msg != null) {
                ttt.gui.message.setText(msg);
                ttt.gui.buttons[0][0].setEnabled(false);
                ttt.gui.buttons[0][1].setEnabled(false);
                ttt.gui.buttons[0][2].setEnabled(false);
                ttt.gui.buttons[1][0].setEnabled(false);
                ttt.gui.buttons[1][1].setEnabled(false);
                ttt.gui.buttons[1][2].setEnabled(false);
                ttt.gui.buttons[2][0].setEnabled(false);
                ttt.gui.buttons[2][1].setEnabled(false);
                ttt.gui.buttons[2][2].setEnabled(false);
            }

            //announce game over?
            outputEvent = ttt._bp.getOutputEvent();
        }

    }
}