package bprobocode.events.input.robocodeEvents;

import robocode.BattleEndedEvent;
import robocode.control.events.BattleStartedEvent;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public class BPBattleEnded extends BPRobocodeEvent {
    public BPBattleEnded(BattleEndedEvent event) {
        super(event);
    }

    @Override
    public BattleStartedEvent getWrappedEvent() {
        return (BattleStartedEvent) super.getWrappedEvent();
    }
}
