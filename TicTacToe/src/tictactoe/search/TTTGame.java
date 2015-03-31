package tictactoe.search;

import bp.BEvent;
import bp.BProgram;
import bp.BThread;
import bp.eventSets.EventSetInterface;
import bp.search.BPAction;
import bp.search.BPState;
import bp.search.adversarial.BPGame;
import bp.search.adversarial.BPPlayer;
import bp.search.adversarial.players.BPSystemPlayer;
import bp.search.adversarial.players.EnvironmentPlayer;
import tictactoe.bThreads.DeclareWinner;
import tictactoe.bThreads.DetectDraw;
import tictactoe.bThreads.EnforceTurns;
import tictactoe.bThreads.SquareTaken;
import tictactoe.events.O;
import tictactoe.events.StaticEvents;
import tictactoe.events.X;
import tictactoe.externalApp.TicTacToe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static bp.eventSets.EventSetConstants.none;
import static tictactoe.events.StaticEvents.XEvents;

public class TTTGame extends BPGame {

    public static final BPPlayer xPlayer = new XPlayer();
    public static final BPPlayer oPlayer = new YPlayer();
    public static final BPPlayer[] players = {xPlayer, oPlayer};

    private BProgram program;
    private EnforceTurns turns;
    private Collection<SquareTaken> taken;
    private DetectDraw detectDraw;
    private Collection<BThread> xwins;
    private Collection<BThread> owins;
    private DeclareWinner declareWinner;
    private TicTacToe ttt;

    public TTTGame(BProgram bp, EnforceTurns turns,
                   Collection<SquareTaken> squaresTaken, DetectDraw draw,
                   Collection<BThread> xwins, Set<BThread> owins,
                   DeclareWinner declareWinner, TicTacToe ttt) {
        program = bp;
        this.turns = turns;
        this.taken = squaresTaken;
        detectDraw = draw;
        this.xwins = xwins;
        this.owins = owins;
        this.declareWinner = declareWinner;
        this.ttt = ttt;
    }

    @Override
    public TTTState getInitialState() {
        return new TTTState(program, ttt, taken, declareWinner);
    }

    @Override
    public BPPlayer[] getPlayers() {
        return players;
    }

    @Override
    public BPPlayer getPlayer(BPState state) {
        EventSetInterface waitedEvents = turns.getWaitedEvents();
        if (waitedEvents == XEvents) {
            return xPlayer;
        } else {
            return oPlayer;
        }
    }

    @Override
    public List<BPAction> getActions(BPState state) {
//        state.restore();
        List<BPAction> actions = new ArrayList<>();
        if (xwinEventRequested()) {
            actions.add(new BPAction(StaticEvents.XWin));
        } else if (owinEventRequested()) {
            actions.add(new BPAction(StaticEvents.OWin));
        } else if (detectDraw.getRequestedEvents().contains(StaticEvents.draw)) {
            actions.add(new BPAction(StaticEvents.draw));
        } else if (getPlayer(state) == xPlayer) {
            for (SquareTaken st : taken) {
                EventSetInterface watchedEvents = st.getWaitedEvents();
                if (watchedEvents != none) {
                    actions.add(new BPAction(new X(st._row, st._col)));
                }
            }
        } else {
            for (SquareTaken st : taken) {
                EventSetInterface watchedEvents = st.getWaitedEvents();
                if (watchedEvents != none) {
                    actions.add(new BPAction(new O(st._row,
                            st._col)));
                }
            }
        }

        // bplog("#actions possible: " + actions.size());
        bplog("actions possible: " + actions);
        return actions;
    }

    private boolean xwinEventRequested() {
        for (BThread winbt : xwins) {
            if (winbt.getRequestedEvents().contains(StaticEvents.XWin)) {
                return true;
            }
        }

        return false;
    }

    private boolean owinEventRequested() {
        for (BThread winbt : owins) {
            if (winbt.getRequestedEvents().contains(StaticEvents.OWin)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public BPState getResult(BPState state, BPAction action) {
        return action.apply(state);
    }

    @Override
    public boolean isTerminal(BPState state) {
        BEvent lastEvent = state.bp.getLastEvent();
        bplog("state is terminal? last event = " + lastEvent);
        return (lastEvent == StaticEvents.XWin
                || lastEvent == StaticEvents.OWin || lastEvent == StaticEvents.draw);
    }

    @Override
    public double getUtility(BPState state, BPPlayer player) {
        BEvent winEvent;
        BEvent loseEvent;
        if (player == xPlayer) {
            winEvent = StaticEvents.XWin;
            loseEvent = StaticEvents.OWin;
        } else {
            winEvent = StaticEvents.OWin;
            loseEvent = StaticEvents.XWin;
        }

        double retVal;
        BEvent lastEvent = state.bp.getLastEvent();
        if (lastEvent == winEvent) {
            retVal = 1;
        } else if (lastEvent == loseEvent) {
            retVal = -1;
        } else {
            retVal = 0;
        }

        bplog("utility is " + retVal);
        return retVal;
    }

    public void bplog(String s) {
        System.out.println("[" + this.getClass().getSimpleName() + "]: "
                + s);
    }

    private static class YPlayer extends BPSystemPlayer {
        private YPlayer() {
            super();
        }
    }

    /**
     * Created by orelmosheweinstock on 3/29/15.
     */
    public static class XPlayer extends EnvironmentPlayer {
        protected XPlayer() {
            super();
        }
    }
}
