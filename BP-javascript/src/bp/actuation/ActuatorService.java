package bp.actuation;

import bp.BEvent;
import bp.BProgramControls;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by orelmosheweinstock on 6/14/15.
 */
public class ActuatorService implements
        Map<BEvent, BActuator> {

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

    @Override
    public int size() {
        return _map.size();
    }

    @Override
    public boolean isEmpty() {
        return _map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return _map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return _map.containsValue(value);
    }

    @Override
    public BActuator get(Object key) {
        return null;
    }

    @Override
    public BActuator put(BEvent key, BActuator value) {
        return null;
    }

    public ActuatorFactory put(Class<? extends BEvent> key, ActuatorFactory value) {
        return _map.put(key, value);
    }

    @Override
    public BActuator remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends BEvent, ? extends BActuator> m) {
    }

    @Override
    public void clear() {
        _map.clear();
    }

    @Override
    public Set<BEvent> keySet() {
        return null;
    }

    @Override
    public Collection<BActuator> values() {
        return null;
    }

    @Override
    public Set<Entry<BEvent, BActuator>> entrySet() {
        return null;
    }
}
