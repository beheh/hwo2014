package gwbot.bot;

import gwbot.message.CarPositionsMessage;
import gwbot.message.GameInitMessage;
import gwbot.message.JoinMessage;
import gwbot.message.TurboAvailableMessage;
import gwbot.message.YourCarMessage;
import java.util.List;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public interface BotInterface {

	public void onJoinMessage(JoinMessage joinMessage);

	public void onYourCarMessage(YourCarMessage yourCarMessage);

	public void onGameInitMessage(GameInitMessage gameInitMessage);

	public void onCarPositions(List<CarPositionsMessage> carPositionsMessage);

	public void onTurboAvailable(TurboAvailableMessage turboAvailableMessage);
}
