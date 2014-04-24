package gwbot.message;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class YourCarMessage extends AbstractCarMessage {

	public YourCarMessage(final String name, final String color) {
		super(name, color);
	}

	@Override
	protected String msgType() {
		return "yourCar";
	}

}
