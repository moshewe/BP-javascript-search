package bp.search;

import bp.BEvent;
import bp.BProgram;
import bp.BThread;

import java.util.*;

/**
 * A class for capturing a state of a be-_program.
 */
public class BPState {

    public BProgram getProgram() {
        return _program;
    }

    /**
     * The be-_program whose state is captured
     */
    public final BProgram _program;
    /**
     * The states of the relevant be-threads
     */
    private List<BTState> _btstates;
    protected Deque<BEvent> eventLog;

    public BPState(BPState other) {
        this._program = other._program;
        eventLog = new LinkedList<>(other.eventLog);
        _btstates = new ArrayList<>();
        for (BThread bt : _program.getBThreads()) {
            getBTstates().add(new BTState(bt));
        }
    }

    public BPState(BProgram bp) {
        this._program = bp;
        eventLog = new LinkedList<>(bp.eventLog);
        _btstates = new ArrayList<>();
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

    public void restore() {
        _program.eventLog = new LinkedList<>(eventLog);
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
