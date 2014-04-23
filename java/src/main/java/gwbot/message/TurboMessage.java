package gwbot.message;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class TurboMessage extends Message {

	private final String turboMessage;

	/**
	 * Creates a turbo message. The turbo will last the duration specified in the last TurboAvailableMessage.
	 * @param turboMessage a turbo message to send (can by anything)
	 */
	public TurboMessage(String turboMessage) {
		this.turboMessage = turboMessage;
	}

	@Override
	protected Object msgData() {
		return turboMessage;
	}

	@Override
	protected String msgType() {
		return "turbo";
	}

}
