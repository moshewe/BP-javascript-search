package tictactoe.externalApp;

import bp.BEvent;
import bp.search.BPSearchApplication;
import bp.search.adversarial.AdversarialSearchArbiter;
import bp.search.adversarial.BPAdversarialSearch;
import bp.search.adversarial.BPAlphaBetaSearch;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import tictactoe.events.Move;
import tictactoe.events.O;
import tictactoe.events.X;
import tictactoe.search.TTTGame;

import javax.swing.*;
import java.net.MalformedURLException;

import static tictactoe.events.StaticEvents.*;

/**
 * The main entry point to the TicTacToe _program.
 */
public class TicTacToe extends BPSearchApplication {

    private String _initScript;
    public GUI gui;

    public TicTacToe() {
        super();
        _bp.setName("TicTacToe");
        addBThreads();
        setupBThreadScopes();
        TTTGame game = new TTTGame(_bp);
        BPAlphaBetaSearch search = new BPAlphaBetaSearch(game);
        _arbiter = new AdversarialSearchArbiter((BPAdversarialSearch) search, game);
//        BPGoalTest goalTest = new TTTGoalTest();
//        HeuristicFunction heuristic = new TTTHeuristicFunction();
//        BPQueueSearch bpQSearch = new BPQueueSearch();
//        _arbiter = new InformedSearchArbiter(
//                new BPAStarSearch(bpQSearch,
//                        heuristic),
//                new TTTInitStateGenerator(_bp),
//                goalTest);
        _bp.setArbiter(_arbiter);
        // Start the graphical user interface
        gui = new GUI(_bp);
    }

    protected void addBThreads() {
//        _squaresTaken = new ArrayList<>();
        evaluateInGlobalScope("out/production/TicTacToe/tictactoe/bThreads/SquareTaken.js");
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
            _initScript = "out/production/TicTacToe/tictactoe/globalScopeInit.js";
            evaluateInGlobalScope(_initScript);
        } finally {
            Context.exit();
        }
    }

    public static void main(String[] args) throws MalformedURLException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        final TicTacToe ttt = new TicTacToe();
        Thread tttThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ttt.start();
            }
        });
        tttThread.start();
        BEvent outputEvent = ttt._bp.getOutputEvent();
        String msg = null;
        while (true) {
            if (outputEvent instanceof X ||
                    outputEvent instanceof O) {
                Move move = (Move) outputEvent;
                JButton btt = ttt.gui.buttons[move.row][move.col];
                btt.setText(move.displayString());
            } else if (outputEvent.getName().equals("Draw")) {
                msg = "Draw!";
            } else if (outputEvent.getName().equals("OWin")) {
                msg = "O Wins!";
            } else if (outputEvent.getName().equals("XWin")) {
                msg = "X Wins!";
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
                break;
            }

            //announce game over?
            outputEvent = ttt._bp.getOutputEvent();
        }

    }
}