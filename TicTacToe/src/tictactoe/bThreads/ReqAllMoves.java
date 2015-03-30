package tictactoe.bThreads;

import bp.BThread;

/**
 * Created by orelmosheweinstock on 3/30/15.
 */
public class ReqAllMoves extends BThread {

    public ReqAllMoves() {
        super();
        String source = "while(true){\n" +
                "bsynch(xevents + oevents, none, none);\n" +
                "}";
        setScript(source);
    }
}
