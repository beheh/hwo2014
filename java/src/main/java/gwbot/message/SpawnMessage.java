package gwbot.message;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class SpawnMessage extends AbstractCarMessage {

	public SpawnMessage(final String name, final String color) {
		super(name, color);
	}

	@Override
	protected String msgType() {
		return "spawn";
	}

}
