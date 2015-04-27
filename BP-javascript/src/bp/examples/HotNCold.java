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
        File dir = new File(".");
        try {
            bplog(dir.getCanonicalFile().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        evaluateInGlobalScope("./BP-javascript/src/bp/examples/HotNCold.js");
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

//    public class HotBt extends BThread {
//
//        public String hotStr = "\"HOT!1\"";
//
//        public HotBt() {
//            String source = "java.lang.System.out.println(\"hotbt started!\")\n" +
//                    "bsync(hotEvent,noneEvent,noneEvent)\n" +
//                    "java.lang.System.out.println(hotStr)\n" +
//                    "var ans = bsync(hotEvent,noneEvent,noneEvent)\n" +
//                    "java.lang.System.out.println(ans)\n" +
//                    "bsync(hotEvent,noneEvent,noneEvent)\n" +
//                    "java.lang.System.out.println(\"HOT!3\")\n";
//            setScript(source);
//            setupScope(globalScope);
//        }
//    }

//    public class ColdBt extends BThread {
//
//        public ColdBt() {
//            String source = "java.lang.System.out.println(\"coldbt started!\")\n" +
//                    "bsync(coldEvent,noneEvent,noneEvent)\n" +
////                    _name + ".bsync(coldEvent,noneEvent,noneEvent)\n" +
//                    "java.lang.System.out.println(\"COLD!1\")\n" +
//                    "bsync(coldEvent,noneEvent,noneEvent)\n" +
////                    _name + ".bsync(coldEvent,noneEvent,noneEvent)\n" +
//                    "java.lang.System.out.println(\"COLD!2\")\n" +
//                    "bsync(coldEvent,noneEvent,noneEvent)\n" +
////                    _name + ".bsync(coldEvent,noneEvent,noneEvent)\n" +
//                    "java.lang.System.out.println(\"COLD!3\")\n";
//            setScript(source);
//            setupScope(globalScope);
//        }
//    }

//    public class AlternatorBt extends BThread {
//
//        public AlternatorBt() {
//            String source = "java.lang.System.out.println(\"alternator started!\")\n" +
//                    "for(i=0;i<3;i++){\n" +
//                    "java.lang.System.out.println(\"blocking hot \" + i)\n" +
//                    "bsync(noneEvent,coldEvent,hotEvent)\n" +
//                    "java.lang.System.out.println(\"blocking cold \" + i)\n" +
//                    "bsync(noneEvent,hotEvent,coldEvent)\n" +
//                    "}\n" +
//                    "java.lang.System.out.println(\"alternator done!\")\n";
//            setScript(source);
//            setupScope(globalScope);
//        }
//
//    }
}