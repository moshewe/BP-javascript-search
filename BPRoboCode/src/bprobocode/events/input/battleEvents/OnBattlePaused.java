package bprobocode.events.input.battleEvents;

import bprobocode.events.BPBattleEvent;
import robocode.control.events.BattlePausedEvent;

/**
 * Created by orelmosheweinstock on 6/21/15.
 */
public class OnBattlePaused extends BPBattleEvent<BattlePausedEvent> {
    public OnBattlePaused(BattlePausedEvent event) {
        super(event);
    }
}
