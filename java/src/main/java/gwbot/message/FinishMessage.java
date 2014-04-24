package gwbot.message;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class FinishMessage extends AbstractCarMessage {

	public FinishMessage(final String name, final String color) {
		super(name, color);
	}

	@Override
	protected String msgType() {
		return "finish";
	}

}
