package bprobocode.events.input.battleEvents;

import bprobocode.events.BPBattleEvent;
import robocode.control.events.RoundStartedEvent;

/**
 * Created by orelmosheweinstock on 6/21/15.
 */
public class OnRoundStarted extends BPBattleEvent<RoundStartedEvent> {
    public OnRoundStarted(RoundStartedEvent event) {
        super(event);
    }
}
