package bp.actuation;

import bp.BEvent;

/**
 * Created by orelmosheweinstock on 7/31/15.
 */
public interface IActuatorService {

    void actuate(BEvent key);

    void register(Class<? extends BEvent> key, ActuatorFactory value);
}
