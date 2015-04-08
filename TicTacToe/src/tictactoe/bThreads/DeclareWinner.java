package tictactoe.bThreads;

import bp.BThread;
import bp.eventSets.EventSet;
import tictactoe.externalApp.TicTacToe;

import static tictactoe.events.StaticEvents.*;

/**
 * BThread that waits for a Win message and prints its message
 */
public class DeclareWinner extends BThread {

    public final EventSet eset;

    public DeclareWinner(TicTacToe ttt) {
//		this.ttt = ttt;
        eset = new EventSet("WinnerDecided", XWin, OWin, draw);
//        _btScopeObjects.add(eset);
//        String source = "var lastEvent = " + jsIdentifier() +
//                ".bsync(none, " + eset.jsIdentifier() + ", none);\n" +
        String source = "var lastEvent = bsync(none, eset, none);\n" +
                "var msg;\n" +
                "if (lastEvent == XWin) {\n" +
                "msg = \"X Wins\";\n" +
                "} else if (lastEvent == OWin) {\n" +
                "msg = \"O Wins\";\n" +
                "} else\n" +
                "msg = \"A Draw\";\n" +
                "ttt.gui.message.setText(msg);\n" +
//                jsIdentifier() + ".bsync(gameOver, none, none);\n" +
                "bsync(gameOver, none, none);\n" +
                "ttt.gui.buttons[0][0].setEnabled(false);\n" +
                "ttt.gui.buttons[0][1].setEnabled(false);\n" +
                "ttt.gui.buttons[0][2].setEnabled(false);\n" +
                "ttt.gui.buttons[1][0].setEnabled(false);\n" +
                "ttt.gui.buttons[1][1].setEnabled(false);\n" +
                "ttt.gui.buttons[1][2].setEnabled(false);\n" +
                "ttt.gui.buttons[2][0].setEnabled(false);\n" +
                "ttt.gui.buttons[2][1].setEnabled(false);\n" +
                "ttt.gui.buttons[2][2].setEnabled(false);\n";
        setScript(source);
    }

//	public void runBThread() throws BPJException {
//		bsync(none, new EventSet("WinnerDecided", XWin, OWin, draw), none);
//		String msg;
//		if (bp.getLastEvent() == XWin) {
//			msg = "X Wins";
//		} else if (bp.getLastEvent() == OWin) {
//			msg = "O Wins";
//		} else
//			msg = "A Draw";
//
//		System.out.println(msg);
//		ttt.gui.message.setText(msg);
//		bsync(gameOver, none, none);
//	}

}
