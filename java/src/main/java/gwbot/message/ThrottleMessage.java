package gwbot.message;

/**
 *
 * @author Goldwipf <goldwipf@beheh.de>
 */
public class ThrottleMessage extends Message {

	private final double value;

	/**
	 * Creates a throttle message. The car will keep the throttle until the next throttle message is sent.
	 *
	 * @param value the throttle value between 0.0 and 1.0
	 */
	public ThrottleMessage(double value) {
		this.value = value;
	}

	@Override
	protected Object msgData() {
		return value;
	}

	@Override
	protected String msgType() {
		return "throttle";
	}
}
