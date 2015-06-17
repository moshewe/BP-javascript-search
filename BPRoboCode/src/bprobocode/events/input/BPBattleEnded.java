package bprobocode.events.input;

import robocode.BattleEndedEvent;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public class BPBattleEnded extends BPRobocodeEvent {
    public BPBattleEnded(BattleEndedEvent event) {
        super(event);
    }

    @Override
    public BattleEndedEvent getRobocodeEvent() {
        return (BattleEndedEvent) super.getRobocodeEvent();
    }
}
