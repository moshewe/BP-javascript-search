package bprobocode.events.input.battleEvents;

import bprobocode.events.BPBattleEvent;
import robocode.control.events.BattleCompletedEvent;

/**
 * Created by orelmosheweinstock on 6/21/15.
 */
public class OnBattleCompleted extends BPBattleEvent<BattleCompletedEvent> {
    public OnBattleCompleted(BattleCompletedEvent event) {
        super(event);
    }
}
