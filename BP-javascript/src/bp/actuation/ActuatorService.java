package bp.actuation;

import bp.BEvent;
import bp.BProgramControls;

import java.util.HashMap;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public class ActuatorService implements IActuatorService {

    HashMap<Class<? extends BEvent>, ActuatorFactory> _map;

    public ActuatorService() {
        _map = new HashMap<>();
    }

    protected void bplog(String s) {
        if (BProgramControls.debugMode)
            System.out.println(getClass().getSimpleName() + ": " + s);
    }

    public void actuate(BEvent key) {
        ActuatorFactory factory = _map.get(key.getClass());
        if (factory == null) return;
        BActuator actuator = factory.newInstance();
        actuator.actuate(key);
    }

    public boolean containsEvent(BEvent key) {
        return _map.containsKey(key);
    }

    public void register(Class<? extends BEvent> key, ActuatorFactory value) {
        _map.put(key, value);
    }

}
