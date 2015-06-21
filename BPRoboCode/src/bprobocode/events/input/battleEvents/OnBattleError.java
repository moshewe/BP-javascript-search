package bprobocode.events.input.battleEvents;

import bprobocode.events.BPBattleEvent;
import robocode.control.events.BattleErrorEvent;

/**
 * Created by orelmosheweinstock on 6/21/15.
 */
public class OnBattleError extends BPBattleEvent<BattleErrorEvent> {
    public OnBattleError(BattleErrorEvent event) {
        super(event);
    }
}
