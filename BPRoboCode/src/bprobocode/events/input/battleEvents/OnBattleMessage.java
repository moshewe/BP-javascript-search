package bprobocode.events.input.battleEvents;

import bprobocode.events.BPBattleEvent;
import robocode.control.events.BattleMessageEvent;

/**
 * Created by orelmosheweinstock on 6/21/15.
 */
public class OnBattleMessage extends BPBattleEvent<BattleMessageEvent> {
    public OnBattleMessage(BattleMessageEvent event) {
        super(event);
    }
}
