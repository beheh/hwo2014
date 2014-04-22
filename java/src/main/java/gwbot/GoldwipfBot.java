package gwbot;

import gwbot.bot.GenericBot;
import gwbot.message.CarPositionsMessage;
import gwbot.message.GameInitMessage;
import gwbot.message.JoinMessage;
import gwbot.message.ThrottleMessage;
import gwbot.message.YourCarMessage;
import java.util.Collection;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public class GoldwipfBot extends GenericBot {

	public GoldwipfBot(Main main) {
		super(main);
	}

	@Override
	public void onGameInitMessage(GameInitMessage gameInitMessage) {
	}

	@Override
	public void onJoinMessage(JoinMessage joinMessage) {
	}

	@Override
	public void onYourCarMessage(YourCarMessage yourCarMessage) {
	}

	@Override
	public void onCarPositions(Collection<CarPositionsMessage> carPositionsMessage) {
		send(new ThrottleMessage(0.6));
	}

}
