package gwbot.result;

import gwbot.car.Car;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class Result {

	private final Car car;

	public Result(Car car) {
		this.car = car;
	}

	public String getName() {
		return car.getName();
	}

	public String getColor() {
		return car.getColor();
	}
}
