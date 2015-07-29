package bpbwapi.events;

import bp.BEvent;
import bwapi.Player;

/**
 * Created by moshewe on 28/07/2015.
 */
public class PlayerDropped extends PlayerEvent {
    public PlayerDropped(Player event) {
        super(event);
    }
}
