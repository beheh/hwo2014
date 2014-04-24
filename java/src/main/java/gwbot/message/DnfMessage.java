package gwbot.message;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class DnfMessage extends AbstractCarMessage {

	public DnfMessage(final String name, final String color) {
		super(name, color);
	}

	@Override
	protected String msgType() {
		return "dnf";
	}

}
