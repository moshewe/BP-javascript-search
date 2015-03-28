package bp.search;

import java.io.IOException;
import java.io.Serializable;

import aima.core.agent.Action;
import aima.core.search.framework.ResultFunction;

public class BPResultFunction implements ResultFunction, Serializable {

	@Override
	public Object result(Object s, Action a) {
		try {
			return result((BPState) s, (BPAction) a);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public BPState result(BPState bps, final BPAction a)
			throws ClassNotFoundException, IOException {
		return a.apply(bps);
	}
}
