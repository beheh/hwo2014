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

	/**
	 * Returns the name of your own vehicle, usually your team name.
	 *
	 * @return the vehicle name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the color of your own vehicle.
	 *
	 * @return the vehicle color
	 */
	public String getColor() {
		return color;
	}

}
