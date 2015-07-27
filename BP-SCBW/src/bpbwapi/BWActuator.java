package bpbwapi;

import bp.BEvent;
import bp.actuation.BActuator;
import bwapi.Game;
import bwapi.Player;

/**
 * Created by moshewe on 27/07/2015.
 */
public abstract class BWActuator<EventType extends BEvent,
        ReturnType> extends BActuator<EventType, ReturnType> {

    protected Game _game;
    protected Player _self;

    public BWActuator(Game game) {
        _game = game;
        _self = game.self();
    }

}
