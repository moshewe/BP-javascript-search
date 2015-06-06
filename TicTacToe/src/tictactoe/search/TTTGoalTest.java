package tictactoe.search;

import bp.BEvent;
import bp.search.BPState;
import bp.search.framework.BPGoalTest;

import static tictactoe.events.StaticEvents.OWin;

/**
 * Created by orelmosheweinstock on 6/6/15.
 */
public class TTTGoalTest extends BPGoalTest {
    @Override
    public boolean isGoalState(BPState state) {
        BEvent lastEvent = state._program.getLastEvent();
        return lastEvent == OWin;
    }
}
