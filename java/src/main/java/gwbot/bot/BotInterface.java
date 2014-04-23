package gwbot.bot;

import gwbot.message.CarPositionsMessage;
import gwbot.message.GameEndMessage;
import gwbot.message.GameInitMessage;
import gwbot.message.GameStartMessage;
import gwbot.message.JoinMessage;
import gwbot.message.TurboAvailableMessage;
import gwbot.message.YourCarMessage;
import java.util.List;

/**
 *
 * @author Benedict Etzel <developer@beheh.de>
 */
public interface BotInterface {

	public void onYourCarMessage(YourCarMessage yourCarMessage);

	public void onGameInitMessage(GameInitMessage gameInitMessage);

	public void onGameStartMessage(GameStartMessage gameStartMessage);

	public void onJoinMessage(JoinMessage joinMessage);

	public void onCarPositions(List<CarPositionsMessage> carPositionsMessage);

	public void onTurboAvailable(TurboAvailableMessage turboAvailableMessage);

	public void onGameEndMessage(GameEndMessage gameEndMessage);

}
