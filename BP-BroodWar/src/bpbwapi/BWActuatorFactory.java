package bpbwapi;

import bp.actuation.ActuatorFactory;
import bwapi.Game;

/**
 * Created by orelmosheweinstock on 7/31/15.
 */
public class BWActuatorFactory<Actuator extends BWActuator>
        extends ActuatorFactory<Actuator> {

    public BWActuatorFactory(Class<Actuator> clazz, Game game) {
        super(clazz, game);
    }
}
