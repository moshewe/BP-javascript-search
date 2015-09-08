package tictactoe;

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
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

import static tictactoe.events.StaticEvents.*;

/**
 * The main entry point to the TicTacToe _app.
 */
public class TicTacToe extends BPSearchApplication {

    public GUI gui;

    public TicTacToe() {
        super();
        setName("TicTacToe");
        addBThreads();
        setupBThreadScopes();
        TTTGame game = new TTTGame(this);
        BPAlphaBetaSearch search = new BPAlphaBetaSearch(game);
        _arbiter = new AdversarialSearchArbiter((BPAdversarialSearch) search, game);
        setArbiter(_arbiter);
        // Start the graphical user interface
        gui = new GUI(this);
    }

    public static void main(String[] args) throws MalformedURLException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException, InterruptedException {
        final TicTacToe ttt = new TicTacToe();
        Thread tttThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ttt.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        tttThread.start();
        BEvent outputEvent = ttt.readOutputEvent();
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
            outputEvent = ttt.readOutputEvent();
        }

    }

    protected void addBThreads() {
        InputStream script;
        List<String> bthreads = new LinkedList<>();
        bthreads.add("SquareTaken.js");
        bthreads.add("DetectWin.js");
        bthreads.add("EnforceTurns.js");
        bthreads.add("DetectDraw.js");
        bthreads.add("ReqAllMoves.js");
        for (String bt : bthreads) {
            script = getClass().getResourceAsStream("bthreads/" +
                    bt);
            evaluateInGlobalScope(script, bt);
        }
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

            InputStream ios = getClass().getResourceAsStream("globalScopeInit.js");
            evaluateInGlobalScope(ios, "TTTGlobalScope");
        } finally {
            Context.exit();
        }
    }
}