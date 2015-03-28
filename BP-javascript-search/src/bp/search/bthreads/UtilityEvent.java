package bp.search.bthreads;

import bp.BEvent;

public class UtilityEvent extends BEvent {

	protected double value = 0;

	public void setValue(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

}
