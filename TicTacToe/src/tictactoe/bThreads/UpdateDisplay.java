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
