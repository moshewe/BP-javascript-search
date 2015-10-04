package bp.search.informed;

import aima.core.search.framework.HeuristicFunction;
import bp.search.BPState;
import bp.search.adversarial.BPPlayer;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

/**
 * Created by Orel on 22/08/2015.
 */
public class BPJSHeuristicFunction implements HeuristicFunction {
    private Scriptable _scope;
    private final Function _func;

    public BPJSHeuristicFunction(Scriptable scope, Function func) {
        this._scope = scope;
        this._func = func;
    }

    @Override
    public double h(Object bpstate) {
        Object[] arglist = {bpstate};
        Context globalContext = ContextFactory.getGlobal().enterContext();
        Scriptable jsBPstate = (Scriptable) Context.javaToJS(bpstate, _scope);
        Double val = (Double) _func.call(globalContext, _scope,
                jsBPstate, arglist);
        Context.exit();
        return val;
    }
}
