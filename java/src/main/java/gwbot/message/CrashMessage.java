package gwbot.message;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class CrashMessage extends AbstractCarMessage {

	public CrashMessage(final String name, final String color) {
		super(name, color);
	}

	@Override
	protected String msgType() {
		return "crash";
	}

}
