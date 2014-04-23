package gwbot.car;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class Car {

	private final String name;
	private final String color;

	public Car(String name, String color) {
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

}
