package tictactoe.bThreads;

import bp.BThread;

/**
 * BThread that blocks _players from playing when its not their turn.
 */
public class EnforceTurns extends BThread {

    public EnforceTurns() {
        String source = "while (true) {\n" +
                "bsync(none, xevents, oevents);\n" +
                "bsync(none, oevents, xevents);\n" +
//                jsIdentifier() + ".bsync(none, xevents, oevents);\n" +
//                jsIdentifier() + ".bsync(none, oevents, xevents);\n" +
                "}\n";
        setScript(source);
    }

}
