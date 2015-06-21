package bprobocode.events.input.battleEvents;

import bprobocode.events.BPBattleEvent;
import robocode.control.events.BattleFinishedEvent;

/**
 * Created by orelmosheweinstock on 6/21/15.
 */
public class OnBattleFinished extends BPBattleEvent<BattleFinishedEvent> {
    public OnBattleFinished(BattleFinishedEvent event) {
        super(event);
    }
}
