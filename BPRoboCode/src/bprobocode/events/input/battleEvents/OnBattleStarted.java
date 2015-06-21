package bprobocode.events.input.battleEvents;

import bprobocode.events.BPBattleEvent;
import robocode.control.events.BattleStartedEvent;

/**
 * Created by orelmosheweinstock on 6/21/15.
 */
public class OnBattleStarted extends BPBattleEvent<BattleStartedEvent> {
    public OnBattleStarted(BattleStartedEvent event) {
        super(event);
    }
}
