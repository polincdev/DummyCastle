package pl.polinc.dummycastle.coding;

/**
 * Exception thrown if an attempt is made to decode invalid data, or some other
 * failure occurs.
 * 
 * @hide This class is not part of the Android public SDK API
 */
public class DecoderException extends IllegalStateException {
	private Throwable cause;

	DecoderException(String msg, Throwable cause) {
		super(msg);

		this.cause = cause;
	}

	public Throwable getCause() {
		return cause;
	}
}