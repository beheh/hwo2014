package gwbot.message;

import gwbot.race.Race;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class GameInitMessage extends Message {

	private final Race race;

	public GameInitMessage(final Race race) {
		this.race = race;
	}

	@Override
	protected String msgType() {
		return "gameInit";
	}

	/**
	 * Returns the current race the car is taking part in.
	 * @return the current race
	 */
	public Race getRace() {
		return race;
	}

}
