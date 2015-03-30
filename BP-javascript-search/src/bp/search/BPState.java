package bp.search;

import bp.BProgram;
import bp.BThread;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for capturing a state of a be-program.
 */
public class BPState {

    /**
     * The be-program whose state is captured
     */
    public final BProgram bp;
    /**
     * The states of the relevant be-threads
     */
    private List<BTState> _btstates;

    public BPState(BPState other) {
        this.bp = other.bp;
        _btstates = new ArrayList<BTState>();
        for (BThread bt : bp.getBThreads()) {
            getBTstates().add(new BTState(bt));
        }
    }

    public BPState(BProgram bp) {
        this.bp = bp;
        _btstates = new ArrayList<BTState>();
        for (BThread bt : bp.getBThreads()) {
            getBTstates().add(new BTState(bt));
        }
    }

    /**
     * States are identified by the constituent be-thread states
     */
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
        return _btstates;
    }

    public BProgram getBp() {
        return bp;
    }

    public void restore() {
        for (BTState bts : _btstates) {
            bts.restore();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (BTState bts : _btstates) {
            sb.append(bts);
        }
        return sb.toString();
    }

    public BPState copy() {
        return new BPState(this);
    }

}
