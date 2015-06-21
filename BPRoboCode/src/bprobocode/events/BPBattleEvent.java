package bprobocode.events;

import bp.events.WrapperEvent;
import robocode.control.events.BattleEvent;

/**
 * Created by orelmosheweinstock on 6/19/15.
 */
public abstract class BPBattleEvent<BE extends BattleEvent>
        extends WrapperEvent<BE> {

    public BPBattleEvent(BE event) {
        super(event);
    }
}
