package gwbot.message;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class GameStartMessage extends Message {

	public GameStartMessage() {
	}

	@Override
	protected String msgType() {
		return "gameStart";
	}

}
