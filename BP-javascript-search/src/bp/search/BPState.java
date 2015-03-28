package bp.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bp.BProgram;
import bp.BThread;
import bp.BEvent;

/**
 * 
 * A class for capturing a state of a be-program.
 * 
 */
public class BPState {

	/**
	 * The be-program whose state is captured
	 */
	public final BProgram bp;
	private BEvent lastEvent;
	/** The states of the relevant be-threads */
	private List<BTState> btstates = new ArrayList<BTState>();

	public BPState(BPState other) {
		this.bp = other.bp;
		this.lastEvent = other.lastEvent;
		for (BTState bts : other.getBTstates()) {
			this.btstates.add(bts.copy());
		}
	}

	public BPState(BProgram bp) throws IOException, ClassNotFoundException {
		this.bp = bp;
		this.lastEvent = bp.getLastEvent();
		for (BThread bt : bp.getBThreads()) {
			getBTstates().add(new BTState(bt));
		}
	}

	public BPState(BProgram bp, List<BTState> btstates) {
		this.bp = bp;
		setBTstates(btstates);
	}

	/** States are identified by the constituent be-thread states */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		BPState other = (BPState) obj;
		if (getBTstates() == null) {
			if (other.getBTstates() != null)
				return false;
		} else if (!getBTstates().equals(other.getBTstates()))
			return false;
		return true;
	}

	public List<BTState> getBTstates() {
		return btstates;
	}

	private void setBTstates(List<BTState> btstates) {
		this.btstates = btstates;
	}

	public BProgram getBp() {
		return bp;
	}

	public void restore() {
		bp.setLastEvent(lastEvent);
		for (BTState bts : btstates) {
			bts.restore();
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (BTState bts : btstates) {
			sb.append(bts);
		}
		return sb.toString();
	}

	public BPState copy() {
		return new BPState(this);
	}

}
