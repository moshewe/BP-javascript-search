package tictactoe.bThreads;

import bp.BThread;
import bp.eventSets.EventSet;
import bp.exceptions.BPJException;
import tictactoe.externalApp.TicTacToe;

import static bp.eventSets.EventSetConstants.none;
import static tictactoe.events.StaticEvents.*;

/**
 * BThread that waits for a Win message and prints its message
 */
public class DeclareWinner extends BThread {

	private TicTacToe ttt;

	public DeclareWinner(TicTacToe ttt) {
		this.ttt = ttt;
	}

	public void runBThread() throws BPJException {
		bsync(none, new EventSet("WinnerDecided", XWin, OWin, draw), none);
		String msg;
		if (bp.getLastEvent() == XWin) {
			msg = "X Wins";
		} else if (bp.getLastEvent() == OWin) {
			msg = "O Wins";
		} else
			msg = "A Draw";

		System.out.println(msg);
		ttt.gui.message.setText(msg);
		bsync(gameOver, none, none);
	}
}
