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

	/**
	 * Returns the name of the current car.
	 *
	 * @return the car name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the color of the current car.
	 *
	 * @return the car color
	 */
	public String getColor() {
		return color;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (getClass() != o.getClass()) {
			return false;
		}
		Car car = (Car) o;
		if (!getName().equals(car.getName())) {
			return false;
		}
		if (!getColor().equals(car.getColor())) {
			return false;
		}
		return true;
	}
}
