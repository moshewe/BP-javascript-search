package tictactoe.bThreads;

import static bp.eventSets.EventSetConstants.none;
import static tictactoe.events.StaticEvents.gameOver;

import javax.swing.JButton;

import tictactoe.events.Move;
import tictactoe.externalApp.TicTacToe;
import bp.BProgram;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.eventSets.EventsOfClass;
import bp.exceptions.BPJException;

/**
 * BThread for updating the labels of the buttons.
 */
public class UpdateDisplay extends BThread {

	private TicTacToe ttt;

	public UpdateDisplay(TicTacToe ttt) {
		this.ttt = ttt;
	}

	public void runBThread() throws BPJException {
		interruptingEvents = new EventSet(gameOver);

		BProgram bp = getBProgram();

		while (true) {

			// Wait for an event
			bsync(none, new EventsOfClass(Move.class), none);

			// Update the board
			Move move = (Move) bp.getLastEvent();
			JButton btt = ttt.gui.buttons[move.row][move.col];
			btt.setText(move.displayString());
			// btt.setEnabled(false);
		}
	}
}
