package gwbot.message;

import gwbot.car.Car;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class LapFinishedMessage extends Message {

	private final Car car;

	public LapFinishedMessage(Car car) {
		this.car = car;
	}

	public Car getCar() {
		return car;
	}

	@Override
	protected String msgType() {
		return "lapFinished";
	}

}
