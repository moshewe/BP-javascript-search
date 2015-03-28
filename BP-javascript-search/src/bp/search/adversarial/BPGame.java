package bp.search.adversarial;

import aima.core.search.adversarial.Game;
import bp.BEvent;
import bp.eventSets.RequestableInterface;
import bp.search.BPAction;
import bp.search.BPState;
import bp.search.BTState;
import bp.search.adversarial.players.BPSystemPlayer;
import bp.search.adversarial.players.EnvironmentPlayer;
import bp.search.bthreads.TerminalStateDetector;
import bp.search.bthreads.UtilityEventListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BPGame implements Game<BPState, BPAction, BPPlayer> {

	protected static BPPlayer[] players = { BPSystemPlayer.instance,
			EnvironmentPlayer.instance };

	/**
	 * Injected into the initial state to allow automatic cutoff.
	 */
	protected TerminalStateDetector term = new TerminalStateDetector();

	/**
	 * Injected into the initial state to calculate the utility of a state
	 * according to its event history.
	 */
	protected UtilityEventListener util = new UtilityEventListener();

	@Override
	public BPState getInitialState() {
		return null;
	}

	@Override
	public BPPlayer[] getPlayers() {
		return players;
	}

	@Override
	public BPPlayer getPlayer(BPState state) {

		return null;

	}

	@Override
	public List<BPAction> getActions(BPState state) {
		List<BPAction> ans = new ArrayList<BPAction>();
		for (BTState bts : state.getBTstates()) {
			for (RequestableInterface req : bts.requestedEvents) {
				for (BEvent e : req.getEventList()) {
					if (!state.getBp().isBlocked(e)) {
						ans.add(new BPAction(e));
					}
				}
			}
		}

		return ans;
	}

	@Override
	public BPState getResult(BPState state, BPAction action) {
		return action.apply(state);
	}

	@Override
	public boolean isTerminal(BPState state) {
		return term.isTerminal();
	}

	@Override
	public double getUtility(BPState state, BPPlayer player) {
		return util.getUtility();
	}

}
