package gwbot.message;

import gwbot.message.data.BotId;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class JoinRaceMessage extends Message {

	private final BotId botId;
	private final Integer carCount;
	private final String trackName;
	private final String password;

	public JoinRaceMessage(BotId botId, Integer carCount, String trackName, String password) {
		this.botId = botId;
		this.carCount = carCount;
		this.trackName = trackName;
		this.password = password;
	}

	@Override
	protected String msgType() {
		return "joinRace";
	}
}