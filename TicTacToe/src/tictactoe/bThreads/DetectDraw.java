package tictactoe.bThreads;

import bp.BThread;

public class DetectDraw extends BThread {
//    public void runBThread() throws BPJException {
//        interruptingEvents = new EventSet(gameOver);
//
//        // Wait for 9 events
//        for (int count = 0; count < 9; count++) {
//            bsync(none, moves, none);
//        }
//        bsync(draw, none, none);
//    }

    public DetectDraw() {
        String source = "for (count = 0; count < 9; count++) {\n" +
                jsIdentifier() + ".bsync(none, moves, none);\n" +
                "}\n" +
                jsIdentifier() + ".bsync(draw, none, none);\n";
        setScript(source);
    }

    public String toString() {
        return "DetectDraw";
    }

}
