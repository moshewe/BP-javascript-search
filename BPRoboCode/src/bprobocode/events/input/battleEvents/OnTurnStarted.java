package bprobocode.events.input.battleEvents;

import bprobocode.events.BPBattleEvent;
import robocode.control.events.TurnStartedEvent;

/**
 * Created by orelmosheweinstock on 6/21/15.
 */
public class OnTurnStarted extends BPBattleEvent<TurnStartedEvent> {
    public OnTurnStarted(TurnStartedEvent event) {
        super(event);
    }
}
