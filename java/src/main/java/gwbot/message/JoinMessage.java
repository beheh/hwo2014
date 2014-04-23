package gwbot.message;

/**
 *
 * @author Goldwipf <goldwipf@beheh.de>
 */
public class JoinMessage extends Message {

	private final String name;
	private final String key;

	public JoinMessage(final String name, final String key) {
		this.name = name;
		this.key = key;
	}

	@Override
	protected String msgType() {
		return "join";
	}
}
