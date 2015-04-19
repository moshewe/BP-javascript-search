package bp.search;

import bp.BPJavascriptApplication;
import bp.BThread;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Function;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by orelmosheweinstock on 4/6/15.
 */
public abstract class BPSearchApplication extends BPJavascriptApplication {

    private String initScript;

    protected List<BThread> _simBThreads = new ArrayList<>();


    @Override
    protected void setupGlobalScope() {
        super.setupGlobalScope();
        initScript = "/Users/orelmosheweinstock/IdeaProjects/BP-javascript-search/BP-javascript-search/src/bp/search/globalScopeInit.js";
        evaluateInGlobalScope(initScript);
        String path = "/Users/orelmosheweinstock/IdeaProjects/BP-javascript-search/out/production/BP-javascript-search/bp/search/bthreads/SimulatorBThread.js";
        evaluateInGlobalScope(path);
    }

    public void registerSimBThread(Function func) {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            BThread simBT = new BThread(func);
            _bp.add(simBT);
            _simBThreads.add(simBT);
        } finally {
            Context.exit();
        }
    }

}