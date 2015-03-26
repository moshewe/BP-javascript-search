package bp;

import org.mozilla.javascript.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by orelmosheweinstock on 3/24/15.
 */
public class HotNCold {

    final Object HOT = new Object();
    final Object COLD = new Object();
    Scriptable globalScope;
    private HotBt _hotbt;
    private ColdBt _coldbt;
    private AlternatorBt _alternator;

    public static void main(String[] args) {
        System.out.println("starting BTs");
        HotNCold hnc = new HotNCold();
        hnc.setup();
        Collection<BThread> bThreads = new ArrayList<BThread>();
        bThreads.add(hnc._hotbt);
        bThreads.add(hnc._coldbt);
        bThreads.add(hnc._alternator);
        for (BThread bt : bThreads) {
            bt.start();
        }
        while (!bThreads.isEmpty()) {
            System.out.println("last event is: " + lastEvent);
            for (BThread bt : bThreads) {
                if (bt._request.equals(lastEvent) ||
                        bt._wait.equals(lastEvent)) {
                    bt.resume(lastEvent);
                }
            }
            System.out.println("pause for user");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                String name = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setup() {
        Context cx = ContextFactory.getGlobal().enterContext();
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
        } finally {
            Context.exit();
        }
    }

    class HotBt extends BThread {

        public HotBt() {
            Context cx = ContextFactory.getGlobal().enterContext();
            try {
                String source = "java.lang.System.out.println(\"hotbt started!\")\n" +
                        "hotBt.bsync(\"hot\",[],[])\n" +
                        "java.lang.System.out.println(\"HOT!1\")\n" +
                        "hotBt.bsync(\"hot\",[],[])\n" +
                        "java.lang.System.out.println(\"HOT!2\")\n" +
                        "hotBt.bsync(\"hot\",[],[])\n" +
                        "java.lang.System.out.println(\"HOT!3\")\n";
                _script = cx.compileString(source, "hotscript", 1, null);
            } finally {
                Context.exit();
            }
            _scope = globalScope;
        }
    }

    class ColdBt extends BThread {

        public ColdBt() {
            Context cx = ContextFactory.getGlobal().enterContext();
            try {
                String source = "java.lang.System.out.println(\"coldbt started!\")\n" +
                        "coldBt.bsync(\"cold\",[],[])\n" +
                        "java.lang.System.out.println(\"COLD!1\")\n" +
                        "coldBt.bsync(\"cold\",[],[])\n" +
                        "java.lang.System.out.println(\"COLD!2\")\n" +
                        "coldBt.bsync(\"cold\",[],[])\n" +
                        "java.lang.System.out.println(\"COLD!3\")\n";
                _script = cx.compileString(source, "coldscript", 1, null);
            } finally {
                Context.exit();
            }
            _scope = globalScope;
        }

    }

    class AlternatorBt extends BThread {

        public AlternatorBt() {
            Context cx = ContextFactory.getGlobal().enterContext();
            try {
                String source = "java.lang.System.out.println(\"alternator started!\")\n" +
                        "for(i=0;i<3;i++){\n" +
                        "java.lang.System.out.println(\"blocking hot \" + i)\n" +
                        "alternatorBt.bsync([],\"cold\",\"hot\")\n" +
                        "java.lang.System.out.println(\"blocking cold \" + i)\n" +
                        "alternatorBt.bsync([],\"hot\",\"cold\")\n" +
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