package bp.exceptions;

import bp.BThread;

@SuppressWarnings("serial")
public class BPJDuplicateBthreadException extends BPJException {

	BThread existing;
	
	public BPJDuplicateBthreadException(BThread existing) {
		this.existing = existing;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BPJDuplicateBthreadException [name=" + existing +"]";
	}
	
	

}
