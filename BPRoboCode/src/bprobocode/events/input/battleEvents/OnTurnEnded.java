package bprobocode.events.input.battleEvents;

import bprobocode.events.BPBattleEvent;
import robocode.control.events.TurnEndedEvent;

/**
 * Created by orelmosheweinstock on 6/21/15.
 */
public class OnTurnEnded extends BPBattleEvent<TurnEndedEvent> {
    public OnTurnEnded(TurnEndedEvent event) {
        super(event);
    }
}
