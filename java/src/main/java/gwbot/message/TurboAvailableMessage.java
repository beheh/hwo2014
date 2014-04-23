package gwbot.message;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class TurboAvailableMessage extends Message {

	private final double turboDurationMilliseconds;
	private final int turboDurationTicks;
	private final double turboFactor;

	public TurboAvailableMessage(double turboDurationMilliseconds, int turboDurationTicks, double turboFactor) {
		this.turboDurationMilliseconds = turboDurationMilliseconds;
		this.turboDurationTicks = turboDurationTicks;
		this.turboFactor = turboFactor;
	}

	@Override
	protected String msgType() {
		return "turboAvailable";
	}

	public double getTurboDurationMilliseconds() {
		return turboDurationMilliseconds;
	}

	public int getTurboDurationTicks() {
		return turboDurationTicks;
	}

	public double getTurboFactor() {
		return turboFactor;
	}

}
