package bp.examples;

import bp.Arbiter;
import bp.BEvent;
import bp.BPJavascriptApplication;
import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by orelmosheweinstock on 3/24/15.
 */
public class HotNCold extends BPJavascriptApplication{

    public HotNCold() {
        super();
        _bp.setName("HotNCold");
        _arbiter = new Arbiter();
        _bp.setArbiter(_arbiter);

        evaluateInGlobalScope("out/production/BP-javascript/bp/examples/HotNCold.js");

        setupBThreadScopes();
    }

    @Test
    public void hotNColdTest() {
        HotNCold hnc = new HotNCold();
        hnc.start();
    }

    @Override
    protected void setupGlobalScope() {
        super.setupGlobalScope();
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            cx.setOptimizationLevel(-1); // must use interpreter mode
            _globalScope.put("hotEvent", _globalScope,
                    Context.javaToJS(new BEvent("HOT!"), _globalScope));
            _globalScope.put("coldEvent", _globalScope,
                    Context.javaToJS(new BEvent("COLD!"), _globalScope));
        } finally {
            Context.exit();
        }
    }
}