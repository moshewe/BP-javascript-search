package bp.exceptions;

public class VerificationHotStateException extends RuntimeException {
	String description;

	public VerificationHotStateException(String description) {
		super();
		this.description = description;
	}

	@Override
	public String toString() {
		return "VerificationHotStateException" + description ;
	}

}
