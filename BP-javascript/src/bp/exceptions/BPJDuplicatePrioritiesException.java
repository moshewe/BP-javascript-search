package bp.exceptions;

import bp.BThread;

@SuppressWarnings("serial")
public class BPJDuplicatePrioritiesException extends BPJException {

	BThread existing;
	BThread newBT;
	
	public BPJDuplicatePrioritiesException(BThread existing, BThread newBT) {
		this.existing = existing;
		this.newBT = newBT;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BPJDuplicatePrioritiesException [existing=" + existing + " new=" + newBT +"]";
	}
	
	

}
