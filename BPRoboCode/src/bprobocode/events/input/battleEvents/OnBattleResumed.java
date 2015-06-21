package bprobocode.events.input.battleEvents;

import bprobocode.events.BPBattleEvent;
import robocode.control.events.BattleResumedEvent;

/**
 * Created by orelmosheweinstock on 6/21/15.
 */
public class OnBattleResumed extends BPBattleEvent<BattleResumedEvent> {
    public OnBattleResumed(BattleResumedEvent event) {
        super(event);
    }
}
