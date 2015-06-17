package bp.search.bthreads;

import bp.BThread;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import java.io.InputStream;

/**
 * Created by orelmosheweinstock on 4/25/15.
 */
public class SimulatorBThread extends BThread {

    public boolean _simMode = false;

    public SimulatorBThread(String name, Function func) {
        super(name, func);
    }

    @Override
    protected void generateBThreadScope(Scriptable programScope) {
        super.generateBThreadScope(programScope);
        InputStream res = SimulatorBThread.class.getResourceAsStream("simscope.js");
        Scriptable simScope = (Scriptable)
                evaluateInBThreadScope(res, "simscope");

        simScope.setPrototype(_scope);
        _scope = simScope;
    }
}
