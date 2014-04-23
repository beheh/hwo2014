package gwbot.message;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class GameEndMessage extends Message {

	public GameEndMessage() {
	}

	@Override
	protected String msgType() {
		return "gameEnd";
	}

}
