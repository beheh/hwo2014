package gwbot.message;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class CarPositionsMessage extends Message {

	@Override
	protected String msgType() {
		return "carPositions";
	}
}
