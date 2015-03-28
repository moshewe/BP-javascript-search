package tictactoe.search;

import bp.BProgram;
import bp.eventSets.EventSetConstants;
import bp.search.BPState;
import tictactoe.bThreads.DeclareWinner;
import tictactoe.bThreads.SquareTaken;
import tictactoe.externalApp.TicTacToe;

import javax.swing.*;
import java.io.IOException;
import java.util.Collection;

public class TTTState extends BPState {

	private Collection<SquareTaken> taken;
	private DeclareWinner declareWinner;
	private TicTacToe ttt;

	public TTTState(BProgram bp, TicTacToe ttt, Collection<SquareTaken> taken,
			DeclareWinner declareWinner) throws IOException,
			ClassNotFoundException {
		super(bp);
		this.ttt = ttt;
		this.taken = taken;
		this.declareWinner = declareWinner;
	}

	public TTTState(TTTState other) {
		super(other);
		this.taken = other.taken;
		this.declareWinner = other.declareWinner;
		this.ttt = other.ttt;
	}

	@Override
	public TTTState copy() {
		return new TTTState(this);
	}

	@Override
	public void restore() {
		super.restore();
		for (SquareTaken st : taken) {
			if (st.getBlockedEvents() == EventSetConstants.none) {
				JButton btt = ttt.gui.buttons[st.row][st.col];
				btt.setEnabled(true);
				btt.setText("");
			}
		}

		if (declareWinner.getRequestedEvents() == EventSetConstants.none) {
			ttt.gui.message.setText("");
		}
	}

}
