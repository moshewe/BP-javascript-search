package bp.search.informed;

import aima.core.search.framework.HeuristicFunction;
import aima.core.search.informed.AStarSearch;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

/**
 * Created by Orel on 22/08/2015.
 */
public class AStarArbiter extends InformedSearchArbiter {

    /**
     * All these variables must be set in advance for the
     * application to work.
     */
    private HeuristicFunction _hf;

    public AStarArbiter() {
        BPQueueSearch qsearch = new BPQueueSearch();
        _algorithm = new AStarSearch(qsearch, _hf);
    }

    public void registerHeuristicFunction(final Function func) {
        _hf = new BPJSHeuristicFunction((Scriptable) new Object(), func);
    }

}
