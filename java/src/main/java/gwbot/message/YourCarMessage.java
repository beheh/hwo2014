package gwbot.message;

import gwbot.car.Car;

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
	 * @deprecated use getCar() instead
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the color of your own vehicle.
	 *
	 * @return the vehicle color
	 * @deprecated use getCar() instead
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Returns your current vehicle.
	 *
	 * @return the vehicle
	 */
	public Car getCar() {
		return new Car(name, color);
	}

}
