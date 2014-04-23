package gwbot.message;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class SwitchLaneMessage extends Message {

	public enum Direction {LEFT, RIGHT};
	private final Direction direction;

	/**
	 * Creates a switch lane message. The car will switch at the next possible intersection
	 *
	 * @param direction the direction to switch
	 */
	public SwitchLaneMessage(Direction direction) {
		this.direction = direction;
	}

	private String capitalize(String string) {
		return Character.toUpperCase(string.charAt(0)) + string.substring(1);
	}

	@Override
	protected Object msgData() {;
		return capitalize(direction.toString().toLowerCase());
	}

	@Override
	protected String msgType() {
		return "switchLane";
	}

}
