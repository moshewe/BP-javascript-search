package tictactoe.bThreads;

import bp.BThread;
import tictactoe.externalApp.TicTacToe;

/**
 * BThread for updating the labels of the buttons.
 */
public class UpdateDisplay extends BThread {

    public UpdateDisplay() {
        String source = "while(true){\n" +
                "var move = " + jsIdentifier() +
                ".bsync(none,moves,none);\n" +
                "var btt = ttt.gui.buttons[move.row][move.col];\n" +
                "btt.setText(move.displayString());\n" +
                "}\n";
        setScript(source);
    }
}
