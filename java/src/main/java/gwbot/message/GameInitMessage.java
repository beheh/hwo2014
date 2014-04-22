package gwbot.message;

import gwbot.race.Race;

/**
 *
 * @author benedict
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

	public Race getRace() {
		return race;
	}

}
