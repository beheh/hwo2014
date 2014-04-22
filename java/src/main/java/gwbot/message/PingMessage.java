package gwbot.message;

/**
 *
 * @author Goldwipf <goldwipf@beheh.de>
 */
public class PingMessage extends Message {

	@Override
	protected String msgType() {
		return "ping";
	}
}
