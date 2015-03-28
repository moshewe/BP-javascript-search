package bp.search.adversarial;

import java.util.List;

import bp.search.BPAction;
import bp.search.BPState;

import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.framework.Metrics;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 169.<br>
 * 
 * <pre>
 * <code>
 * function MINIMAX-DECISION(state) returns an action
 *   return argmax_[a in ACTIONS(s)] MIN-VALUE(RESULT(state, a))
 * 
 * function MAX-VALUE(state) returns a utility value
 *   if TERMINAL-TEST(state) then return UTILITY(state)
 *   v = -infinity
 *   for each a in ACTIONS(state) do
 *     v = MAX(v, MIN-VALUE(RESULT(s, a)))
 *   return v
 * 
 * function MIN-VALUE(state) returns a utility value
 *   if TERMINAL-TEST(state) then return UTILITY(state)
 *     v = infinity
 *     for each a in ACTIONS(state) do
 *       v  = MIN(v, MAX-VALUE(RESULT(s, a)))
 *   return v
 * </code>
 * </pre>
 * 
 * Figure 5.3 An algorithm for calculating minimax decisions. It returns the
 * action corresponding to the best possible move, that is, the move that leads
 * to the outcome with the best utility, under the assumption that the opponent
 * plays to minimize utility. The functions MAX-VALUE and MIN-VALUE go through
 * the whole game tree, all the way to the leaves, to determine the backed-up
 * value of a state. The notation argmax_[a in S] f(a) computes the element a of
 * set S that has the maximum value of f(a).
 * 
 * 
 * @author Ruediger Lunde
 * 
 */
public class BPMinimaxSearch implements AdversarialSearch<BPState, BPAction> {

	private BPGame game;
	private int expandedNodes;

	public BPMinimaxSearch(BPGame game) {
		this.game = game;
	}

	@Override
	public BPAction makeDecision(BPState state) {
		expandedNodes = 0;
		BPAction result = null;
		double resultValue = Double.NEGATIVE_INFINITY;
		BPPlayer player = game.getPlayer(state);
		List<BPAction> actions = game.getActions(state);
		System.out.println("actions possible: " + actions);
		for (BPAction action : actions) {
			System.out.println("EXPLORING branch " + action);
			double value = minValue(game.getResult(state, action), player);
			if (value > resultValue) {
				result = action;
				resultValue = value;
			}
			System.out.println("FINISHED EXPLORING branch " + action);
			state.restore();
		}
		return result;
	}

	public double maxValue(BPState state, BPPlayer player) { // returns an
																// utility
																// value
		expandedNodes++;
		if (game.isTerminal(state))
			return game.getUtility(state, player);
		double value = Double.NEGATIVE_INFINITY;
		List<BPAction> actions = game.getActions(state);
		System.out.println("actions possible: " + actions);
		for (BPAction action : actions) {
			System.out.println("EXPLORING branch " + action);
			value = Math.max(value,
					minValue(game.getResult(state, action), player));
			System.out.println("FINISHED EXPLORING branch " + action);
			state.restore();
		}
		return value;
	}

	public double minValue(BPState state, BPPlayer player) { // returns an
																// utility
																// value
		expandedNodes++;
		if (game.isTerminal(state))
			return game.getUtility(state, player);
		double value = Double.POSITIVE_INFINITY;
		List<BPAction> actions = game.getActions(state);
		System.out.println("actions possible: " + actions);
		for (BPAction action : actions) {
			System.out.println("EXPLORING branch " + action);
			value = Math.min(value,
					maxValue(game.getResult(state, action), player));
			System.out.println("FINISHED EXPLORING branch " + action);
			state.restore();
		}
		if (value == Double.POSITIVE_INFINITY) {
			System.out.println();
		}
		return value;
	}

	@Override
	public Metrics getMetrics() {
		Metrics result = new Metrics();
		result.set("expandedNodes", expandedNodes);
		return result;
	}
}
