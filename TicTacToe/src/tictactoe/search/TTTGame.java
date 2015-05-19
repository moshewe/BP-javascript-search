package tictactoe.search;

import bp.BEvent;
import bp.BProgram;
import bp.BThread;
import bp.search.BPState;
import bp.search.adversarial.BPGame;
import bp.search.adversarial.BPPlayer;
import bp.search.adversarial.players.BPSystemPlayer;
import bp.search.adversarial.players.EnvironmentPlayer;
import tictactoe.events.StaticEvents;

import java.util.List;

import static bp.BProgramControls.debugMode;

public class TTTGame extends BPGame {

    public static final BPPlayer xPlayer = new XPlayer();
    public static final BPPlayer oPlayer = new OPlayer();
    public static final BPPlayer[] players = {xPlayer, oPlayer};
    private List<BThread> _taken;

    public TTTGame(BProgram bp, List<BThread> taken) {
        super(bp);
        _taken = taken;
    }

    @Override
    public BPPlayer[] getPlayers() {
        return players;
    }

    @Override
    public TTTState getInitialState() {
        TTTState state = new TTTState(_program, _taken);
        return (TTTState) applySimStart(state);
    }

    @Override
    public boolean isTerminal(BPState state) {
        BEvent lastEvent = state._program.getLastEvent();
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
        BEvent lastEvent = state._program.getLastEvent();
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
        if (debugMode)
            System.out.println("[" + this.getClass().getSimpleName() + "]: "
                    + s);
    }

    private static class OPlayer extends BPSystemPlayer {
        private OPlayer() {
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
