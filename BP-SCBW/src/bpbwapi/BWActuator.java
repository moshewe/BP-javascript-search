package bpbwapi;

import bp.BActuator;
import bpbwapi.events.input.OnStartEvent;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Player;
import bwta.BWTA;

/**
 * Created by moshewe on 07/07/2015.
 */
public class BWActuator extends BActuator{

    protected final Mirror _mirror;
    protected Game _game;
    protected Player _self;

    public BWActuator(Mirror mirror) {
        _mirror = mirror;
    }

    public void visit(OnStartEvent event) {
        _game = _mirror.getGame();
        _self = _game.self();

        //Use BWTA to analyze map
        //This may take a few minutes if the map is processed first time!
        bplog("Analyzing map...");
        BWTA.readMap();
        BWTA.analyze();
        bplog("Map data ready");
    }
}
