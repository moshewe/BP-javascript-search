package bp.examples;

import bp.BEvent;
import bp.BProgram;
import bp.BThread;
import static bp.eventSets.EventSetConstants.none;
import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by orelmosheweinstock on 3/24/15.
 */
public class HotNCold{

    final Object HOT = new Object();
    final Object COLD = new Object();
    static Scriptable globalScope;
    private HotBt _hotbt;
    private ColdBt _coldbt;
    private AlternatorBt _alternator;

    @Test
    public void hotNColdTest() {
        System.out.println("starting BTs");
        setup();
        BProgram prog = new BProgram();
        prog.add(_hotbt);
        prog.add(_coldbt);
        prog.add(_alternator);
        prog.start();
    }

    private void setup() {
        Context cx = ContextFactory.getGlobal().enterContext();
        cx.setOptimizationLevel(-1); // must use interpreter mode
        try {
            globalScope = cx.initStandardObjects();
            cx.setOptimizationLevel(-1); // must use interpreter mode
            _hotbt = new HotBt();
            globalScope.put("hotBt", globalScope,
                    Context.javaToJS(_hotbt, globalScope));
            _coldbt = new ColdBt();
            globalScope.put("coldBt", globalScope,
                    Context.javaToJS(_coldbt, globalScope));
            _alternator = new AlternatorBt();
            globalScope.put("alternatorBt", globalScope,
                    Context.javaToJS(_alternator, globalScope));
            globalScope.put("hotEvent", globalScope,
                    Context.javaToJS(new BEvent("HOT!"), globalScope));
            globalScope.put("coldEvent", globalScope,
                    Context.javaToJS(new BEvent("COLD!"), globalScope));
            globalScope.put("noneEvent", globalScope,
                    Context.javaToJS(none, globalScope));
        } finally {
            Context.exit();
        }
    }

    public class HotBt extends BThread {

        public HotBt() {
            Context cx = ContextFactory.getGlobal().enterContext();
            cx.setOptimizationLevel(-1); // must use interpreter mode
            try {
                String source = "java.lang.System.out.println(\"hotbt started!\")\n" +
                        "hotBt.bsync(hotEvent,noneEvent,noneEvent)\n" +
                        "java.lang.System.out.println(\"HOT!1\")\n" +
                        "hotBt.bsync(hotEvent,noneEvent,noneEvent)\n" +
                        "java.lang.System.out.println(\"HOT!2\")\n" +
                        "hotBt.bsync(hotEvent,noneEvent,noneEvent)\n" +
                        "java.lang.System.out.println(\"HOT!3\")\n";
                _script = cx.compileString(source, "hotscript", 1, null);
            } finally {
                Context.exit();
            }
            _scope = globalScope;
        }
    }

    public class ColdBt extends BThread {

        public ColdBt() {
            Context cx = ContextFactory.getGlobal().enterContext();
            cx.setOptimizationLevel(-1); // must use interpreter mode
            try {
                String source = "java.lang.System.out.println(\"coldbt started!\")\n" +
                        "coldBt.bsync(coldEvent,noneEvent,noneEvent)\n" +
                        "java.lang.System.out.println(\"COLD!1\")\n" +
                        "coldBt.bsync(coldEvent,noneEvent,noneEvent)\n" +
                        "java.lang.System.out.println(\"COLD!2\")\n" +
                        "coldBt.bsync(coldEvent,noneEvent,noneEvent)\n" +
                        "java.lang.System.out.println(\"COLD!3\")\n";
                _script = cx.compileString(source, "coldscript", 1, null);
            } finally {
                Context.exit();
            }
            _scope = globalScope;
        }

    }

    public class AlternatorBt extends BThread {

        public AlternatorBt() {
            Context cx = ContextFactory.getGlobal().enterContext();
            cx.setOptimizationLevel(-1); // must use interpreter mode
            try {
                String source = "java.lang.System.out.println(\"alternator started!\")\n" +
                        "for(i=0;i<3;i++){\n" +
                        "java.lang.System.out.println(\"blocking hot \" + i)\n" +
                        "alternatorBt.bsync(noneEvent,coldEvent,hotEvent)\n" +
                        "java.lang.System.out.println(\"blocking cold \" + i)\n" +
                        "alternatorBt.bsync(noneEvent,hotEvent,coldEvent)\n" +
                        "}\n" +
                        "java.lang.System.out.println(\"alternator done!\")\n";
                _script = cx.compileString(source, "alternatorscript", 1, null);
            } finally {
                Context.exit();
            }
            _scope = globalScope;
        }

    }
}