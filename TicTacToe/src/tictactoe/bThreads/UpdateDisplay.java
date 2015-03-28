package tictactoe.bThreads;

import bp.BProgram;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.eventSets.EventsOfClass;
import bp.exceptions.BPJException;
import tictactoe.events.Move;
import tictactoe.externalApp.TicTacToe;

import javax.swing.*;

import static bp.eventSets.EventSetConstants.none;
import static tictactoe.events.StaticEvents.gameOver;

/**
 * BThread for updating the labels of the buttons.
 */
public class UpdateDisplay extends BThread {

    private TicTacToe ttt;
    private EventsOfClass xevents = new EventsOfClass(Move.class);

    public UpdateDisplay(TicTacToe ttt) {
        this.ttt = ttt;
        String source = "while(true){\n" +
                "var move = " + JSIdentifier() + ".bsync(none,xevents,none);\n" +
                "var btt = ttt.gui.buttons[move.row][move.col];\n" +
                "btt.setText(move.displayString());\n" +
                "}\n";
        setScript(source);
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
//            JButton btt = ttt.gui.buttons[move.row][move.col];
//            btt.setText(move.displayString());
//            // btt.setEnabled(false);
//        }
//    }
}
