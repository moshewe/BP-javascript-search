package bp.search.informed;

import aima.core.search.framework.HeuristicFunction;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

/**
 * Created by Orel on 22/08/2015.
 */
public class BPHeuristicFunction implements HeuristicFunction {
    private Scriptable _scope;
    private final Function _func;

    public BPHeuristicFunction(Scriptable scope, Function func) {
        this._scope = scope;
        this._func = func;
    }

    @Override
    public double h(Object o) {
        Object[] arglist = {o};
        Context globalContext = ContextFactory.getGlobal().enterContext();
        Scriptable oJS = (Scriptable) Context.javaToJS(o, _scope);
        Double val = (Double) _func.call(globalContext, _scope,
                oJS, arglist);
        Context.exit();
        return val;
    }
}
