package bp.examples;

import bp.Arbiter;
import bp.BEvent;
import bp.BJavascriptProgram;
import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;

import java.net.URL;

/**
 * Created by orelmosheweinstock on 3/24/15.
 */
public class HotNCold extends BJavascriptProgram {

    private class HotEvent extends BEvent {

        public HotEvent() {
            _name = "hotEvent";
            _outputEvent = true;
        }
    }

    private class ColdEvent extends BEvent {

        public ColdEvent() {
            _name = "coldEvent";
            _outputEvent = true;
        }
    }

    private class AllDoneEvent extends BEvent {

        public AllDoneEvent() {
            _name = "ALLDONE";
            _outputEvent = true;
        }
    }

    public HotNCold() {
        super();
        setName("HotNCold");
        _arbiter = new Arbiter();
        setArbiter(_arbiter);

//        evaluateInGlobalScope("out/production/BP-javascript/bp/examples/HotNCold.js");
//        String s = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        evaluateInGlobalScope(HotNCold.class.getResourceAsStream("HotNCold.js"), "HotNCold");

        setupBThreadScopes();
    }

    @Test
    public void hotNColdTest() throws InterruptedException {
        final HotNCold hnc = new HotNCold();
        hnc.start();
        System.out.println("starting output event read loop");
//      imagine this is run in a separate thread on another machine
        BEvent outputEvent = hnc.readOutputEvent();
        while (!outputEvent.getName().equals("ALLDONE")) {
            System.out.println("program emitted " + outputEvent);
            outputEvent = hnc.readOutputEvent();
        }
        System.out.println("got ALLDONE event");
    }

    @Override
    protected void setupGlobalScope() {
        super.setupGlobalScope();
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            cx.setOptimizationLevel(-1); // must use interpreter mode
            _globalScope.put("hotEvent", _globalScope,
                    Context.javaToJS(new HotEvent(), _globalScope));
            _globalScope.put("coldEvent", _globalScope,
                    Context.javaToJS(new ColdEvent(), _globalScope));
            _globalScope.put("allDone", _globalScope,
                    Context.javaToJS(new AllDoneEvent(), _globalScope));
        } finally {
            Context.exit();
        }
    }
}