package gwbot.message;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class TurboMessage extends Message {

	private final String turboMessage;

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
