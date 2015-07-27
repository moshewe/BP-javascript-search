package bp.actuation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by moshewe on 27/07/2015.
 */
public class ActuatorFactory<Actuator extends BActuator> {

    protected Constructor<Actuator> _ctor;
    protected Object[] _actParams;

    public ActuatorFactory(Class<Actuator> clazz, Object[] actParams) {
        Constructor<?>[] constructors = clazz.getConstructors();
        _ctor = (Constructor<Actuator>) constructors[0];
        setParameters(actParams);
    }

    public Actuator newInstance(){
        try {
           return _ctor.newInstance(_actParams);
        } catch (InstantiationException e) {
            handleInstantiationException(e);
        } catch (IllegalAccessException e) {
            handeIllegalAccessException(e);
        } catch (InvocationTargetException e) {
            HandleInvocationTargetException(e);
        }

        return null;
    }

    private void HandleInvocationTargetException(InvocationTargetException e) {
        e.printStackTrace();
    }

    private void handeIllegalAccessException(IllegalAccessException e) {
        e.printStackTrace();
    }

    private void handleInstantiationException(InstantiationException e) {
        e.printStackTrace();
    }

    public void setParameters(List<Object> parameters) {
        _actParams = parameters.toArray();
    }

    public void setParameters(Object... parameters) {
        _actParams = parameters;
    }
}
