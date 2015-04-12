package tictactoe.bThreads;

import bp.BThread;
import org.mozilla.javascript.Scriptable;

/**
 * BThread for updating the labels of the buttons.
 */
public class UpdateDisplay extends BThread {

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void setupScope(Scriptable programScope) {
        super.setupScope(programScope);
    }

    public UpdateDisplay() {
        String source = "while(true){\n" +
                "var move = bsync(none,moves,none);\n" +
                "var btt = ttt.gui.buttons[move.row][move.col];\n" +
                "btt.setText(move.displayString());\n" +
                "}\n";
        setScript(source);
    }
}
