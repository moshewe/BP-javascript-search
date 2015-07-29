package bpbwapi.events;

import bp.BEvent;
import bp.events.WrapperEvent;
import bwapi.Player;

/**
 * Created by moshewe on 28/07/2015.
 */
public class PlayerEvent extends WrapperEvent<Player>{

    public PlayerEvent(Player event) {
        super(event);
    }
}
