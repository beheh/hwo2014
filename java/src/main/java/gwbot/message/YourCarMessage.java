package gwbot.message;

/**
 *
 * @author benedict
 */
public class YourCarMessage extends Message {

	private final String name;
	private final String color;

	public YourCarMessage(final String name, final String color) {
		this.name = name;
		this.color = color;
	}

	@Override
	protected String msgType() {
		return "yourCar";
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

}
