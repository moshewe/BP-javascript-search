package bp.search;

import bp.BThread;
import aima.core.search.framework.HeuristicFunction;

public abstract class BPHeuristicFunction implements HeuristicFunction {

	public abstract double h(BPState state);

	protected double defaultH(BPState state) {
		double hval = 0;
		for (BThread bt : state.getBp().getBThreads()) {
			hval += h(bt);
		}
		return hval;
	}

	public double h(BThread bt) {
		return 0;
	}

	// protected double defaultH(BPState state) {
	// double hval = 0;
	// for (BTState bts : state.getBtstates()) {
	// hval += h(bts);
	// }
	// return hval;
	// }
	//

	@Override
	public double h(Object state) {
		return 0;
	}
}
