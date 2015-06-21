package bprobocode.events.input.battleEvents;

import bprobocode.events.BPBattleEvent;
import robocode.control.events.RoundEndedEvent;

/**
 * Created by orelmosheweinstock on 6/21/15.
 */
public class OnRoundEnded extends BPBattleEvent<RoundEndedEvent> {
    public OnRoundEnded(RoundEndedEvent event) {
        super(event);
    }
}
