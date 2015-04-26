package bp.search.bthreads;

import bp.BThread;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

/**
 * Created by orelmosheweinstock on 4/25/15.
 */
public class SimulatorBThread extends BThread {

    public boolean _simMode = false;

    public SimulatorBThread(String name, Function func) {
        super(name, func);
    }

//    @Override
//    public BEvent bsync(RequestableInterface requestedEvents,
//                        EventSetInterface waitedEvents, EventSetInterface blockedEvents) {
////        bplog("in simulation bsync");
//        if (simMode) {
//            return super.bsync(requestedEvents, waitedEvents, blockedEvents);
//        } else {
//            EventSet eset = new EventSet(requestedEvents, waitedEvents, SimStartEvent.getInstance());
//            BEvent ev = super.bsync(none, eset, blockedEvents);
//            if (ev == SimStartEvent.getInstance()) {
//                bplog("going into simulation mode!");
//                simMode = true;
//                return bsync(requestedEvents, waitedEvents, blockedEvents);
//            }
//            return ev;
//        }
//    }


    @Override
    protected void generateBThreadScope(Scriptable programScope) {
        super.generateBThreadScope(programScope);
        String path = "/Users/orelmosheweinstock/IdeaProjects/BP-javascript-search/out/production/BP-javascript-search/bp/search/bthreads/simscope.js";
        Scriptable simScope = (Scriptable) evaluateInBThreadScope(path);
//        bplog("simulation scope is " + simScope);
        simScope.setPrototype(_scope);
        _scope = simScope;
    }
}
