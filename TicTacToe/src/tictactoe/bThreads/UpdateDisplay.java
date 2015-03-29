package tictactoe.bThreads;

import bp.BThread;
import tictactoe.externalApp.TicTacToe;

/**
 * BThread for updating the labels of the buttons.
 */
public class UpdateDisplay extends BThread {

//    private TicTacToe ttt;
//    private EventsOfClass xevents = new EventsOfClass(Move.class);

    public UpdateDisplay(TicTacToe ttt) {
//        this.ttt = ttt;
        String source = "while(true){\n" +
                "var move = " + jsIdentifier() +
                ".bsync(none,xevents,none);\n" +
                "var btt = ttt.gui.buttons[move.row][move.col];\n" +
                "btt.setText(move.displayString());\n" +
                "}\n";
        setScript(source);
    }

    @Override
    public void setupScope() {

    }

//    public void runBThread() throws BPJException {
//        interruptingEvents = new EventSet(gameOver);
//
//        while (true) {
//
//            // Wait for an event
//            bsync(none, xevents, none);
//
//            // Update the board
//            Move move = (Move) bp.getLastEvent();
//            JButton btt = ttt.gui.buttons[move._row][move._col];
//            btt.setText(move.displayString());
//            // btt.setEnabled(false);
//        }
//    }
}
